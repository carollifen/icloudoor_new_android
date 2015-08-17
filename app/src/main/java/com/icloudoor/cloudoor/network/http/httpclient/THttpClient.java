package com.icloudoor.cloudoor.network.http.httpclient;

import android.text.TextUtils;

import com.icloudoor.cloudoor.network.http.THttpHeader;
import com.icloudoor.cloudoor.network.http.THttpMethod;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.http.THttpResponse;

import junit.framework.Assert;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * Created by Derrick Guan on 7/22/15.
 */

public class THttpClient implements THttp {

    static SchemeRegistry mSchReg;

    static { // 静态变量初始化
        mSchReg = new SchemeRegistry();
        mSchReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        mSchReg.register(new Scheme("https", TrustAllSSLSocketFactory.getDefault(), 443));
    }

    static CookieStore mCookieStore = new BasicCookieStore();
    boolean mIsClosed;

    private HttpClient buildHttpClient() {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
        HttpConnectionParams.setSoTimeout(params, 60 * 1000);
        HttpConnectionParams.setSocketBufferSize(params, 8 * 1024);
        HttpProtocolParams.setUseExpectContinue(params, false);

        ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
                params, mSchReg);
        DefaultHttpClient httpClient = new DefaultHttpClient(conMgr, params);

        httpClient.getParams()
                .setParameter(ClientPNames.HANDLE_REDIRECTS, false);

        httpClient.setCookieStore(mCookieStore);

        return httpClient;
    }

    HttpParams createParams() {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        return params;
    }

    HttpRequestBase createHttpRequest(String url, THttpRequest request) {
        HttpRequestBase requestBase = null;
        if (request.getMethod() == THttpMethod.GET) {
            requestBase = new HttpGet();
        } else if (request.getMethod() == THttpMethod.HEAD) {
            requestBase = new HttpHead();
        } else if (request.getMethod() == THttpMethod.POST) {
            HttpPost post = new HttpPost();

            HttpEntity entity = request.getHttpEntity();
            if (entity != null) {
                post.setEntity(entity);
            }
            requestBase = post;
        } else if (request.getMethod() == THttpMethod.PUT) {
            HttpPut put = new HttpPut();
            HttpEntity entity = request.getHttpEntity();

            if (entity != null) {
                put.setEntity(entity);
            }
            requestBase = put;
        } else if (request.getMethod() == THttpMethod.OPTIONS) {
            requestBase = new HttpOptions();
        } else if (request.getMethod() == THttpMethod.DELETE) {
            requestBase = new HttpDelete();
        } else {
            Assert.assertTrue(false);
        }

        URI uri = URI.create(url);
        requestBase.setURI(uri);

        requestBase.setParams(createParams());

        List<THttpHeader> list = request.getRequestHeaders();
        if (list != null) {
            for (THttpHeader header : list) {
                requestBase.addHeader(header.getKey(), header.getValue());
            }
        }

        return requestBase;
    }


    @Override
    public THttpResponse executeRequest(THttpRequest request)
            throws IOException {
        THttpClientResponse response = null;

        if (!mIsClosed) {
            HttpClient httpClient = buildHttpClient();

            try {
                HttpRequestBase httpRequest = createHttpRequest(request.getUrl(), request);
                HttpResponse httpResponse = httpClient.execute(httpRequest);

                int responseCode = httpResponse.getStatusLine().getStatusCode();
                int maxCount = 3;

                while (responseCode == 302 && --maxCount >= 0) {
                    Header header = httpResponse.getLastHeader("Location");
                    String location = null;
                    if (header != null) {
                        location = header.getValue();
                    }

                    if (!TextUtils.isEmpty(location)) {
                        location = request.onRedirectUrl(location, request);

                        if (!TextUtils.isEmpty(location)) {
                            if (!request.isCancel()) {
                                httpRequest = createHttpRequest(location,
                                        request);
                                httpResponse = httpClient.execute(httpRequest);

                                responseCode = httpResponse.getStatusLine()
                                        .getStatusCode();
                                continue;
                            }
                        }
                    }
                    break;
                }

                response = new THttpClientResponse(httpClient, httpRequest,
                        httpResponse);

            } catch (Exception e) {
                closeHttpClient(httpClient);

                throw new IOException(e.fillInStackTrace());
            }
        }

        return response;
    }

    private void closeHttpClient(HttpClient httpClient) {
        try {
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        mIsClosed = true;
    }

}

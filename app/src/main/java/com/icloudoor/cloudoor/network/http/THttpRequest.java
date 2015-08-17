package com.icloudoor.cloudoor.network.http;

import com.icloudoor.cloudoor.network.frame.Priority;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Derrick Guan (guanyonghui@iorcas.com)
 */

public class THttpRequest implements Priority, Comparable<THttpRequest> {

    private static final int STATE_CANCELED = 0x01;
    private static int nextRequestId = 0;
    int mRequestId;
    THttpMethod mMethod;
    String mUrl;
    HttpEntity mHttpEntity; // POST DELETE PUT
    HttpCallBack mHttpCallBack;
    int mPriority;
    int mGroupId;
    List<THttpHeader> mParameters;
    List<THttpHeader> mHeaders;
    String mTmpUrl;
    String mUrlLocalParam;
    int mTransacionType;
    int mState;

    public THttpRequest(String url) {
        this(url, THttpMethod.POST);
    }

    public THttpRequest(String url, THttpMethod type) {
        if (url == null || url.length() == 0) {
            throw new IllegalArgumentException("url");
        }

        mUrl = url;
        mTmpUrl = url;
        mMethod = type;
        mRequestId = getNextRequestId();
    }

    public synchronized static int getNextRequestId() {
        if (nextRequestId >= Short.MAX_VALUE) {
            nextRequestId = 0;
        }
        return ++nextRequestId;
    }

    private void setState(int state, boolean value) {
        if (value) {
            mState |= state;
        } else {
            mState &= ~state;
        }
    }

    private boolean isState(int state) {
        return (mState & state) != 0;
    }

    public int getRequestID() {
        return mRequestId;
    }

    public void addParameter(String key, String value) {
        if (key == null || value == null) {
            return;
        }
        if (mParameters == null) {
            mParameters = new LinkedList<THttpHeader>();
        }

        mParameters.add(new THttpHeader(key, value));
    }

    protected List<THttpHeader> getHttpParamters() {
        return mParameters;
    }

    public String onRedirectUrl(String url, THttpRequest request) {
        return url;
    }

    protected String getUrlLocalParam() {
        if (mUrlLocalParam == null) {
            return "";
        }
        return mUrlLocalParam;
    }

    public void setUrlLocalParam(String param) {
        mUrlLocalParam = param;
    }

    @Override
    public int getPriority() {
        return mPriority;
    }

    @Override
    public void setPriority(int priority) {
        mPriority = priority;
    }

    @Override
    public int getGroupId() {
        return mGroupId;
    }

    protected void setGroupId(int groupId) {
        mGroupId = groupId;
    }

    public HttpCallBack getHttpCallBack() {
        return mHttpCallBack;
    }

    public void setHttpCallBack(HttpCallBack callBack) {
        mHttpCallBack = callBack;
    }

    public String getUrl() {
        return mTmpUrl;
    }

    public THttpMethod getMethod() {
        return mMethod;
    }

    public String getMethodName() {
        String method = "GET";
        switch (mMethod) {
            case GET:
                break;
            case POST:
                method = "POST";
                break;
            case DELETE:
                method = "DELETE";
                break;
            case HEAD:
                method = "HEAD";
                break;
            case OPTIONS:
                method = "OPTIONS";
                break;
            case PUT:
                method = "PUT";
                break;
        }
        return method;
    }

    public HttpEntity getHttpEntity() {
        if (mHttpEntity == null && mMethod == THttpMethod.POST
                && mParameters != null && mParameters.size() > 0) {
            try {
                return new UrlEncodedFormEntity(mParameters, "utf-8");
            } catch (Exception e) {
            }
        }
        return mHttpEntity;
    }

    public void setHttpEntity(HttpEntity entity) {
        mHttpEntity = entity;
    }

    public void addHeader(String key, String value) {
        if (key == null || key.length() == 0
                || value == null) {
            return;
        }

        if (mHeaders == null) {
            mHeaders = new LinkedList<THttpHeader>();
        }

        mHeaders.add(new THttpHeader(key, value));
    }

    public List<THttpHeader> getRequestHeaders() {
        return mHeaders;
    }

    public void doCancel() {
        setState(STATE_CANCELED, true);
    }

    public boolean isCancel() {
        return isState(STATE_CANCELED);
    }

    public void doBefore() { // 在getUrl之前执行
        if (mParameters != null && mUrl != null) {
            if (mParameters.size() > 0) {
                StringBuilder builder = new StringBuilder(mUrl);
                if (mUrl.indexOf('?') < 0) {
                    builder.append('?');
                } else {
                    builder.append('&');
                }

                builder.append(URLEncodedUtils.format(mParameters, "utf-8"));

                mTmpUrl = builder.toString();
            } else {
                mTmpUrl = mUrl;
            }
        }
    }

    public int getTryTimes() {
        return 2;
    }

    @Override
    public int compareTo(THttpRequest another) {
        int ret = -1;
        if (mPriority < another.mPriority) {
            ret = -1;
        } else if (mPriority > another.mPriority) {
            ret = 1;
        } else {
            ret = 0;
        }

        return ret;
    }

}

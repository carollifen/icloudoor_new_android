package com.icloudoor.cloudoor.network.http.httpclient;

import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.http.THttpResponse;

import java.io.IOException;

/**
 * Created by Derrick Guan on 7/22/15.
 */

public interface THttp {

    public static final String LOCAL_PARAM_TAG = "%local";

    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String GZIP_DEFLATE = "gzip, deflate";

    /**
     * 执行request请求
     *
     * @param request
     * @return
     */
    public THttpResponse executeRequest(THttpRequest request) throws IOException;

    /**
     * 关闭释放资源
     */
    public void close();
}

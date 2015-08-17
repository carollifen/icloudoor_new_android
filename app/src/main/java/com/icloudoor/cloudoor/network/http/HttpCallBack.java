package com.icloudoor.cloudoor.network.http;

import java.io.IOException;

/**
 * Created by Derrick Guan on 7/22/15.
 */

public interface HttpCallBack {

    /**
     * ****************************************************************
     */

    public Object onPreError(THttpRequest request, Object data) throws IOException;

    public Object onPreReceived(THttpRequest request, THttpResponse response) throws IOException;

    /********************************************************************/

    /**
     * ****************************************************************
     */

    public boolean onError(THttpRequest request, int errCode, Object data);

    public boolean onReceived(THttpRequest request, int code, Object data);

    /********************************************************************/
}

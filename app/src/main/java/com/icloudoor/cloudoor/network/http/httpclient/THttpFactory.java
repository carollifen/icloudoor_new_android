package com.icloudoor.cloudoor.network.http.httpclient;

/**
 * Created by Derrick Guan on 7/22/15.
 */

public class THttpFactory {

    public static THttp createHttp() {
        return new THttpClient();
    }

}

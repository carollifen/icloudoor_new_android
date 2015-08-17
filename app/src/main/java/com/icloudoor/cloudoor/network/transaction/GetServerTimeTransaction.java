package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

/**
 * 获取服务器时间事务
 * Created by Derrick Guan on 7/30/15.
 */
public class GetServerTimeTransaction extends CloudoorBaseTransaction {

    public GetServerTimeTransaction() {
        super(TRANSACTION_GET_SERVER_TIME);
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        if (data != null && data instanceof JsonElement) {
            long serverTime = ((JsonElement) data).getAsLong();
            notifySuccess(serverTime);
        } else {
            notifyDataParseError();
        }
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_UTILS_MODULE, GET_SERVER_TIME);
        return new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
    }
}

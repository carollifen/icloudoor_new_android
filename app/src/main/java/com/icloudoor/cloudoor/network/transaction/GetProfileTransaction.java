package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.bean.meta.UserBasicInfo;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

/**
 * 获取用户资料事务
 * Created by Derrick Guan on 7/24/15.
 */
public class GetProfileTransaction extends CloudoorBaseTransaction {

    public GetProfileTransaction() {
        super(TRANSACTION_GET_PROFILE);
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        UserBasicInfo userBasicInfo = null;
        if (data != null) {
            Gson gson = new Gson();
            JsonElement json = (JsonElement) data;
            userBasicInfo = gson.fromJson(json, UserBasicInfo.class);
        }

        if (userBasicInfo != null) {
            notifySuccess(userBasicInfo);
        } else {
            notifyDataParseError();
        }
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_MANAGEMENT_MODULE, GET_PROFILE);
        return new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
    }
}

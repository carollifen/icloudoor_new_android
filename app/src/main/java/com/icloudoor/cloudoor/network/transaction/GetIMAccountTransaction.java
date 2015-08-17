package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.bean.meta.HXAccount;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;

/**
 * 获取用户的IM登录账号事务
 * Created by Derrick Guan on 8/1/15.
 */
public class GetIMAccountTransaction extends CloudoorBaseTransaction {

    public GetIMAccountTransaction() {
        super(TRANSACTION_GET_IM_ACCOUNT);
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        HXAccount account = null;
        if (data != null && data instanceof JsonElement) {
            Gson gson = new Gson();
            JsonElement json = (JsonElement) data;
            account = gson.fromJson(json, HXAccount.class);
        }
        if (account != null) {
            notifySuccess(account);
        } else {
            notifyDataParseError();
        }
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_IM_MODULE, GET_ACCOUNT);
        return new CloudoorHttpRequest(url);
    }
}

package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.bean.meta.Config;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

/**
 * 获取用户配置事务
 * Created by Derrick Guan on 7/29/15.
 */
public class ConfigDefaultTransaction extends CloudoorBaseTransaction {

    public ConfigDefaultTransaction() {
        super(TRANSACTION_CONFIG_DEFAULT);
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        Config config = null;
        if (data != null && data instanceof JsonElement) {
            Gson gson = new Gson();
            config = gson.fromJson((JsonElement) data, Config.class);
        }
        if (config != null) {
            notifySuccess(config);
        } else {
            notifyDataParseError();
        }
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_CONFIG_MODULE, DEFAULT);
        return new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
    }
}

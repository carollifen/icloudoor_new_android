package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.bean.DownloadDoorBean;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import org.apache.http.entity.ByteArrayEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 下载门锁事务
 * Created by Derrick Guan on 7/28/15.
 */
public class DownloadDoorTransaction extends CloudoorBaseTransaction {

    public DownloadDoorTransaction() {
        super(TRANSACTION_DOWNLOAD_DOOR);
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        DownloadDoorBean bean = null;
        if (data != null && data instanceof JsonElement) {
            Gson gson = new Gson();
            JsonElement json = (JsonElement) data;
            bean = gson.fromJson(json, DownloadDoorBean.class);
        }
        if (bean != null) {
            notifySuccess(bean);
        } else {
            notifyDataParseError();
        }
    }

    @Override
    public THttpRequest createRequest() {
        String appId = UUID.randomUUID().toString().replaceAll("-", "");
        String url = getRequestUrl(USER_DOOR_MODULE, DOWNLOAD);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
        Map<String, String> params = new HashMap<String, String>();
        params.put("appId", appId);
        request.setHttpEntity(new ByteArrayEntity(request.getBody(params)));
        return request;
    }
}

package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import org.apache.http.entity.ByteArrayEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证密码事务
 * Created by Derrick Guan on 7/27/15.
 */
public class VerifyPasswordTransaction extends CloudoorBaseTransaction {

    private String mobile;
    private String password;

    public VerifyPasswordTransaction(String mobile, String password) {
        super(TRANSACTION_VERIFY_PASSWORD);
        this.mobile = mobile;
        this.password = password;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        if (data != null && data instanceof JsonElement) {
            boolean ret = ((JsonElement) data).getAsBoolean();
            notifySuccess(ret);
        } else {
            notifyDataParseError();
        }
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_MANAGEMENT_MODULE, VERIFY_PASSWORD);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        params.put("password", password);
        request.setHttpEntity(new ByteArrayEntity(request.getBody(params)));
        return request;
    }
}

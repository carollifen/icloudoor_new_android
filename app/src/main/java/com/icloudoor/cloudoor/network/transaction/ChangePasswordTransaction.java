package com.icloudoor.cloudoor.network.transaction;

import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import org.apache.http.entity.ByteArrayEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改密码事务
 * Created by Derrick Guan on 7/24/15.
 */
public class ChangePasswordTransaction extends CloudoorBaseTransaction {

    private String oldPassword;
    private String newPassword;

    public ChangePasswordTransaction(String oldPassword, String newPassword) {
        super(TRANSACTION_CHANGE_PASSWORD);
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        // 直接notifySuccess
        notifySuccess(null);
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_MANAGEMENT_MODULE, CHANGE_PASSWORD);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);

        Map<String, String> params = new HashMap<String, String>();
        params.put("oldPassword", oldPassword);
        params.put("newPassword", newPassword);
        request.setHttpEntity(new ByteArrayEntity(request.getBody(params)));
        return request;
    }
}

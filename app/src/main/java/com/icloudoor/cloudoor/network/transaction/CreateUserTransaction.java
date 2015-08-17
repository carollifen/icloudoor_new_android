package com.icloudoor.cloudoor.network.transaction;

import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import org.apache.http.entity.ByteArrayEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Transaction中暂不验证密码强度，以后可以将相关逻辑放到Transaction中处理
 * Created by Derrick Guan on 7/23/15.
 */
public class CreateUserTransaction extends CloudoorBaseTransaction {

    private String password;

    public CreateUserTransaction(String password) {
        super(TRANSACTION_CREATE_USER);
        this.password = password;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        // 后端没有返回具体的data，直接notifySuccess
        notifySuccess(null);
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_MANAGEMENT_MODULE, CREATE_USER);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
        // 组装参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("password", password);
        request.setHttpEntity(new ByteArrayEntity(request.getBody(params)));
        return request;
    }
}

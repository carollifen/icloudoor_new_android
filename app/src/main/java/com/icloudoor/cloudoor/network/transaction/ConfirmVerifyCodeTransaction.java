package com.icloudoor.cloudoor.network.transaction;

import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import org.apache.http.entity.ByteArrayEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 确认验证码事务
 * Created by Derrick Guan on 7/23/15.
 */
public class ConfirmVerifyCodeTransaction extends CloudoorBaseTransaction {

    private String verifyCode;

    public ConfirmVerifyCodeTransaction(String verifyCode) {
        super(TRANSACTION_CONFIRM_VERIFY_CODE);
        this.verifyCode = verifyCode;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        // 没有具体的返回数据，直接notifySuccess
        notifySuccess(null);
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_MANAGEMENT_MODULE, CONFIRM_VERIFY_CODE);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
        // 组装参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("verifyCode", verifyCode);
        request.setHttpEntity(new ByteArrayEntity(request.getBody(params)));
        return request;
    }
}

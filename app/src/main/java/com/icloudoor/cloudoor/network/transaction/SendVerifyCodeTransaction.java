package com.icloudoor.cloudoor.network.transaction;

import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import org.apache.http.entity.ByteArrayEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送验证码事务
 * Created by Derrick Guan on 7/23/15.
 */
public class SendVerifyCodeTransaction extends CloudoorBaseTransaction {

    private String mobile;

    public SendVerifyCodeTransaction(String mobile) {
        super(TRANSACTION_SEND_VERIFY_CODE);
        this.mobile = mobile;
    }


    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        // 此请求没有返回的data字段，所以直接notifySuccess即可
        notifySuccess(null);
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_MANAGEMENT_MODULE, SEND_VERIFY_CODE);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
        // 组装参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        request.setHttpEntity(new ByteArrayEntity(request.getBody(params)));
        return request;
    }
}

package com.icloudoor.cloudoor.network.transaction;

import com.icloudoor.cloudoor.network.form.BaseForm;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 同意添加好友申请事务
 * Created by Derrick Guan on 8/1/15.
 */
public class AcceptInvitationTransaction extends CloudoorBaseTransaction {

    private String invitationId;

    public AcceptInvitationTransaction(String invitationId) {
        super(TRANSACTION_ACCEPT_INVITATION);
        this.invitationId = invitationId;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        // 直接notifySuccess
        notifySuccess(null);
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_IM_MODULE, ACCEPT_INVITATION);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url);

        Map<String, String> params = new HashMap<String, String>();
        params.put("invitationId", invitationId);
        BaseForm form = new BaseForm();
        try {
            request.setHttpEntity(new StringEntity(form.toJSONString(params), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return request;
    }
}

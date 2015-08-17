package com.icloudoor.cloudoor.network.transaction;

import com.icloudoor.cloudoor.network.form.ComplainForm;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * 举报事务
 * Created by Derrick Guan on 8/2/15.
 */
public class ComplainTransaction extends CloudoorBaseTransaction {

    private String trgUserId;
    private int type;

    /**
     * 举报
     *
     * @param trgUserId 投诉目标用户id
     * @param type      举报类型
     * @see com.icloudoor.cloudoor.app.CloudoorConstants.ComplainType
     */
    public ComplainTransaction(String trgUserId, int type) {
        super(TRANSACTION_COMPLAIN);
        this.trgUserId = trgUserId;
        this.type = type;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        // 直接notifySuccess
        notifySuccess(null);
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_IM_MODULE, COMPLAIN);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url);

        ComplainForm form = new ComplainForm(trgUserId, type);
        try {
            request.setHttpEntity(new StringEntity(form.toJSONString(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return request;
    }
}

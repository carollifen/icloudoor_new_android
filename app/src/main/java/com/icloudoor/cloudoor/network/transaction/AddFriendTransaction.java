package com.icloudoor.cloudoor.network.transaction;

import com.icloudoor.cloudoor.network.form.BaseForm;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 申请添加好友事务
 * Created by Derrick Guan on 8/1/15.
 */
public class AddFriendTransaction extends CloudoorBaseTransaction {

    private String targetUserId;
    private String targetMobile;
    private String comment;

    /**
     * 申请添加好友
     *
     * @param targetUserId 目标用户ID
     * @param targetMobile 目标用户手机号码
     * @param comment      注释
     */
    public AddFriendTransaction(String targetUserId, String targetMobile, String comment) {
        super(TRANSACTION_ADD_FRIEND);
        this.targetUserId = targetUserId;
        this.targetMobile = targetMobile;
        this.comment = comment;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        // 直接notifySuccess
        notifySuccess(null);
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_IM_MODULE, INVITE);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url);

        Map<String, String> params = new HashMap<String, String>();
        params.put("trgUserId", targetUserId);
        params.put("trgMobile", targetMobile);
        params.put("comment", comment);
        BaseForm form = new BaseForm();
        try {
            request.setHttpEntity(new StringEntity(form.toJSONString(params), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return request;
    }
}

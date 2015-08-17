package com.icloudoor.cloudoor.network.transaction;

import com.icloudoor.cloudoor.network.form.BaseForm;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 删除好友事务
 * Created by Derrick Guan on 8/2/15.
 */
public class RemoveFriendTransaction extends CloudoorBaseTransaction {

    private String friendUserId;

    public RemoveFriendTransaction(String friendUserId) {
        super(TRANSACTION_REMOVE_FRIEND);
        this.friendUserId = friendUserId;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        // 直接notifySuccess
        notifySuccess(null);
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_IM_MODULE, REMOVE_FRIEND);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url);

        Map<String, String> params = new HashMap<String, String>();
        params.put("friendUserId", friendUserId);
        BaseForm form = new BaseForm();
        try {
            request.setHttpEntity(new StringEntity(form.toJSONString(params), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return request;
    }
}

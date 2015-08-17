package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.form.BaseForm;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorServiceCode;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 抢红包事务
 * Created by Derrick Guan on 7/31/15.
 */
public class GrabRedTransaction extends CloudoorBaseTransaction {

    private String doorId;

    public GrabRedTransaction(String doorId) {
        super(TRANSACTION_GRAB_RED);
        this.doorId = doorId;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        if (data != null && data instanceof JsonElement) {
            boolean success = ((JsonElement) data).getAsBoolean();
            if (success) {
                notifySuccess(null);
            } else {
                notifyError(CloudoorServiceCode.ERR_GRAB_RED_FAIL, null);
            }

        } else {
            notifyDataParseError();
        }
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_ACTIVITY_RP, GRAB);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url);

        Map<String, String> params = new HashMap<String, String>();
        params.put("doorId", doorId);
        BaseForm form = new BaseForm();
        try {
            request.setHttpEntity(new StringEntity(form.toJSONString(params), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return request;
    }
}

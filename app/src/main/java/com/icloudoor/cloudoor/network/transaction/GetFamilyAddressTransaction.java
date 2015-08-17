package com.icloudoor.cloudoor.network.transaction;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.form.BaseForm;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 获取是否家庭用户事务
 * Created by Derrick Guan on 8/3/15.
 */
public class GetFamilyAddressTransaction extends CloudoorBaseTransaction {

    private String targetUserId;

    public GetFamilyAddressTransaction(String targetUserId) {
        super(TRANSACTION_GET_FAMILY_ADDRESS);
        this.targetUserId = targetUserId;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        List<String> zoneUserIds = new ArrayList<String>();
        if (data != null && data instanceof JsonElement) {
            JsonArray jsonArray = ((JsonElement) data).getAsJsonArray();
            Iterator it = jsonArray.iterator();
            while (it.hasNext()) {
                JsonElement json = (JsonElement) it.next();
                String zoneUserId = json.getAsString();
                if (!TextUtils.isEmpty(zoneUserId)) {
                    zoneUserIds.add(zoneUserId);
                }
            }
            notifySuccess(zoneUserIds);
        } else {
            notifyDataParseError();
        }
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_API_MODULE, GET_FAMILY_ADDRESS);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url);
        Map<String, String> params = new HashMap<String, String>();
        params.put("trgUserId", targetUserId);
        BaseForm form = new BaseForm();
        try {
            request.setHttpEntity(new StringEntity(form.toJSONString(params), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return request;
    }
}

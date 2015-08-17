package com.icloudoor.cloudoor.network.transaction;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 获取用户标签事务
 * Created by Derrick Guan on 7/29/15.
 */
public class GetTagsTransaction extends CloudoorBaseTransaction {

    public GetTagsTransaction() {
        super(TRANSACTION_GET_TAGS);
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        List<String> tags = new ArrayList<String>();
        if (data != null && data instanceof JsonElement) {
            JsonArray jsonArray = ((JsonElement) data).getAsJsonArray();
            Iterator it = jsonArray.iterator();
            while (it.hasNext()) {
                JsonElement je = (JsonElement) it.next();
                String tag = je.getAsString();
                if (!TextUtils.isEmpty(tag)) {
                    tags.add(tag);
                }
            }
            notifySuccess(tags);
        } else {
            notifyDataParseError();
        }
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_API_MODULE, GET_TAGS);
        return new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
    }
}

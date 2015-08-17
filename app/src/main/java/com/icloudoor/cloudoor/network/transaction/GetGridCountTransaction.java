package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.http.THttpMethod;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

/**
 * 获取未读的公告数以及问卷调查数事务
 * Created by Derrick Guan on 7/29/15.
 */
public class GetGridCountTransaction extends CloudoorBaseTransaction {

    public GetGridCountTransaction() {
        super(TRANSACTION_GET_GRID_COUNT);
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        if (data != null && data instanceof JsonElement) {
            JsonArray jsonArray = ((JsonElement) data).getAsJsonArray();
            // 公告数
            int v1 = 0;
            try {
                v1 = (jsonArray.get(0)).getAsInt();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            // 问卷调查数
            int v2 = 0;
            try {
                v2 = (jsonArray.get(1)).getAsInt();

            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            notifySuccess(new int[]{v1, v2});
        } else {
            notifyDataParseError();
        }
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_PROP_ZONE_MODULE, GET_GRID_COUNT);
        return new CloudoorHttpRequest(url, THttpMethod.GET, RequestVersion.VERSION_URL_ENCODED);
    }
}

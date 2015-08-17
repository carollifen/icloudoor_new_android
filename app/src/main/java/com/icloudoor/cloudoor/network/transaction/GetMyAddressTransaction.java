package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.bean.meta.MyAddress;
import com.icloudoor.cloudoor.network.http.THttpMethod;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 获取我的地址事务
 * Created by Derrick Guan on 7/28/15.
 */
public class GetMyAddressTransaction extends CloudoorBaseTransaction {

    public GetMyAddressTransaction() {
        super(TRANSACTION_GET_MY_ADDRESS);
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        List<MyAddress> myAddressList = new ArrayList<>();
        if (data != null && data instanceof JsonElement) {
            Gson gson = new Gson();
            JsonElement json = (JsonElement) data;
            JsonArray jsonArray = json.getAsJsonArray();
            Iterator it = jsonArray.iterator();
            while (it.hasNext()) {
                JsonElement element = (JsonElement) it.next();
                MyAddress address = gson.fromJson(element, MyAddress.class);
                if (address != null) {
                    myAddressList.add(address);
                }
            }

            notifySuccess(myAddressList);
        } else {
            notifyDataParseError();
        }
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_API_MODULE, GET_MY_ADDRESS);
        return new CloudoorHttpRequest(url, THttpMethod.GET, RequestVersion.VERSION_URL_ENCODED);
    }
}

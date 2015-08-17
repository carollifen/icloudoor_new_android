package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.bean.meta.Almanac;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import org.apache.http.entity.ByteArrayEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 获取黄历事务
 * Created by Derrick Guan on 7/30/15.
 */
public class GetAlmanacTransaction extends CloudoorBaseTransaction {

    private String date;
    private String days;

    /**
     * 黄历接口
     *
     * @param date 开始日期 yyyy-MM-dd
     * @param days 返回的天数 1 ≤days≤10如不传，默认为1
     */
    public GetAlmanacTransaction(String date, String days) {
        super(TRANSACTION_GET_ALMANAC);
        this.date = date;
        this.days = days;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        List<Almanac> almanacList = new ArrayList<Almanac>();
        if (data != null && data instanceof JsonElement) {
            Gson gson = new Gson();
            JsonArray jsonArray = ((JsonElement) data).getAsJsonArray();
            Iterator it = jsonArray.iterator();
            while (it.hasNext()) {
                JsonElement json = (JsonElement) it.next();
                Almanac almanac = gson.fromJson(json, Almanac.class);
                if (almanac != null) {
                    almanacList.add(almanac);
                }
            }
            notifySuccess(almanacList);
        } else {
            notifyDataParseError();
        }
    }


    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_DATA_MODULE, GET_ALMANAC);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
        Map<String, String> params = new HashMap<String, String>();
        params.put("date", date);
        params.put("days", days);
        request.setHttpEntity(new ByteArrayEntity(request.getBody(params)));
        return request;
    }
}

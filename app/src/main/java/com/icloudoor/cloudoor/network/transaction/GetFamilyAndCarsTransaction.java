package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.bean.GetFamilyAndCarsBean;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import org.apache.http.entity.ByteArrayEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取家庭成员手机号 & 车牌事务
 * Created by Derrick Guan on 7/28/15.
 */
public class GetFamilyAndCarsTransaction extends CloudoorBaseTransaction {

    private String zoneUserId;

    public GetFamilyAndCarsTransaction(String zoneUserId) {
        super(TRANSACTION_GET_FAMILY_AND_CARS);
        this.zoneUserId = zoneUserId;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        GetFamilyAndCarsBean bean = null;
        if (data != null && data instanceof JsonElement) {
            Gson gson = new Gson();
            JsonElement json = (JsonElement) data;
            bean = gson.fromJson(json, GetFamilyAndCarsBean.class);
        }
        if (bean != null) {
            notifySuccess(bean);
        } else {
            notifyDataParseError();
        }
    }

    /**
     * 获取家庭成员手机号
     * HTTP Method : get
     * <br>wiki文档上定义此方法为get，但get的情况下会报错<br/>
     * <br>所以目前使用post方法<br/>
     *
     * @return THttpRequest
     */
    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_API_MODULE, GET_FAMILY_USER_AND_CARS);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
        Map<String, String> params = new HashMap<String, String>();
        params.put("zoneUserId", zoneUserId);
        request.setHttpEntity(new ByteArrayEntity(request.getBody(params)));
        return request;
    }
}

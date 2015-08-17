package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.bean.GetOfficeSignsBean;
import com.icloudoor.cloudoor.network.form.BaseForm;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取考勤记录事务
 * Created by Derrick Guan on 8/3/15.
 */
public class GetOfficeSignsTransaction extends CloudoorBaseTransaction {

    private String from;
    private String to;

    public GetOfficeSignsTransaction(String from, String to) {
        super(TRANSACTION_GET_OFFICE_SIGNS);
        this.from = from;
        this.to = to;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        GetOfficeSignsBean bean = null;
        if (data != null && data instanceof JsonElement) {
            Gson gson = new Gson();
            JsonElement json = (JsonElement) data;
            bean = gson.fromJson(json, GetOfficeSignsBean.class);
        }
        if (bean != null) {
            notifySuccess(bean);
        } else {
            notifyDataParseError();
        }
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_API_MODULE, GET_SIGNS);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url);
        Map<String, String> params = new HashMap<String, String>();
        params.put("from", from);
        params.put("to", to);

        BaseForm form = new BaseForm();
        try {
            request.setHttpEntity(new StringEntity(form.toJSONString(params), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return request;
    }
}

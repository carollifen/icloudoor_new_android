package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.bean.UserDetailBean;
import com.icloudoor.cloudoor.network.form.GetUsersDetailForm;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 获取用户详细信息事务
 * Created by Derrick Guan on 8/2/15.
 */
public class GetUsersDetailTransaction extends CloudoorBaseTransaction {

    private List<String> userIds;

    public GetUsersDetailTransaction(List<String> userIds) {
        super(TRANSACTION_GET_USERS_DETAIL);
        this.userIds = userIds;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        List<UserDetailBean> userDetailBeanList = new ArrayList<UserDetailBean>();
        if (data != null && data instanceof JsonElement) {
            Gson gson = new Gson();
            JsonArray jsonArray = ((JsonElement) data).getAsJsonArray();
            Iterator it = jsonArray.iterator();
            while (it.hasNext()) {
                JsonElement json = (JsonElement) it.next();
                UserDetailBean bean = gson.fromJson(json, UserDetailBean.class);
                if (bean != null) {
                    userDetailBeanList.add(bean);
                }
            }
            notifySuccess(userDetailBeanList);
        } else {
            notifyDataParseError();
        }
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_API_MODULE, GET_USERS_DETAIL);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url);

        GetUsersDetailForm form = new GetUsersDetailForm(userIds);
        try {
            request.setHttpEntity(new StringEntity(form.toJSONString(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return request;
    }
}

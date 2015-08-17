package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.database.AccountManager;
import com.icloudoor.cloudoor.network.bean.LoginBean;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import org.apache.http.entity.ByteArrayEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录事务
 * Created by Derrick Guan on 7/23/15.
 */
public class LoginTransaction extends CloudoorBaseTransaction {

    private String mobile;
    private String password;

    public LoginTransaction(String mobile, String password) {
        super(TRANSACTION_LOGIN);
        this.mobile = mobile;
        this.password = password;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        LoginBean bean = null;
        if (data != null) {
            Gson gson = new Gson();
            JsonElement json = (JsonElement) data;
            bean = gson.fromJson(json, LoginBean.class);
        }

        if (bean != null) {
            // 将用户信息写入到数据库中
            AccountManager.getInstance().setLoginAccount(bean);
            notifySuccess(bean);
        } else {
            notifyDataParseError();
        }
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_MANAGEMENT_MODULE, LOGIN);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
        // 组装参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        params.put("password", password);
        request.setHttpEntity(new ByteArrayEntity(request.getBody(params)));
        return request;
    }
}

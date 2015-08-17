package com.icloudoor.cloudoor.network.transaction;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.form.GetIsUserRegForm;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 获取是否注册用户事务
 * Created by Derrick Guan on 8/3/15.
 */
public class GetIsUserRegTransaction extends CloudoorBaseTransaction {

    private List<String> mobiles;

    public GetIsUserRegTransaction(List<String> mobiles) {
        super(TRANSACTION_GET_IS_USER_REG);
        this.mobiles = mobiles;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        List<String> regMobiles = new ArrayList<String>();
        if (data != null && data instanceof JsonElement) {
            JsonArray jsonArray = ((JsonElement) data).getAsJsonArray();
            Iterator it = jsonArray.iterator();
            while (it.hasNext()) {
                JsonElement json = (JsonElement) it.next();
                String regMobile = json.getAsString();
                if (!TextUtils.isEmpty(regMobile)) {
                    regMobiles.add(regMobile);
                }
            }
            notifySuccess(regMobiles);
        } else {
            notifyDataParseError();
        }
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_API_MODULE, IS_USER_REG);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url);

        GetIsUserRegForm form = new GetIsUserRegForm(mobiles);
        try {
            request.setHttpEntity(new StringEntity(form.toJSONString(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return request;
    }
}

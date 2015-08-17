package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.app.CloudoorConstants;
import com.icloudoor.cloudoor.network.bean.SignBean;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import org.apache.http.entity.ByteArrayEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 签到事务
 * Created by Derrick Guan on 7/30/15.
 */
public class SignTransaction extends CloudoorBaseTransaction {

    private String doorId;
    private String type;

    /**
     * 签到
     *
     * @param doorId 门id
     * @param type   签到类型: 1. 上班; 2. 下班
     * @see com.icloudoor.cloudoor.app.CloudoorConstants.SignType
     */
    public SignTransaction(int transType, String doorId, String type) {
        super(transType);
        this.doorId = doorId;
        this.type = type;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        if (type.equalsIgnoreCase(CloudoorConstants.SignType.OnDuty)) {
            // 上班签到
            if (data != null && data instanceof JsonElement) {
                Gson gson = new Gson();
                SignBean bean = gson.fromJson((JsonElement) data, SignBean.class);
                if (bean != null) {
                    notifySuccess(bean);
                } else {
                    notifyDataParseError();
                }
            } else {
                notifyDataParseError();
            }
        } else if (type.equalsIgnoreCase(CloudoorConstants.SignType.OffDuty)) {
            // 下班签到
            notifySuccess(null);
        }
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_API_MODULE, SIGN);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
        Map<String, String> params = new HashMap<String, String>();
        params.put("doorId", doorId);
        params.put("type", type);
        request.setHttpEntity(new ByteArrayEntity(request.getBody(params)));
        return request;
    }
}

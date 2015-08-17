package com.icloudoor.cloudoor.network.transaction;


import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import org.apache.http.entity.ByteArrayEntity;

import java.util.Map;

/**
 * 判断参数的合法性，暂由上层调用者处理。以后可以放到此Transaction中统一处理
 * <p>profileMap 中只需存放需要修改的字段，不需要修改的字段不需要存放在profileMap中</p>
 * Created by Derrick Guan on 7/24/15.
 */
public class UpdateProfileTransaction extends CloudoorBaseTransaction {

    private Map<String, String> profileMap;

    public UpdateProfileTransaction(Map<String, String> profileMap) {
        super(TRANSACTION_UPDATE_PROFILE);
        this.profileMap = profileMap;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        // 直接notifySuccess
        notifySuccess(null);
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_MANAGEMENT_MODULE, UPDATE_PROFILE);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
        request.setHttpEntity(new ByteArrayEntity(request.getBody(profileMap)));
        return request;
    }
}

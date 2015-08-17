package com.icloudoor.cloudoor.network.transaction;

import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

/**
 * 用户登出事务
 * Created by Derrick Guan on 7/24/15.
 */
public class LogoutTransaction extends CloudoorBaseTransaction {

    public LogoutTransaction() {
        super(TRANSACTION_LOGOUT);
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        // TODO 可以在此统一处理退出登录前需要清除的数据

        // 直接notifySuccess
        notifySuccess(null);
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_MANAGEMENT_MODULE, LOGOUT);
        return new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
    }
}

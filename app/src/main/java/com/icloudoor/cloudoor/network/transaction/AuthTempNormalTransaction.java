package com.icloudoor.cloudoor.network.transaction;

import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import org.apache.http.entity.ByteArrayEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 授权临时普通门权限事务
 * Created by Derrick Guan on 7/29/15.
 */
public class AuthTempNormalTransaction extends AuthBaseTransaction {

    private AuthToTarget toTarget;
    private String toUser;

    private String authDate;

    private String authFrom;
    private String authTo;


    /**
     * 授权临时普通门权限
     *
     * @param zoneUserId 住址id
     * @param toTarget   授权目标
     * @param toUser     手机号或目标用户id
     * @param authDate   授权日期 yyyy-MM-dd格式
     * @see com.icloudoor.cloudoor.network.transaction.AuthBaseTransaction.AuthToTarget
     */
    public AuthTempNormalTransaction(String zoneUserId, AuthToTarget toTarget, String toUser, String authDate) {
        super(TRANSACTION_AUTH_TEMP_NORMAL);
        this.zoneUserId = zoneUserId;
        this.toTarget = toTarget;
        this.toUser = toUser;
        this.authDate = authDate;
        this.authFrom = null;
        this.authTo = null;
    }

    /**
     * 授权临时普通门权限
     *
     * @param zoneUserId 住址id
     * @param toTarget   授权目标
     * @param toUser     手机号或目标用户id
     * @param authFrom   授权开始时间  yyyy-MM-dd HH:mm:ss
     * @param authTo     授权结束时间  yyyy-MM-dd HH:mm:ss
     * @see com.icloudoor.cloudoor.network.transaction.AuthBaseTransaction.AuthToTarget
     */
    public AuthTempNormalTransaction(String zoneUserId, AuthToTarget toTarget, String toUser, String authFrom, String authTo) {
        super(TRANSACTION_AUTH_TEMP_NORMAL);
        this.zoneUserId = zoneUserId;
        this.toTarget = toTarget;
        this.toUser = toUser;
        this.authDate = null;
        this.authFrom = authFrom;
        this.authTo = authTo;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        // 直接notifySuccess
        notifySuccess(null);
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_API_MODULE, AUTH_TEMP_NORMAL);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
        Map<String, String> params = new HashMap<String, String>();

        if (authDate != null) {
            params.put("zoneUserId", zoneUserId);
            params.put("authDate", authDate);
        } else {
            params.put("zoneUserId", zoneUserId);
            params.put("authFrom", authFrom);
            params.put("authTo", authTo);
        }

        switch (toTarget) {
            case MOBILE:
                params.put("toMobile", toUser);
                break;
            case USER_ID:
                params.put("toUserId", toUser);
                break;
        }

        request.setHttpEntity(new ByteArrayEntity(request.getBody(params)));
        return request;
    }
}

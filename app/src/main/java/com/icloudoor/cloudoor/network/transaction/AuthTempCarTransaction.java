package com.icloudoor.cloudoor.network.transaction;

import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import org.apache.http.entity.ByteArrayEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 授权临时车门权限
 * Created by Derrick Guan on 7/29/15.
 */
public class AuthTempCarTransaction extends AuthBaseTransaction {

    private String plateNum;
    private AuthToTarget toTarget;
    private String toUser;
    private String carPosStatus;
    private String authFrom;


    /**
     * 授权临时车门权限
     *
     * @param zoneUserId   住址id
     * @param plateNum     车牌号
     * @param toTarget     授权目标
     * @param toUser       手机号或目标用户id
     * @param carPosStatus 当前车状态
     * @param authFrom     授权时间  yyyy-MM-dd HH:mm:ss
     * @see com.icloudoor.cloudoor.network.transaction.AuthBaseTransaction.AuthToTarget
     */
    public AuthTempCarTransaction(String zoneUserId, String plateNum,
                                  AuthToTarget toTarget, String toUser, String carPosStatus, String authFrom) {
        super(TRANSACTION_AUTH_TEMP_CAR);
        this.zoneUserId = zoneUserId;
        this.plateNum = plateNum;
        this.toTarget = toTarget;
        this.toUser = toUser;
        this.carPosStatus = carPosStatus;
        this.authFrom = authFrom;
    }


    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        // 直接notifySuccess
        notifySuccess(null);
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_API_MODULE, AUTH_TEMP_CAR);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
        Map<String, String> params = new HashMap<String, String>();
        params.put("zoneUserId", zoneUserId);
        params.put("plateNum", plateNum);
        params.put("carPosStatus", carPosStatus);
        params.put("authFrom", authFrom);
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

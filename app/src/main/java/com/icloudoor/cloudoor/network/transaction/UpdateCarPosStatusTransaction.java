package com.icloudoor.cloudoor.network.transaction;

import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import org.apache.http.entity.ByteArrayEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 更新车状态事务
 * <br>此事务与“归还车钥匙”事务基本一致<br/>
 * <br>可以考虑将两个事务合并成一个，减少代码冗余<br/>
 * Created by Derrick Guan on 7/29/15.
 */
public class UpdateCarPosStatusTransaction extends CloudoorBaseTransaction {

    private String l1ZoneId;
    private String plateNum;
    private String carPosStatus;

    /**
     * 更新车状态
     *
     * @param l1ZoneId     小区id
     * @param plateNum     车牌号
     * @param carPosStatus 车状态
     */
    public UpdateCarPosStatusTransaction(String l1ZoneId, String plateNum, String carPosStatus) {
        super(TRANSACTION_UPDATE_CAR_POS_STATUS);
        this.l1ZoneId = l1ZoneId;
        this.plateNum = plateNum;
        this.carPosStatus = carPosStatus;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        // 直接notifySuccess
        notifySuccess(null);
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_API_MODULE, UPDATE_CAR_POS_STATUS);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
        Map<String, String> params = new HashMap<String, String>();
        params.put("l1ZoneId", l1ZoneId);
        params.put("plateNum", plateNum);
        params.put("carPosStatus", carPosStatus);
        request.setHttpEntity(new ByteArrayEntity(request.getBody(params)));
        return request;
    }
}

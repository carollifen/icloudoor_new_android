package com.icloudoor.cloudoor.network.bean.meta;

/**
 * 授权记录
 * Created by Derrick Guan on 8/2/15.
 */
public class AuthRecordKey extends BaseKey {

    /**
     * 钥匙使用状态
     * @see com.icloudoor.cloudoor.app.CloudoorConstants.UseStatus
     */
    private int useStatus;

    public int getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(int useStatus) {
        this.useStatus = useStatus;
    }
}

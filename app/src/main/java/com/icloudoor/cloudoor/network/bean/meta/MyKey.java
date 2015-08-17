package com.icloudoor.cloudoor.network.bean.meta;

/**
 * 我的钥匙
 * Created by Derrick Guan on 8/2/15.
 */
public class MyKey extends BaseKey {

    /**
     * authStatus
     * 1. 可授权 2. 已授权 (对于人行门, 一直都会是1)
     * 对于可授权状态，必须调用5.1(ref: v1.3 wiki)接口判断是否家庭用户
     */
    private int authStatus;

    public int getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
    }
}

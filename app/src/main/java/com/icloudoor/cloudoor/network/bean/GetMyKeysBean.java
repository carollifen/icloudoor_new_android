package com.icloudoor.cloudoor.network.bean;

import com.icloudoor.cloudoor.network.bean.meta.MyKey;

import java.util.List;

/**
 * 我的钥匙数据结构
 * Created by Derrick Guan on 8/2/15.
 */
public class GetMyKeysBean {

    // 标记住址
    private String zoneUserId;
    // 地址
    private String address;
    // 小区id
    private String l1ZoneId;

    // 钥匙列表
    private List<MyKey> keys;

    public String getL1ZoneId() {
        return l1ZoneId;
    }

    public void setL1ZoneId(String l1ZoneId) {
        this.l1ZoneId = l1ZoneId;
    }
    
    public String getZoneUserId() {
        return zoneUserId;
    }

    public void setZoneUserId(String zoneUserId) {
        this.zoneUserId = zoneUserId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<MyKey> getKeys() {
        return keys;
    }

    public void setKeys(List<MyKey> keys) {
        this.keys = keys;
    }
}

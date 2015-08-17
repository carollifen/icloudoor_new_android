package com.icloudoor.cloudoor.network.bean.meta;

/**
 * MyAddress
 * Created by Derrick on 7/28/15.
 */
public class MyAddress {

    // 小区ID，l1ZoneId+plateNum 可确定当前车状态
    private String l1ZoneId;
    // 住户用户ID， 用于与后台交互，可唯一标识一个地址
    private String zoneUserId;
    // 地址
    private String address;

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
}

package com.icloudoor.cloudoor.network.bean.meta;

/**
 * Zone
 * Created by Derrick Guan on 7/28/15.
 */
public class Zone {

    // 小区/园区/楼栋id
    private String zoneId;
    // 上级小区/园区id，一级小区时，该值等于zoneId
    private String parentZoneId;
    // ZoneName
    private String ZoneName;

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getParentZoneId() {
        return parentZoneId;
    }

    public void setParentZoneId(String parentZoneId) {
        this.parentZoneId = parentZoneId;
    }

    public String getZoneName() {
        return ZoneName;
    }

    public void setZoneName(String zoneName) {
        ZoneName = zoneName;
    }
}

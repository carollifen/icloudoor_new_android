package com.icloudoor.cloudoor.network.bean.meta;

/**
 * DoorAuth
 * Created by Derrick Guan on 7/28/15.
 */
public class DoorAuth {

    // 小区/园区/楼栋ID
    private String zoneId;
    // 门id
    private String doorId;
    // 车牌号,只对车行门有效
    private String plateNum;
    // 设备id
    private String deviceId;
    // 门名
    private String doorName;
    // 门类型
    private int doorType;
    // 授权开始时间 yyyy-MM-dd HH-mm-ss
    private String authFrom;
    // 授权结束时间 yyyy-MM-dd HH-mm-ss
    private String authTo;
    // 方向: 1进2出，只对一级小区车门有效，人行门为空或二/三级车门为空
    private String direction;

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getDoorId() {
        return doorId;
    }

    public void setDoorId(String doorId) {
        this.doorId = doorId;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }

    public int getDoorType() {
        return doorType;
    }

    public void setDoorType(int doorType) {
        this.doorType = doorType;
    }

    public String getAuthFrom() {
        return authFrom;
    }

    public void setAuthFrom(String authFrom) {
        this.authFrom = authFrom;
    }

    public String getAuthTo() {
        return authTo;
    }

    public void setAuthTo(String authTo) {
        this.authTo = authTo;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}

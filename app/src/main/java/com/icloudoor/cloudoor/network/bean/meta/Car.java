package com.icloudoor.cloudoor.network.bean.meta;

/**
 * Car
 *
 * Created by Derrick on 7/27/15.
 */
public class Car {

    // 小区ID
    private String l1ZoneId;
    // 车牌号
    private String plateNum;
    // 车状态
    private String carStatus;
    // 车位置状态
    private String carPosStatus;

    public String getL1ZoneId() {
        return l1ZoneId;
    }

    public void setL1ZoneId(String l1ZoneId) {
        this.l1ZoneId = l1ZoneId;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public String getCarPosStatus() {
        return carPosStatus;
    }

    public void setCarPosStatus(String carPosStatus) {
        this.carPosStatus = carPosStatus;
    }
}

package com.icloudoor.cloudoor.network.bean;

import com.icloudoor.cloudoor.network.bean.meta.Car;
import com.icloudoor.cloudoor.network.bean.meta.DoorAuth;
import com.icloudoor.cloudoor.network.bean.meta.Zone;

import java.util.List;

/**
 * 下载门锁信息数据结构
 * Created by Derrick Guan on 7/28/15.
 */
public class DownloadDoorBean {

    private List<Car> cars;

    private List<DoorAuth> doorAuths;

    private List<Zone> zones;

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<DoorAuth> getDoorAuths() {
        return doorAuths;
    }

    public void setDoorAuths(List<DoorAuth> doorAuths) {
        this.doorAuths = doorAuths;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }
}

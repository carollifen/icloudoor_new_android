package com.icloudoor.cloudoor.network.bean;

import com.icloudoor.cloudoor.network.bean.meta.CarPlate;
import com.icloudoor.cloudoor.network.bean.meta.Mobile;

import java.util.List;

/**
 * 获取家庭成员手机号&车牌号数据结构
 * Created by Derrick Guan on 7/28/15.
 */
public class GetFamilyAndCarsBean {

    private List<Mobile> families;

    private List<CarPlate> cars;

    public List<Mobile> getFamilies() {
        return families;
    }

    public void setFamilies(List<Mobile> families) {
        this.families = families;
    }

    public List<CarPlate> getCars() {
        return cars;
    }

    public void setCars(List<CarPlate> cars) {
        this.cars = cars;
    }
}

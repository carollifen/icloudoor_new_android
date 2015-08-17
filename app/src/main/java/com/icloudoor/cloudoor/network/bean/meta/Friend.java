package com.icloudoor.cloudoor.network.bean.meta;

import com.icloudoor.cloudoor.app.CloudoorConstants;

import java.io.Serializable;

/**
 * 好友对象类
 * Created by Derrick Guan on 8/1/15.
 */
public class Friend implements Serializable {

    // 用户id
    protected String userId;
    // 用户昵称
    protected String nickname;
    // 用户头像
    protected String portraitUrl;
    // 省份编码
    protected int provinceId;
    // 城市编码
    protected int cityId;
    // 区域编码
    protected int districtId;
    // 性别 : 默认为男性
    private int sex = CloudoorConstants.Sex.Male;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}

package com.icloudoor.cloudoor.network.bean;

import com.icloudoor.cloudoor.app.CloudoorConstants;

/**
 * 获取用户详细信息数据结构
 * Created by Derrick Guan on 8/2/15.
 */
public class UserDetailBean {

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
    // 值为0，1，2 定义参照透传消息的userInfo.type，当 type不等于0时， districtId和sex会为0，由于当前小区没有区域和性别的设置
    private int type;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

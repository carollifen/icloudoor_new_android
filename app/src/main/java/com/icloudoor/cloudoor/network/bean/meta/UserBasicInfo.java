package com.icloudoor.cloudoor.network.bean.meta;

import java.io.Serializable;
import java.util.List;

/**
 * 用户基本资料
 *
 * Created by Derrick Guan on 7/23/15.
 */
public class UserBasicInfo implements Serializable {

    // 用户名
    private String userName;
    // 昵称
    private String nickname;
    // 手机号码
    private String mobile;
    // 性别
    private int sex;
    // 身份证
    private String idCardNo;
    // 生日 yyyy-MM-dd
    private String birthday;
    // 省份编码
    private int provinceId;
    // 城市编码
    private int cityId;
    // 区域编码
    private int districtId;
    // 用户id
    private String userId;
    // 头像url
    private String portraitUrl;
    // 用户状态
    private int userStatus;
    // 用户是否有权限访问物业服务
    private boolean isHasPropServ;
    // 用户角色，十进制化，目前设置共2字节，数据为 00000000 00000000
    // 从右往左，第一位为 住户， 第二位为 员工， 例如返回1表示用户是住户，返回2是员工， 返回3表示两种角色都有
    private int role;
    // 一级小区列表
    private List<L1ZoneInfo> l1Zones;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public boolean isHasPropServ() {
        return isHasPropServ;
    }

    public void setHasPropServ(boolean isHasPropServ) {
        this.isHasPropServ = isHasPropServ;
    }

    public List<L1ZoneInfo> getL1Zones() {
        return l1Zones;
    }

    public void setL1Zones(List<L1ZoneInfo> l1Zones) {
        this.l1Zones = l1Zones;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}

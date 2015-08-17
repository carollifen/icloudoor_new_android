package com.icloudoor.cloudoor.database.table;

import android.provider.BaseColumns;

/**
 * 云门用户基础数据表
 * Created by Derrick Guan on 8/15/15.
 */
public class AccountTable implements BaseColumns {

    // 帐号名
    public static final String TABLE_NAME = "Account";

    // 用户id
    public static final String C_USER_ID = "user_id";
    // 用户名
    public static final String C_USERNAME = "username";
    // 昵称
    public static final String C_NICKNAME = "nickname";
    // 手机号码
    public static final String C_MOBILE = "mobile";
    /**
     * 性别
     * @see com.icloudoor.cloudoor.app.CloudoorConstants.Sex
     */
    public static final String C_GENDER = "gender";
    // 身份证
    public static final String C_ID_CARD = "id_card";
    // 生日
    public static final String C_BIRTHDAY = "birthday";
    // 省份编码
    public static final String C_PROVINCE_ID = "province_id";
    // 城市编码
    public static final String C_CITY_ID = "city_id";
    // 区域编码
    public static final String C_DISTRICT_ID = "district_id";
    // 头像url
    public static final String C_PORTRAIT_URL = "portrait_url";
    /**
     * 用户状态
     * @see com.icloudoor.cloudoor.app.CloudoorConstants.UserStatus
     */
    public static final String C_USER_STATUS = "user_status";
    // 用户是否有权限访问物业服务
    public static final String C_HAS_PRO_SERV = "has_pro_serv";
    // 用户角色
    public static final String C_ROLE = "role";
    // 一级小区列表json
    public static final String C_L1ZONES_JSON = "l1zones_json";
    // 环信用户名
    public static final String C_CHAT_USERNAME = "chat_username";
    // 环信用户密码
    public static final String C_CHAT_PASSWORD = "chat_password";
    // 是否最近一次登录 1为登录，其他为未登录
    public static final String C_LAST_LOGIN = "last_login";
}

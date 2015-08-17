package com.icloudoor.cloudoor.network.protocol;

import com.icloudoor.cloudoor.network.frame.TransTypeCode;

/**
 * 云门后端业务逻辑层返回码
 *
 * Created by Derrick Guan on 7/22/15.
 */
public class CloudoorServiceCode implements TransTypeCode {

    /**
     * App内部定义错误码(非后端定义)
     * <br>根据内部逻辑需要而定义的错误码，请注意与后端错误码区分<br/>
     */
    public static final int ERR_GRAB_RED_FAIL = -10001;


    /**
     * 通用成功返回码
     */
    public static final int SUCCESS = 1;
    /**
     * 通用失败错误码
     */
    // 参数不对
    public static final int ERR_INVALID_PARAMS = -1;
    // 未登录
    public static final int ERR_NOT_LOGGED_IN = -2;
    // 未知错误
    public static final int ERR_UNKNOWN = -99;

    /**
     * 发送验证码
     */
    // 发送短信数量超出限制(1天10条)
    public static final int ERR_SEND_MSN_OVER_LIMIT = -20;
    // 发送短信过于频繁(60秒一次)
    public static final int ERR_SEND_MSN_OVER_FREQUENCY = -21;

    /**
     * 确认验证码
     */
    // 验证码错误
    public static final int ERR_VERIFY_CODE_ERROR = -30;
    // 验证码已失效，重新引导用户发送验证码
    public static final int ERR_INVALID_VERIFY_CODE = -31;

    /**
     * 确认验证码并验证用户是否注册
     */
    // 该手机号已被注册
    public static final int ERR_MOBILE_REGISTERED = -40;

    /**
     * 创建用户
     */
    // 密码强度不够
    public static final int ERR_PASSWORD_TOO_WEAK = -41;

    /**
     * 设置用户资料
     */
    // 该昵称已被使用
    public static final int ERR_NICKNAME_HAS_BEEN_USED = -42;

    /**
     * 修改密码
     */
    // 旧密码不对
    public static final int ERR_WRONG_OLD_PASSWORD = -51;

    /**
     * 通过验证码修改密码
     */
    // 用户名不存在
    public static final int ERR_USER_NOT_EXIST = -72;

    /**
     * 登录
     */
    // 用户名不存在或者密码不对
    public static final int ERR_INVALID_USERNAME_OR_PASSWORD = -71;

    /**
     * 上传用户头像
     */
    // 文件类型不对，只允许jpeg，jpg,png
    public static final int ERR_INVALID_FILE_TYPE = -77;
    // 文件大小超出限制，最大为5M
    public static final int ERR_FILE_SIZE_OVER_LIMIT = -78;

    /**
     * 授权临时车门权限
     */
    // 该车还在借出中
    public static final int ERR_CAR_HAS_BEEN_RENT = -100;
    // 用户尚未注册云门app
    public static final int ERR_USER_NOT_REGISTER = -101;
    // 您不能授权给自己
    public static final int ERR_CANNOT_AUTH_YOURSELF = -104;
    // 在同一个小区借和拥有同时最多只能有一辆车
    public static final int ERR_MORE_ONE_CAR = -105;
    // 车只能借给同一住户的人
    public static final int ERR_NOT_IN_SAME_ZONE = -108;

    /**
     * 授权临时普通门权限
     */
    // 用户发送次数超限（一天5次）
    public static final int ERR_AUTH_TEMP_NORMAL_OVER_LIMIT = -102;
    // 该用户已有该临时钥匙
    public static final int ERR_USER_HAS_TEMP_KEY = -103;
    // 用户接收次数超限（一天5次）
    public static final int ERR_USER_RECEIVED_OVER_LIMIT = -106;

    /**
     * 归还车钥匙
     */
    // 该车未在借用状态
    public static final int ERR_CAR_NOT_IN_BORROWED = -107;

    /**
     * 签到
     */
    // 已签到
    public static final int ERR_HAVE_ALREADY_SIGNED = -91;

    /**
     * 申请添加好友
     */
    // 该用户已是您的好友
    public static final int ERR_ALREADY_YOUR_FRIEND = -150;
    // 不能添加自己为好友
    public static final int ERR_CANNOT_ADD_MYSELF = -151;



}

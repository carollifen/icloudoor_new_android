package com.icloudoor.cloudoor.utils;

import com.icloudoor.cloudoor.network.frame.TransTypeCode;
import com.icloudoor.cloudoor.network.protocol.CloudoorServiceCode;

/**
 * 用于将各类错误信息进行转义的工具类
 * Created by Derrick Guan on 7/23/15.
 */
public class CloudoorErrorUtil {

    /**
     * 网络层错误信息
     */
    public static final String ERR_HTTP = "网络请求出错了，请稍后重试";
    public static final String ERR_HTTP_CANCEL = "网络连接有问题，正在取消请求";
    public static final String ERR_NETWORK_IO_EXCEPTION = "网络连接出现了点问题";
    public static final String ERR_NETWORK_EXCEPTION = "网络连接出现了点问题";

    /**
     * 解析数据错误信息
     */
    public static final String ERR_INTERNAL_DATA_UNKNOWN = "数据解析出错";
    public static final String ERR_DATA_PARSE_EXCEPTION = "数据解析出错";

    /**
     * App内部逻辑错误信息
     */
    public static final String ERR_GRAB_RED_FAIL = "遗憾，没抢到红包";
    /**
     * 后端业务逻辑错误信息
     */
    public static final String ERR_INVALID_PARAMS = "请求参数有误";
    public static final String ERR_NOT_LOGGED_IN = "帐号未登录";
    public static final String ERR_UNKNOWN = "出现了奇怪的未知错误";
    public static final String ERR_SEND_MSN_OVER_LIMIT = "发送短信数量超出限制";
    public static final String ERR_SEND_MSN_OVER_FREQUENCY = "发送短信过于频繁";
    public static final String ERR_VERIFY_CODE_ERROR = "验证码错误";
    public static final String ERR_INVALID_VERIFY_CODE = "验证码已失效, 请重新发送";
    public static final String ERR_MOBILE_REGISTERED = "该手机号已被注册";
    public static final String ERR_PASSWORD_TOO_WEAK = "密码强度不够";
    public static final String ERR_NICKNAME_HAS_BEEN_USED = "该昵称已被使用";
    public static final String ERR_WRONG_OLD_PASSWORD = "旧密码不对";
    public static final String ERR_USER_NOT_EXIST = "用户名不存在";
    public static final String ERR_INVALID_USERNAME_OR_PASSWORD = "用户名不存在或者密码不对";
    public static final String ERR_INVALID_FILE_TYPE = "上传的文件类型无效";
    public static final String ERR_FILE_SIZE_OVER_LIMIT = "文件大小超出限制，最大为5M";
    public static final String ERR_CAR_HAS_BEEN_RENT = "该车还在借出中";
    public static final String ERR_USER_NOT_REGISTER = "用户尚未注册云门app";
    public static final String ERR_CANNOT_AUTH_YOURSELF = "您不能授权给自己";
    public static final String ERR_MORE_ONE_CAR = "抱歉，在一个小区借和拥有同时最多只能拥有一辆车";
    public static final String ERR_AUTH_TEMP_NORMAL_OVER_LIMIT = "用户发送次数超限（一天5次）";
    public static final String ERR_USER_HAS_TEMP_KEY = "该用户已有该临时钥匙";
    public static final String ERR_USER_RECEIVED_OVER_LIMIT = "用户接收次数超限（一天5次）";
    public static final String ERR_CAR_NOT_IN_BORROWED = "该车未在借用状态";
    public static final String ERR_HAVE_ALREADY_SIGNED = "已签到";
    public static final String ERR_NOT_IN_SAME_ZONE = "车只能借给同一住户的人";
    public static final String ERR_ALREADY_YOUR_FRIEND = "该用户已是您的好友";
    public static final String ERR_CANNOT_ADD_MYSELF = "不能添加自己为好友";


    public static String getErrorMessage(int errCode) {
        String errStr;
        switch (errCode) {
            case TransTypeCode.ERR_HTTP:
                errStr = ERR_HTTP;
                break;
            case TransTypeCode.ERR_HTTP_CANCEL:
                errStr = ERR_HTTP_CANCEL;
                break;
            case TransTypeCode.ERR_NETWORK_IO_EXCEPTION:
                errStr = ERR_NETWORK_IO_EXCEPTION;
                break;
            case TransTypeCode.ERR_NETWORK_EXCEPTION:
                errStr = ERR_NETWORK_EXCEPTION;
                break;
            case TransTypeCode.ERR_INTERNAL_DATA_UNKNOWN:
                errStr = ERR_INTERNAL_DATA_UNKNOWN;
                break;
            case TransTypeCode.ERR_DATA_PARSE_EXCEPTION:
                errStr = ERR_DATA_PARSE_EXCEPTION;
                break;
            case CloudoorServiceCode.ERR_INVALID_PARAMS:
                errStr = ERR_INVALID_PARAMS;
                break;
            case CloudoorServiceCode.ERR_NOT_LOGGED_IN:
                errStr = ERR_NOT_LOGGED_IN;
                break;
            case CloudoorServiceCode.ERR_UNKNOWN:
                errStr = ERR_UNKNOWN;
                break;
            case CloudoorServiceCode.ERR_SEND_MSN_OVER_LIMIT:
                errStr = ERR_SEND_MSN_OVER_LIMIT;
                break;
            case CloudoorServiceCode.ERR_SEND_MSN_OVER_FREQUENCY:
                errStr = ERR_SEND_MSN_OVER_FREQUENCY;
                break;
            case CloudoorServiceCode.ERR_VERIFY_CODE_ERROR:
                errStr = ERR_VERIFY_CODE_ERROR;
                break;
            case CloudoorServiceCode.ERR_INVALID_VERIFY_CODE:
                errStr = ERR_INVALID_VERIFY_CODE;
                break;
            case CloudoorServiceCode.ERR_MOBILE_REGISTERED:
                errStr = ERR_MOBILE_REGISTERED;
                break;
            case CloudoorServiceCode.ERR_PASSWORD_TOO_WEAK:
                errStr = ERR_PASSWORD_TOO_WEAK;
                break;
            case CloudoorServiceCode.ERR_NICKNAME_HAS_BEEN_USED:
                errStr = ERR_NICKNAME_HAS_BEEN_USED;
                break;
            case CloudoorServiceCode.ERR_WRONG_OLD_PASSWORD:
                errStr = ERR_WRONG_OLD_PASSWORD;
                break;
            case CloudoorServiceCode.ERR_USER_NOT_EXIST:
                errStr = ERR_USER_NOT_EXIST;
                break;
            case CloudoorServiceCode.ERR_INVALID_USERNAME_OR_PASSWORD:
                errStr = ERR_INVALID_USERNAME_OR_PASSWORD;
                break;
            case CloudoorServiceCode.ERR_INVALID_FILE_TYPE:
                errStr = ERR_INVALID_FILE_TYPE;
                break;
            case CloudoorServiceCode.ERR_FILE_SIZE_OVER_LIMIT:
                errStr = ERR_FILE_SIZE_OVER_LIMIT;
                break;
            case CloudoorServiceCode.ERR_CAR_HAS_BEEN_RENT:
                errStr = ERR_CAR_HAS_BEEN_RENT;
                break;
            case CloudoorServiceCode.ERR_USER_NOT_REGISTER:
                errStr = ERR_USER_NOT_REGISTER;
                break;
            case CloudoorServiceCode.ERR_CANNOT_AUTH_YOURSELF:
                errStr = ERR_CANNOT_AUTH_YOURSELF;
                break;
            case CloudoorServiceCode.ERR_MORE_ONE_CAR:
                errStr = ERR_MORE_ONE_CAR;
                break;
            case CloudoorServiceCode.ERR_AUTH_TEMP_NORMAL_OVER_LIMIT:
                errStr = ERR_AUTH_TEMP_NORMAL_OVER_LIMIT;
                break;
            case CloudoorServiceCode.ERR_USER_HAS_TEMP_KEY:
                errStr = ERR_USER_HAS_TEMP_KEY;
                break;
            case CloudoorServiceCode.ERR_USER_RECEIVED_OVER_LIMIT:
                errStr = ERR_USER_RECEIVED_OVER_LIMIT;
                break;
            case CloudoorServiceCode.ERR_CAR_NOT_IN_BORROWED:
                errStr = ERR_CAR_NOT_IN_BORROWED;
                break;
            case CloudoorServiceCode.ERR_HAVE_ALREADY_SIGNED:
                errStr = ERR_HAVE_ALREADY_SIGNED;
                break;
            case CloudoorServiceCode.ERR_GRAB_RED_FAIL:
                errStr = ERR_GRAB_RED_FAIL;
                break;
            case CloudoorServiceCode.ERR_NOT_IN_SAME_ZONE:
                errStr = ERR_NOT_IN_SAME_ZONE;
                break;
            case CloudoorServiceCode.ERR_ALREADY_YOUR_FRIEND:
                errStr = ERR_ALREADY_YOUR_FRIEND;
                break;
            case CloudoorServiceCode.ERR_CANNOT_ADD_MYSELF:
                errStr = ERR_CANNOT_ADD_MYSELF;
                break;
            default:
                errStr = ERR_UNKNOWN;
                break;
        }
        return errStr;
    }

}

package com.icloudoor.cloudoor.utils;

/**
 * Text相关基础工具类
 * Created by Derrick Guan on 8/7/15.
 */
public class CloudoorTextUtil {

    /**
     * 判断手机号码合法性
     *
     * @param phoneNum 手机号码
     * @return boolean
     */
    public static boolean isValidatePhoneNum(String phoneNum) {
        return phoneNum.matches("1[0-9]{10}");
    }

    /**
     * 判断密码长度合法性
     *
     * @param password 密码
     * @return boolean
     */
    public static boolean isPasswordValitdate(String password) {
        return password != null && password.length() >= 6 && password.length() <= 16;
    }

    /**
     * 计算字串长度
     *
     * @param src 字串
     * @return int
     */
    public static int getWordCount(String src) {
        String s = src.replaceAll("[^\\x00-\\xff]", "*");
        return s.length();
    }


    /**
     * 按指定长度截取字串
     *
     * @param src 字串
     * @return String
     */
    public static String getSummaryString(String src) {
        return getSummaryString(src, 40);
    }

    /**
     * 按指定长度截取字串
     *
     * @param src 字串
     * @param max 最大长度
     * @return String
     */
    public static String getSummaryString(String src, int max) {
        if (max <= 0) {
            return "";
        }
        if (src.length() <= max) {
            return src;
        }

        return src.substring(0, max) + "…";
    }


}

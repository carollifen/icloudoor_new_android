package com.icloudoor.cloudoor.PreferMgr;

/**
 * Created by Derrick Guan on 7/23/15.
 */
public class CloudoorPreHelper extends PreferHelper {

    /**
     * 用户当前sid
     * 不建议将sid以SharedPreference形式存储，应该维护一个本地的用户数据库，存储常用的用户信息
     */
    private static final String USER_SID = "sid";

    public static String getUserSid() {
        return getString(USER_SID, "");
    }

    public static void putUserSid(String sid) {
        putString(USER_SID, sid);
    }


}

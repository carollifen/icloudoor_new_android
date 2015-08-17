package com.icloudoor.cloudoor.network.bean.meta;

import java.io.Serializable;

/**
 * 后端返回的环信帐号
 * Created by Derrick Guan on 7/23/15.
 */
public class HXAccount implements Serializable {

    // 环信帐号
    private String userId;
    // 环信密码
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

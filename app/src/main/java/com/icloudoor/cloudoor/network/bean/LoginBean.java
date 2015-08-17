package com.icloudoor.cloudoor.network.bean;

import com.icloudoor.cloudoor.network.bean.meta.HXAccount;
import com.icloudoor.cloudoor.network.bean.meta.UserBasicInfo;

import java.io.Serializable;

/**
 * 登录成功后，后端翻回的数据结构
 * Created by Derrick Guan on 7/23/15.
 */
public class LoginBean implements Serializable {

    // 基本用户数据
    private UserBasicInfo info;
    // 环信帐号
    private IM im;

    public UserBasicInfo getInfo() {
        return info;
    }

    public void setInfo(UserBasicInfo info) {
        this.info = info;
    }

    public IM getIm() {
        return im;
    }

    public void setIm(IM im) {
        this.im = im;
    }

    public class IM {
        private HXAccount account;

        public HXAccount getAccount() {
            return account;
        }

        public void setAccount(HXAccount account) {
            this.account = account;
        }
    }

}

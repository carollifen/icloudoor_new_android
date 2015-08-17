package com.icloudoor.cloudoor.network.bean.meta;

/**
 * 搜索功能返回的用户结构体
 * Created by Derrick Guan on 8/1/15.
 */
public class SearchFriend extends Friend {

    // 手机号码
    private String mobile;
    // 是否好友
    private boolean isFriend;
    // 普通用户，由于该搜索只能搜索普通用户，所以该值会一直为0
    private int type;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean isFriend) {
        this.isFriend = isFriend;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

package com.icloudoor.cloudoor.network.transaction;

/**
 * 授权临时权限事务
 * <br>授权临时车门 or 授权临时普通门等事务都要继承此父类<br/>
 * Created by Derrick Guan on 8/1/15.
 */
public abstract class AuthBaseTransaction extends CloudoorBaseTransaction {

    protected String zoneUserId;

    protected AuthBaseTransaction(int type) {
        super(type);
    }

    public enum AuthToTarget {
        MOBILE, USER_ID
    }
}

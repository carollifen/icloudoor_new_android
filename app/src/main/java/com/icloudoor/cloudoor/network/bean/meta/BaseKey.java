package com.icloudoor.cloudoor.network.bean.meta;

/**
 * 钥匙基类
 * Created by Derrick Guan on 8/2/15.
 */
public class BaseKey {

    // name
    protected String name;

    /**
     * 门类型
     * @see com.icloudoor.cloudoor.app.CloudoorConstants.DoorType
     */
    protected int doorType;

    // 授权开始时间 yyyy-MM-dd HH:mm:ss
    protected String authFrom;
    // 授权结束时间 yyyy-MM-dd HH:mm:ss
    protected String authTo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDoorType() {
        return doorType;
    }

    public void setDoorType(int doorType) {
        this.doorType = doorType;
    }

    public String getAuthFrom() {
        return authFrom;
    }

    public void setAuthFrom(String authFrom) {
        this.authFrom = authFrom;
    }

    public String getAuthTo() {
        return authTo;
    }

    public void setAuthTo(String authTo) {
        this.authTo = authTo;
    }
}

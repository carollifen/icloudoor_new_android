package com.icloudoor.cloudoor.network.bean.meta;

import java.io.Serializable;

/**
 * 一级小区对象
 *
 * Created by Derrick Guan on 7/24/15.
 */

public class L1ZoneInfo implements Serializable {

    // 小区id, 用于标识小区
    private String l1ZoneId;
    // 小区名
    private String l1ZoneName;

    public String getL1ZoneId() {
        return l1ZoneId;
    }

    public void setL1ZoneId(String l1ZoneId) {
        this.l1ZoneId = l1ZoneId;
    }

    public String getL1ZoneName() {
        return l1ZoneName;
    }

    public void setL1ZoneName(String l1ZoneName) {
        this.l1ZoneName = l1ZoneName;
    }
}

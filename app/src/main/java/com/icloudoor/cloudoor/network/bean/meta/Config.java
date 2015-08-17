package com.icloudoor.cloudoor.network.bean.meta;

/**
 * Config
 * Created by Derrick Guan on 7/29/15.
 */
public class Config {

    // No explanations from Wiki document for these two fields
    private String reloadTimes;
    private String reloadDays;

    public String getReloadTimes() {
        return reloadTimes;
    }

    public void setReloadTimes(String reloadTimes) {
        this.reloadTimes = reloadTimes;
    }

    public String getReloadDays() {
        return reloadDays;
    }

    public void setReloadDays(String reloadDays) {
        this.reloadDays = reloadDays;
    }
}

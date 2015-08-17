package com.icloudoor.cloudoor.network.bean.meta;

/**
 * 签到记录对象
 * Created by Derrick Guan on 8/3/15.
 */
public class Sign {

    // 日期
    private String date;
    // 上班时间
    private String on;
    // 下班时间
    private String off;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }

    public String getOff() {
        return off;
    }

    public void setOff(String off) {
        this.off = off;
    }
}

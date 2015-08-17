package com.icloudoor.cloudoor.network.bean.meta;

/**
 * 黄历类
 * Created by Derrick Guan on 7/30/15.
 */
public class Almanac {

    // 日期
    private String date;
    // 宜
    private String yi;
    // 忌
    private String ji;
    // 阴历
    private String yinli;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYi() {
        return yi;
    }

    public void setYi(String yi) {
        this.yi = yi;
    }

    public String getJi() {
        return ji;
    }

    public void setJi(String ji) {
        this.ji = ji;
    }

    public String getYinli() {
        return yinli;
    }

    public void setYinli(String yinli) {
        this.yinli = yinli;
    }
}

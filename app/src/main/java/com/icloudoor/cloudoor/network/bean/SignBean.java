package com.icloudoor.cloudoor.network.bean;

/**
 * 签到返回的数据结构
 * Created by Derrick Guan on 7/30/15.
 */
public class SignBean {

    // 排名
    private int rank;
    // 是否迟到
    private boolean isLate;
    // 击败百分比
    private String beatRatio;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isLate() {
        return isLate;
    }

    public void setLate(boolean isLate) {
        this.isLate = isLate;
    }

    public String getBeatRatio() {
        return beatRatio;
    }

    public void setBeatRatio(String beatRatio) {
        this.beatRatio = beatRatio;
    }
}

package com.icloudoor.cloudoor.network.bean;

import com.icloudoor.cloudoor.network.bean.meta.Sign;

import java.util.List;

/**
 * 获取办公室考勤记录数据结构
 * Created by Derrick Guan on 8/3/15.
 */
public class GetOfficeSignsBean {

    // 考勤记录
    private List<Sign> signs;
    // 迟到列表
    private List<String> lates;
    // 早退列表
    private List<String> earlies;

    public List<Sign> getSigns() {
        return signs;
    }

    public void setSigns(List<Sign> signs) {
        this.signs = signs;
    }

    public List<String> getLates() {
        return lates;
    }

    public void setLates(List<String> lates) {
        this.lates = lates;
    }

    public List<String> getEarlies() {
        return earlies;
    }

    public void setEarlies(List<String> earlies) {
        this.earlies = earlies;
    }
}

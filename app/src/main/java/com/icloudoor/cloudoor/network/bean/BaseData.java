package com.icloudoor.cloudoor.network.bean;

import com.google.gson.JsonElement;

/**
 * 后端返回的基础数据结构体
 * Created by Derrick Guan on 7/23/15.
 */

public class BaseData {

    // 返回码
    private int code;
    // 返回信息
    private String message;
    // 返回的数据，可能为null
    private JsonElement data;
    // 会话id
    private String sid;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}

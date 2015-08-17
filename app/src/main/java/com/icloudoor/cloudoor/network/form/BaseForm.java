package com.icloudoor.cloudoor.network.form;

import com.google.gson.Gson;

import java.util.Map;


/**
 * 以JSON形式提交的参数
 *
 * Created by Derrick Guan on 7/27/15.
 */
public class BaseForm {

    public String toJSONString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String toJSONString(Map<String, String> maps) {
        Gson gson = new Gson();
        return gson.toJson(maps);
    }

}

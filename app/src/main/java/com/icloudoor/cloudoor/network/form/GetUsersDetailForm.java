package com.icloudoor.cloudoor.network.form;

import java.util.List;

/**
 * 获取用户详细信息请求体
 * Created by Derrick Guan on 8/2/15.
 */
public class GetUsersDetailForm extends BaseForm {

    private List<String> userIds;

    public GetUsersDetailForm(List<String> userIds) {
        this.userIds = userIds;
    }
}

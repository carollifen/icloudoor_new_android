package com.icloudoor.cloudoor.network.form;

/**
 * 举报请求体
 * Created by Derrick Guan on 8/2/15.
 */
public class ComplainForm extends BaseForm {

    // 投诉目前用户id
    private String trgUserId;
    /**
     * 投诉类型
     * @see com.icloudoor.cloudoor.app.CloudoorConstants.ComplainType
     */
    private int type;

    public ComplainForm(String trgUserId, int type) {
        this.trgUserId = trgUserId;
        this.type = type;
    }
}

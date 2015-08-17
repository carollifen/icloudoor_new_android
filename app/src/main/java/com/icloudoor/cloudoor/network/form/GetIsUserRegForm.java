package com.icloudoor.cloudoor.network.form;

import java.util.List;

/**
 * 是否注册用户请求体
 * Created by Derrick Guan on 8/3/15.
 */
public class GetIsUserRegForm extends BaseForm {

    private List<String> mobiles;

    public GetIsUserRegForm(List<String> mobiles) {
        this.mobiles = mobiles;
    }
}

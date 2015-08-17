package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.bean.meta.SearchFriend;
import com.icloudoor.cloudoor.network.form.BaseForm;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 搜索好友事务
 * Created by Derrick Guan on 8/1/15.
 */
public class SearchUserTransaction extends CloudoorBaseTransaction {

    private String searchValue;

    public SearchUserTransaction(String searchValue) {
        super(TRANSACTION_SEARCH_USER);
        this.searchValue = searchValue;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        List<SearchFriend> friendList = new ArrayList<SearchFriend>();
        if (data != null && data instanceof JsonElement) {
            Gson gson = new Gson();
            JsonArray jsonArray = ((JsonElement) data).getAsJsonArray();
            Iterator it = jsonArray.iterator();
            while (it.hasNext()) {
                JsonElement json = (JsonElement) it.next();
                SearchFriend friend = gson.fromJson(json, SearchFriend.class);
                if (friend != null) {
                    friendList.add(friend);
                }
            }
            notifySuccess(friendList);
        } else {
            notifyDataParseError();
        }
    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_IM_MODULE, SEARCH_USER);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url);

        Map<String, String> params = new HashMap<String, String>();
        params.put("searchValue", searchValue);
        BaseForm form = new BaseForm();
        try {
            request.setHttpEntity(new StringEntity(form.toJSONString(params), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return request;
    }
}

package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.bean.meta.Friend;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 获取好友列表事务
 * Created by Derrick Guan on 8/1/15.
 */
public class GetFriendsTransaction extends CloudoorBaseTransaction {

    public GetFriendsTransaction() {
        super(TRANSACTION_GET_FRIENDS);
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        List<Friend> friendList = new ArrayList<Friend>();
        if (data != null && data instanceof JsonElement) {
            Gson gson = new Gson();
            JsonArray jsonArray = ((JsonElement) data).getAsJsonArray();
            Iterator it = jsonArray.iterator();
            while (it.hasNext()) {
                JsonElement json = (JsonElement) it.next();
                Friend friend = gson.fromJson(json, Friend.class);
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
        String url = getRequestUrl(USER_IM_MODULE, GET_FRIENDS);
        return new CloudoorHttpRequest(url);
    }
}

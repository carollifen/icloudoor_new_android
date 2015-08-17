package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.bean.GetMyAuthKeysBean;
import com.icloudoor.cloudoor.network.bean.GetMyBorrowKeysBean;
import com.icloudoor.cloudoor.network.bean.GetMyKeysBean;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 获取我的钥匙事务
 * Created by Derrick Guan on 8/2/15.
 */
public class KeyManagementTransaction extends CloudoorBaseTransaction {

    private int transactionType;

    /**
     * 钥匙管理:
     * <br>我的钥匙<br/>
     * <br>借入钥匙<br/>
     * <br>授权钥匙<br/>
     *
     * @param transactionType 事务类型
     */
    public KeyManagementTransaction(int transactionType) {
        super(transactionType);
        this.transactionType = transactionType;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        List<Object> list = new ArrayList<Object>();
        if (data != null && data instanceof JsonElement) {
            Gson gson = new Gson();
            JsonArray jsonArray = ((JsonElement) data).getAsJsonArray();
            Iterator it = jsonArray.iterator();
            while (it.hasNext()) {
                JsonElement json = (JsonElement) it.next();
                if (transactionType == CloudoorBaseTransaction.TRANSACTION_GET_MY_KEYS) {
                    GetMyKeysBean bean = gson.fromJson(json, GetMyKeysBean.class);
                    if (bean != null) {
                        list.add(bean);
                    }
                } else if (transactionType == CloudoorBaseTransaction.TRANSACTION_GET_MY_BORROW_KEYS) {
                    GetMyBorrowKeysBean bean = gson.fromJson(json, GetMyBorrowKeysBean.class);
                    if (bean != null) {
                        list.add(bean);
                    }
                } else if (transactionType == CloudoorBaseTransaction.TRANSACTION_GET_MY_AUTH_KEYS) {
                    GetMyAuthKeysBean bean = gson.fromJson(json, GetMyAuthKeysBean.class);
                    if (bean != null) {
                        list.add(bean);
                    }
                }
            }
            notifySuccess(list);
        } else {
            notifyDataParseError();
        }
    }

    @Override
    public THttpRequest createRequest() {
        String url = "";
        if (transactionType == CloudoorBaseTransaction.TRANSACTION_GET_MY_KEYS) {
            url = getRequestUrl(USER_API_MODULE, GET_MY_KEYS);
        } else if (transactionType == CloudoorBaseTransaction.TRANSACTION_GET_MY_BORROW_KEYS) {
            url = getRequestUrl(USER_API_MODULE, GET_MY_BORROW_KEYS);
        } else if (transactionType == CloudoorBaseTransaction.TRANSACTION_GET_MY_AUTH_KEYS) {
            url = getRequestUrl(USER_API_MODULE, GET_MY_AUTH_KEYS);
        }
        return new CloudoorHttpRequest(url);
    }
}

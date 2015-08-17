package com.icloudoor.cloudoor.network.frame;

import android.os.Handler;
import android.os.Message;

import com.icloudoor.cloudoor.network.protocol.CloudoorCallback;
import com.icloudoor.cloudoor.utils.CloudoorErrorUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Created by Derrick Guan on 7/22/15.
 */
public class GroupTransactionListener implements TransactionListener {

    // 事务执行成功
    private static final int TRANSACTION_SUCCESS = 0x1;
    // 事务执行失败
    private static final int TRANSACTION_ERROR = 0x2;

    WeakHashMap<CloudoorCallback, Void> mCallbacks = new WeakHashMap<CloudoorCallback, Void>();
    Handler mHandler = new InternalHandler();

    @Override
    public void onTransactionSuccess(int type, int tid, Object data) {
        Result info = new Result("", data);
        Message msg = mHandler.obtainMessage(TRANSACTION_SUCCESS, type, tid,
                info);
        msg.sendToTarget();
    }

    @Override
    public void onTransactionError(int errCode, int type, int tid, Object data) {
        String errStr = CloudoorErrorUtil.getErrorMessage(errCode);

        Result info = new Result(errStr, null);
        Message msg = mHandler.obtainMessage(TRANSACTION_ERROR, type, tid, info);
        msg.sendToTarget();
    }

    /**
     * 传递成功或者错误数据给CloudoorCallback
     */
    private void onSuccess(int type, int tid, Object data) {
        Set<CloudoorCallback> set = mCallbacks.keySet();
        List<CloudoorCallback> list = new LinkedList<CloudoorCallback>();
        list.addAll(set);
        if (list.size() > 0) {
            for (CloudoorCallback mCallBack : list) {
                try {
                    mCallBack.onSuccess(type, tid, data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onError(int type, int tid, String errCode) {
        Set<CloudoorCallback> set = mCallbacks.keySet();
        List<CloudoorCallback> list = new LinkedList<CloudoorCallback>();
        list.addAll(set);
        if (list.size() > 0) {
            for (CloudoorCallback mCallBack : list) {
                try {
                    mCallBack.onError(type, tid, errCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    synchronized public void addListener(CloudoorCallback listener) {
        if (listener != null) {
            mCallbacks.put(listener, null);
        }
    }

    synchronized public void removeListener(CloudoorCallback listener) {
        if (listener != null) {
            mCallbacks.remove(listener);
        }
    }

    static class Result {
        public String mCode = "";
        public Object mData = null;

        public Result(String code, Object data) {
            mCode = code;
            mData = data;
        }
    }

    private class InternalHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case TRANSACTION_SUCCESS:
                        onSuccess(msg.arg1, msg.arg2, ((Result) msg.obj).mData);
                        break;

                    case TRANSACTION_ERROR:
                        onError(msg.arg1, msg.arg2, ((Result) msg.obj).mCode);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

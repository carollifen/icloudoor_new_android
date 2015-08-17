package com.icloudoor.cloudoor.network.frame;

import com.icloudoor.cloudoor.network.http.CharArrayPool;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Derrick Guan on 7/21/15.
 */
public class NotifyTransaction extends Transaction {

    // 数据回应通知事务，例如获取到http数据后进行回调通知
    public static final int TYPE_NOTIFY = -1;

    public static final int NOTIFY_TYPE_SUCCESS = 0x00; // 成功通知，调用onTransactionSuccess方法通知
    public static final int NOTIFY_TYPE_ERROR = 0x01; // 出错通知，调用onTransactionError方法通知
    private static CharArrayPool mCharPool = new CharArrayPool(4 * 1024);
    AsyncTransaction mTran;
    Object mData;
    int mNotifyType;
    int mCode;

    public NotifyTransaction(AsyncTransaction tran, int type, int code, Object data) {
        super(TYPE_NOTIFY);
        setPriority(Priority.EMERGENCY);

        mTran = tran;
        mData = data;
        mNotifyType = type;
        mCode = code;
    }

    protected static CharArrayPool getCharPool() {
        return mCharPool;
    }

    protected int getCode() {
        return mCode;
    }

    public boolean isSuccessNotify() {
        return mNotifyType == NOTIFY_TYPE_SUCCESS;
    }

    protected void setNotifyTypeAndCode(int notifyType, int code) {
        mNotifyType = notifyType & 0x01;
        mCode = code;
    }

    protected void resetData(Object data) {
        resetData(data, true);
    }

    protected void resetData(Object data, boolean close) {
        if (close && mData != null && mData instanceof InputStream) {
            try {
                ((InputStream) mData).close();
            } catch (IOException e) {
            }
        }

        mData = null;

        mData = data;
    }

    protected Object getData() {
        return mData;
    }

    /**
     * 自定义Notify继承这个方法
     */
    public void doBeforeTransact() {
    }

    @Override
    public final void onTransact() {
        doBeforeTransact();

        if (!mTran.isCancel()) {
            try {
                if (mNotifyType == NOTIFY_TYPE_SUCCESS) {
                    mTran.onTransactionSuccess(mData);
                } else {
                    mTran.onTransactionError(mCode, mData);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mTran.onTransactionError(TransTypeCode.ERR_HTTP, mData);
            }
        }

        resetData(null);
    }

    @Override
    public void onTransactException(int errorCode, Exception e) {

    }
}

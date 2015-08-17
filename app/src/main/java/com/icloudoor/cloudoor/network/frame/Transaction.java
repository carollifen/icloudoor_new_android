package com.icloudoor.cloudoor.network.frame;

import java.lang.ref.WeakReference;

/**
 * 事务抽象类
 * Created by Derrick Guan on 7/21/15.
 */

public abstract class Transaction implements Runnable, Comparable<Transaction> {

    static int mTransactionId = 0;
    /**
     * 事务类型
     */
    int mType;
    /**
     * 事务管理器
     */
    TransactionEngine mTransactionEngine;
    /**
     * 事务监听器
     */
    WeakReference<TransactionListener> mListener;
    /**
     * 事务优先级
     */
    int mPriority;
    /**
     * 分组ID, 非0有效
     */
    int mGroupID;
    long mCreateTime = System.currentTimeMillis();
    /**
     * Transaction 唯一标识.
     */
    private int mId;
    /**
     * 是否被取消标记
     */
    private boolean isCancel;

    protected Transaction(int type) {
        mType = type;
        mId = getNextTransactionId();
        mPriority = Priority.NORMAL;
    }

    /**
     * 生成TransactionId
     *
     * @return
     */
    private synchronized static int getNextTransactionId() {
        if (mTransactionId >= Short.MAX_VALUE) {
            mTransactionId = 0;
        }
        return ++mTransactionId;
    }

    public TransactionEngine getTransactionEngine() {
        return mTransactionEngine;
    }

    public void setTransactionEngine(TransactionEngine tranEngine) {
        mTransactionEngine = tranEngine;
    }

    public void doEnd() {
        if (mTransactionEngine != null) {
            mTransactionEngine.endTransaction(this);
        }
    }

    public TransactionListener getListener() {
        return mListener != null ? mListener.get() : null;
    }

    public void setListener(TransactionListener listener) {
        mListener = new WeakReference<TransactionListener>(listener);
    }

    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int priority) {
        mPriority = priority;
    }

    public int getGroupID() {
        return mGroupID;
    }

    public void setGroupID(int gid) {
        mGroupID = gid;
    }

    /**
     * 返回该事务Id
     *
     * @return
     */
    public int getId() {
        return mId;
    }

    /**
     * 返回该事务类型(由具体的业务逻辑去定义mType)
     *
     * @return
     */
    public int getType() {
        return mType;
    }

    /**
     * 是否取消
     *
     * @return 如果取消返回true, 否则返回false
     */
    public boolean isCancel() {
        return isCancel;
    }

    /**
     * 取消事务
     */
    public void doCancel() {
        isCancel = true;
    }

    public void run() {
        try {
            if (!isCancel()) {
                onTransact();
            }
        } catch (Exception e) {
            e.printStackTrace();

            doCancel();
            onTransactException(0, e);
        }

        mTransactionEngine.endTransaction(this);
    }

    /**
     * 所有事务的入口，由具体的Transaction子类独自实现
     */
    public abstract void onTransact();

    public void onTransactException(int errorCode, Exception e) {
    }

    /**
     * 通过TransactionListener回调给GroupTransactionListen(implements TransactionListener)
     */
    public void notifySuccess(Object data) {
        TransactionListener listener = getListener();
        if (listener != null) {
            listener.onTransactionSuccess(mType, mId, data);
        }
    }

    public void notifyError(int errCode, Object data) {
        TransactionListener listenr = getListener();
        if (listenr != null) {
            listenr.onTransactionError(errCode, mType, mId, data);
        }
    }

    @Override
    public int compareTo(Transaction another) {
        int priority = mPriority & 0xFF;
        int aPriority = another.mPriority & 0xFF;
        if (priority > aPriority) {
            return -1;
        } else if (priority < aPriority) {
            return 1;
        }

        if (mCreateTime < another.mCreateTime) {
            return -1;
        } else if (mCreateTime > another.mCreateTime) {
            return 1;
        }

        return 0;
    }
}

package com.icloudoor.cloudoor.network.frame;

import android.content.Context;

/**
 * Created by Derrick Guan on 7/21/15.
 */
public abstract class BaseService {

    private static Context mAppContext;
    TransactionEngine mTransactionEngine;
    DataChannel mDataChanel;

    public BaseService(DataChannel dataChanel) {
        mDataChanel = dataChanel;
        mTransactionEngine = dataChanel.getTransactionEngine();
    }

    public static void initServiceContext(Context appContext) {
        if (mAppContext == null) {
            mAppContext = appContext;
        }
    }

    public static Context getServiceContext() {
        return mAppContext;
    }

    protected int beginTransaction(Transaction trans) {
        int ret = -1;

        if (trans != null) {
            ret = trans.getId();

            if (trans instanceof AsyncTransaction) {
                ((AsyncTransaction) trans).setDataChannel(mDataChanel);
            }
            if (mTransactionEngine != null) {
                mTransactionEngine.beginTransaction(trans);
            }
        }

        return ret;
    }

    public void cancelTransaction(int tid) {
        if (mTransactionEngine != null) {
            mTransactionEngine.cancelTransaction(tid);
        }
    }
}

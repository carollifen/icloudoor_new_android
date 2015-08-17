package com.icloudoor.cloudoor.network.http;

import com.icloudoor.cloudoor.network.frame.AsyncTransaction;
import com.icloudoor.cloudoor.network.frame.DataChannel;
import com.icloudoor.cloudoor.network.frame.NotifyTransaction;
import com.icloudoor.cloudoor.network.frame.Priority;
import com.icloudoor.cloudoor.network.frame.TransactionEngine;

import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by Derrick Guan on 7/22/15.
 */

public class HttpDataChannel extends DataChannel implements HttpCallBack {


    /**
     * Hashtable<RequestID, Transaction>
     */
    Hashtable<Integer, AsyncTransaction> mTransactionMap;

    /**
     * Hashtable<TransactionID, HttpRequest>
     */
    Hashtable<Integer, THttpRequest> mRequestMap;

    HttpEngine mHttpEngine;

    public HttpDataChannel(TransactionEngine engine, HttpEngine httpEngine) {
        super(engine);

        mTransactionMap = new Hashtable<Integer, AsyncTransaction>();
        mRequestMap = new Hashtable<Integer, THttpRequest>();
        mHttpEngine = httpEngine;
    }

    @Override
    public void sendRequest(Object request, AsyncTransaction transaction) {
        if (request == null || !(request instanceof THttpRequest)) {
            return;
        }

        THttpRequest httpRequest = (THttpRequest) request;
        if (transaction == null) {
            httpRequest.setPriority(Priority.LOW);
            mHttpEngine.addRequest(httpRequest);
        } else {
            Integer requestID = Integer.valueOf(httpRequest.getRequestID());
            Integer tID = Integer.valueOf(transaction.getId());

            httpRequest.setHttpCallBack(this);
            httpRequest.setPriority(transaction.getPriority());

            mTransactionMap.put(requestID, transaction);
            mRequestMap.put(tID, httpRequest);

            mHttpEngine.addRequest(httpRequest);
        }
    }

    @Override
    public void cancelRequest(AsyncTransaction t) {
        Object key = Integer.valueOf(t.getId());

        THttpRequest request = null;

        request = (THttpRequest) mRequestMap.remove(key);

        if (request != null) {
            Integer requestId = Integer.valueOf(request.getRequestID());
            request.doCancel();
            mTransactionMap.remove(requestId);
        }
    }

    @Override
    public void adjustPriorityByGID(int tid, int priority) {
        mHttpEngine.adjustPriorityByGID(tid, priority);
    }

    @Override
    public void close() {
        mTransactionMap.clear();
        mRequestMap.clear();

        if (mHttpEngine != null) {
            mHttpEngine.shutdown();
        }
    }

    @Override
    public Object onPreError(THttpRequest request, Object data)
            throws IOException {
        AsyncTransaction trans = mTransactionMap.get(Integer.valueOf(
                request.getRequestID()));
        Object ret = null;
        if (trans != null) {
            ret = trans.onDataChannelPreNotify(request, data,
                    NotifyTransaction.NOTIFY_TYPE_ERROR);
        }
        return ret;
    }

    @Override
    public Object onPreReceived(THttpRequest request, THttpResponse response) throws IOException {
        AsyncTransaction trans = mTransactionMap.get(Integer.valueOf(
                request.getRequestID()));
        Object ret = null;
        if (trans != null) {
            ret = trans.onDataChannelPreNotify(request, response,
                    NotifyTransaction.NOTIFY_TYPE_SUCCESS);
        }
        return ret;
    }

    @Override
    public boolean onError(THttpRequest request, int errCode, Object data) {
        return onRequestNotify(NotifyTransaction.NOTIFY_TYPE_ERROR, request, errCode, data);
    }

    @Override
    public boolean onReceived(THttpRequest request, int code, Object data) {
        return onRequestNotify(NotifyTransaction.NOTIFY_TYPE_SUCCESS, request, code, data);
    }


    private boolean onRequestNotify(int notifyType, THttpRequest request, int code, Object data) {
        AsyncTransaction tran = null;
        Integer key = Integer.valueOf(request.getRequestID());

        tran = mTransactionMap.remove(key);

        if (tran != null) {
            mRequestMap.remove(Integer.valueOf(tran.getId()));
        }

        boolean ret = false;
        if (tran != null) {
            ret = true;
            NotifyTransaction notify = tran.createNotifyTransaction(notifyType, code, data);
            mTransactionEngine.beginTransaction(notify);
        }

        return ret;
    }

}

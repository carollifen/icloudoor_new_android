package com.icloudoor.cloudoor.network.frame;

import com.icloudoor.cloudoor.network.http.CancelException;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.http.THttpResponse;
import com.icloudoor.cloudoor.network.protocol.CloudoorServiceCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Derrick Guan on 7/21/15.
 */
public abstract class AsyncTransaction extends Transaction {

    DataChannel mDataChannel;

    protected AsyncTransaction(int type) {
        super(type);
    }

    protected static Object readString(Object request, InputStream in, String charset) throws IOException {
        BufferedReader reader = null;
        char[] data = null;

        try {
            THttpRequest tRequest;
            if (request != null && request instanceof THttpRequest) {
                tRequest = (THttpRequest) request;
            } else {
                tRequest = new THttpRequest("http");
            }

            if (charset != null) {
                charset = charset.toLowerCase();
                if ("utf-8".equals(charset)) {
                    charset = null;
                }
            }

            if (charset != null) {
                reader = new BufferedReader(new InputStreamReader(in, charset));
            } else {
                reader = new BufferedReader(new InputStreamReader(in));
            }

            StringBuilder buffer = new StringBuilder();
            int length;

            data = NotifyTransaction.getCharPool().getBuf(2048);

            while ((length = reader.read(data)) != -1) {
                buffer.append(data, 0, length);

                if (tRequest.isCancel()) {
                    throw new CancelException();
                }
            }

            return buffer.toString();
        } finally {
            if (data != null) {
                NotifyTransaction.getCharPool().returnBuf(data);
            }

            if (reader != null) {
                reader.close();
            }
        }

    }

    public void setDataChannel(DataChannel dataChannel) {
        mDataChannel = dataChannel;
    }

    protected void sendRequest(Object obj) {
        if (mDataChannel != null) {
            mDataChannel.sendRequest(obj, this);
        }
    }

    protected void sendRequest(Object obj, AsyncTransaction trans) {
        if (mDataChannel != null) {
            mDataChannel.sendRequest(obj, trans);
        }
    }

    protected void cancelRequest() {
        if (mDataChannel != null) {
            mDataChannel.cancelRequest(this);
        }
    }

    @Override
    public void doCancel() {
        super.doCancel();
        cancelRequest();
    }

    public final void run() {
        try {
            if (!isCancel()) {
                onTransact();
            }
        } catch (Exception e) {
            e.printStackTrace();

            doCancel();
            onTransactException(0, e);
            doEnd();
        }
    }

    /**
     * 派发出错事务
     */
    protected abstract void onTransactionError(int errCode, Object obj);

    /**
     * 派发数据回应事务
     */
    protected abstract void onTransactionSuccess(Object obj);

    public Object onDataChannelPreNotify(Object request, Object data,
                                         int notifyType) throws IOException {
        if (data != null) {

            if (data instanceof THttpResponse) {
                THttpResponse response = (THttpResponse) data;
                String charset = null;

                String ContentType = response.getFirstHeader("Content-Type");
                if (ContentType != null) {
                    int i = ContentType.indexOf("=");
                    if (i > 0) {
                        charset = ContentType.substring(i + 1);
                    }
                }

                return readString(request, ((THttpResponse) data).getResponseStream(), charset);
            }
        }
        return null;
    }

    public NotifyTransaction createNotifyTransaction(int notifyType, int code, Object data) {
        return new NotifyTransaction(this, notifyType, code, data);
    }


    @Override
    public void onTransactException(int errorCode, Exception e) {
        notifyError(errorCode, e.toString());
    }
}

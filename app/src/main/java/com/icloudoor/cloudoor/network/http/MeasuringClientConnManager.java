package com.icloudoor.cloudoor.network.http;

import org.apache.http.HttpConnectionMetrics;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

import java.util.concurrent.TimeUnit;

/**
 * Created by Derrick Guan on 7/22/15.
 */

public class MeasuringClientConnManager extends ThreadSafeClientConnManager {

    private WireListener mListener;
    private long mReceivedBytes = -1;
    private long mSentBytes = -1;

    public MeasuringClientConnManager(HttpParams params, SchemeRegistry schreg) {
        super(params, schreg);
    }

    @Override
    public void releaseConnection(ManagedClientConnection conn,
                                  long validDuration, TimeUnit timeUnit) {
        HttpConnectionMetrics metrics = conn.getMetrics();
        mReceivedBytes = metrics.getReceivedBytesCount();
        mSentBytes = metrics.getSentBytesCount();
        metrics.reset();
        rxtx();
        super.releaseConnection(conn, validDuration, timeUnit);
    }

    public void setWireListener(WireListener wl) {
        mListener = wl;
    }

    void rxtx() {
        WireListener l = mListener;
        if (l != null) {
            l.onUpdate(mSentBytes, mReceivedBytes);
        }
    }

    public interface WireListener {
        void onUpdate(long sent, long recv);
    }

}

package com.icloudoor.cloudoor.network.http;

import android.util.Log;

import com.icloudoor.cloudoor.network.frame.Priority;
import com.icloudoor.cloudoor.network.frame.TransTypeCode;
import com.icloudoor.cloudoor.network.http.httpclient.THttp;
import com.icloudoor.cloudoor.network.http.httpclient.THttpFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Derrick Guan on 7/22/15.
 */

public class HttpEngine {

    private static final int MAX_TRY_TIME = 25 * 1000;

    private static HttpEngine mInstance;

    boolean mIsClosed;
    LinkedList<HttpThread> mHttpThreads;
    HttpThread mEmergencyThread;
    PriorityBlockingQueue<THttpRequest> mRequestQueue;

    Map<String, List<THttpRequest>> mRunningRequests;

    THttp mHttp;

    ByteArrayPool mBytePool;


    public HttpEngine(int httpThread, int threadPriority) {
        mRunningRequests = new HashMap<String, List<THttpRequest>>();
        mRequestQueue = new PriorityBlockingQueue<THttpRequest>();
        mHttpThreads = new LinkedList<HttpThread>();

        mBytePool = new ByteArrayPool(8 * 1024);

        for (int i = 0; i < httpThread; i++) {
            HttpThread thread = new HttpThread();
            thread.setPriority(threadPriority);
            thread.start();

            mHttpThreads.add(thread);
        }
    }

    /**
     * 缺省3个线程方式的HttpEngine
     *
     * @return
     */
    public static HttpEngine Instance() {
        if (mInstance == null) {
            mInstance = new HttpEngine(3, Thread.NORM_PRIORITY - 1);
        }
        return mInstance;
    }

    private synchronized THttp getHttp() {
        if (mHttp == null) {
            mHttp = THttpFactory.createHttp();
        }
        return mHttp;
    }

    private synchronized void closeHttp() {
        if (mHttp != null) {
            mHttp.close();
        }
        mHttp = null;
    }

    private void checkStartEmergencyHttpThread(int priority) {
        if ((priority & 0xFF) == Priority.EMERGENCY
                && mEmergencyThread == null) {

            mEmergencyThread = new EmergencyHttpThread();
            mEmergencyThread.start();
        }
    }

    public void addRequest(THttpRequest httpRequest) {

        if (!mIsClosed && httpRequest != null) {
            mRequestQueue.offer(httpRequest);

            checkStartEmergencyHttpThread(httpRequest.getPriority());
        }
    }

    public void adjustPriorityByGID(int gid, int priority) {
        if (!mIsClosed) {
            boolean find = false;
            Iterator<THttpRequest> requests = mRequestQueue.iterator();
            while (requests.hasNext()) {
                THttpRequest request = requests.next();
                if (request.getGroupId() == gid) {
                    request.setPriority(priority);
                    find = true;
                }
            }

            if (find) {
                checkStartEmergencyHttpThread(priority);
            }
        }
    }

    public void shutdown() {
        mIsClosed = true;
        closeHttp();

        if (mEmergencyThread != null) {
            mEmergencyThread.interrupt();
            mEmergencyThread = null;
        }

        if (mHttpThreads != null) {
            for (HttpThread httpThread : mHttpThreads) {
                httpThread.interrupt();
            }
            mHttpThreads.clear();
            mHttpThreads = null;
        }
    }

    private void notifyError(THttpRequest request, int errCode, Object data) {
        if (mIsClosed) {
            return;
        }

        if (request.getHttpCallBack() != null) {
            request.getHttpCallBack().onError(request, errCode, data);
        }
    }

    private void notifyReceived(THttpRequest request, int code, Object data) {
        if (mIsClosed) {
            return;
        }

        if (request.getHttpCallBack() != null) {
            request.getHttpCallBack().onReceived(request, code, data);
        }

    }

    private class HttpThread extends Thread {

        @Override
        public void run() {
            while (!mIsClosed) {
                try {
                    THttpRequest request = mRequestQueue.take();
                    execute(request);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void execute(THttpRequest request) {
            if (request == null) {
                return;
            }

            if (request.isCancel()) {
                notifyError(request, TransTypeCode.ERR_HTTP_CANCEL, null);
            }

            //组装参数
            request.doBefore();

            THttpResponse response = null;
            THttp http = null;

            long time = System.currentTimeMillis();

            for (int i = request.getTryTimes(); ; i--) {
                try {
                    http = getHttp();
                    response = http.executeRequest(request);

                    Object ret = null;
                    if (response != null) {
                        int respCode = response.getResponseCode();

                        if (respCode == 200) {
                            HttpCallBack httpCallback = request.getHttpCallBack();
                            if (httpCallback != null) {
                                ret = httpCallback.onPreReceived(request, response);
                                if (ret != null) {
                                    notifyReceived(request, TransTypeCode.HTTP_SUCCESS, ret);
                                    break;
                                }
                            }
                        } else {
                            HttpCallBack httpCallback = request.getHttpCallBack();
                            if (httpCallback != null) {
                                ret = httpCallback.onPreError(request, response);
                            }
                            notifyError(request, TransTypeCode.ERR_HTTP, ret);
                            break;
                        }
                    } else {
                        notifyError(request, TransTypeCode.ERR_NETWORK_EXCEPTION, null);
                        break;
                    }
                } catch (CancelException e) {
                    notifyError(request, TransTypeCode.ERR_HTTP_CANCEL, null);
                    break;
                } catch (IOException e) {
                    Log.e("HttpEngine", e.getMessage());
                    closeHttp();
                    if (i < 0) {
                        notifyError(request, TransTypeCode.ERR_NETWORK_IO_EXCEPTION, e.getMessage());
                        break;
                    }
                } catch (Exception e) {
                    if (i < 0) {
                        notifyError(request, TransTypeCode.ERR_NETWORK_EXCEPTION, e.getMessage());
                        break;
                    }
                } finally {
                    if (response != null) {
                        response.close();
                    }
                }

                if (System.currentTimeMillis() - time > MAX_TRY_TIME) {
                    notifyError(request, TransTypeCode.ERR_NETWORK_EXCEPTION, null);
                    break;
                }
            }
        }
    }

    /**
     * 有需要紧急处理事务的情况下，创建紧急HttpThread进行处理HttpRequest
     */
    private class EmergencyHttpThread extends HttpThread {

        @Override
        public void run() {
            if (!mIsClosed) {
                THttpRequest request = mRequestQueue.poll();
                execute(request);
            }

            mEmergencyThread = null;
        }
    }

}

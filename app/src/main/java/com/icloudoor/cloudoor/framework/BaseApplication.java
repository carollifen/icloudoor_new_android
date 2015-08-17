package com.icloudoor.cloudoor.framework;

import android.app.Application;

/**
 * Abstract Base Application
 * Created by Derrick Guan on 8/5/15.
 */
public abstract class BaseApplication extends Application {

    public static class BaseException implements Thread.UncaughtExceptionHandler {
        private BaseApplication mBaseApplication;
        public BaseException(BaseApplication app) {
            mBaseApplication = app;
        }

        @Override
        public void uncaughtException(Thread thread, Throwable exception) {
            // TODO 捕捉到异常后，将异常上传到友盟错误统计
            mBaseApplication.onExceptionExit(exception);
        }

        public static void init(BaseApplication app) {
            Thread.setDefaultUncaughtExceptionHandler(new BaseException(app));
        }
    }
    /**
     * 当程序异常退出时调用
     * s
     * @param exception 异常
     */
    public abstract void onExceptionExit(Throwable exception) ;

}

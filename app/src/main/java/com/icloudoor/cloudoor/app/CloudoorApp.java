package com.icloudoor.cloudoor.app;

import com.icloudoor.cloudoor.PreferMgr.CloudoorPreHelper;
import com.icloudoor.cloudoor.database.AccountManager;
import com.icloudoor.cloudoor.framework.BaseApplication;
import com.icloudoor.cloudoor.framework.FrameworkActivityManager;
import com.icloudoor.cloudoor.network.frame.BaseService;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Cloudoor Application
 * Created by Derrick Guan on 7/20/15.
 */
public class CloudoorApp extends BaseApplication {

    private static CloudoorApp sApp;

    public static CloudoorApp getAppInstance() {
        if (sApp == null) {
            throw new NullPointerException("sApp not create or be terminated!");
        }
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        // 初始化网络层框架服务
        BaseService.initServiceContext(sApp);
        // 初始化BaseApplication的异常捕捉服务
        BaseException.init(sApp);
    }

    @Override
    public void onExceptionExit(Throwable exception) {
        if (exception != null) {
            StringWriter sw = new StringWriter();
            exception.printStackTrace(new PrintWriter(sw));
            // 可将exception写到本地某个文件当中
        }
        finishApp();
    }

    /**
     * 通过back键退出应用时调用
     */
    public void onExitApp() {
        finishApp();
    }

    /**
     * 退出应用时，调用清除app资源的框架代码
     */
    public void finishApp() {
        FrameworkActivityManager.getInstance().FinishAllActivity();
    }

    public void doLogout() {
        AccountManager.getInstance().logout();
        // 清除sid
        CloudoorPreHelper.putUserSid("");
    }
}

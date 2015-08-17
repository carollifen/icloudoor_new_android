package com.icloudoor.cloudoor.activity.frame;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.icloudoor.cloudoor.app.CloudoorApp;
import com.icloudoor.cloudoor.network.protocol.CloudoorCallback;
import com.icloudoor.cloudoor.network.protocol.CloudoorService;

/**
 * 云门Activity基类
 * Created by Derrick Guan on 8/5/15.
 */
public abstract class CloudoorBaseActivity extends BaseActivity {

    protected static void startActivity(Activity context, Bundle bundle, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloudoorService.getInstance().addListener(mBaseCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO 环信相关操作
        // TODO 友盟相关操作
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CloudoorService.getInstance().removeListener(mBaseCallback);
    }


    private boolean bIsLogoutDialogShowing;
    private AlertDialog mDialog = null;

    // 显示强制退出对话框
    private void showLogoutDialog() {
        // TODO 创建对话框
    }


    private CloudoorCallback mBaseCallback = new CloudoorCallback() {
        @Override
        public void onSidInvalidError(int tid, String err) {
            if (!bIsLogoutDialogShowing) {
                // TODO showToast for test
                showToast("sid 过期");
                // TODO 创建对话框
                showLogoutDialog();
            }
        }
    };

    // 退出操作
    private void doLogout() {
        CloudoorApp.getAppInstance().doLogout();
        finish();
        // TODO 跳转到某一个页面
    }


    /**
     * ******* Waiting 相关*********
     */
    private ProgressDialog mWaiting;

    public void showWaiting(@StringRes int msgResId) {
        String message = getString(msgResId);
        showWaiting(message);
    }

    public void showWaiting(@StringRes int titleResId, @StringRes int msgResId) {
        String title = getString(titleResId);
        String message = getString(msgResId);
        showWaiting(title, message);
    }

    public void showWaiting(String message) {
        showWaiting(null, message);
    }

    public void showWaiting(String title, String message) {
        showWaiting(title, message, true);
    }

    public void showWaiting(String tile, String message, boolean cancelable) {
        if (mWaiting != null) {
            stopWaiting();
        }
        mWaiting = ProgressDialog.show(this, tile, message, true, cancelable);
    }

    /**
     * 停止Waiting
     */
    public void stopWaiting() {
        if (mWaiting != null) {
            mWaiting.dismiss();
            mWaiting = null;
        }
    }


    /**
     * ******* Toast 相关*********
     */

    public void showToast(@StringRes int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }

    public void showToast(@StringRes int resId, int duration) {
        Toast.makeText(this, resId, duration).show();
    }

    public void showToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    public void showToast(String message, int duration) {
        Toast.makeText(this, message, duration).show();
    }

    public void showToast(CharSequence message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    public void showToast(CharSequence message, int duration) {
        Toast.makeText(this, message, duration).show();
    }

}

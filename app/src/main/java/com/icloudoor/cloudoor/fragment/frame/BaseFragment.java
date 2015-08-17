package com.icloudoor.cloudoor.fragment.frame;

import android.app.ProgressDialog;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Fragment基类
 * <br>提供一些公用的方法<br/>
 * Created by Derrick Guan on 8/6/15.
 */
public class BaseFragment extends Fragment {


    /********** Waiting 相关**********/
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
        mWaiting = ProgressDialog.show(getActivity(), tile, message, true, cancelable);
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

    /********** Waiting 相关**********/

    /********** Toast 相关**********/

    public void showToast(@StringRes int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }

    public void showToast(@StringRes int resId, int duration) {
        Toast.makeText(getActivity(), resId, duration).show();
    }

    public void showToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    public void showToast(String message, int duration) {
        Toast.makeText(getActivity(), message, duration).show();
    }

    public void showToast(CharSequence message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    public void showToast(CharSequence message, int duration) {
        Toast.makeText(getActivity(), message, duration).show();
    }

    /********** Toast 相关**********/
}

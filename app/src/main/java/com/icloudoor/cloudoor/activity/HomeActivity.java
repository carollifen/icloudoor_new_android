package com.icloudoor.cloudoor.activity;


import android.support.v4.app.Fragment;

import com.icloudoor.cloudoor.R;
import com.icloudoor.cloudoor.activity.frame.CloudoorFragmentActivity;
import com.icloudoor.cloudoor.app.CloudoorApp;
import com.icloudoor.cloudoor.fragment.HomeFragment;
import com.icloudoor.cloudoor.fragment.frame.BackHandleInterface;
import com.icloudoor.cloudoor.fragment.frame.HandleBackFragment;

/**
 * 主页面Activity
 * HomeFragment的托盘
 * Created by Derrick Guan on 8/8/15.
 */
public class HomeActivity extends CloudoorFragmentActivity implements BackHandleInterface {

    private HandleBackFragment mFragment;

    @Override
    protected Fragment createFragment() {
        return new HomeFragment();
    }

    @Override
    public void setSelectedFragment(HandleBackFragment selectedFragment) {
        mFragment = selectedFragment;
    }

    private void onExit() {
        finish();
        CloudoorApp.getAppInstance().onExitApp();
    }

    // 上一次按物理返回键的时间
    private long mLastDownTime = 0;
    @Override
    public void onBackPressed() {
        if (mFragment == null || !mFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                if (System.currentTimeMillis() - mLastDownTime > 2000) {
                    mLastDownTime = System.currentTimeMillis();
                    showToast(R.string.exit_tip);
                } else {
                    onExit();
                }
            }
        }
    }
}

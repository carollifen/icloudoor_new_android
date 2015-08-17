package com.icloudoor.cloudoor.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.icloudoor.cloudoor.activity.frame.CloudoorFragmentActivity;
import com.icloudoor.cloudoor.fragment.DownloadDoorFragment;
import com.icloudoor.cloudoor.fragment.GetUserProfileFragment;

/**
 * 测试类
 * <p/>
 * Created by Derrick Guan on 7/24/15.
 */
public class TestActivity extends CloudoorFragmentActivity {

    private static final String EXTRA_TYPE = "type";

    // 获取用户资料页
    public static final int GET_USER_PROFILE = 0;
    // 下载门锁页
    public static final int DOWNLOAD_DOOR = 1;

    public static void startActivity(Activity context, int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_TYPE, type);
        startActivity(context, bundle, TestActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        int type = -1;
        if (getIntent().getExtras() != null) {
            type = getIntent().getIntExtra(EXTRA_TYPE, -1);
        }
        Fragment fragment = null;
        switch (type) {
            case GET_USER_PROFILE:
                fragment = new GetUserProfileFragment();
                break;
            case DOWNLOAD_DOOR:
                fragment = new DownloadDoorFragment();
                break;
        }
        return fragment;
    }

}

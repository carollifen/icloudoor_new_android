package com.icloudoor.cloudoor.activity.frame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.icloudoor.cloudoor.framework.FrameworkActivityManager;

/**
 * Activity基类
 * <br>主要用于与FrameworkActivityManger交互<br/>
 * <br>实现app内部自身的管理<br/>
 * Created by Derrick on 8/5/15.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameworkActivityManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        FrameworkActivityManager.getInstance().removeActivity(this);
        super.onDestroy();
    }
}

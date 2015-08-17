package com.icloudoor.cloudoor.activity.frame;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;

import com.icloudoor.cloudoor.R;

/**
 * 所有用Fragment作为UI显示逻辑的Activity抽象类
 * Created by Derrick Guan on 8/5/15.
 */
public abstract class CloudoorFragmentActivity extends CloudoorBaseActivity {

    /**
     * 具体创建Fragment的逻辑由各子类实现
     * @return Fragment
     */
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setId(R.id.activity_container_id);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        root.setLayoutParams(lp);
        setContentView(root);

        if (findViewById(R.id.activity_container_id) != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment fragment = createFragment();
            ft.add(R.id.activity_container_id, fragment)
                    .commit();
        }
    }
}

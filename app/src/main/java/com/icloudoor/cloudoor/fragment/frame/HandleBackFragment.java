package com.icloudoor.cloudoor.fragment.frame;

import android.os.Bundle;

/**
 * 需要针对物理返回键有特别处理的Fragment抽象类
 * Created by Derrick Guan on 8/10/15.
 */
public abstract class HandleBackFragment extends BaseFragment {

    public abstract boolean onBackPressed();

    protected BackHandleInterface mBackHandledInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof BackHandleInterface)) {
            throw new ClassCastException(
                    "Hosting Activity must implement BackHandledInterface");
        } else {
            this.mBackHandledInterface = (BackHandleInterface) getActivity();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mBackHandledInterface.setSelectedFragment(this);
    }
}

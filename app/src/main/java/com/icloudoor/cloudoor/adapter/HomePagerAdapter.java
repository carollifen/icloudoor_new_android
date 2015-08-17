package com.icloudoor.cloudoor.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.icloudoor.cloudoor.fragment.frame.BaseFragment;
import com.icloudoor.cloudoor.fragment.GetUserProfileFragment;
import com.icloudoor.cloudoor.fragment.HomeFragment;


/**
 * HomePagerAdapter
 * Created by Derrick Guan on 8/8/15.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private int[] mTabImageResIds;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = null;
        switch (position) {
            case HomeFragment.TAB_INDEX_KEY:
                // TODO for test
                fragment = new GetUserProfileFragment();
                break;
            case HomeFragment.TAB_INDEX_MESSAGE:
                // TODO for test
                fragment = new GetUserProfileFragment();
                break;
            case HomeFragment.TAB_INDEX_SERVICE:
                // TODO for test
                fragment = new GetUserProfileFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }


    public int getTabImageRedIds(int position) {
        if (mTabImageResIds == null || position < 0 || position > mTabImageResIds.length) {
            return -1;
        }
        return mTabImageResIds[position];
    }

    public void setTabImageResIds(int[] resIds) {
         mTabImageResIds = resIds;
    }
}

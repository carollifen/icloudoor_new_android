package com.icloudoor.cloudoor.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.icloudoor.cloudoor.R;
import com.icloudoor.cloudoor.adapter.HomePagerAdapter;
import com.icloudoor.cloudoor.fragment.frame.HandleBackFragment;
import com.icloudoor.cloudoor.view.CustomViewPager;
import com.icloudoor.cloudoor.view.SlidingTabLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * 主页面Fragment
 * <br>包含一个顶部Toolbar & ViewPager<br/>
 * Created by Derrick Guan on 8/8/15.
 */
public class HomeFragment extends HandleBackFragment {

    // 钥匙开门Fragment
    public static final int TAB_INDEX_KEY = 0;
    // 消息Fragment
    public static final int TAB_INDEX_MESSAGE = 1;
    // 物业服务Fragment
    public static final int TAB_INDEX_SERVICE = 2;
    // 当前显示的Fragment
    private int mCurrentTab;

    // 顶部bar
    private SlidingTabLayout mSlidingTabLayout;

    // 包含主页面各个Fragment的ViewPager
    private CustomViewPager mHomeViewPager;
    private HomePagerAdapter mHomePagerAdapter;

    // 抽屉布局
    private DrawerLayout mDrawer;


    @IntDef({TAB_INDEX_KEY, TAB_INDEX_MESSAGE, TAB_INDEX_SERVICE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HomeTab {
    }

    @HomeTab
    public int getHomeTab() {
        return mCurrentTab;
    }

    public void setHomeTab(@HomeTab int tab) {
        mCurrentTab = tab;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 用户点击物理返回键
     *
     * @return boolean
     */
    @Override
    public boolean onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.END)) {
            mDrawer.closeDrawer(GravityCompat.END);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                openDrawerOrNot(GravityCompat.END);
                return false;
            }
        });
        toolbar.setContentInsetsAbsolute(0, 0);

        mDrawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);

        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);

        mHomePagerAdapter = new HomePagerAdapter(getChildFragmentManager());
        mHomeViewPager = (CustomViewPager) view.findViewById(R.id.home_viewpager);
        mHomeViewPager.setAdapter(mHomePagerAdapter);
        mHomeViewPager.setOffscreenPageLimit(3);

        // TODO testCode
        HomeTabView tab1 = new HomeTabView(getActivity());
        tab1.setView(R.drawable.ic_launcher, "01", 0);
        HomeTabView tab2 = new HomeTabView(getActivity());
        tab2.setView(R.drawable.ic_launcher, "02", 1);
        HomeTabView tab3 = new HomeTabView(getActivity());
        tab3.setView(R.drawable.ic_launcher, "03", 2);
        List<View> tabViews = new ArrayList<>();
        tabViews.add(tab1);
        tabViews.add(tab2);
        tabViews.add(tab3);

        mSlidingTabLayout.setViewPager(mHomeViewPager, tabViews);
        mSlidingTabLayout.setSelectedIndicatorColors(R.color.style_color_accent);
    }

    private void openDrawerOrNot(int gravity) {
        if (!mDrawer.isDrawerOpen(gravity)) {
            mDrawer.openDrawer(gravity);
        } else {
            mDrawer.closeDrawer(gravity);
        }
    }


    /**
     * 主页面顶部栏: 自定义TabView
     */
    private class HomeTabView extends FrameLayout {

        private ImageView icon;
        private TextView name;
        private TextView point;

        private HomeTabView(Context context) {
            super(context);
            init();
        }

        private void init() {
            View v = inflate(getActivity(), R.layout.view_home_tab, this);
            icon = (ImageView) v.findViewById(R.id.home_tab_iv);
            name = (TextView) v.findViewById(R.id.home_tab_tv);
            point = (TextView) v.findViewById(R.id.home_tab_point);
        }

        private void setView(@DrawableRes int drawableRes, String tabName, int count) {
            icon.setImageResource(drawableRes);
            name.setText(tabName);
            if (count <= 0) {
                point.setVisibility(View.INVISIBLE);
            } else {
                point.setText(String.valueOf(count));
            }
        }

    }

}

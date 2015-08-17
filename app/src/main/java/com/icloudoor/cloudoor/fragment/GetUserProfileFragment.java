package com.icloudoor.cloudoor.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icloudoor.cloudoor.R;
import com.icloudoor.cloudoor.fragment.frame.BaseFragment;
import com.icloudoor.cloudoor.network.bean.meta.UserBasicInfo;
import com.icloudoor.cloudoor.network.protocol.CloudoorCallback;
import com.icloudoor.cloudoor.network.protocol.CloudoorService;

/**
 * 获取用户资料页
 * Created by Derrick Guan on 7/28/15.
 */
public class GetUserProfileFragment extends BaseFragment {

    private TextView mContentTv;

    // 网络请求tid
    private int mTid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloudoorService.getInstance().addListener(mCallback);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        doGetUserProfile();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CloudoorService.getInstance().removeListener(mCallback);
    }

    private void initView(View view) {
        mContentTv = (TextView) view.findViewById(R.id.content_tv);
    }


    private void doGetUserProfile() {
        mTid = CloudoorService.getInstance().doGetProfile();
        showWaiting("waiting");
    }

    private CloudoorCallback mCallback = new CloudoorCallback() {
        @Override
        public void onGetProfile(int tid, UserBasicInfo userBasicInfo) {
            if (mTid != tid) {
                return;
            }
            stopWaiting();
            StringBuilder sb = new StringBuilder();
            sb.append("User Info : ")
                    .append(userBasicInfo.getMobile()).append("\n")
                    .append(userBasicInfo.getUserName()).append("\n")
                    .append(userBasicInfo.getIdCardNo()).append("\n")
                    .append(userBasicInfo.getPortraitUrl()).append("\n")
                    .append(userBasicInfo.getRole()).append("\n")
                    .append(userBasicInfo.getNickname()).append("\n");
            mContentTv.setText(sb.toString());
            Log.e("Portrait", userBasicInfo.getPortraitUrl());
        }

        @Override
        public void onGetProfileError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            stopWaiting();
            showToast(err);
        }
    };
}

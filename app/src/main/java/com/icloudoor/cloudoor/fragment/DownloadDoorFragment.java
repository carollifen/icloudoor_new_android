package com.icloudoor.cloudoor.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icloudoor.cloudoor.R;
import com.icloudoor.cloudoor.fragment.frame.BaseFragment;
import com.icloudoor.cloudoor.network.bean.DownloadDoorBean;
import com.icloudoor.cloudoor.network.protocol.CloudoorCallback;
import com.icloudoor.cloudoor.network.protocol.CloudoorService;

/**
 * 获取门锁测试类
 * <br>可以在网络请求回调成功的方法中将数据打印出来<br/>
 *
 * Created by Derrick Guan on 7/28/15.
 */
public class DownloadDoorFragment extends BaseFragment {

    private TextView mContentTv;

    // 网络请求tid
    private int mTid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloudoorService.getInstance().addListener(mCallback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CloudoorService.getInstance().removeListener(mCallback);
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
        doDownloadDoor();
    }

    private void doDownloadDoor() {
        mTid = CloudoorService.getInstance().doDownloadDoor();
    }

    private void initView(View view) {
        mContentTv = (TextView) view.findViewById(R.id.content_tv);
    }

    private CloudoorCallback mCallback = new CloudoorCallback() {
        @Override
        public void onDownloadDoor(int tid, DownloadDoorBean bean) {
            if (mTid != tid) {
                return;
            }
            showToast("Download Success");
        }

        @Override
        public void onDownloadDoorError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast("Download Fail");
        }
    };
}

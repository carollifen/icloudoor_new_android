package com.icloudoor.cloudoor.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icloudoor.cloudoor.R;
import com.icloudoor.cloudoor.fragment.frame.BaseFragment;
import com.icloudoor.cloudoor.icdcrypto.ICDCrypto;
import com.icloudoor.cloudoor.network.bean.DownloadDoorBean;
import com.icloudoor.cloudoor.network.bean.meta.Config;
import com.icloudoor.cloudoor.network.bean.meta.UserBasicInfo;
import com.icloudoor.cloudoor.network.protocol.CloudoorCallback;
import com.icloudoor.cloudoor.network.protocol.CloudoorService;
import com.icloudoor.cloudoor.service.CloudoorBluetoothLeService;
import com.icloudoor.cloudoor.utils.PlatformUtil;

/**
 * Created by flying on 2015/8/15.
 */
public class KeyFragment extends BaseFragment {
    private CloudoorBluetoothLeService mBluetoothLeService;
    private String mDoorId;

    private int mDownloadKeyTid;
    private int mGrabRedTid;
    private int mUpdateCarPositionStateTid;
    private int mGetDefaultConfigTid;
    private int mGetProfileTid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CloudoorService.getInstance().addListener(mCallback);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_key, null);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        CloudoorService.getInstance().removeListener(mCallback);
    }

    /**
     * 下载钥匙
     */
    private void doDownloadKey() {
        mDownloadKeyTid = CloudoorService.getInstance().doDownloadDoor();
    }


    /**
     * 更新车的位置状态，在小区外或内
     */
    private void doUpdateCarPositonStatus() {
//        mUpdateCarPositionStateTid =  CloudoorService.getInstance().doUpdateCarPosStatus()；
    }


    /**
     * 获取用户资料
     */
    private void doGetProfile() {
        mGetProfileTid = CloudoorService.getInstance().doGetProfile();
    }


    /**
     * 获取用户配置,得到钥匙无联网状态下可使用次数和天数；联网后自动刷新
     */
    private void doGetDefaultConfig() {
        mGetDefaultConfigTid = CloudoorService.getInstance().doGetDefaultConfig();
    }

    /**
     * 抢红包
     *
     * @param doorId
     */
    private void doGrabRed(String doorId) {
        mGrabRedTid = CloudoorService.getInstance().doGrabRed(doorId);
    }

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (CloudoorBluetoothLeService.ACTION_DO_OPEN_DOOR.equals(action)) {
                if (ICDCrypto.checkIfOpenDoorSuccess(intent.getByteArrayExtra(CloudoorBluetoothLeService.EXTRA_DATA))) {
                    if (PlatformUtil.hasConnected()) {
                        doGrabRed(mDoorId);
                    }
                    //TODO
                }
            } else if (CloudoorBluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                //TODO
            }
        }
    };

    private CloudoorCallback mCallback = new CloudoorCallback() {
        @Override
        public void onDownloadDoor(int tid, DownloadDoorBean bean) {
        }

        @Override
        public void onDownloadDoorError(int tid, String err) {
        }


        @Override
        public void onUpdateCarPosStatus(int tid) {
        }

        @Override
        public void onUpdateCarPosStatusError(int tid, String err) {
        }


        @Override
        public void onGetProfile(int tid, UserBasicInfo userBasicInfo) {
        }

        @Override
        public void onGetProfileError(int tid, String err) {
        }

        @Override
        public void onGetDefaultConfig(int tid, Config config) {
        }

        @Override
        public void onGetDefaultConfigError(int tid, String err) {
        }

        @Override
        public void onGrabRed(int tid) {
        }

        @Override
        public void onGrabRedError(int tid, String err) {
        }

    };
}

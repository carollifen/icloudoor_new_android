package com.icloudoor.cloudoor.activity;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.icloudoor.cloudoor.R;
import com.icloudoor.cloudoor.activity.frame.CloudoorBaseActivity;
import com.icloudoor.cloudoor.app.CloudoorConstants;
import com.icloudoor.cloudoor.network.bean.GetFamilyAndCarsBean;
import com.icloudoor.cloudoor.network.bean.GetMyAuthKeysBean;
import com.icloudoor.cloudoor.network.bean.GetMyBorrowKeysBean;
import com.icloudoor.cloudoor.network.bean.GetMyKeysBean;
import com.icloudoor.cloudoor.network.bean.LoginBean;
import com.icloudoor.cloudoor.network.bean.SignBean;
import com.icloudoor.cloudoor.network.bean.meta.AbstractBanner;
import com.icloudoor.cloudoor.network.bean.meta.Almanac;
import com.icloudoor.cloudoor.network.bean.meta.AnnouncementBanner;
import com.icloudoor.cloudoor.network.bean.meta.Config;
import com.icloudoor.cloudoor.network.bean.meta.Friend;
import com.icloudoor.cloudoor.network.bean.meta.HXAccount;
import com.icloudoor.cloudoor.network.bean.meta.LinkBanner;
import com.icloudoor.cloudoor.network.bean.meta.Mobile;
import com.icloudoor.cloudoor.network.bean.meta.MyAddress;
import com.icloudoor.cloudoor.network.protocol.CloudoorCallback;
import com.icloudoor.cloudoor.network.protocol.CloudoorService;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends CloudoorBaseActivity {

    private Toolbar mToolbar;

    // 发送验证码接口按钮
    private TextView mSendVerifyCodeBtn;
    // 登录接口按钮
    private TextView mLoginBtn;
    // 获取用户资料接口按钮
    private TextView mGetProfileBtn;

    // 昵称输入
    private EditText mNicknameInput;
    // 更新用户资料接口按钮
    private TextView mUpdateProfileBtn;

    // 选择图片按钮
    private TextView mSelectPicBtn;
    // 头像显示
    private ImageView mPortraitIv;

    // 验证密码接口按钮
    private TextView mVerifyPswBtn;

    // 下载门锁信息接口按钮
    private TextView mDownloadDoorBtn;

    // 获取我的地址接口按钮
    private TextView mGetMyAddressBtn;

    // 获取家庭成员手机接口按钮
    private TextView mGetFamilyUserBtn;

    // 获取用户标签接口按钮
    private TextView mGetTagsBtn;

    // 获取用户配置接口按钮
    private TextView mGetConfigBtn;

    // 获取未读的公告数以及问卷调查数接口按钮
    private TextView mGetPropNotifyBtn;

    // 获取Banners接口按钮
    private TextView mGetBannersBtn;

    // 获取黄历接口按钮
    private TextView mGetAlmanacBtn;

    // 上班签到接口按钮
    private TextView mSignOnBtn;
    // 下班签到接口按钮
    private TextView mSignOffBtn;

    // 获取服务器时间接口按钮
    private TextView mGetServerTimeBtn;

    // 抢红包接口按钮
    private TextView mGrabRedBtn;

    // 获取用户的IM登录账号接口按钮
    private TextView mGetIMAccountBtn;

    // 获取好友列表接口按钮
    private TextView mGetFriendsBtn;

    // 获取我的钥匙接口按钮
    private TextView mGetMyKeysBtn;
    // 获取我借入的钥匙接口按钮
    private TextView mGetMyBorrowKeysBtn;
    // 获取我授权的钥匙接口按钮
    private TextView mGetMyAuthKeysBtn;


    // 如果请求不是并发的话，只定义个tid即可；如果同时发出多个请求，需要定义多个tid
    private int mTid;

    // For Test !
    private String mZoneUserId;

    private static final int REQUEST_ALBUM_PHOTO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        // 必须在Activity或Fragment的onCreate方法中调用
        // CloudoorService.getInstance().addListener(CloudoorCallback callback);
        CloudoorService.getInstance().addListener(mCallback);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 必须在Activity或Fragment的onDestroy方法中调用
        // CloudoorService.getInstance().removeListener(CloudoorCallback callback);
        // 以防止内存泄漏
        CloudoorService.getInstance().removeListener(mCallback);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_white);

        mSendVerifyCodeBtn = (TextView) findViewById(R.id.send_verify_code_btn);
        mSendVerifyCodeBtn.setOnClickListener(mOnClick);

        mLoginBtn = (TextView) findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(mOnClick);

        mGetProfileBtn = (TextView) findViewById(R.id.get_profile_btn);
        mGetProfileBtn.setOnClickListener(mOnClick);

        mNicknameInput = (EditText) findViewById(R.id.nickname_input);
        mUpdateProfileBtn = (TextView) findViewById(R.id.update_profile_btn);
        mUpdateProfileBtn.setOnClickListener(mOnClick);

        mSelectPicBtn = (TextView) findViewById(R.id.select_pic_btn);
        mSelectPicBtn.setOnClickListener(mOnClick);
        mPortraitIv = (ImageView) findViewById(R.id.pic_iv);

        mVerifyPswBtn = (TextView) findViewById(R.id.verify_password_btn);
        mVerifyPswBtn.setOnClickListener(mOnClick);

        mDownloadDoorBtn = (TextView) findViewById(R.id.download_door_btn);
        mDownloadDoorBtn.setOnClickListener(mOnClick);

        mGetMyAddressBtn = (TextView) findViewById(R.id.get_my_address_btn);
        mGetMyAddressBtn.setOnClickListener(mOnClick);

        mGetFamilyUserBtn = (TextView) findViewById(R.id.get_family_btn);
        mGetFamilyUserBtn.setOnClickListener(mOnClick);

        mGetTagsBtn = (TextView) findViewById(R.id.get_tags_btn);
        mGetTagsBtn.setOnClickListener(mOnClick);

        mGetConfigBtn = (TextView) findViewById(R.id.get_config_btn);
        mGetConfigBtn.setOnClickListener(mOnClick);

        mGetPropNotifyBtn = (TextView) findViewById(R.id.get_prop_notify_btn);
        mGetPropNotifyBtn.setOnClickListener(mOnClick);

        mGetBannersBtn = (TextView) findViewById(R.id.get_banners_btn);
        mGetBannersBtn.setOnClickListener(mOnClick);

        mGetAlmanacBtn = (TextView) findViewById(R.id.get_almanac_btn);
        mGetAlmanacBtn.setOnClickListener(mOnClick);

        mSignOnBtn = (TextView) findViewById(R.id.sign_on_btn);
        mSignOffBtn = (TextView) findViewById(R.id.sign_off_btn);
        mSignOnBtn.setOnClickListener(mOnClick);
        mSignOffBtn.setOnClickListener(mOnClick);

        mGetServerTimeBtn = (TextView) findViewById(R.id.get_server_time_btn);
        mGetServerTimeBtn.setOnClickListener(mOnClick);

        mGrabRedBtn = (TextView) findViewById(R.id.grab_red_btn);
        mGrabRedBtn.setOnClickListener(mOnClick);

        mGetIMAccountBtn = (TextView) findViewById(R.id.get_im_account_btn);
        mGetIMAccountBtn.setOnClickListener(mOnClick);

        mGetFriendsBtn = (TextView) findViewById(R.id.get_friends_btn);
        mGetFriendsBtn.setOnClickListener(mOnClick);

        mGetMyKeysBtn = (TextView) findViewById(R.id.get_my_keys_btn);
        mGetMyBorrowKeysBtn = (TextView) findViewById(R.id.get_my_borrow_keys_btn);
        mGetMyAuthKeysBtn = (TextView) findViewById(R.id.get_my_auth_keys_btn);
        mGetMyKeysBtn.setOnClickListener(mOnClick);
        mGetMyBorrowKeysBtn.setOnClickListener(mOnClick);
        mGetMyAuthKeysBtn.setOnClickListener(mOnClick);

    }

    private void doSendVerifyCode() {
        mTid = CloudoorService.getInstance().doSendVerifyCode("13632421088");
    }

    private void doLogin() {
        mTid = CloudoorService.getInstance().doLogin("13632421088", "123456");
    }


    private void doUpdateProfile() {

        String nickname = mNicknameInput.getEditableText().toString();
        if (!TextUtils.isEmpty(nickname)) {
            Map<String, String> profileMap = new HashMap<String, String>();

            profileMap.put("nickname", nickname);
            mTid = CloudoorService.getInstance().doUpdateProfile(profileMap);
        }
    }

    private void doUploadPortrait(File file) {
        mTid = CloudoorService.getInstance().doUploadPortrait(file);
        showWaiting("Uploading Portrait");
    }

    private void doUpdatePortrait(String portraitUrl) {
        if (!TextUtils.isEmpty(portraitUrl)) {
            Map<String, String> profileMap = new HashMap<String, String>();
            profileMap.put("portraitUrl", portraitUrl);
            mTid = CloudoorService.getInstance().doUpdateProfile(profileMap);

        }
    }

    private void doGetMyAddress() {
        mTid = CloudoorService.getInstance().doGetMyAddress();
    }

    private void doVerifyPassword(String mobile, String password) {
        mTid = CloudoorService.getInstance().doVerifyPassword(mobile, password);
    }

    private void doGetFamilyAndCars(String zoneUserId) {
        if (TextUtils.isEmpty(zoneUserId)) {
            showToast("zoneUserId为空，先点击获取我的地址请求按钮");
            return;
        }
        mTid = CloudoorService.getInstance().doGetFamilyAndCars(zoneUserId);
    }

    private void doGetTags() {
        mTid = CloudoorService.getInstance().doGetTags();
    }

    private void doGetConfig() {
        mTid = CloudoorService.getInstance().doGetDefaultConfig();
    }

    private void doGetPropNotifyCount() {
        mTid = CloudoorService.getInstance().doGetPropNotifyCounts();
    }

    private void doGetBanners() {
        mTid = CloudoorService.getInstance().doGetBanners();
    }

    private void doGetAlmanac() {
        mTid = CloudoorService.getInstance().doGetAlmanac("2015-07-30");
    }

    private void doSign(String type) {
        mTid = CloudoorService.getInstance().doSign("14340740191200111415", type);
    }

    private void doGetServerTime() {
        mTid = CloudoorService.getInstance().doGetServerTime();
    }

    private void doGrabRed(String doorId) {
        mTid = CloudoorService.getInstance().doGrabRed(doorId);
    }

    private void doGetIMAccount() {
        mTid = CloudoorService.getInstance().doGetIMAccount();
    }

    private void doGetFriends() {
        mTid = CloudoorService.getInstance().doGetFriends();
    }

    private void doGetMyKeys() {
        mTid = CloudoorService.getInstance().doGetMyKeys();
    }

    private void doGetMyBorrowKeys() {
        mTid = CloudoorService.getInstance().doGetMyBorrowKeys();
    }

    private void doGetMyAuthKeys() {
        mTid = CloudoorService.getInstance().doGetMyAuthKeys();
    }

    private String getFilePathFromContentUri(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(
                this,
                uri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_ALBUM_PHOTO:
                if (data != null) {
                    Uri uri = data.getData();
                    Glide.with(this)
                            .load(uri)
                            .centerCrop()
                            .into(mPortraitIv);
                    File file = new File(getFilePathFromContentUri(uri));
                    doUploadPortrait(file);
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private View.OnClickListener mOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.send_verify_code_btn:
                    doSendVerifyCode();
                    break;
                case R.id.login_btn:
                    doLogin();
                    break;
                case R.id.get_profile_btn:
                    TestActivity.startActivity(MainActivity.this, TestActivity.GET_USER_PROFILE);
                    break;
                case R.id.update_profile_btn:
                    doUpdateProfile();
                    break;
                case R.id.select_pic_btn:
                    Uri uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
                    Intent intent = new Intent(Intent.ACTION_PICK, uri);
                    startActivityForResult(intent, REQUEST_ALBUM_PHOTO);
                    break;
                case R.id.verify_password_btn:
                    doVerifyPassword("13632421088", "123456");
                    break;
                case R.id.download_door_btn:
                    TestActivity.startActivity(MainActivity.this, TestActivity.DOWNLOAD_DOOR);
                    break;
                case R.id.get_my_address_btn:
                    doGetMyAddress();
                    break;
                case R.id.get_family_btn:
                    doGetFamilyAndCars(mZoneUserId);
                    break;
                case R.id.get_tags_btn:
                    doGetTags();
                    break;
                case R.id.get_config_btn:
                    doGetConfig();
                    break;
                case R.id.get_prop_notify_btn:
                    doGetPropNotifyCount();
                    break;
                case R.id.get_banners_btn:
                    doGetBanners();
                    break;
                case R.id.get_almanac_btn:
                    doGetAlmanac();
                    break;
                case R.id.sign_on_btn:
                    doSign(CloudoorConstants.SignType.OnDuty);
                    break;
                case R.id.sign_off_btn:
                    doSign(CloudoorConstants.SignType.OffDuty);
                    break;
                case R.id.get_server_time_btn:
                    doGetServerTime();
                    break;
                case R.id.grab_red_btn:
                    doGrabRed("14340740191200111415");
                    break;
                case R.id.get_im_account_btn:
                    doGetIMAccount();
                    break;
                case R.id.get_friends_btn:
                    doGetFriends();
                    break;
                case R.id.get_my_keys_btn:
                    doGetMyKeys();
                    break;
                case R.id.get_my_borrow_keys_btn:
                    doGetMyBorrowKeys();
                    break;
                case R.id.get_my_auth_keys_btn:
                    doGetMyAuthKeys();
                    break;
            }
        }
    };

    private CloudoorCallback mCallback = new CloudoorCallback() {

        @Override
        public void onSendVerifyCode(int tid) {
            if (mTid != tid) {
                return;
            }
            showToast("Success");
        }

        @Override
        public void onSendVerifyCodeError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onLogin(int tid, LoginBean bean) {
            if (mTid != tid) {
                return;
            }
            showToast(bean.getInfo().toString());
        }

        @Override
        public void onLoginError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onUpdateProfile(int tid) {
            if (mTid != tid) {
                return;
            }
            mNicknameInput.setText("");
            showToast("Update Success");

        }

        @Override
        public void onUpdateProfileError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onUploadPortrait(int tid, String portraitUrl) {
            if (mTid != tid) {
                return;
            }
            stopWaiting();
            showToast("Upload Portrait Success");
            // 上传头像成功，然后更新用户头像
            doUpdatePortrait(portraitUrl);
        }

        @Override
        public void onUploadPortraitError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            stopWaiting();
            showToast(err);
        }

        @Override
        public void onVerifyPassword(int tid, boolean isPswCorrect) {
            if (mTid != tid) {
                return;
            }

            if (isPswCorrect) {
                showToast("密码正确");
            } else {
                showToast("密码错误");
            }
        }

        @Override
        public void onVerifyPasswordError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onGetMyAddress(int tid, List<MyAddress> myAddressList) {
            if (mTid != tid) {
                return;
            }
            if (myAddressList != null && myAddressList.size() > 0) {
                MyAddress myAddress = myAddressList.get(0);
                String l1ZoneId = myAddress.getL1ZoneId();
                String zoneUserId = myAddress.getZoneUserId();
                String address = myAddress.getAddress();

                showToast("l1ZoneId : " + l1ZoneId + " zoneUserId : " + zoneUserId + " address : " + address);

                // For Test!
                mZoneUserId = zoneUserId;
            }
        }

        @Override
        public void onGetMyAddressError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onGetFamilyAndCars(int tid, GetFamilyAndCarsBean bean) {
            if (mTid != tid) {
                return;
            }
            if (bean.getFamilies() != null && bean.getFamilies().size() > 0) {
                Mobile mobile = bean.getFamilies().get(0);
                String number = mobile.getMobile();
                showToast(number);
            }
        }

        @Override
        public void onGetFamilyAndCarsError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onGetTags(int tid, List<String> tags) {
            if (mTid != tid) {
                return;
            }
            if (tags != null && tags.size() > 0) {
                String tag = tags.get(0);
                showToast(tag);
            } else {
                showToast("tags are empty");
            }
        }

        @Override
        public void onGetTagsError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onGetDefaultConfig(int tid, Config config) {
            if (mTid != tid) {
                return;
            }
            showToast("reloadTime : " + config.getReloadTimes()
                    + " reloadDays : " + config.getReloadDays());
        }

        @Override
        public void onGetDefaultConfigError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onGetPropNotifyCount(int tid, int[] unReadCounts) {
            if (mTid != tid) {
                return;
            }
            showToast("公告数 : " + unReadCounts[0]
                    + " 问卷调查数 : " + unReadCounts[1]);
        }

        @Override
        public void onGetPropNotifyCountError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onGetBanners(int tid, List<AbstractBanner> banners) {
            if (mTid != tid) {
                return;
            }
            if (banners.size() > 0) {
                for (AbstractBanner banner : banners) {
                    String type = banner.getType();
                    if (type.equalsIgnoreCase(CloudoorConstants.BannerType.Announcement)) {
                        AnnouncementBanner announcementBanner = (AnnouncementBanner) banner;
                        Log.e("Banner", announcementBanner.getTitle());
                    } else if (type.equalsIgnoreCase(CloudoorConstants.BannerType.Link)) {
                        LinkBanner linkBanner = (LinkBanner) banner;
                        Log.e("Banner", linkBanner.getPhotoUrl());
                    }
                }
            } else {
                showToast("There are no Banners");
            }
        }

        @Override
        public void onGetBannersError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onGetAlmanac(int tid, List<Almanac> almanacList) {
            if (mTid != tid) {
                return;
            }
            if (almanacList.size() > 0) {
                Almanac almanac = almanacList.get(0);
                showToast("Date : " + almanac.getDate()
                        + " 宜 : " + almanac.getYi()
                        + " 忌 : " + almanac.getJi()
                        + " 阴历 : " + almanac.getYinli());
            } else {
                showToast("黄历列表为空");
            }
        }

        @Override
        public void onGetAlmanacError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onSignOnDuty(int tid, SignBean signBean) {
            if (mTid != tid) {
                return;
            }
            showToast("rank : " + signBean.getRank()
                    + " isLate : " + Boolean.toString(signBean.isLate())
                    + " beatRatio : " + signBean.getBeatRatio());
        }

        @Override
        public void onSignOnDutyError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onSignOffDuty(int tid) {
            if (mTid != tid) {
                return;
            }
            showToast("下班签到成功");
        }

        @Override
        public void onSignOffDutyError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onGetServerTime(int tid, long serverTime) {
            if (mTid != tid) {
                return;
            }
            showToast("server time : " + serverTime);
        }

        @Override
        public void onGetServerTimeError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onGrabRed(int tid) {
            if (mTid != tid) {
                return;
            }
            showToast("抢红包成功");
        }

        @Override
        public void onGrabRedError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onGetIMAccount(int tid, HXAccount account) {
            if (mTid != tid) {
                return;
            }
            showToast("userId : " + account.getUserId() + " psw : " + account.getPassword());
        }

        @Override
        public void onGetIMAccountError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onGetFriends(int tid, List<Friend> friendList) {
            if (mTid != tid) {
                return;
            }
            if (friendList.size() > 0) {
                Friend friend = friendList.get(0);
                showToast("friend's nickname : " + friend.getNickname());
            }

        }

        @Override
        public void onGetFriendsError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onGetMyKeys(int tid, List<GetMyKeysBean> myKeysList) {
            if (mTid != tid) {
                return;
            }
            if (myKeysList.size() > 0) {
                GetMyKeysBean bean = myKeysList.get(0);
                Log.e("My keys", "zoneUserId : " + bean.getZoneUserId());
                Log.e("My keys", "address : " + bean.getAddress());
                Log.e("My keys", "keys size : " + bean.getKeys().size());
            }
        }

        @Override
        public void onGetMyBorrowKeys(int tid, List<GetMyBorrowKeysBean> myBorrowKeysList) {
            if (mTid != tid) {
                return;
            }
            if (myBorrowKeysList.size() > 0) {
                GetMyBorrowKeysBean bean = myBorrowKeysList.get(0);
                Log.e("My borrow keys", "zoneUserId : " + bean.getZoneUserId());
                Log.e("My borrow keys", "address : " + bean.getAddress());
                Log.e("My borrow keys", "l1ZoneId : " + bean.getL1ZoneId());
                Log.e("My borrow keys", "keys size : " + bean.getKeys().size());

            }
        }

        @Override
        public void onGetMyAuthKeys(int tid, List<GetMyAuthKeysBean> myAuthKeysList) {
            if (mTid != tid) {
                return;
            }
            if (myAuthKeysList.size() > 0) {
                GetMyAuthKeysBean bean = myAuthKeysList.get(0);
                Log.e("My auth keys", "date : " + bean.getDate());
                Log.e("My borrow keys", "record size : " + bean.getRecords().size());

            }
        }

        @Override
        public void onGetMyKeysError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onGetMyBorrowKeysError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }

        @Override
        public void onGetMyAuthKeysError(int tid, String err) {
            if (mTid != tid) {
                return;
            }
            showToast(err);
        }
    };
}
package com.icloudoor.cloudoor.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.icloudoor.cloudoor.app.CloudoorApp;
import com.icloudoor.cloudoor.database.table.AccountTable;
import com.icloudoor.cloudoor.network.bean.LoginBean;
import com.icloudoor.cloudoor.network.bean.meta.L1ZoneInfo;
import com.icloudoor.cloudoor.network.bean.meta.UserBasicInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 云门用户基础资料数据表管理类
 * Created by Derrick Guan on 8/17/15.
 */
public class AccountManager {

    // 全局静态Context
    private static Context sContext = CloudoorApp.getAppInstance()
            .getApplicationContext();

    // AccountManager单例
    private static AccountManager sInstance = null;

    // 当前Account
    private Account mCurrentAccount;

    public static synchronized AccountManager getInstance() {
        if (sInstance == null) {
            sInstance = new AccountManager();
        }
        if (sContext == null) {
            sContext = CloudoorApp.getAppInstance().getApplicationContext();
        }
        return sInstance;
    }

    public boolean setLoginAccount(LoginBean bean) {
        boolean ret;
        // 用户基础信息
        UserBasicInfo userInfo = bean.getInfo();
        // 环信用户名
        String chatUsername = bean.getIm().getAccount().getUserId();
        // 环信密码
        String chatPassword = bean.getIm().getAccount().getPassword();

        Account account = new Account();
        account.mUid = userInfo.getUserId();
        account.mUsername = userInfo.getUserName();
        account.mNickname = userInfo.getNickname();
        account.mMobile = userInfo.getMobile();
        account.mGender = userInfo.getSex();
        account.mIdCard = userInfo.getIdCardNo();
        account.mBirthday = userInfo.getBirthday();
        account.mProvinceId = userInfo.getProvinceId();
        account.mCityId = userInfo.getCityId();
        account.mDistrictId = userInfo.getDistrictId();
        account.mPortraitUrl = userInfo.getPortraitUrl();
        account.mUserStatus = userInfo.getUserStatus();
        account.bHasProServ = userInfo.isHasPropServ();
        account.mRole = userInfo.getRole();
        account.mL1Zones = userInfo.getL1Zones();
        account.mChatUsername = chatUsername;
        account.mChatPassword = chatPassword;
        account.bIsLastLogin = true;

        Account oldAccount = null;
        if (!TextUtils.isEmpty(account.mUid)) {
            oldAccount = getAccountByUserId(account.mUid);
        }

        logout();

        if (oldAccount != null) {
            // 如果数据库中已存在相关用户信息，则更新数据库
            ret = updateAccountByUserId(account);
        } else {
            // 否则向数据库中插入一条新记录
            ret = addAccount(account);
        }

        return ret;
    }

    /**
     * 获取当前登录帐号信息
     *
     * @return Account
     */
    public synchronized Account getCurrentAccount() {
        if (mCurrentAccount == null) {
            mCurrentAccount = getLastLoginAccount();
        }
        return mCurrentAccount;
    }

    /**
     * 退出登录前，清除当前用户的登录状态
     *
     * @return int(可以忽略此返回值)
     */
    public int logout() {
        mCurrentAccount = null;
        ContentValues values = new ContentValues();
        values.put(AccountTable.C_LAST_LOGIN, "0");

        String selection = AccountTable.C_LAST_LOGIN + "=?";
        String[] selectionArgs = new String[]{String.valueOf(1)};

        SQLiteDatabase db = new CloudoorDBHelper(sContext).getWritableDatabase();
        int ret = db.update(AccountTable.TABLE_NAME, values, selection,
                selectionArgs);
        db.close();
        return ret;
    }

    /**
     * 通过用户id获取Account信息
     *
     * @param uid 用户id
     * @return Account
     */
    private Account getAccountByUserId(String uid) {
        Account acc = null;

        if (!TextUtils.isEmpty(uid)) {
            String selection = AccountTable.C_USER_ID + "=?";
            String[] selectionArgs = new String[]{uid};

            SQLiteDatabase db = new CloudoorDBHelper(sContext)
                    .getReadableDatabase();
            Cursor c = db.query(AccountTable.TABLE_NAME, null, selection,
                    selectionArgs, null, null, null);

            if (c != null) {
                try {
                    if (c.moveToFirst()) {
                        acc = getAccountByCursor(c);
                    }
                } finally {
                    c.close();
                    db.close();
                }
            }
        }
        return acc;
    }

    /**
     * 向数据库插入一条用户信息
     *
     * @param account 待插入数据库的用户信息
     * @return 是否成功
     */
    private boolean addAccount(Account account) {
        ContentValues values = new ContentValues();
        values.clear();

        if (!TextUtils.isEmpty(account.mUid)) {
            values.put(AccountTable.C_USER_ID, account.mUid);
        }
        if (!TextUtils.isEmpty(account.mUsername)) {
            values.put(AccountTable.C_USERNAME, account.mUsername);
        }
        if (!TextUtils.isEmpty(account.mChatUsername)) {
            values.put(AccountTable.C_CHAT_USERNAME, account.mChatUsername);
        }
        if (!TextUtils.isEmpty(account.mChatPassword)) {
            values.put(AccountTable.C_CHAT_PASSWORD, account.mChatPassword);
        }

        values.put(AccountTable.C_NICKNAME, account.mNickname);
        values.put(AccountTable.C_MOBILE, account.mMobile);
        values.put(AccountTable.C_GENDER, account.mGender);
        values.put(AccountTable.C_ID_CARD, account.mIdCard);
        values.put(AccountTable.C_BIRTHDAY, account.mBirthday);
        values.put(AccountTable.C_PROVINCE_ID, account.mProvinceId);
        values.put(AccountTable.C_CITY_ID, account.mCityId);
        values.put(AccountTable.C_DISTRICT_ID, account.mDistrictId);
        values.put(AccountTable.C_PORTRAIT_URL, account.mPortraitUrl);
        values.put(AccountTable.C_USER_STATUS, account.mUserStatus);
        values.put(AccountTable.C_HAS_PRO_SERV, account.bHasProServ ? 1 : 0);
        values.put(AccountTable.C_ROLE, account.mRole);
        values.put(AccountTable.C_L1ZONES_JSON, account.getL1ZonesJson());
        values.put(AccountTable.C_LAST_LOGIN, account.bIsLastLogin ? 1 : 0);

        SQLiteDatabase db = new CloudoorDBHelper(sContext).getWritableDatabase();
        long ret = db.insert(AccountTable.TABLE_NAME, null, values);
        db.close();
        return ret > 0;

    }

    /**
     * 获取当前登录用户Account信息
     *
     * @return Account
     */
    private Account getLastLoginAccount() {
        Account account = null;
        String selection = AccountTable.C_LAST_LOGIN + " = 1";
        SQLiteDatabase db = new CloudoorDBHelper(sContext).getReadableDatabase();
        Cursor c = db.query(AccountTable.TABLE_NAME, null, selection, null,
                null, null, null);

        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    account = getAccountByCursor(c);
                }
            } finally {
                c.close();
                db.close();
            }
        }
        return account;
    }

    /**
     * 通过Cursor获取Account信息
     *
     * @param c Cursor
     * @return Account
     */
    private Account getAccountByCursor(Cursor c) {
        Account account = new Account();

        account.mUid = c.getString(c.getColumnIndex(AccountTable.C_USER_ID));
        account.mUsername = c.getString(c.getColumnIndex(AccountTable.C_USERNAME));
        account.mNickname = c.getString(c.getColumnIndex(AccountTable.C_NICKNAME));
        account.mMobile = c.getString(c.getColumnIndex(AccountTable.C_MOBILE));
        account.mGender = c.getInt(c.getColumnIndex(AccountTable.C_GENDER));
        account.mIdCard = c.getString(c.getColumnIndex(AccountTable.C_ID_CARD));
        account.mBirthday = c.getString(c.getColumnIndex(AccountTable.C_BIRTHDAY));
        account.mProvinceId = c.getInt(c.getColumnIndex(AccountTable.C_PROVINCE_ID));
        account.mCityId = c.getInt(c.getColumnIndex(AccountTable.C_CITY_ID));
        account.mDistrictId = c.getInt(c.getColumnIndex(AccountTable.C_DISTRICT_ID));
        account.mPortraitUrl = c.getString(c.getColumnIndex(AccountTable.C_PORTRAIT_URL));
        account.mUserStatus = c.getInt(c.getColumnIndex(AccountTable.C_USER_STATUS));
        account.bHasProServ = c.getInt(c.getColumnIndex(AccountTable.C_HAS_PRO_SERV)) == 1;
        account.mRole = c.getInt(c.getColumnIndex(AccountTable.C_ROLE));
        account.mChatUsername = c.getString(c.getColumnIndex(AccountTable.C_CHAT_USERNAME));
        account.mChatPassword = c.getString(c.getColumnIndex(AccountTable.C_CHAT_PASSWORD));
        account.bIsLastLogin = c.getInt(c.getColumnIndex(AccountTable.C_LAST_LOGIN)) == 1;

        String l1ZonesJson = c.getString(c.getColumnIndex(AccountTable.C_L1ZONES_JSON));
        account.mL1Zones = getL1ZoneInfo(l1ZonesJson);

        return account;
    }

    /**
     * 通过json转换L1Zones列表
     *
     * @param src 数据表中，L1Zones的json字串
     * @return 一级小区信息列表
     */
    private List<L1ZoneInfo> getL1ZoneInfo(String src) {
        List<L1ZoneInfo> l1Zones = new ArrayList<L1ZoneInfo>();
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(src)) {

            JsonElement json = gson.toJsonTree(src);

            l1Zones = gson.fromJson(json, new TypeToken<List<L1ZoneInfo>>() {
            }.getType());
        }

        return l1Zones;
    }

    /**
     * 通过User Id更新用户数据
     *
     * @param account 待更新的用户数据
     * @return 是否更新成功
     */
    private boolean updateAccountByUserId(Account account) {
        ContentValues values = new ContentValues();
        values.clear();

        if (!TextUtils.isEmpty(account.mUid)) {
            values.put(AccountTable.C_USER_ID, account.mUid);
        }
        if (!TextUtils.isEmpty(account.mUsername)) {
            values.put(AccountTable.C_USERNAME, account.mUsername);
        }
        if (!TextUtils.isEmpty(account.mChatUsername)) {
            values.put(AccountTable.C_CHAT_USERNAME, account.mChatUsername);
        }
        if (!TextUtils.isEmpty(account.mChatPassword)) {
            values.put(AccountTable.C_CHAT_PASSWORD, account.mChatPassword);
        }

        values.put(AccountTable.C_NICKNAME, account.mNickname);
        values.put(AccountTable.C_MOBILE, account.mMobile);
        values.put(AccountTable.C_GENDER, account.mGender);
        values.put(AccountTable.C_ID_CARD, account.mIdCard);
        values.put(AccountTable.C_BIRTHDAY, account.mBirthday);
        values.put(AccountTable.C_PROVINCE_ID, account.mProvinceId);
        values.put(AccountTable.C_CITY_ID, account.mCityId);
        values.put(AccountTable.C_DISTRICT_ID, account.mDistrictId);
        values.put(AccountTable.C_PORTRAIT_URL, account.mPortraitUrl);
        values.put(AccountTable.C_USER_STATUS, account.mUserStatus);
        values.put(AccountTable.C_HAS_PRO_SERV, account.bHasProServ ? 1 : 0);
        values.put(AccountTable.C_ROLE, account.mRole);
        values.put(AccountTable.C_L1ZONES_JSON, account.getL1ZonesJson());
        values.put(AccountTable.C_LAST_LOGIN, account.bIsLastLogin ? 1 : 0);

        String selection = AccountTable.C_USER_ID + "=?";
        String[] selectionArgs = new String[]{account.mUid};

        SQLiteDatabase db = new CloudoorDBHelper(sContext).getWritableDatabase();
        int ret = db.update(AccountTable.TABLE_NAME, values, selection,
                selectionArgs);
        db.close();
        return ret > 0;
    }


    /**
     * 用户基础资料类
     */
    public static class Account {
        // 用户id
        public String mUid;
        // 用户名
        public String mUsername;
        // 用户昵称
        public String mNickname;
        // 用户手机
        public String mMobile;
        // 用户性别
        public int mGender;
        // 用户身份证
        public String mIdCard;
        // 用户生日
        public String mBirthday;
        // 省份编码
        public int mProvinceId;
        // 城市编码
        public int mCityId;
        // 区域编码
        public int mDistrictId;
        // 头像url
        public String mPortraitUrl;
        // 用户状态
        public int mUserStatus;
        // 是否有权限访问物业服务
        public boolean bHasProServ;
        // 用户角色
        public int mRole;
        // 一级小区列表json
        public List<L1ZoneInfo> mL1Zones;
        // 环信聊天帐号
        public String mChatUsername;
        // 环信聊天密码
        public String mChatPassword;
        // 是否当前登录用户
        public boolean bIsLastLogin;

        public String getL1ZonesJson() {
            Gson gson = new Gson();
            return gson.toJson(mL1Zones);
        }
    }

}

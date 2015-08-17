package com.icloudoor.cloudoor.PreferMgr;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.icloudoor.cloudoor.app.CloudoorApp;

/**
 * SharedPreference底层辅助类
 * Created by Derrick Guan on 7/23/15.
 */
public class PreferHelper {

    private static String sPrefName = null;

    private static SharedPreferences getSharedPreferences() {
        if (CloudoorApp.getAppInstance() != null) {
            if (TextUtils.isEmpty(sPrefName)) {
                return PreferenceManager.getDefaultSharedPreferences(CloudoorApp.getAppInstance());
            } else {
                return CloudoorApp.getAppInstance().getSharedPreferences(sPrefName, Context.MODE_PRIVATE);
            }
        } else {
            return null;
        }
    }

    protected static void setPrefFileName(String fileName) {
        sPrefName = fileName;
    }

    protected static boolean getBoolean(String prefKey, boolean defaultValue) {
        return getSharedPreferences().getBoolean(prefKey, defaultValue);
    }

    protected static float getFloat(String prefKey, float defaultValue) {
        return getSharedPreferences().getFloat(prefKey, defaultValue);
    }

    protected static int getInt(String prefKey, int defaultValue) {
        return getSharedPreferences().getInt(prefKey, defaultValue);
    }

    protected static long getLong(String prefKey, long defaultValue) {
        return getSharedPreferences().getLong(prefKey, defaultValue);
    }

    protected static String getString(String prefKey, String defaultValue) {
        return getSharedPreferences().getString(prefKey, defaultValue);
    }

    protected static void putBoolean(String prefKey, boolean value) {
        getSharedPreferences().edit().putBoolean(prefKey, value).commit();
    }

    protected static void putFloat(String prefKey, float value) {
        getSharedPreferences().edit().putFloat(prefKey, value).commit();
    }

    protected static void putInt(String prefKey, int value) {
        getSharedPreferences().edit().putInt(prefKey, value).commit();
    }

    protected static void putLong(String prefKey, Long value) {
        getSharedPreferences().edit().putLong(prefKey, value).commit();
    }

    protected static void putString(String prefKey, String value) {
        getSharedPreferences().edit().putString(prefKey, value).commit();
    }

    protected static void remove(String prefKey) {
        getSharedPreferences().edit().remove(prefKey).commit();
    }
}

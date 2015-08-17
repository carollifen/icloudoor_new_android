package com.icloudoor.cloudoor.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.icloudoor.cloudoor.app.CloudoorApp;

import java.lang.reflect.Method;

/**
 * 系统工具类
 * Created by Derrick Guan on 7/23/15.
 */
public class PlatformUtil {

    private static final Context sContext = CloudoorApp.getAppInstance();

    /**
     * 获取Manifest中定义的meta-data
     *
     * @param metaName meta-data : name
     * @return String
     */
    public static String getMetaData(String metaName) {
        String metaValue = "";
        try {
            ApplicationInfo info = CloudoorApp.getAppInstance().getPackageManager().
                    getApplicationInfo(
                            CloudoorApp.getAppInstance().getPackageName(),
                            PackageManager.GET_META_DATA);
            metaValue = info.metaData.getString(metaName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return metaValue;
    }


    /**
     * 获取手机imei号，模拟器可能返回null/空串/全0字串
     *
     * @return String
     */
    public static String getPhoneIMEI() {
        TelephonyManager mTelephonyMgr = (TelephonyManager) CloudoorApp.getAppInstance()
                .getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getDeviceId();
    }

    /**
     * 获取OS版本
     *
     * @return String
     */
    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取设备名字
     *
     * @return String
     */
    public static String getMobileName() {
        return android.os.Build.MODEL;
    }

    /**
     * 判断当前是否联网
     *
     * @return boolean
     */
    public static boolean hasConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) sContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (null != anInfo && anInfo.isAvailable()
                            && anInfo.isConnectedOrConnecting()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断当前是否使用手机流量的网络环境
     *
     * @return boolean
     */
    public static boolean isMobileNetWork() {
        NetworkInfo networkInfo = ((ConnectivityManager) sContext
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting()
                && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 判断当前是否使用wife的网络环境
     *
     * @return boolean
     */
    public static boolean isWifiNetWork() {
        NetworkInfo networkInfo = ((ConnectivityManager) sContext
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting()
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 获取网络类型名称
     *
     * @return boolean
     */
    public static String getNetWorkName() {
        NetworkInfo networkInfo = ((ConnectivityManager) sContext
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.getTypeName();
        } else {
            return "";
        }
    }

    /**
     * 获取Wifi Mac地址
     *
     * @return String
     */
    public static String getWifiMacAddress() {
        try {
            WifiManager wifi = (WifiManager) sContext
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取运营商名称
     *
     * @return String
     */
    public static String getOperatorName() {
        String OperatorName = "";
        try {
            TelephonyManager tm = (TelephonyManager) sContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String imsi = tm.getSubscriberId();
            if (imsi != null) {
                // 因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
                if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                    OperatorName = "中国移动";
                } else if (imsi.startsWith("46001")) {
                    OperatorName = "中国联通";
                } else if (imsi.startsWith("46003")) {
                    OperatorName = "中国电信";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OperatorName;
    }

    private static int mDeviceWidth;
    private static int mDeviceHeight;

    /**
     * 获取屏幕像素
     *
     * @return int[] : 1. 屏幕宽 2. 屏幕高
     */
    public static int[] getScreenDisplayMetrics() {
        if (mDeviceWidth > 0 && mDeviceHeight > 0) {

        } else {
            int width = 0;
            int height = 0;
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager wm = (WindowManager) sContext
                    .getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            display.getMetrics(metrics);

            width = metrics.widthPixels;
            height = metrics.heightPixels;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                try {
                    Method mGetRawH = Display.class.getMethod("getRawWidth");
                    Method mGetRawW = Display.class.getMethod("getRawHeight");
                    width = (Integer) mGetRawW.invoke(display);
                    height = (Integer) mGetRawH.invoke(display);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (width < height) {
                mDeviceWidth = width;
                mDeviceHeight = height;
            } else {
                mDeviceWidth = height;
                mDeviceHeight = width;
            }
        }

        return new int[]{mDeviceWidth, mDeviceHeight};
    }

    /**
     * 获取当前屏幕像素密度
     *
     * @return float
     */
    public static float getDisplayDensity() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) sContext
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }

    /**
     * dp转px
     *
     * @param dpValue dp value
     * @return int
     */
    public static int dip2px(float dpValue) {
        final float scale = sContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param pxValue px value
     * @return int
     */
    public static int px2dip(float pxValue) {
        final float scale = sContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 是否有External Storage
     * @return boolean
     */
    public static boolean hasExternalStorage() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }


    public static String getSystemStoragePath() {
        String base;
        if (hasExternalStorage()) {
            base = Environment.getExternalStorageDirectory().getPath();
        } else {
            base = sContext.getFilesDir().getParent();
            if (base.endsWith("/")) {
                base = base.substring(0, base.length() - 1);
            }
        }
        return base;
    }

    /**
     * 通过包名检查是否安装某个应用
     * @param packageName 应用包名
     * @return boolean
     */
    public static boolean isInstallApk(String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = sContext.getPackageManager().getPackageInfo(packageName, 0);
        } catch(PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        return packageInfo != null;
    }
}

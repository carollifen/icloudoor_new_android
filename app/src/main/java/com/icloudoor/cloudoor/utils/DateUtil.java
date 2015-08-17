package com.icloudoor.cloudoor.utils;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 时间转换工具类
 * Created by Derrick Guan on 8/12/15.
 */
public class DateUtil {

    // yyyy年MM月dd日
    public static final String FORMAT_YYYY_MM_DD = "yyyy年MM月dd日";
    // MM月dd日
    public static final String FORMAT_MM_DD = "MM月dd日";

    @StringDef({FORMAT_YYYY_MM_DD, FORMAT_MM_DD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CloudoorDateFormat {
    }


    /**
     * 获取当前时间, 以"MM月dd日"形式返回
     *
     * @return String
     */
    public static String getCurrentDate() {
        return getDate(0);
    }

    /**
     * 获取当前时间, 以指定形式返回
     *
     * @param format 日期形式
     * @return String
     */
    public static String getCurrentDate(@CloudoorDateFormat String format) {
        return getDate(0, format);
    }

    /**
     * 以当前日期为基准，获取delta天前(后)的日期
     * <br>默认以"MM月dd日"形式返回<br/>
     *
     * @param delta 多少天前(后)
     * @return String
     */
    public static String getDate(int delta) {
        return getDate(delta, FORMAT_MM_DD);
    }

    public static String getDate(int delta, @CloudoorDateFormat String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, delta);
        return sdf.format(cal.getTime());
    }
}

package com.zs.project.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zs
 * Date：2018年 04月 09日
 * Time：13:42
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class DateUtil {

    public static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static SimpleDateFormat formatDate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    public static SimpleDateFormat formatDate2 = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
    public static SimpleDateFormat formatDate3 = new SimpleDateFormat("MM-dd", Locale.getDefault());
    public static SimpleDateFormat formatDate4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static int mDayTime = 24 * 60 * 60 * 1000;

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendlyTime(String sdate) {
        Date time = parseDateTime(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 所在时区时8，系统初始时间是1970-01-01 80:00:00，注意是从八点开始，计算的时候要加回去
        int offSet = Calendar.getInstance().getTimeZone().getRawOffset();
        long lt = (time.getTime() - offSet) / mDayTime;
        long ct = (cal.getTimeInMillis() - offSet) / mDayTime;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = formatDate.format(time);
        }
        return ftime;
    }

    /**
     *  格式化时间格式
     *
     * @param sdate
     * @return
     */
    public static String getStandTime1(String sdate) {
        Date time = parseDateTime(sdate);
        if (time == null) {
            return "Unknown";
        }
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Calendar.getInstance().setTime(time);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (year == currentYear){
            return formatDate2.format(time);
        }else{
            return formatDate1.format(time);
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String getStandTime2(String sdate) {
        Date time = parseDateTime(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 所在时区时8，系统初始时间是1970-01-01 80:00:00，注意是从八点开始，计算的时候要加回去
        int offSet = Calendar.getInstance().getTimeZone().getRawOffset();
        long lt = (time.getTime() + offSet) / mDayTime;
        long ct = (cal.getTimeInMillis() + offSet) / mDayTime;
        int days = (int) (ct - lt);
        if (days == 0) {
            ftime = "今天";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10 && days < 20) {

            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            Calendar.getInstance().setTime(time);
            int year = Calendar.getInstance().get(Calendar.YEAR);
            if (year == currentYear){
                ftime = formatDate.format(time);
            }else{
                ftime = formatDate3.format(time);
            }
        }
        return ftime;
    }


    /**
     * 解析日期
     *
     * @param datetime
     * @return
     */
    public static Date parseDateTime(String datetime) {
        Date mDate = null;
        try {
            if (datetime.length() < 18){
                mDate = formatDate1.parse(datetime);
            }else{
                mDate = formatDate4.parse(datetime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mDate;
    }

}

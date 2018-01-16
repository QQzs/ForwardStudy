package com.zs.project.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.Toast;

import com.zs.project.app.MyApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 *
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class StringUtils {
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    private final static Pattern IMG_URL = Pattern
            .compile(".*?(gif|jpeg|png|jpg|bmp)");

    private final static Pattern URL = Pattern
            .compile("^(https|http)://.*?$(net|com|.com.cn|org|me|)");

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        return toDate(sdate, dateFormater.get());
    }

    public static Date toDate(String sdate, SimpleDateFormat dateFormater) {
        try {
            return dateFormater.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getDateString(Date date) {
        return dateFormater.get().format(date);
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        Date time = null;

        if (TimeZoneUtil.isInEasternEightZones())
            time = toDate(sdate);
        else
            time = TimeZoneUtil.transformTime(toDate(sdate),
                    TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());

        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
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
            ftime = "前天 ";
        } else if (days > 2 && days < 31) {
            ftime = days + "天前";
        } else if (days >= 31 && days <= 2 * 31) {
            ftime = "一个月前";
        } else if (days > 2 * 31 && days <= 3 * 31) {
            ftime = "2个月前";
        } else if (days > 3 * 31 && days <= 4 * 31) {
            ftime = "3个月前";
        } else {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    public static String friendly_time2(String sdate) {
        String res = "";
        if (isEmpty(sdate))
            return "";

        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String currentData = StringUtils.getDataTime("MM-dd");
        int currentDay = toInt(currentData.substring(3));
        int currentMoth = toInt(currentData.substring(0, 2));

        int sMoth = toInt(sdate.substring(5, 7));
        int sDay = toInt(sdate.substring(8, 10));
        int sYear = toInt(sdate.substring(0, 4));
        Date dt = new Date(sYear, sMoth - 1, sDay - 1);

        if (sDay == currentDay && sMoth == currentMoth) {
            res = "今天 / " + weekDays[getWeekOfDate(new Date())];
        } else if (sDay == currentDay + 1 && sMoth == currentMoth) {
            res = "昨天 / " + weekDays[(getWeekOfDate(new Date()) + 6) % 7];
        } else {
            if (sMoth < 10) {
                res = "0";
            }
            res += sMoth + "/";
            if (sDay < 10) {
                res += "0";
            }
            res += sDay + " / " + weekDays[getWeekOfDate(dt)];
        }

        return res;
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static int getWeekOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 返回long类型的今天的日期
     *
     * @return
     */
    public static long getToday() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater2.get().format(cal.getTime());
        curDate = curDate.replace("-", "");
        return Long.parseLong(curDate);
    }

    public static String getCurTimeStr() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater.get().format(cal.getTime());
        return curDate;
    }

    /***
     * 计算两个时间差，返回的是的秒s
     *
     * @param dete1
     * @param date2
     * @return
     * @author 火蚁 2015-2-9 下午4:50:06
     */
    public static long calDateDifferent(String dete1, String date2) {

        long diff = 0;

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = dateFormater.get().parse(dete1);
            d2 = dateFormater.get().parse(date2);

            // 毫秒ms
            diff = d2.getTime() - d1.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return diff / 1000;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 判断一个url是否为图片url
     *
     * @param url
     * @return
     */
    public static boolean isImgUrl(String url) {
        if (url == null || url.trim().length() == 0)
            return false;
        return IMG_URL.matcher(url).matches();
    }

    /**
     * 判断是否为一个合法的url地址
     *
     * @param str
     * @return
     */
    public static boolean isUrl(String str) {
        if (str == null || str.trim().length() == 0)
            return false;
        return URL.matcher(str).matches();
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    public static String getString(String s) {
        return s == null ? "" : s;
    }

    /**
     * 将一个InputStream流转换成字符串
     *
     * @param is
     * @return
     */
    public static String toConvertString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                res.append(line + "<br>");
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (isr != null) {
                    isr.close();
                }
                if (null != read) {
                    read.close();
                    read = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }

    /***
     * 截取字符串
     *
     * @param start 从那里开始，0算起
     * @param num   截取多少个
     * @param str   截取的字符串
     * @return
     */
    public static String getSubString(int start, int num, String str) {
        if (str == null) {
            return "";
        }
        int leng = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > leng) {
            start = leng;
        }
        if (num < 0) {
            num = 1;
        }
        int end = start + num;
        if (end > leng) {
            end = leng;
        }
        return str.substring(start, end);
    }

    /**
     * 获取当前时间为每年第几周
     *
     * @return
     */
    public static int getWeekOfYear() {
        return getWeekOfYear(new Date());
    }

    /**
     * 获取当前时间为每年第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        int week = c.get(Calendar.WEEK_OF_YEAR) - 1;
        week = week == 0 ? 52 : week;
        return week > 0 ? week : 1;
    }

    public static int[] getCurrentDate() {
        int[] dateBundle = new int[3];
        String[] temp = getDataTime("yyyy-MM-dd").split("-");

        for (int i = 0; i < 3; i++) {
            try {
                dateBundle[i] = Integer.parseInt(temp[i]);
            } catch (Exception e) {
                dateBundle[i] = 0;
            }
        }
        return dateBundle;
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * 判断是否为空
     *
     * @param text
     * @return
     */
    public static boolean isNullOrEmpty(String text) {
        if (text == null || "".equals(text.trim()) || text.trim().length() == 0
                || "null".equals(text.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isListNullOrEmpty(List list){
        return list == null || list.size() == 0;
    }

    public static String cleatNull(String str) {
        return (isNullOrEmpty(str)) ? "" : str;
    }

    //检验姓名
    public static boolean checkName(String str) {
        if (isNullOrEmpty(str)) {
            Toast.makeText(MyApplication.getInstance(), "请输入姓名", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (str.length() > 32) {
                Toast.makeText(MyApplication.getInstance(), "姓名不能超过32个字符，请修改", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    //检验手机号
    public static boolean checkPhone(String str) {
        if (isNullOrEmpty(str)) {
            Toast.makeText(MyApplication.getInstance(), "请输入11位手机号", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!validatePhoneNumber(str)) {
                Toast.makeText(MyApplication.getInstance(), "请输入正确的11位手机号", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }


    //检验邮箱
    public static boolean checkMail(String str) {
        if (isNullOrEmpty(str)) {
            Toast.makeText(MyApplication.getInstance(), "请输入邮箱", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (str.length() > 32) {
                Toast.makeText(MyApplication.getInstance(), "邮箱不能超过32个字符，请修改", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!validateEmail(str)) {
                Toast.makeText(MyApplication.getInstance(), "格式错误，请输入正确格式的邮箱", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    /**
     * 邮箱合法性验证
     *
     * @param mail 邮箱
     * @return
     */
    public static boolean validateEmail(String mail) {
        Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = pattern.matcher(mail);
        return m.matches();
    }

    /**
     * 电话号码合法性验证
     *
     * @param phoneNumber 手机号码
     * @return
     */
    public static boolean validatePhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern
                .compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = pattern.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 验证输入的身份证号是否符合格式要求
     *
     * @param IDNum 身份证号
     * @return 符合国家的格式要求为 true;otherwise,false;
     */
    public static boolean validateIDcard(String IDNum) {
        String id_regEx1 = "^([1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|(3[0-1]))\\d{3})|([1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|(3[0-1]))\\d{3}[0-9Xx])$";
        Pattern pattern = Pattern.compile(id_regEx1);
        Matcher m = pattern.matcher(IDNum);
        return m.matches();
    }

    public static SpannableString getSpannable(Context context, int id) {
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), id);
        ImageSpan imgSpan = new ImageSpan(context, b);
        SpannableString spanString = new SpannableString("icon");
        spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }

    public static String getFromAssets(Context context, String fileName) {
        String result = "";
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            int lenght = in.available();
            byte[] buffer = new byte[lenght];
            in.read(buffer);
            String tempstr = new String(buffer);
            in.close();

            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(tempstr);
            result = m.replaceAll("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String new_friendly_time(String time) {
        if (!StringUtils.isNullOrEmpty(time)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            long newsTime = Long.valueOf(time);
            long currentTime = System.currentTimeMillis();
            if (currentTime - newsTime < 1000 * 60 * 60) {
                return ((int) ((currentTime - newsTime) / 1000 / 60)) + "分钟以前";
            } else if (getPointTime(currentTime) < newsTime) {
                return ((int) ((currentTime - newsTime) / 1000 / 60 / 60)) + "小时以前";
            } else if (getPointTime(currentTime) - getPointTime(newsTime) == (24 * 60 * 60 * 1000)) {
                return "昨天";
            }
            return sdf.format(newsTime);
        } else {
            return "";
        }
    }

    public static long getPointTime(long newsTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd");
            return (sdf.parse(sdf.format(newsTime))).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getStandardDate(String timeStr) {
        String str = "";
        try {
            SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM-dd");
            Long creattime = Long.parseLong(timeStr);
            Date date1 = new Date(creattime);
            String date1str = formart.format(date1);
            Date jintian = getDay(0);
            String jintianstr = formart.format(jintian);
            Date zuotian = getDay(-1);
            String zuotianstr = formart.format(zuotian);
            Date qiantian = getDay(-2);
            String qiantianstr = formart.format(qiantian);

            if (date1str.equals(jintianstr)) {
                long times = System.currentTimeMillis() - creattime;
                if (times / 1000 / 60 > 1) {
                    SimpleDateFormat formart1 = new SimpleDateFormat("HH:mm");
                    str = formart1.format(date1);
                } else {
                    str = "刚刚";
                }
            } else if (date1str.equals(zuotianstr)) {
                SimpleDateFormat formart1 = new SimpleDateFormat("昨天 HH:mm");
                str = formart1.format(date1);
            } else if (date1str.equals(qiantianstr)) {
                SimpleDateFormat formart1 = new SimpleDateFormat("MM-dd");
                str = formart1.format(date1);
            } else {
                SimpleDateFormat formart1 = new SimpleDateFormat("yyyy-MM-dd");
                str = formart1.format(date1);
            }

        } catch (Exception e) {

        }
        return str;
    }

    // 2.2.0新通知时间的格式，当天：12:30，反之2-23
    public static String getCommunityFormatTime(String timeStr) {
        String str = "";
        try {
            SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM-dd");
            Long creattime = Long.parseLong(timeStr);
            Date date1 = new Date(creattime);
            String date1str = formart.format(date1);
            Date jintian = getDay(0);
            String jintianstr = formart.format(jintian);
            if (date1str.equals(jintianstr)) {
                long times = System.currentTimeMillis() - creattime;
                if (times / 1000 / 60 > 1) {
                    SimpleDateFormat formart1 = new SimpleDateFormat("HH:mm");
                    str = formart1.format(date1);
                } else {
                    str = "刚刚";
                }
            } else {
                SimpleDateFormat formart1 = new SimpleDateFormat("MM-dd");
                str = formart1.format(date1);
            }
        } catch (Exception e) {

        }
        return str;
    }

    public static Date getDay(int num) {
        Date date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, num);
        date = calendar.getTime();
        return date;
    }

    /**
     * 校验身份证号
     *
     * @param text
     * @return
     */
    public boolean personIdValidation(String text) {
        String checkCode = "(^\\d{18}$)|(^\\d{15}$)|(^(\\d{14}|\\d{17})(\\d|[xX])$)";
        Pattern regex = Pattern.compile(checkCode);
        Matcher matcher = regex.matcher(text);
        return matcher.matches();
    }

    public static String stringFilter(String str) {
        // 只允许字母、数字
        String regEx = "[^a-zA-Z0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 将double类型的数字转整数
     *
     * @param number
     * @return
     */
    public static String formatIntNumber(double number) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("0");
        return df.format(number);
    }
    /**
     * 将double类型的数字保留两位小数（四舍五入）
     *
     * @param number
     * @return
     */
    public static String formatNumber(double number) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("0.00%");
        return df.format(number);
    }

    /**
     * 将double类型的数字保留两位小数 向上取1
     *
     * @param number
     * @return
     */
    public static double formatNumber2(double number) {
        return new BigDecimal(number).setScale(2, RoundingMode.CEILING).doubleValue();
    }

    /**
     * 将float类型的数字保留两位小数不够补零
     *
     * @param number
     * @return
     */
    public static String formatFloatNumber(Object number) {
        String newStr = String.valueOf(number);
        if(isNullOrEmpty(newStr)) {
            newStr = "0";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(new BigDecimal(newStr)).trim();
    }

    /**
     * 格式化数据
     * @param str
     * @return
     */
    public static String floatFormat(Object str) {
        String newStr = String.valueOf(str);
        if(isNullOrEmpty(newStr)) {
            newStr = "0.00";
        }
        // 保留两位小数
        DecimalFormat df = new DecimalFormat("###0.00");
        float data = Float.valueOf(df.format(new BigDecimal(newStr)).trim());
        // 小数点后面为零的不显示后面的 .0 .00
        int data1 = (int)data;
        if (data1 == data){
            return String.valueOf(data1);
        }else{
            return String.valueOf(data);
        }
    }
}

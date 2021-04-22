package com.rails.purchaseplatform.framwork.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时间戳格式化工具类
 * author:wangchao
 * email:wangchao@chengantech.com
 * data:
 */
public class TimeUtil {

    public static final String GMT = "EEE, dd MMM yyyy HH:mm:ss 'GMT'";
    public static final String UTC = "yyyy-MM-dd'T'HH:mm:ss'.000Z'";
    public static final String MDHM = "yyyy年MM月dd日";

    public static final String W_YMDHM = "yyyy年MM月dd日 HH:mm";

    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMDHM = "yyyy.MM.dd HH:mm";
    public static final String YMDHM_ = "yyyy-MM-dd HH:mm";
    public static final String YDHM = "MM-dd HH:mm";
    public static final String YMD = "yyyy.MM.dd";
    public static final String YMD_ = "yyyy-MM-dd";
    public static final String HMS = "HH:mm:ss";
    public static final String HM = "HH:mm";
    public static final String YYYY = "yyyy";
    public static final String MM = "M";
    public static final String DD = "d";

    public static final String YY = "yyyy年MM月dd日HH时mm分ss秒";

    public static final String FILE_YMDHM = "yyyy.MM.dd HH:mm";

    public static final String MD = "MM.dd";
    public static final String MD_ = "MM-dd";
    public static final String MD_H = "M月d日";

    public static String LongtoFileTime(long time) {
        return LongtoStringFormat(time, FILE_YMDHM);
    }


    /**
     * @param time   时间戳
     * @param format 转换样式
     * @return
     */
    public static String LongtoStringFormat(long time, String format) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /**
     * long转成2015.01.03格式
     *
     * @param time 单位ms
     * @return
     */
    public static String LongtoStringFormatYMD(long time) {
        return LongtoStringFormat(time, YMD);
    }


    public static String LongtoStringFormatMDHM(long time) {
        return LongtoStringFormat(time, MDHM);
    }


    /**
     * @param time
     * @return
     */
    public static String LongtoStringFormatYMDHM(long time) {
        return LongtoStringFormat(time, YMDHM);
    }


    /**
     * @param time
     * @return
     */
    public static String LongtoStringFormatMD(long time) {
        return LongtoStringFormat(time, MD);
    }


    /**
     * long转成00:01:03格式
     *
     * @param time 单位ms
     * @return
     */
    public static String LongtoStringFormatHMS(long time) {
        return LongtoStringFormat(time, HMS);
    }


    /**
     * 获取当前时和分
     *
     * @param time
     * @return
     */
    public static String LongtoStringFormatHM(long time) {
        return LongtoStringFormat(time, HM);
    }


    /**
     * 当前月 获取当前月
     *
     * @param time 时间戳
     * @return
     */
    public static String LongtoYear(long time) {
        return LongtoStringFormat(time, YYYY);
    }


    /**
     * 事件列表规则
     *
     * @return
     */
    public static String actionTime(long time) {
        String year = LongtoStringFormat(time, YYYY);
        String curYear = LongtoStringFormat(System.currentTimeMillis(), YYYY);
        if (curYear.equals(year)) {
            return LongtoStringFormat(time, YDHM);
        } else {
            return LongtoStringFormat(time, YMDHM_);
        }
    }


    /**
     * 当前月 获取当前月
     *
     * @param time 时间戳
     * @return
     */
    public static String LongtoMonth(long time) {
        return LongtoStringFormat(time, MM);
    }


    /**
     * 获取当前日
     *
     * @param time 时间戳
     * @return
     */
    public static String LongtoDay(long time) {
        return LongtoStringFormat(time, DD);
    }


    /**
     * 时间戳转化成就近时间规则字符串
     *
     * @param startTime
     * @return
     */
    public static String LongtoStringRecent(long startTime) {
        long currentTmieWithSecond = System.currentTimeMillis();
        long timeD_Value = (currentTmieWithSecond - startTime) / 1000;// 与现在时间相差秒数
        String time = "";
        if (timeD_Value > 12 * 30 * 24 * 60 * 60) {
            time = LongtoStringFormatYMD(startTime);
        } else if (timeD_Value > 30 * 24 * 60 * 60) {// 月天以上
            time = LongtoStringFormat(startTime, MD_H);
        } else if (timeD_Value > 24 * 60 * 60) {// 1天以上
            time = timeD_Value / (24 * 60 * 60) + "天前";
        } else if (timeD_Value > 60 * 60) {// 1小时前
            time = timeD_Value / (60 * 60) + "小时前";
        } else if (timeD_Value > 60) {// 1分钟前
            time = timeD_Value / 60 + "分钟前";
        } else if (timeD_Value > 1) {
            time = timeD_Value + "秒以前";
        } else {// 1秒钟-59秒钟
            time = "刚刚";
        }
        return time;
    }


    /**
     * 时间段 毫秒换成00:00:00
     *
     * @param finishTime
     * @return
     */
    public static String LongtoDuration(double finishTime) {
        int totalTime = (int) finishTime;//秒
        int hour = 0, minute = 0, second = 0;

        if (3600 <= totalTime) {
            hour = totalTime / 3600;
            totalTime = totalTime - 3600 * hour;
        }
        if (60 <= totalTime) {
            minute = totalTime / 60;
            totalTime = totalTime - 60 * minute;
        }
        if (0 <= totalTime) {
            second = totalTime;
        }
        StringBuilder sb = new StringBuilder();

        if (hour < 10) {
            sb.append("0").append(hour).append(":");
        } else {
            sb.append(hour).append(":");
        }
        if (minute < 10) {
            sb.append("0").append(minute).append(":");
        } else {
            sb.append(minute).append(":");
        }
        if (second < 10) {
            sb.append("0").append(second);
        } else {
            sb.append(second);
        }
        return sb.toString();
    }


    public static String LongtoDuration(double finishTime, int type) {
        int totalTime = (int) finishTime;//秒
        int hour = 0, minute = 0, second = 0;

        if (3600 <= totalTime) {
            hour = totalTime / 3600;
            totalTime = totalTime - 3600 * hour;
        }
        if (60 <= totalTime) {
            minute = totalTime / 60;
            totalTime = totalTime - 60 * minute;
        }
        if (0 <= totalTime) {
            second = totalTime;
        }
        StringBuilder sb = new StringBuilder();

        if (hour > 0)
            sb.append(hour).append("小时");
        if (minute > 0)
            sb.append(minute).append("分");
        if (second > 0) {
            sb.append(second).append("秒");
        }
        return sb.toString();
    }


    public static long getMorningLong(long time) {
        Date date = new Date(time * 1000);
        return getMorningLong(date);
    }


    /**
     * 获取当天凌晨的时间戳（单位：s）
     */
    public static long getMorningLong() {
        return getMorningLong(new Date());
    }

    /**
     * 获取指定时间当天的凌晨的时间戳（单位：s）
     *
     * @param date
     * @return
     */
    public static long getMorningLong(Date date) {
        Date d = getMorning(date);
        long time = d.getTime();
        return time / 1000;
    }

    /**
     * 获取今天凌晨
     *
     * @return
     */
    public static Date getMorning() {
        return getMorning(new Date());
    }

    /**
     * 获取指定时间当天的凌晨
     *
     * @param date 给定时间当天的凌晨
     * @return
     */
    public static Date getMorning(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }


    /**
     * UTC时间转换成本地时间
     *
     * @param utcTime
     * @param utcTimePatten
     * @param localTimePatten
     * @return
     */
    public static String utcTlocal(String utcTime, String utcTimePatten, String localTimePatten) {
        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten, Locale.CHINA);
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return utcTime;
        }
        long time = gpsUTCDate.getTime();
        SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(time);
        return localTime;
    }


    /**
     * 字符串转换成时间戳
     *
     * @param utcTime
     * @param utcTimePatten
     * @return
     */
    public static long stringFormatLong(String utcTime, String utcTimePatten) {
        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten, Locale.CHINA);
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        return gpsUTCDate.getTime();
    }


    /**
     * UTC时间转换成本地时间
     *
     * @param utcTime
     * @param utcTimePatten
     * @return
     */
    public static String utcTlocal(String utcTime, String utcTimePatten) {
        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten, Locale.CHINA);
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (Exception e) {
            e.printStackTrace();
            return utcTime;
        }
        long time = gpsUTCDate.getTime();
        return LongtoStringRecent(time);
    }


    //判断是周几
    public static String getWeek(long timeStamp) {
        String week = "周";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                week += "日";
                break;
            case 2:
                week += "一";
                break;
            case 3:
                week += "二";
                break;
            case 4:
                week += "三";
                break;
            case 5:
                week += "四";
                break;
            case 6:
                week += "五";
                break;
            case 7:
                week += "六";
                break;
        }
        return week;
    }


    /**
     * 消息通知
     *
     * @return
     */
    public static String longToTime(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");    //设置时间格式

        Date date = new Date(timeStamp);//取得的时间

        String time = sdf.format(date);

        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = sdf.format(curDate);

        Date d1 = null;
        try {
            d1 = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date d2 = null;
        try {
            d2 = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long l = d1.getTime() - d2.getTime();
        long year = l / (24 * 60 * 60 * 1000 * 365);//年
        long day = l / (24 * 60 * 60 * 1000);//天
        long hour = (l / (60 * 60 * 1000));//时
        long min = ((l / (60 * 1000)));//分

        long s = (l / 1000);//秒

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dateYear = calendar.get(Calendar.YEAR);
        int dateDay = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTime(curDate);
        int curYear = calendar.get(Calendar.YEAR);
        int curDay = calendar.get(Calendar.DAY_OF_YEAR);
        int yeasDay = calendar.get(Calendar.DAY_OF_YEAR) - 1;

        if (dateYear == curYear && dateDay == curDay) {
            return "今天";
        } else if (dateYear == curYear && dateDay == yeasDay) {
            return "昨天";
        } else if (dateYear == curYear) {
            String dayTime = LongtoStringFormatYMD(timeStamp);
            return dayTime.substring(5, dayTime.length());
        } else {
            return LongtoStringFormatYMD(timeStamp);
        }

    }


    /**
     * 消息通知
     *
     * @return
     */
    public static String messageTime(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");    //设置时间格式

        Date date = new Date(timeStamp);//取得的时间

        String time = sdf.format(date);

        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = sdf.format(curDate);

        Date d1 = null;
        try {
            d1 = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date d2 = null;
        try {
            d2 = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long l = d1.getTime() - d2.getTime();
        long year = l / (24 * 60 * 60 * 1000 * 365);//年
        long day = l / (24 * 60 * 60 * 1000);//天
        long hour = (l / (60 * 60 * 1000));//时
        long min = ((l / (60 * 1000)));//分

        long s = (l / 1000);//秒

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dateYear = calendar.get(Calendar.YEAR);
        int dateDay = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTime(curDate);
        int curYear = calendar.get(Calendar.YEAR);
        int curDay = calendar.get(Calendar.DAY_OF_YEAR);
        int yeasDay = calendar.get(Calendar.DAY_OF_YEAR) - 1;

        if (min < 1) {
            return "刚刚";
        } else if (hour < 1) {
            return min + "分钟前";
        } else if (dateYear == curYear && dateDay == curDay) {
            return "今天" + LongtoStringFormatHM(timeStamp);
        } else if (dateYear == curYear && dateDay == yeasDay) {
            return "昨天" + LongtoStringFormatHM(timeStamp);
        } else if (dateYear == curYear) {
            String dayTime = LongtoStringFormatYMDHM(timeStamp);
            return dayTime.substring(5, dayTime.length());
        } else {
            return LongtoStringFormatYMDHM(timeStamp);
        }

    }


    //判断是上午还是下午
    public static String getAPM(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        int apm = calendar.get(Calendar.AM_PM);
        return apm == 0 ? "AM" : "PM";
    }


    //日期转化成时间戳
    public static long getTime(String user_time) {
        long l = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(YY);
        Date d;
        try {
            d = sdf.parse(user_time);
            l = d.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return l;
    }


    /**
     * 将日期变更成时间戳
     *
     * @param date
     * @return
     */
    public static long getDateTime(String date) {
        long l = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(YMD_);
        Date d;
        try {
            d = sdf.parse(date);
            l = d.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return l;
    }

}

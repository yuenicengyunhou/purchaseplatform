package com.rails.purchaseplatform.framwork.utils;

import android.text.TextUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 对所有字符串的操作
 * author wangchao
 * email  wangchao@chengantech.com
 * date   on 2017/12/19.
 */

public class StringUtil {


    /**
     * 获取汉字首字母
     *
     * @param chinese
     * @return
     */
    public static String getFristLetter(String chinese) {
        if (TextUtils.isEmpty(chinese))
            return "";
        else
            return String.valueOf(chinese.charAt(0));
    }


    /**
     * 拼接字符串
     *
     * @param splitChar 字符串之间的分割符号
     * @param strs      要拼接的字符串
     * @return
     */
    public static String getJointString(String splitChar, String... strs) {

        try {
            if (strs == null || strs.length <= 0)
                return "";

            StringBuffer stringBuffer = new StringBuffer();
            for (String str : strs) {
                if (TextUtils.isEmpty(str))
                    continue;
                else
                    stringBuffer.append(str + splitChar);
            }
            String jointStr = stringBuffer.toString().trim();
            return jointStr.substring(0, jointStr.length() - 1);
        } catch (Exception e) {
            return "";
        }

    }


    /**
     * 拼接字符串,中间为空格
     *
     * @param strs 要拼接的字符串
     * @return
     */
    public static String getJointEmptyString(String... strs) {

        try {
            if (strs == null || strs.length <= 0)
                return "";

            StringBuffer stringBuffer = new StringBuffer();
            for (String str : strs) {
                if (TextUtils.isEmpty(str))
                    continue;
                else
                    stringBuffer.append(str + "  ");
            }
            String jointStr = stringBuffer.toString().trim();
            return jointStr;
        } catch (Exception e) {
            return "";
        }

    }


    /**
     * 拼接字符串,中间为空格
     *
     * @param strs 要拼接的字符串
     * @return
     */
    public static String getJointEmptyString(List<String> strs) {

        try {
            if (strs == null || strs.size() <= 0)
                return "";

            StringBuffer stringBuffer = new StringBuffer();
            for (String str : strs) {
                if (TextUtils.isEmpty(str))
                    continue;
                else
                    stringBuffer.append(str + "  ");
            }
            String jointStr = stringBuffer.toString().trim();
            return jointStr;
        } catch (Exception e) {
            return "";
        }

    }


    /**
     * @param splitChar
     * @param integers
     * @return
     */
    public static String getJointInteger(String splitChar, ArrayList<Integer> integers) {

        if (integers == null || integers.size() <= 0)
            return "";

        StringBuffer stringBuffer = new StringBuffer();
        for (Integer str : integers) {
            stringBuffer.append(str + splitChar);
        }
        String jointStr = stringBuffer.toString();
        return jointStr.substring(0, jointStr.length() - 1);
    }


    /**
     * 拼接字符串
     *
     * @param splitChar 字符串之间的分割符号
     * @param strs      要拼接的字符串
     * @return
     */
    public static String getJointString(String splitChar, List<String> strs) {

        if (strs == null || strs.size() <= 0)
            return "";

        StringBuffer stringBuffer = new StringBuffer();
        for (String str : strs) {
            stringBuffer.append(str + splitChar);
        }
        String jointStr = stringBuffer.toString().trim();
        return jointStr.substring(0, jointStr.length() - 1);
    }

    /**
     * 如果为空，返回暂无
     *
     * @param str
     * @return
     */
    public static String isEmpty(String str) {
        if (TextUtils.isEmpty(str)) {
            return "暂无";
        } else
            return str;
    }


    /**
     * 如果为空，返回暂无
     *
     * @param str
     * @return
     */
    public static String isEmpty(String str, String def) {
        if (TextUtils.isEmpty(str)) {
            return def;
        } else
            return str;
    }


    public static String isSplitEmpty(String str, String flag) {
        String[] strings;
        if (TextUtils.isEmpty(str)) {
            return "暂无";
        } else {
            try {
                strings = str.split(flag);
                return strings[0];
            } catch (Exception e) {
                return str;
            }

        }
    }


    /**
     * 单位换算
     *
     * @param number
     * @return
     */
    public static String getNumber(int number) {
        double num = 0;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(1);
        df.setRoundingMode(RoundingMode.FLOOR);
        if (number >= 1000 && number < 10000) {
            num = number * 1.0 / 1000;
            return df.format(num) + "k";
        } else if (number >= 10000) {
            num = number * 1.0 / 10000;
            return df.format(num) + "w";
        } else {
            return number + "";
        }
    }


    /**
     * 获取隐藏的手机号码
     *
     * @param phone
     * @return
     */
    public static String getHidePhone(String phone) {
        if (TextUtils.isEmpty(phone))
            return "";
        else {
            return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
    }


    public static String getHideCardId(String cardId) {
        if (TextUtils.isEmpty(cardId))
            return "";
        else {
            int len = cardId.length() - 4;
            return cardId.replace(cardId.substring(4, len), "***********");
        }
    }


    /**
     * 获取随机数
     *
     * @param length 随机数的长度
     * @return
     */
    public static String getRandom(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


    /**
     * 版本号比较
     * 0代表相等，1代表有新版本，-1版本降级
     *
     * @param newVersion
     * @param oldVersion
     * @return
     */
    public static int compareVersion(String newVersion, String oldVersion) {
        if (newVersion.equals(oldVersion)) {
            return 0;
        }
        String[] version1Array = newVersion.split("\\.");
        String[] version2Array = oldVersion.split("\\.");
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        while (index < minLen && (diff = Integer.parseInt(version1Array[index]) - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }
}





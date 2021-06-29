package com.rails.purchaseplatform.framwork.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @date: 2017/04/05  18:30
 * Tip:验证工具类
 */
public class VerificationUtil {
    /**
     * 验证用户名称
     *
     * @param strUserName
     * @return
     */
    public static boolean isUserName_login(String strUserName) {
//        String strPattern = "^(?!_)(?!.*?_$)([a-zA-Z0-9_]|[\u4E00-\u9FA5\uf900-\ufa2d])+$";
        String strPattern = "^[a-zA-Z][a-zA-Z0-9_]{5,17}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strUserName);
        return m.matches();
    }

    /**
     * 校验手机
     *
     * @param strMoible
     * @return
     */
    public static boolean isMobile(String strMoible) {
        String strPattern = "^1+[0-9]{10}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strMoible);
        return m.matches();
    }


    /**
     * 判断是否是隐藏文件
     *
     * @param fileName
     * @return
     */
    public static boolean isHideFile(String fileName) {
        String strPattern = "\\.+[\\s\\S]*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(fileName);
        return m.matches();
    }

    /**
     * 验证码
     *
     * @param identify
     * @return
     */
    public static boolean isIdentify(String identify) {
        String strPattern = "^[0-9A-Za-z]{6}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(identify);
        return m.matches();
    }

    /**
     * 检查短信验证码是否符合规则
     */
    public static boolean checkSmsCode(String smsCode) {
        if (TextUtils.isEmpty(smsCode)) {
            return false;
        } else {
            smsCode = smsCode.trim();
            if (smsCode.length() != 4) {
                return false;
            } else {
                Pattern p = Pattern.compile("[0-9]+");
                Matcher m = p.matcher(smsCode);
                return m.matches();
            }
        }
    }


    /**
     * 随机码格式校验
     *
     * @param randomCodes
     * @return
     */
    public static boolean isRandomCode(String... randomCodes) {
        boolean isChecked = true;
        for (String code : randomCodes) {
            if (TextUtils.isEmpty(code)) {
                isChecked = false;
                break;
            }
            Pattern p = Pattern.compile("^[0-9]{3}$");
            Matcher m = p.matcher(code.trim());
            if (!m.matches()) {
                isChecked = false;
                break;
            }
        }
        return isChecked;
    }

    /**
     * 验证邮箱
     *
     * @param strEmail
     * @return
     */

    public static boolean isEmail(String strEmail) {
        String strPattern = "^[\\w.]+[@][a-zA-Z0-9.]+$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 对用户输入的密码进行格式检查
     *
     * @param password
     * @return
     */
    public static boolean isPaw(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        } else {
            password = password.trim();
            // 后台给的正则 好像不太对的样子
            // "[^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*,.]+$)(?![a-zA-z\d]+$)(?![a-zA-z!@#$%^&*,.]+$)(?![\d!@#$%^&*,.]+$)[a-zA-Z\d!@#$%^&*,.]+$]"
            String strPattern = "(?=.*?[A-Z])(?=.*[a-z]+)(?=.*[\\d]+)(?=.*[\\W]+)(?!.*\\s).{8,}";
            Pattern p = Pattern.compile(strPattern);
            Matcher m = p.matcher(password);
            return m.matches();
        }
    }


    /**
     * 检查手机密码  8-20位数字和字母
     *
     * @param mobiles
     * @return
     */
    public static boolean isPassword(String mobiles) {
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    /**
     * 判断字符是否为字母
     *
     * @param c
     */
    public static boolean isCharacter(String c) {
        String regEx = "[^a-zA-Z]";
        if (c.matches(regEx)) {
            return true;
        }
        return false;
    }

    /**
     * 过滤输入内容
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        String regEx = "[^a-zA-Z0-9]";// 只允许字母和数字
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


    /**
     * 输入框过滤，只能是汉字或字母
     *
     * @param source
     * @return
     */
    public static boolean filterChineseWord(String source) {
        Pattern p = Pattern.compile("[a-zA-Z|\u4e00-\u9fa5]+");
        Matcher m = p.matcher(source);
        if (!m.matches()) return false;
        return true;
    }


    /**
     * 获取字符串中html标签属性
     *
     * @param source  内容
     * @param element 标签
     * @param attr    属性Html
     * @return
     */
    public static String matchHtml(String source, String element, String attr) {
        String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String r = m.group(1);
            return r;
        }
        return "";
    }


    /**
     * 获取字符串中html标签属性
     *
     * @param source  内容
     * @param element 标签
     * @param attr    属性Html
     * @return
     */
    public static String matchHtmlAttr(String source, String element, String attr) {
        String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String r = m.group(1);
            return r;
        }
        return "";
    }


    /**
     * 获取标签中的内容
     *
     * @param source  内容
     * @param element 标签
     * @param attr    属性Html
     * @return
     */
    public static String matchHtmlContent(String source, String element, String attr) {
        String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>([^<]*)</" + element + ">";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String r = m.group(2);
            return r;
        }
        return "";
    }


    /**
     * 基本功能：过滤所有以"<"开头以">"结尾的标签
     *
     * @param str
     * @return String
     */
    public static String filterHtml(String str) {
        String regxpForHtml = "<([^>]*)>";
        Pattern pattern = Pattern.compile(regxpForHtml);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


}

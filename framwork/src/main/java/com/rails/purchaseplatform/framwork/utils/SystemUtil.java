package com.rails.purchaseplatform.framwork.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.lang.reflect.Method;

import androidx.core.content.FileProvider;


/**
 * 调用系统
 * author :Created by sk.
 * date : 2016/11/8.
 */

public class SystemUtil {


    /**
     * 拨打电话
     *
     * @param context
     * @param tel
     */
    public static void callPhone(Context context, String tel) {
        if (isPad(context))
            return;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + tel);
        intent.setData(data);
        context.startActivity(intent);
    }


    /**
     * 调用短信息
     *
     * @param context
     * @param tel
     */
    public static void callMessage(Context context, String tel) {
        Uri uri = Uri.parse("smsto:" + tel);
        Intent intentMessage = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intentMessage);
    }


    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    /**
     * 显示软键盘
     *
     * @param context
     * @param target
     */
    public static void showSoftware(Context context, View target) {
        if (target != null) {
            InputMethodManager inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.showSoftInput(target, 0);
        }
    }

    /**
     * 显示软键盘
     *
     * @param context
     */
    public static void showSoftware(Context context) {
        InputMethodManager inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示软键盘
     *
     * @param context
     * @param isHide  如果软键盘显示，是否可以关闭
     */
    public static void showSoftware(Context context, boolean isHide) {
        InputMethodManager inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isHide)
            inputmanger.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        else
            inputmanger.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);
    }


    /**
     * 关闭软件盘
     *
     * @param aty
     */
    public static void closeSoftware(Activity aty) {
        View target = aty.getWindow().peekDecorView();
        if (target != null) {
            InputMethodManager inputmanger = (InputMethodManager) aty.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(target.getWindowToken(), 0);
        }
    }


    /**
     * 获取电量
     *
     * @param context
     * @return
     */
    public static String getPower(Context context) {
        int power = 60;
        if (Build.VERSION.SDK_INT >= 21) {
            BatteryManager manager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
            power = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        }
        return power + "";
    }


    /**
     * 关闭软件盘
     *
     * @param context
     * @param target
     */
    public static void closeSoftware(Context context, View target) {
        if (target != null) {
            InputMethodManager inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(target.getWindowToken(), 0);
        }
    }


    /**
     * 判断view是否完全可见
     *
     * @return
     */
    public static boolean isVisiable(View view) {
        boolean isVisiable = false;
        Rect rect = new Rect();
        isVisiable = view.getGlobalVisibleRect(rect);
        return isVisiable;
    }


    public static boolean isAllVisiable(View view) {
        boolean isVisiable = false;
        Rect rect = new Rect();
        isVisiable = view.getLocalVisibleRect(rect);
        return isVisiable;
    }



    /**
     * 相机调用
     *
     * @param context
     * @param file
     * @return
     */
    private static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), context.getPackageName() + ".fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }



    /**
     * 检测是否有虚拟键
     *
     * @param context
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }


    /**
     * 复制内容到剪切板
     *
     * @param context
     * @param content
     */
    public static void copy(Context context, String content) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("simple text copy", content);
        clipboardManager.setPrimaryClip(clipData);
    }


    /**
     * 调用系统的剪切板
     */
    public static void copy(String content, Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }


    //获取版本名

    /**
     * 获取版本号名字
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }


    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    /**
     * 通过PackageInfo得到的想要启动的应用的包名
     *
     * @param context
     * @return
     */
    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pInfo = null;

        try {
            //通过PackageManager可以得到PackageInfo
            PackageManager pManager = context.getPackageManager();
            pInfo = pManager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pInfo;
    }


    /**
     * 获取渠道号
     *
     * @param context
     * @return
     */
    public static String getChannelName(Context context) {
        if (context == null) {
            return null;
        }
        String channelName = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.
                        getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = String.valueOf(applicationInfo.metaData.get("UMENG_CHANNEL"));
                        if (channelName.contains("_")) {
                            channelName = channelName.replace("_", "");
                        }
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }

}

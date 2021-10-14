package com.rails.purchaseplatform.framwork.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * 手机屏幕尺寸
 * author sk
 * data
 */
public class ScreenSizeUtil {

    private static DisplayMetrics metrics;

    /**
     * 根据绝对尺寸得到相对尺寸，在不同的分辨率设备上有一致的显示效果, dip->pix
     *
     * @param context
     * @param givenAbsSize 绝对尺寸
     * @return
     */
    public static int getSizeByGivenAbsSize(Context context, int givenAbsSize) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, givenAbsSize, context.getResources().getDisplayMetrics());
    }

    private static DisplayMetrics getDisplayMetrics(Context context) {
        if (metrics != null) {
            return metrics;
        }
        metrics = new DisplayMetrics();
        if (context instanceof Activity){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            else
                ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        }else{
            metrics =context.getResources().getDisplayMetrics();
        }

        return metrics;
    }

    /**
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels; // 屏幕宽度（像素）
    }

    /**
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;// 屏幕高度（像素）
    }


    /**
     * 获取手机屏幕密度
     *
     * @param context
     * @return
     */
    public static float getScreenDensity(Context context) {
        return getDisplayMetrics(context).density; // 屏幕密度（0.75 / 1.0 / 1.5）
    }

    /**
     * 获取手机屏幕 dpi
     *
     * @param context
     * @return
     */
    public static int getScreenDensityDpi(Context context) {
        return getDisplayMetrics(context).densityDpi;// 屏幕密度DPI（120 / 160 / 240）
    }


    /**
     * dp 转化 px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    /**
     * px 转化 dp
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue, Context context) {
        return (int) (pxValue / context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue, Context context) {
        return (int) (spValue * context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }


    /**
     * 获取虚拟功能键高度
     *
     * @param context
     * @return
     */
    public static int getVirtualBarHeigh(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }


    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */

    public static boolean hasNavBar(Context context) {
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
     * 判断设备是否存在NavigationBar
     *
     * @return true 存在, false 不存在
     */
    public static boolean deviceHasNavigationBar() {
        boolean haveNav = false;
        try {
            //1.通过WindowManagerGlobal获取windowManagerService
            // 反射方法：IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
            Class<?> windowManagerGlobalClass = Class.forName("android.view.WindowManagerGlobal");
            Method getWmServiceMethod = windowManagerGlobalClass.getDeclaredMethod("getWindowManagerService");
            getWmServiceMethod.setAccessible(true);
            //getWindowManagerService是静态方法，所以invoke null
            Object iWindowManager = getWmServiceMethod.invoke(null);

            //2.获取windowMangerService的hasNavigationBar方法返回值
            // 反射方法：haveNav = windowManagerService.hasNavigationBar();
            Class<?> iWindowManagerClass = iWindowManager.getClass();
            Method hasNavBarMethod = iWindowManagerClass.getDeclaredMethod("hasNavigationBar");
            hasNavBarMethod.setAccessible(true);
            haveNav = (Boolean) hasNavBarMethod.invoke(iWindowManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return haveNav;
    }


    /**
     * 获取vivo手机设置中的"navigation_gesture_on"值，判断当前系统是使用导航键还是手势导航操作
     *
     * @param context app Context
     * @return false 表示使用的是虚拟导航键(NavigationBar)， true 表示使用的是手势， 默认是false
     */
    public static boolean vivoNavigationGestureEnabled(Context context) {
        String NAVIGATION_GESTURE = "navigation_gesture_on";
        int NAVIGATION_GESTURE_OFF = 0;
        int val = Settings.Secure.getInt(context.getContentResolver(), NAVIGATION_GESTURE, NAVIGATION_GESTURE_OFF);
        return val != NAVIGATION_GESTURE_OFF;
    }


    /**
     * 获取pop到底部的高度
     *
     * @param context
     * @param target
     */
    public static int getPopHeihgt(Context context, View target) {
        int[] location = new int[2];
        target.getLocationOnScreen(location);
        int height;
        boolean hasNavBar = deviceHasNavigationBar() && !vivoNavigationGestureEnabled(context);
        if (!hasNavBar)
            height = ScreenSizeUtil.getScreenHeight(context) - location[1];
        else
            height = ScreenSizeUtil.getScreenHeight(context) - location[1] - ScreenSizeUtil.getVirtualBarHeigh(context);
        return height;

    }


    /**
     * 获取pop到底部的高度
     *
     * @param activity
     * @param target
     */
    public static int getPopHeihgt(Activity activity, View target) {
        int[] location = new int[2];
        target.getLocationOnScreen(location);

        Rect outRect = new Rect();
        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);

        int height = outRect.height() - location[1];
        return height;

    }


    /**
     * 获取activity的高度
     *
     * @param activity
     * @return
     */
    public static int getActivityHeight(Activity activity) {
        Rect outRect = new Rect();
        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);
        int height = outRect.height();
        return height;
    }


}

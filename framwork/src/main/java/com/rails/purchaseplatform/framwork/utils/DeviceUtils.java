package com.rails.purchaseplatform.framwork.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;

/**
 * @author： sk_comic@163.com
 * @date: 2022/6/10
 */
public class DeviceUtils {

    public static String getDeviceId(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        return MD5Util.md5(id);
    }


    public static String getUserAgent(Context context) {
        StringBuffer sb = new StringBuffer();
        sb.append("welfaremall/");
        sb.append(SystemUtil.getVersionName(context) + ";");
        sb.append("Android;");
        sb.append("Android_" + Build.VERSION.RELEASE + ";");
        sb.append("Scale/" + ScreenSizeUtil.getScreenDensity(context));
        return sb.toString();
    }


    public static String getNetType(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return "";
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return "wifi";
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            return "mobile";
                        }
                    }
                }
            }
        }
        return "";
    }
}

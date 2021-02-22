package com.rails.purchaseplatform.framwork.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 网络监测服务
 * author :Created by wangchao {wangchao@ejiajx.com}
 * date : 2017/6/7.
 */
public class NetWorkUtil {

    /**
     * 检查当前网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean checkNet(Context context) {

        ConnectivityManager manager = ServiceManager.getConnectivityManager(context);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isAvailable() && info.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是不是wifi连接
     *
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager manager = ServiceManager.getConnectivityManager(context);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            int type = networkInfo.getType();
            if (networkInfo.isAvailable() && networkInfo.isConnected() && type == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前网络是否是移动流量连接
     *
     * @param context
     * @return
     */
    public static boolean isMobile(Context context) {
        ConnectivityManager manager = ServiceManager.getConnectivityManager(context);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            int type = networkInfo.getType();
            if (networkInfo.isAvailable() && networkInfo.isConnected() && type == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断wifi是否已经打开
     *
     * @param context
     * @return
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager mgrConn = ServiceManager.getConnectivityManager(context);
        TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * 判断是否是4G网络连接
     *
     * @param context
     * @return
     */

    public static boolean is4G(Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable() && TelephonyManager.NETWORK_TYPE_LTE == info.getSubtype()) {
            return true;
        }
        return false;
    }


    /**
     * 当前连接的网络类型名称
     *
     * @param context
     * @return
     */
    public static String getNetworkName(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null) {
            // 网络类型名称
            return info.getTypeName();
        }
        return "";
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Context context) {
        Intent intent = new Intent();
        if (android.os.Build.VERSION.SDK_INT > 10) {
            intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        } else {
            // android4.0系统找不到此activity。
            intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
        }
        context.startActivity(intent);
    }

    /**
     * 检查网络本地的Ip
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }
}

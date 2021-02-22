package com.rails.purchaseplatform.framwork.utils;

import android.app.AppOpsManager;
import android.app.Service;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 获得系统服务管理器
 */
public class ServiceManager {

    private static ConnectivityManager cm;
    private static LocationManager locationManager;
    private static TelephonyManager telephonyManager;
    private static InputMethodManager inputMethodManager;
    private static Vibrator vibrator;
    private static SensorManager sensorManager;
    private static Sensor accelerometerSensor;
    private static Sensor lightSensor;//光线传感器引用
    private static LayoutInflater inflater;
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    private ServiceManager() {
    }

    ;

    /**
     * 获得ConnectivityManager
     *
     * @param context
     * @return
     */
    public static ConnectivityManager getConnectivityManager(Context context) {

        if (cm == null) {
            synchronized (ServiceManager.class) {
                if (cm == null) {
                    cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                }
            }
        }
        return cm;
    }

    /**
     * 获得LocationManager
     *
     * @param context
     * @return
     */
    public static LocationManager getLocationManager(Context context) {
        if (locationManager == null) {
            synchronized (ServiceManager.class) {
                if (locationManager == null) {
                    locationManager = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
                }
            }
        }
        return locationManager;
    }

    /**
     * 判断是否开启了定位
     * <p>
     * liuguang 2017-10-12
     */
    public static boolean isOpenGps(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        }
        return false;
    }

    /**
     * 判断手机推送设置是否开启
     * liuguang 2017-10-12
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isNotificationEnabled(Context context) {
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获得TelephonyManager
     *
     * @param context
     * @return
     */
    public static TelephonyManager getTelephonyManager(Context context) {
        if (telephonyManager == null) {
            synchronized (ServiceManager.class) {
                if (telephonyManager == null) {
                    telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
                }
            }
        }
        return telephonyManager;
    }

    /**
     * 获得InputMethodManager
     *
     * @param context
     * @return
     */
    public static InputMethodManager getInputMethodManager(Context context) {
        if (inputMethodManager == null) {
            synchronized (ServiceManager.class) {
                if (inputMethodManager == null) {
                    inputMethodManager = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
                }
            }
        }
        return inputMethodManager;
    }

    /**
     * 获得震动的控制器
     *
     * @param context
     * @return
     */
    public static Vibrator getVibrator(Context context) {
        if (vibrator == null) {
            synchronized (ServiceManager.class) {
                if (vibrator == null) {
                    vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
                }
            }
        }
        return vibrator;
    }

    /**
     * 获得传感器管理器
     *
     * @param context
     * @return
     */
    public static SensorManager getSensorManager(Context context) {
        if (sensorManager == null) {
            synchronized (ServiceManager.class) {
                if (sensorManager == null) {
                    sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
                }
            }
        }
        return sensorManager;
    }

    /**
     * 获得加速管理器
     *
     * @param context
     * @return
     */
    public static Sensor getAccelerometerSensor(Context context) {
        if (accelerometerSensor == null) {
            synchronized (ServiceManager.class) {
                if (accelerometerSensor == null) {
                    accelerometerSensor = getSensorManager(context).getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                }
            }
        }
        return accelerometerSensor;
    }

    public static Sensor getLightSensor(Context context) {
        if (lightSensor == null) {
            synchronized (ServiceManager.class) {
                if (lightSensor == null) {
                    lightSensor = getSensorManager(context).getDefaultSensor(Sensor.TYPE_LIGHT);
                }
            }
        }
        return lightSensor;
    }

    public static LayoutInflater getLayoutInflate(Context context) {
        if (inflater == null) {
            synchronized (ServiceManager.class) {
                if (inflater == null) {
                    inflater = LayoutInflater.from(context);
                }
            }
        }
        return inflater;
    }
}

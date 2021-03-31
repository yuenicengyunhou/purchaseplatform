package com.rails.purchaseplatform.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;


/**
 * 高德地图定位信息
 * author: wangchao
 */

public class LocationUtil {

    /**
     * 获取定位信息
     *
     * @param context
     */
    public static void getLocation(final Context context, final CallBack callBack) {
        if (context == null)
            return;
        final AMapLocationClient mapLocationClient = new AMapLocationClient(context);
        AMapLocationClientOption mapLocationClientOption = new AMapLocationClientOption();
        mapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mapLocationClientOption.setInterval(2000);
        mapLocationClientOption.setNeedAddress(true);
        mapLocationClient.setLocationOption(mapLocationClientOption);
        mapLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (mapLocationClient != null) {
                    mapLocationClient.stopLocation();
                    mapLocationClient.onDestroy();
                }
                callBack.getMapLocation(aMapLocation);
            }
        });
        mapLocationClient.startLocation();
    }


    /**
     * 自定义view转换成bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap convertToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;

    }


    public interface CallBack {
        void getMapLocation(AMapLocation mapLocation);
    }

}

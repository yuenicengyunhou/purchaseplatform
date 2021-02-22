package com.rails.purchaseplatform.common.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rails.purchaseplatform.common.R;


/**
 * toast工具类
 */
public class ToastUtil {
    private ToastUtil() {

    }

    /**
     * 弹出一个toast
     *
     * @param context
     * @param resId
     */
    public static void show(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出一个toast
     *
     * @param context
     * @param message
     */
    public static void show(Context context, String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();
    }


    /**
     * @param context
     * @param msg
     */
    public static void showImage(Context context, String msg) {
        if (TextUtils.isEmpty(msg))
            return;
        View view = LayoutInflater.from(context).inflate(R.layout.toast_center_img, null);
        LinearLayout root = view.findViewById(R.id.root);
        LinearLayout layout = view.findViewById(R.id.ll_toast_center);
        layout.setAlpha(0.8f);
        TextView tv_toast = view.findViewById(R.id.tv_toast);
        tv_toast.setText(msg);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(outMetrics.widthPixels,
                outMetrics.heightPixels);
        vlp.setMargins(0, 0, 0, 0);
        root.setLayoutParams(vlp);

        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);// 显示的位置
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();

    }


    /**
     * 显示在中部
     *
     * @param context
     * @param message
     */
    public static void showCenter(Context context, String message) {
        if (TextUtils.isEmpty(message))
            return;
        View view = LayoutInflater.from(context).inflate(R.layout.toast_center, null);
        LinearLayout layout = view.findViewById(R.id.ll_toast_center);
        TextView tv_toast = view.findViewById(R.id.tv_toast);
        tv_toast.setText(message);
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);// 显示的位置
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }


    /**
     * 吐司文本提示显示在中间
     * <p>
     * liuguang 2017-08-17
     *
     * @param context 上下文
     * @param str     str提示文本
     */
    public static void showCenter(Context context, String str, int duration) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_center, null);
        LinearLayout layout = view.findViewById(R.id.ll_toast_center);
        layout.setAlpha(0.8f);
        TextView tv_toast = view.findViewById(R.id.tv_toast);
        tv_toast.setText(str);
        Toast toast = Toast.makeText(context, str, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);// 显示的位置
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }


}

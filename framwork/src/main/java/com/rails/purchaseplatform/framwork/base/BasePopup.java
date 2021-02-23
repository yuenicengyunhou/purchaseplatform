package com.rails.purchaseplatform.framwork.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.viewbinding.ViewBinding;

/**
 * 自定义popup
 * author wangchao
 * date   on 2018/2/27.
 */

public abstract class BasePopup<T extends ViewBinding> extends BasePopupWindow implements BaseView {

    protected Activity activity;
    protected Context context;

    public T binding;

    public BasePopup(Activity context, int... WH) {
        this.activity = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            try {
                Class<T> cls = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
                Method method = cls.getMethod("inflate", LayoutInflater.class, ViewGroup.class, Boolean.TYPE);
                binding = (T) method.invoke(null, inflater, null, false);
            } catch (Exception e) {
                e.getCause();
            }
        }

        setContentView(binding.getRoot());

        initialize();

        if (WH != null && WH.length >= 2)
            setConfig(WH[0], WH[1]);
    }


    public BasePopup(Context context, int... WH) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            try {
                Class<T> cls = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
                Method method = cls.getMethod("inflate", LayoutInflater.class, ViewGroup.class, Boolean.TYPE);
                binding = (T) method.invoke(null, inflater, null, false);
            } catch (Exception e) {
                e.getCause();
            }
        }

        setContentView(binding.getRoot());

        initialize();

        if (WH != null && WH.length >= 2)
            setConfig(WH[0], WH[1]);
    }


    /**
     * 设置弹窗宽高
     *
     * @param width
     * @param height
     */
    private void setConfig(int width, int height) {
        this.setWidth(width);
        this.setHeight(height);
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x66000000);
        this.setBackgroundDrawable(dw);
    }

    /**
     * 设置背景
     *
     * @param color
     */
    public void setBackGround(int color) {
        ColorDrawable dw = new ColorDrawable(color);
        this.setBackgroundDrawable(dw);
    }


    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void showResDialog(int res) {

    }

    @Override
    public void dismissDialog() {

    }


    /**
     * init object
     */
    protected abstract void initialize();
}

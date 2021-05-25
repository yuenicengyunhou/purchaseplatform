package com.rails.purchaseplatform.framwork.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.rails.purchaseplatform.framwork.R;
import com.rails.purchaseplatform.framwork.utils.AnimationUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewbinding.ViewBinding;

/**
 * author wangchao
 * date   on 2017/12/11.
 */

public abstract class BasePop<T extends ViewBinding> extends DialogFragment {
    final private String TAG = BasePop.class.getSimpleName();

    private static final float DEFAULT_ALPHA = 0.35f;

    int gravity = 0;
    int alpha;
    int y;
    int type;

    public T binding;


    public static final int MATCH_MATCH = 0;
    public static final int MATCH_WRAP = 1;
    public static final int WRAP_WRAP = 3;
    public static final int MATCH_CUSTOM = 4;


    public BasePop setType(int type) {
        this.type = type;
        return this;
    }

    public BasePop setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public BasePop setAlpha(int alpha) {
        this.alpha = alpha;
        return this;
    }


    public BasePop setY(int y) {
        this.y = y;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            try {
                Class<T> cls = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
                Method method = cls.getMethod("inflate", LayoutInflater.class, ViewGroup.class, Boolean.TYPE);
                binding = (T) method.invoke(null, inflater, container, false);
            } catch (Exception e) {
                e.getCause();
            }
        }
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Log.d(TAG, "onViewCreated");
        view.setClickable(true);
        if (gravity == Gravity.BOTTOM)
            AnimationUtil.slideToUp(view);
        initialize(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        setScreenHeight(type, y);

    }


    /**
     * @param type
     * @param height
     */
    private void setScreenHeight(int type, int height) {
        //设置宽度铺满全屏
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        win.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        WindowManager.LayoutParams params = win.getAttributes();
        params.dimAmount = alpha == 0 ? DEFAULT_ALPHA : alpha;
        params.gravity = gravity == 0 ? Gravity.BOTTOM : gravity;
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        if (type == MATCH_MATCH) {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else if (type == MATCH_WRAP) {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else if (type == WRAP_WRAP) {
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else if (type == MATCH_CUSTOM) {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = height;
        } else {
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        }

        if (gravity == Gravity.TOP) {
            params.y = ScreenSizeUtil.dp2px(getContext(), y);
        }
        win.setAttributes(params);
    }


    /**
     * init object
     */
    protected abstract void initialize(Bundle bundle);


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public interface Listener {

        /**
         * 当关闭执行
         */
        void onDismissAction();
    }
}

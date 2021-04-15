package com.rails.purchaseplatform.framwork.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.orhanobut.logger.Logger;
import com.rails.purchaseplatform.framwork.R;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.viewbinding.ViewBinding;


/**
 * Created by wangchao on 2017/5/16.
 * 绑定Butternife,权限
 */
public abstract class BaseActivity<T extends ViewBinding> extends BaseAbsActivity implements BaseView {

    public T binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        BaseActManager.getInstance().addActivity(this);
        setLayout();
        setStatusBar();
        initialize(savedInstanceState);
        onClick();
    }


    /**
     * 添加子布局
     */
    protected void setLayout() {
        try {
            Type type = this.getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                try {
                    Class<T> cls = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
                    Method method = cls.getMethod("inflate", LayoutInflater.class);
                    binding = (T) method.invoke(null, getLayoutInflater());
                } catch (Exception e) {
                }
                setContentView(binding.getRoot());
            }
        } catch (Exception e) {
        }

    }


    /**
     * Activity 关联布局文件
     *
     * @return
     */
    protected void onClick() {

    }

    @Override
    public void onError(ErrorBean errorBean) {

    }

    /**
     * 对象初始化，方法调用
     */
    protected abstract void initialize(Bundle bundle);


    @Override
    public void showDialog(String msg) {
        toggleShowDialogLoading(msg);
    }

    @Override
    public void showResDialog(int res) {
        toggleShowDialogLoading(getResources().getString(res));
    }

    @Override
    public void dismissDialog() {
        toggleDismissDialogLoading();
    }


    /**
     * isn't  status bar transparent
     * true?transparent : toolbar color
     */
    private void setStatusBar() {
        if (isSetSystemBar()) {
            int color = getColor();
            if (color == 0) {
                setupSystemBar(Color.TRANSPARENT);
            } else if (color == android.R.color.white) {
                setLightMode();
            } else {
                setupSystemBar(getColor());
            }
        }
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            return createConfigurationContext(config).getResources();
        } else {
            res.updateConfiguration(config, res.getDisplayMetrics());
            return res;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }


    public void finishAct() {
        super.finish();
    }


    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
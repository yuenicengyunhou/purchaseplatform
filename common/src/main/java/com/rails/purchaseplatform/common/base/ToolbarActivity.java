package com.rails.purchaseplatform.common.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rails.purchaseplatform.common.R;
import com.rails.purchaseplatform.common.databinding.BaseToolbarBinding;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.utils.NetWorkUtil;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.viewbinding.ViewBinding;


/**
 * toolbar activity
 * author: wangchao
 * date:on 2017/5/31
 */

public abstract class ToolbarActivity<T extends ViewBinding> extends BaseErrorActivity<BaseToolbarBinding> {

    public T barBinding;

    @Override
    protected void setLayout() {
        binding = BaseToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            try {
                Class<T> cls = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
                Method method = cls.getMethod("inflate", LayoutInflater.class, ViewGroup.class, Boolean.TYPE);
                barBinding = (T) method.invoke(null, getLayoutInflater(), binding.flContent, true);
            } catch (Exception e) {
                e.getCause();
            }
        }

        binding.titleBar.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });

        binding.netEmpty.setImgEmpty(R.drawable.ic_launcher_background).setContentEmpty(R.string.app_name).setMarginTop(100).setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetWorkUtil.isWifiEnabled(ToolbarActivity.this)) {
                    binding.netEmpty.setVisibility(View.GONE);
                    reNetLoad();
                } else {
                    binding.netEmpty.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public void onError(ErrorBean errorBean) {
        super.onError(errorBean);
        switch (errorBean.getCode()) {

        }
    }


    /**
     * 返回键处理
     */
    public void onBack() {
        finish();
    }


    /**
     *
     */
    protected void reNetLoad() {
    }


    @Override
    public void finish() {
        super.finish();
    }
}


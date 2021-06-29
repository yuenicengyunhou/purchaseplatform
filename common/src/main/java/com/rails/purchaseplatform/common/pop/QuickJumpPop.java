package com.rails.purchaseplatform.common.pop;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;


import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.ResultWebBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.databinding.PopQuickJumpBinding;
import com.rails.purchaseplatform.framwork.base.BaseActManager;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.bean.BusEvent;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

public class QuickJumpPop extends BasePop<PopQuickJumpBinding> {

    private QuickTabListener quickTabListener;

    public void setQuickTabListener(QuickTabListener quickTabListener) {
        this.quickTabListener = quickTabListener;
    }

    @Override
    protected void initialize(Bundle bundle) {
        binding.tvToCart.setOnClickListener(v -> {
            if (null != quickTabListener) {
                quickTabListener.onQuickClick("购物车");
            }
//            BaseActManager.getInstance().clear();
//            String json = "{\"type\": 1,\"msg\": \"采购单提交成功\",\"btnleft\": \"查看采购单\",\"btnright\": \"返回首页\",\"urlleft\": \"/order/mian\",\"urlright\": \"/rails/main\",\"code\": 2}";
//            toActivity(ConRoute.RAILS.MAIN,json);
        });

        binding.tvToHome.setOnClickListener(v -> {
            if (null != quickTabListener) {
                quickTabListener.onQuickClick("首页");
            }
//            BaseActManager.getInstance().clearToTop();
//            String json = "{\"type\": 1,\"msg\": \"采购单提交成功\",\"btnleft\": \"查看采购单\",\"btnright\": \"返回首页\",\"urlleft\": \"/order/mian\",\"urlright\": \"/rails/main\",\"code\": 0}";
//            toActivity(ConRoute.RAILS.MAIN,json);
        });

        binding.tvToSearch.setOnClickListener(v -> {
            if (null != quickTabListener) {
                quickTabListener.onQuickClick("搜索");
            }
//            BaseActManager.getInstance().clearToTop();
//            String json = "{\"type\": 1,\"msg\": \"采购单提交成功\",\"btnleft\": \"查看采购单\",\"btnright\": \"返回首页\",\"urlleft\": \"/order/mian\",\"urlright\": \"/rails/main\",\"code\": 0}";
//            toActivity(ConRoute.COMMON.SEARCH,json);
        });

        binding.tvToMine.setOnClickListener(v -> {
            if (null != quickTabListener) {
                quickTabListener.onQuickClick("我的");
            }
//            BaseActManager.getInstance().clearToTop();
//            String json = "{\"type\": 1,\"msg\": \"采购单提交成功\",\"btnleft\": \"查看采购单\",\"btnright\": \"返回首页\",\"urlleft\": \"/order/mian\",\"urlright\": \"/rails/main\",\"code\": 3}";
//            toActivity(ConRoute.RAILS.MAIN,json);
        });

        binding.tvDismiss.setOnClickListener(v -> dismiss());
    }



    public interface QuickTabListener{
        void onQuickClick(String type);
    }
}

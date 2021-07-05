package com.rails.purchaseplatform.common.pop;

import android.os.Bundle;
import android.util.Log;


import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.ResultWebBean;
import com.rails.purchaseplatform.common.databinding.PopQuickJumpBinding;
import com.rails.purchaseplatform.framwork.base.BaseActManager;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;


public class QuickJumpPop extends BasePop<PopQuickJumpBinding> {


    @Override
    protected void initialize(Bundle bundle) {
        binding.tvToCart.setOnClickListener(v -> {
            String json = "{\"type\": 1,\"msg\": \"采购单提交成功\",\"btnleft\": \"查看采购单\",\"btnright\": \"返回首页\",\"urlleft\": \"/order/mian\",\"urlright\": \"/rails/main\",\"code\": 2}";
            toActivity(json);
        });

        binding.tvToHome.setOnClickListener(v -> {
            String json = "{\"type\": 1,\"msg\": \"采购单提交成功\",\"btnleft\": \"查看采购单\",\"btnright\": \"返回首页\",\"urlleft\": \"/order/mian\",\"urlright\": \"/rails/main\",\"code\": 0}";
            toActivity(json);
        });

        binding.tvToSearch.setOnClickListener(v -> {
            String json = "{\"type\": 1,\"msg\": \"采购单提交成功\",\"btnleft\": \"查看采购单\",\"btnright\": \"返回首页\",\"urlleft\": \"/order/mian\",\"urlright\": \"/common/SearchActivityX\",\"code\": 0}";
            toActivity(json);
        });

        binding.tvToMine.setOnClickListener(v -> {
            String json = "{\"type\": 1,\"msg\": \"采购单提交成功\",\"btnleft\": \"查看采购单\",\"btnright\": \"返回首页\",\"urlleft\": \"/order/mian\",\"urlright\": \"/rails/main\",\"code\": 3}";
            toActivity(json);
        });

        binding.tvDismiss.setOnClickListener(v -> dismiss());
    }

    private void toActivity(String json) {
        ResultWebBean webBean = JsonUtil.parseJson(json, ResultWebBean.class);
        if (webBean.getUrlright().contains("main")) {
            BaseActManager.getInstance().clear();
        }
        ARouter.getInstance()
                .build(webBean.getUrlright())
                .withParcelable("webBean", webBean)
                .navigation();
        dismiss();
    }


}

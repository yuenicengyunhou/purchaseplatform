package com.rails.purchaseplatform.common.pop;

import android.os.Bundle;


import com.rails.purchaseplatform.common.databinding.PopQuickJumpBinding;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

public class QuickJumpPop extends BasePop<PopQuickJumpBinding> {
    @Override
    protected void initialize(Bundle bundle) {
        binding.tvToCart.setOnClickListener(v -> {
            ToastUtil.showCenter(getContext(),"去购物车");
        });

        binding.tvToHome.setOnClickListener(v -> {
            ToastUtil.showCenter(getContext(),"去首页");
        });

        binding.tvToSearch.setOnClickListener(v -> {
            ToastUtil.showCenter(getContext(),"去搜索");
        });

        binding.tvToMine.setOnClickListener(v -> {
            ToastUtil.showCenter(getContext(),"我的");
        });

        binding.tvDismiss.setOnClickListener(v -> {
            dismiss();
        });
    }
}

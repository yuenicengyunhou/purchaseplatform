package com.rails.purchaseplatform.user.ui.activity;

import android.os.Bundle;

import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.user.databinding.ActivityFindPassword2Binding;

public class FindPassword2Activity extends BaseErrorActivity<ActivityFindPassword2Binding> {


    @Override
    protected int getColor() {
        return 0;
    }

    @Override
    protected boolean isSetSystemBar() {
        return false;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected void initialize(Bundle bundle) {

    }

    @Override
    protected void onClick() {
        super.onClick();
        binding.ibBack.setOnClickListener(v -> finish());
        binding.btnConfirm.setOnClickListener(v -> finish());
    }
}
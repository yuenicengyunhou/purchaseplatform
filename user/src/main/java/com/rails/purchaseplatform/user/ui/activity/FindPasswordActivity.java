package com.rails.purchaseplatform.user.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.user.databinding.ActivityFindPasswordBinding;

public class FindPasswordActivity extends BaseErrorActivity<ActivityFindPasswordBinding> {

    @Override
    protected void initialize(Bundle bundle) {

    }

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
    protected void onClick() {
        super.onClick();
        binding.btnConfirm.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(this, FindPassword2Activity.class);
            startActivity(intent);
        });
    }
}
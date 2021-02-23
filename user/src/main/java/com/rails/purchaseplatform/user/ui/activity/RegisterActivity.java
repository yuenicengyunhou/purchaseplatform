package com.rails.purchaseplatform.user.ui.activity;

import android.os.Bundle;

import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.user.databinding.ActivityUserRegisterBinding;

/**
 * 注册页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/23
 */
public class RegisterActivity extends BaseErrorActivity<ActivityUserRegisterBinding> {
    @Override
    protected void initialize(Bundle bundle) {

    }

    @Override
    protected int getColor() {
        return android.R.color.white;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void onFailure(String errorMsg) {

    }
}

package com.rails.purchaseplatform.user.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.user.databinding.ActivityRetrievePasswordConfirmBinding;

/**
 * @author ZhangXiaofu
 * @version 1
 * <p>
 * 找回密码确认页面
 * 用户名和邮箱验证通过
 * @since 2021.03.28
 */
@Route(path = ConRoute.USER.RETRIEVE_PASSWORD)
public class RetrievePasswordConfirmActivity extends BaseErrorActivity<ActivityRetrievePasswordConfirmBinding> {
    private String mEmail;

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
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        mEmail = extras.getString("email");
    }

    @Override
    protected void onClick() {
        super.onClick();
        binding.tvEmailForFindPassword.setText(mEmail);
        binding.ibBack.setOnClickListener(v -> finish());
        binding.btnConfirm.setOnClickListener(v -> finish());
    }
}
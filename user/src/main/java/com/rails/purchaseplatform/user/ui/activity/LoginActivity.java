package com.rails.purchaseplatform.user.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.user.databinding.ActivityUserLoginBinding;

/**
 * 登录页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/27
 */
@Route(path = ConRoute.USER.LOGIN)
public class LoginActivity extends BaseErrorActivity<ActivityUserLoginBinding> {


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

    @Override
    protected void onClick() {
        super.onClick();
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ConRoute.RAILS.MAIN).navigation();
            }
        });
    }
}

package com.rails.purchaseplatform.user.ui.activity;

import android.os.Bundle;

import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.contract.LoginContract;
import com.rails.lib_data.contract.LoginPresneterImpl;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.user.databinding.ActivityUserRegisterBinding;

/**
 * 注册页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/23
 */
public class RegisterActivity extends BaseErrorActivity<ActivityUserRegisterBinding> implements LoginContract.LoginView {

    private LoginContract.LoginPresenter presenter;

    @Override
    protected void initialize(Bundle bundle) {
        presenter = new LoginPresneterImpl(this, this);
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
    public void onResult(int type, String msg, String token) {

    }

    @Override
    public void getUserInfo(UserInfoBean bean) {

    }

    @Override
    public void setVerifyCode(String verifyCode) {

    }

    @Override
    public void onRandomInitSuccess(String randInit) {

    }
}

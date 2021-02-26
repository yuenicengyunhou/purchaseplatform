package com.rails.purchaseplatform.user.ui.activity;

import android.os.Bundle;

import com.rails.lib_data.bean.TestBean;
import com.rails.lib_data.contract.LoginContract;
import com.rails.lib_data.presenter.LoginPresneterImpl;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.user.databinding.ActivityUserRegisterBinding;

import java.util.ArrayList;

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
        presenter.getTests();
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
    public void getTests(ArrayList<TestBean> testBeans) {
            startIntent(LoginActivity.class);
    }
}

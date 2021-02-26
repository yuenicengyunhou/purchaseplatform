package com.rails.lib_data.presenter;

import android.app.Activity;

import com.orhanobut.logger.Logger;
import com.rails.lib_data.bean.TestBean;
import com.rails.lib_data.contract.LoginContract;
import com.rails.lib_data.model.LoginModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;

/**
 * @author： sk_comic@163.com
 * @date: 2021/2/23
 */
public class LoginPresneterImpl extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter {


    private LoginModel model;

    public LoginPresneterImpl(Activity mContext, LoginContract.LoginView loginView) {
        super(mContext, loginView);
        model = new LoginModel();
    }

    @Override
    public void getTests() {
        model.getTests(new HttpRxObserver<ArrayList<TestBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ArrayList<TestBean> beans) {
                baseView.getTests(beans);
            }
        });
    }
}

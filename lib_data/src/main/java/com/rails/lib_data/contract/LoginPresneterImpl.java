package com.rails.lib_data.contract;

import android.app.Activity;
import android.widget.Toast;

import com.rails.lib_data.R;
import com.rails.lib_data.model.LoginModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.framwork.utils.VerificationUtil;

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
    public void onLogin(String phone, String paw, String code) {

        // TODO: 2021/3/31 正则 - 验证校验

        if (!VerificationUtil.isMobile(phone)) {
            ToastUtil.showCenter(mContext, "手机号码格式错误");
            return;
        }

        if (!VerificationUtil.isPaw(paw)) {
            ToastUtil.showCenter(mContext, "密码格式错误");
            return;
        }

        if (!VerificationUtil.isIdentify(code)) {
            ToastUtil.showCenter(mContext, "验证码格式错误");
            return;
        }


        baseView.showResDialog(R.string.loading);
        model.onLogin(phone, paw, code, new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(String response) {
                baseView.dismissDialog();
                baseView.onResult(0, "response");
            }
        });
    }

    @Override
    public void getCode(String phone) {
        baseView.showResDialog(R.string.loading);
        model.getCode(phone, new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(String response) {
                baseView.dismissDialog();
                baseView.onResult(1, "response");
            }
        });
    }
}

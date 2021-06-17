package com.rails.lib_data.contract;

import android.Manifest;
import android.app.Activity;
import android.text.TextUtils;

import com.rails.lib_data.ConShare;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.model.LoginModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.framwork.utils.VerificationUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;


/**
 * author： sk_comic@163.com
 * date: 2021/2/23
 */
public class LoginPresneterImpl extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter {

    private LoginModel model;

    public LoginPresneterImpl(Activity mContext, LoginContract.LoginView loginView) {
        super(mContext, loginView);
        model = new LoginModel();
    }


    @Override
    public void onLogin(String phone, String paw, String code) {
//        String code_phone = PrefrenceUtil.getInstance(mContext).getString(ConShare.CODE_PHONE, "");
//
//        if (TextUtils.isEmpty(code_phone)) {
//            ToastUtil.showCenter(mContext, "请先获取验证码");
//            return;
//        }

        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showCenter(mContext, "手机号码不能为空");
            return;
        }

        if (!VerificationUtil.isMobile(phone)) {
            ToastUtil.showCenter(mContext, "手机号码格式错误");
            return;
        }

//        if (!code_phone.equals(phone)) {
//            ToastUtil.showCenter(mContext, "与申请验证码的手机号码不一致");
//            return;
//        }


        if (TextUtils.isEmpty(paw)) {
            ToastUtil.showCenter(mContext, "密码不能为空");
            return;
        }

        if (!VerificationUtil.isPaw(paw)) {
            ToastUtil.showCenter(mContext, "密码格式错误");
            return;
        }

        if (TextUtils.isEmpty(code)) {
            ToastUtil.showCenter(mContext, "验证码不能为空");
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
                baseView.onResult(0, "登录成功", response);
            }
        });
    }

    @Override
    public void getCode(String phone) {

        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showCenter(mContext, "手机号码不能为空");
            return;
        }

        if (!VerificationUtil.isMobile(phone)) {
            ToastUtil.showCenter(mContext, "手机号码格式错误");
            return;
        }

        baseView.showResDialog(R.string.loading);
        model.getCode(phone, new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
//                PrefrenceUtil.getInstance(mContext).setString(ConShare.CODE_PHONE, phone);
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(String response) {
//                PrefrenceUtil.getInstance(mContext).setString(ConShare.CODE_PHONE, phone);
                baseView.dismissDialog();
                baseView.onResult(1, "获取成功", response);
                baseView.setVerifyCode(response);

            }
        });
    }


    /**
     * 获取用户信息
     */
    @Override
    public void getUserInfo(boolean isDialog, String token) {
        if (TextUtils.isEmpty(token))
            return;

        if (isDialog)
            baseView.showResDialog(R.string.loading);
        model.getUserInfo(token, new HttpRxObserver<UserInfoBean>() {
            @Override
            protected void onError(ErrorBean e) {
//                PrefrenceUtil.getInstance(mContext).setString(ConShare.CODE_PHONE, phone);
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(UserInfoBean bean) {
                baseView.dismissDialog();
                baseView.getUserInfo(bean);
            }
        });
    }
}

package com.rails.lib_data.contract;

import android.app.Activity;
import android.text.TextUtils;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.model.LoginModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.MD5Util;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.framwork.utils.VerificationUtil;


/**
 * author： sk_comic@163.com
 * date: 2021/2/23
 */
public class LoginPresneterImpl extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter {
    final private String TAG = LoginPresneterImpl.class.getSimpleName();

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

    @Override
    public void randomInit(String code, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);
        model.randomInit(code, new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(String response) {
                baseView.onRandomInitSuccess(response);
                baseView.dismissDialog();
            }
        });
    }

    /**
     * 随机码登录
     *
     * @param account
     * @param password
     * @param randInit
     * @param randomCode1
     * @param randomCode2
     * @param randomCode3
     * @param isDialog
     */
    @Override
    public void randomCodeLogin(String account, String password, String randInit, String randomCode1, String randomCode2, String randomCode3, boolean isDialog) {
        if (TextUtils.isEmpty(account)) {
            ToastUtil.showCenter(mContext, "用户名不能为空");
            return;
        }

        if (!VerificationUtil.isUserName_login(account)) {
            ToastUtil.showCenter(mContext, "用户名格式错误");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtil.showCenter(mContext, "密码不能为空");
            return;
        }

        if (!VerificationUtil.isPaw(password)) {
            ToastUtil.showCenter(mContext, "密码格式错误");
            return;
        }

        if (!VerificationUtil.isRandomCode(randomCode1, randomCode2, randomCode3)) {
            ToastUtil.showCenter(mContext, "随机码格式错误");
            return;
        }

        String rndCodeAsDoubleMd5 = MD5Util.md5md5(MD5Util.md5md5(randInit + randomCode1 + randomCode2 + randomCode3).toUpperCase()).toUpperCase();
        String passwordAsDoubleMd5 = MD5Util.MD5(MD5Util.MD5(password));

        if (isDialog) baseView.showResDialog(R.string.loading);
        model.randomCodeLogin(account, passwordAsDoubleMd5, rndCodeAsDoubleMd5, randInit, new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(String response) {
                baseView.dismissDialog();
                baseView.onResult(0, "登录成功", response);
            }
        });
    }
}

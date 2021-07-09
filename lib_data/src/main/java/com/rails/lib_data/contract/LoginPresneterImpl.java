package com.rails.lib_data.contract;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.model.LoginModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.GpsUtils;
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

        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showCenter(mContext, "手机号码不能为空");
            return;
        }

        if (!VerificationUtil.isMobile(phone)) {
            ToastUtil.showCenter(mContext, "手机号码格式错误");
            return;
        }

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

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ToastUtil.showCenter(mContext, "请授予地理位置权限");
            return;
        }
        double[] location = GpsUtils.getInstance(mContext).getLocation();

        baseView.showResDialog(R.string.loading);
        model.onLogin(phone, paw, code, location, new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(String response) {
                baseView.dismissDialog();
                baseView.onResult(0, "", response);
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
                baseView.setVerifyCode(response,phone);

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
                ToastUtil.showCenter(mContext, "登录成功");
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

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ToastUtil.showCenter(mContext, "请授予地理位置权限");
            return;
        }
        double[] location = GpsUtils.getInstance(mContext).getLocation();

        if (isDialog) baseView.showResDialog(R.string.loading);
        model.randomCodeLogin(account, passwordAsDoubleMd5, rndCodeAsDoubleMd5, randInit, location, new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(String response) {
                baseView.dismissDialog();
                baseView.onResult(0, "", response);
            }
        });
    }
}

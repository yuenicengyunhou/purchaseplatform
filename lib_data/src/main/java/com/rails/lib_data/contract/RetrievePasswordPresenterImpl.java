package com.rails.lib_data.contract;

import android.app.Activity;
import android.text.TextUtils;

import com.rails.lib_data.R;
import com.rails.lib_data.model.LoginModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.framwork.utils.VerificationUtil;

public class RetrievePasswordPresenterImpl extends BasePresenter<RetrievePasswordContract.RetrievePasswordView> implements RetrievePasswordContract.RetrievePasswordPresenter {
    final private String TAG = RetrievePasswordPresenterImpl.class.getSimpleName();

    private LoginModel model;

    public RetrievePasswordPresenterImpl(Activity mContext, RetrievePasswordContract.RetrievePasswordView retrievePasswordView) {
        super(mContext, retrievePasswordView);
        model = new LoginModel();
    }

    @Override
    public void retrievePassword(String userName, String email, boolean isDialog) {

        if (TextUtils.isEmpty(userName)) {
            ToastUtil.showCenter(mContext, "用户名不能为空");
            return;
        }

        if (!VerificationUtil.isUserName_login(userName)) {
            ToastUtil.showCenter(mContext, "用户名格式错误");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            ToastUtil.showCenter(mContext, "邮箱不能为空");
            return;
        }

        if (!VerificationUtil.isEmail(email)) {
            ToastUtil.showCenter(mContext, "邮箱格式错误");
            return;
        }

        if (isDialog) baseView.showResDialog(R.string.loading);
        model.retrievePassword(userName, email, new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(String response) {
                // 找回密码邮件已发送到邮箱：1023725142@qq.com，请点击邮件中的更改密码链接进行更改密码操作。链接仅当天有效，请及时登录您的邮箱查看。
                baseView.onRetrieveSuccess(response.split("。")[0].split("，")[0].split("：")[1]);
                baseView.dismissDialog();
            }
        });
    }
}

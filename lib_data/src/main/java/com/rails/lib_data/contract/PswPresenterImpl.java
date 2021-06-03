package com.rails.lib_data.contract;

import android.app.Activity;
import android.text.TextUtils;

import com.rails.lib_data.R;
import com.rails.lib_data.model.PasswordModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.framwork.utils.VerificationUtil;

public class PswPresenterImpl extends BasePresenter<PswContract.PassWordView> implements PswContract.PswPresenter {


    private final PasswordModel model;

    public PswPresenterImpl(Activity mContext, PswContract.PassWordView passWordView) {
        super(mContext, passWordView);
        model = new PasswordModel();
    }

    @Override
    public void verifyToCommit(String oldPsw, String newPsw, String newPswAgain) {

        if (TextUtils.isEmpty(oldPsw)) {
            ToastUtil.showCenter(mContext, "请输入原密码");
            return;
        }
        if (TextUtils.isEmpty(newPsw)) {
            ToastUtil.showCenter(mContext, "请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(newPswAgain)) {
            ToastUtil.showCenter(mContext, "请再次输入新密码");
            return;
        }
        if (!newPsw.equals(newPswAgain)) {
            ToastUtil.showCenter(mContext, "两次输入密码不一致");
            return;
        }

        if (!VerificationUtil.isPaw(newPsw)) {
            ToastUtil.showCenter(mContext, "密码格式错误");
            return;
        }
        commitPassword(oldPsw, newPsw, newPswAgain);
    }

    //    @Override
    public void commitPassword(String oldPsw, String newPsw, String newPswAgain) {
        baseView.showResDialog(R.string.loading);
        model.updatePassword(oldPsw, newPsw, newPswAgain, new HttpRxObserver<Object>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(Object response) {
                baseView.dismissDialog();
                ToastUtil.showCenter(mContext, "操作成功");
                baseView.finishPage();
//                String msg = response.getMsg();
//                Log.e("WQ", "====" + "msg");
//                ToastUtil.showCenter();
            }
        });
    }
}

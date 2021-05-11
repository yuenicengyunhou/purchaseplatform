package com.rails.lib_data.contract;

import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonObject;
import com.rails.lib_data.R;
import com.rails.lib_data.model.LoginModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import java.util.regex.Pattern;

public class RetrievePasswordPresenterImpl extends BasePresenter<RetrievePasswordContract.RetrievePasswordView> implements RetrievePasswordContract.RetrievePasswordPresenter {
    final private String TAG = RetrievePasswordPresenterImpl.class.getSimpleName();

    private LoginModel model;

    public RetrievePasswordPresenterImpl(Activity mContext, RetrievePasswordContract.RetrievePasswordView retrievePasswordView) {
        super(mContext, retrievePasswordView);
        model = new LoginModel();
    }

    @Override
    public void retrievePassword(String userName, String email, boolean isDialog) {

        if (!matchUsername(userName)) {
            ToastUtil.showCenter(mContext, "用户名格式错误");
            return;
        }
        if (!matchEmail(email)) {
            ToastUtil.showCenter(mContext, "邮箱格式错误");
            return;
        }

        if (isDialog) baseView.showResDialog(R.string.loading);
        model.retrievePassword(userName, email, new HttpRxObserver<JsonObject>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(JsonObject response) {
                response.toString();
                Log.d(TAG, response.toString());
                baseView.onRetrieveSuccess("成功");
                baseView.dismissDialog();
            }
        });
    }

    /**
     * 验证邮箱格式
     *
     * @param email 邮箱
     * @return 不为空并且匹配格式返回true，否则返回false
     */
    private boolean matchEmail(String email) {
        // TODO: 2021/3/28 邮箱正则
        Pattern pattern = Pattern.compile("^\\d{6}$");
//        return !email.equals("") && pattern.matcher(email).matches();
        return true;
    }

    /**
     * 验证用户名格式
     *
     * @param username 用户名
     * @return 不为空并且匹配格式返回true，否则返回false
     */
    private boolean matchUsername(String username) {
        // TODO: 2021/3/28 用户名正则
        Pattern pattern = Pattern.compile("^\\d{6}$");
//        return !username.equals("") && pattern.matcher(username).matches();
        return true;
    }
}

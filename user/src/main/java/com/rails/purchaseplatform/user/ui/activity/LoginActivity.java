package com.rails.purchaseplatform.user.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.lib_data.contract.LoginContract;
import com.rails.lib_data.contract.LoginPresneterImpl;
import com.rails.lib_data.contract.UserToolContract;
import com.rails.lib_data.contract.UserToolPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.lib_data.ConShare;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.user.databinding.ActivityUserLoginBinding;

import java.lang.ref.WeakReference;

/**
 * 登录页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/27
 */
@Route(path = ConRoute.USER.LOGIN)
public class LoginActivity extends BaseErrorActivity<ActivityUserLoginBinding> implements LoginContract.LoginView
        , UserToolContract.UserToolView {

    private int COUNTING = 1;
    private int COUNT_NUM = 60;

    private Handler mHandler;

    private LoginContract.LoginPresenter presenter;
    private UserToolContract.UserToolPresenter toolPresenter;

    @Override
    protected void initialize(Bundle bundle) {
        mHandler = new TimerHandler(this);

        presenter = new LoginPresneterImpl(this, this);
        toolPresenter = new UserToolPresenterImpl(this, this);
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
    protected void onClick() {
        super.onClick();
        binding.btnLogin.setOnClickListener(v -> presenter.onLogin(
                binding.etPhoneInput.getText().toString().trim(),
                binding.etPasswordInput.getText().toString().trim(),
                binding.etVerifyNumInput.getText().toString().trim()));

        binding.tvGetVerifyNum.setOnClickListener(v ->
                presenter.getCode(binding.etPhoneInput.getText().toString().trim()));

        binding.tvForgetPassword.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(this, RetrievePasswordActivity.class);
            startActivity(intent);
        });

        binding.etPhoneInput.setOnFocusChangeListener((v, hasFocus) ->
                setInputLineBackground(hasFocus, binding.viewPhoneInputLine));

        binding.etPasswordInput.setOnFocusChangeListener((v, hasFocus) ->
                setInputLineBackground(hasFocus, binding.viewPasswordInputLine));

        binding.etVerifyNumInput.setOnFocusChangeListener((v, hasFocus) ->
                setInputLineBackground(hasFocus, binding.viewVerifyNumInputLine));
    }

    /**
     * 设置输入框下方的线颜色变化
     *
     * @param hasFocus 是否有焦点
     * @param view     需要改变颜色的View
     */
    private void setInputLineBackground(boolean hasFocus, View view) {
        Drawable drawableHasFocus = getResources().getDrawable(com.rails.purchaseplatform.common.R.drawable.bg_corner_blue_5);
        Drawable drawableLoseFocus = getResources().getDrawable(com.rails.purchaseplatform.common.R.drawable.bg_corner_gray_5);
        if (hasFocus)
            view.setBackground(drawableHasFocus);
        else
            view.setBackground(drawableLoseFocus);
    }


    @Override
    public void onResult(int type, String msg, String token) {
        ToastUtil.showCenter(this, msg);
        if (type == 1) {
            binding.tvGetVerifyNum.setVisibility(View.INVISIBLE);
            binding.tvCountDown.setVisibility(View.VISIBLE);
            Message message = new Message();
            message.what = COUNTING;
            message.obj = COUNT_NUM;
            mHandler.sendMessageDelayed(message, 300);
        }
        if (type == 0) {
            PrefrenceUtil.getInstance(this).setString(ConShare.TOKEN, token);
            presenter.getUserInfo(false, token);
        }
    }

    @Override
    public void getUserInfo(UserInfoBean bean) {
        PrefrenceUtil.getInstance(this).setBean(ConShare.USERINFO, bean);
        if (bean != null) {
            toolPresenter.queryResourceButton(bean.getId(), bean.getAccountType());
            toolPresenter.queryResource(bean.getId(), bean.getAccountType());
        }
        finish();
    }

    @Override
    public void getUserStatictics(UserStatisticsBean bean) {

    }

    @Override
    public void getUserInfoStatictics(UserStatisticsBean bean) {

    }

    @Override
    public void checkPermissions(UserStatisticsBean bean) {

    }

    private static class TimerHandler extends Handler {
        private final WeakReference<LoginActivity> mWeakReference;

        public TimerHandler(LoginActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            LoginActivity activity = mWeakReference.get();
            super.handleMessage(msg);
            if (msg.what == activity.COUNTING) {
                int count = (int) msg.obj;
                activity.binding.tvCountDown.setText(count + "s");
                if (count > 0) {
                    Message msg1 = obtainMessage();
                    msg1.what = activity.COUNTING;
                    msg1.obj = count - 1;
                    sendMessageDelayed(msg1, 1000);
                } else {
                    activity.binding.tvCountDown.setVisibility(View.GONE);
                    activity.binding.tvGetVerifyNum.setVisibility(View.VISIBLE);
                }
            }
        }
    }

}

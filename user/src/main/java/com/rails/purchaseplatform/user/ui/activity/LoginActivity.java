package com.rails.purchaseplatform.user.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.contract.LoginContract;
import com.rails.lib_data.contract.LoginPresneterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.user.databinding.ActivityUserLoginBinding;

import java.lang.ref.WeakReference;
import java.util.regex.Pattern;

/**
 * 登录页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/27
 */
@Route(path = ConRoute.USER.LOGIN)
public class LoginActivity extends BaseErrorActivity<ActivityUserLoginBinding> implements LoginContract.LoginView {
    final private String TAG = LoginActivity.class.getSimpleName();

    private int COUNTING = 1;
    private int COUNT_NUM = 60;

    private Handler mHandler;

    private LoginContract.LoginPresenter presenter;

    @Override
    protected void initialize(Bundle bundle) {
        mHandler = new TimerHandler(this);

        presenter = new LoginPresneterImpl(this, this);
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
        binding.btnLogin.setOnClickListener(v -> {
            String phone = binding.etPhoneInput.getText().toString().trim(),
                    password = binding.etPasswordInput.getText().toString().trim(),
                    verifyCode = binding.etVerifyNumInput.getText().toString().trim();

            presenter.onLogin("13311111122", "Pass!word@1234", "a8bn6t");
        });

        binding.tvGetVerifyNum.setOnClickListener(v -> {
            presenter.getCode("13311111122");

        });

        binding.tvForgetPassword.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(this, RetrievePasswordActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public void onResult(int type, String msg) {
        ToastUtil.showCenter(this, msg);
        if (type == 1) {
            binding.tvGetVerifyNum.setVisibility(View.INVISIBLE);
            binding.tvCountDown.setVisibility(View.VISIBLE);
            Message message = new Message();
            message.what = COUNTING;
            message.obj = COUNT_NUM;
            mHandler.sendMessageDelayed(message, 300);
        }
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

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
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.user.databinding.ActivityUserLoginBinding;

import java.lang.ref.WeakReference;

/**
 * 登录页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/27
 */
@Route(path = ConRoute.USER.LOGIN)
public class LoginActivity extends BaseErrorActivity<ActivityUserLoginBinding> {
    final private String TAG = LoginActivity.class.getSimpleName();

    private int COUNTING = 1;
    private int COUNT_NUM = 60;

    private Handler mHandler;

    @Override
    protected void initialize(Bundle bundle) {
        mHandler = new TimerHandler(this);
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
            Log.d(TAG, "登录按钮");
            ARouter.getInstance().build(ConRoute.RAILS.MAIN).navigation();
        });

        binding.tvGetVerifyNum.setOnClickListener(v -> {
            Log.d(TAG, "获取验证码按钮");
            binding.tvGetVerifyNum.setVisibility(View.INVISIBLE);
            binding.tvCountDown.setVisibility(View.VISIBLE);

            Message msg = new Message();
            msg.what = COUNTING;
            msg.obj = COUNT_NUM;
            mHandler.sendMessage(msg);
        });

        binding.tvForgetPassword.setOnClickListener(v -> {
            Log.d(TAG, "忘记密码按钮");
            Bundle bundle = new Bundle();
            Intent intent = new Intent(this, FindPasswordActivity.class);
            startActivity(intent);
            Toast.makeText(this, "跳转到找回密码页面", Toast.LENGTH_SHORT).show();
        });
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

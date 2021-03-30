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
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
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
            String phone = binding.etPhoneInput.getText().toString().trim(),
                    password = binding.etPasswordInput.getText().toString().trim(),
                    verifyCode = binding.etVerifyNumInput.getText().toString().trim();
            if (!matchPhone(phone)) {
                Toast.makeText(this, "手机号码格式错误", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!matchPassword(password)) {
                Toast.makeText(this, "密码格式错误", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!matchVerifyCode(verifyCode)) {
                Toast.makeText(this, "验证码格式错误", Toast.LENGTH_SHORT).show();
                return;
            }
            // TODO: 2021/3/28 发送登录请求
            Log.d(TAG, "登录成功");
//            ARouter.getInstance().build(ConRoute.RAILS.MAIN).navigation();
        });

        binding.tvGetVerifyNum.setOnClickListener(v -> {
            binding.tvGetVerifyNum.setVisibility(View.INVISIBLE);
            binding.tvCountDown.setVisibility(View.VISIBLE);

            Message msg = new Message();
            msg.what = COUNTING;
            msg.obj = COUNT_NUM;
            mHandler.sendMessageDelayed(msg, 300);
        });

        binding.tvForgetPassword.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(this, RetrievePasswordActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 验证验证码
     *
     * @param verifyCode 验证码
     * @return 验证码不为空并且匹配格式返回true，否则返回false
     */
    private boolean matchVerifyCode(String verifyCode) {
        Pattern pattern = Pattern.compile("^\\d{6}$");
        return !verifyCode.equals("") && pattern.matcher(verifyCode).matches();
    }

    /**
     * 验证密码
     *
     * @param password 密码
     * @return 密码不为空并且匹配格式返回true，否则返回false
     */
    private boolean matchPassword(String password) {
        // TODO: 2021/3/28 密码格式验证 - 正则
        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
//        return !password.equals("") && pattern.matcher(password).matches();
        return true;
    }

    /**
     * 验证手机号码
     *
     * @param phone 手机号码
     * @return 手机号码不为空并且匹配格式返回true，否则返回false
     */
    private boolean matchPhone(String phone) {
        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        return !phone.equals("") && pattern.matcher(phone).matches();
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

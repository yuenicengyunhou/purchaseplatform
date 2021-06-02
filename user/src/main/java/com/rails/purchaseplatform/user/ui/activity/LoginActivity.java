package com.rails.purchaseplatform.user.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.Logger;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.contract.LoginContract;
import com.rails.lib_data.contract.LoginPresneterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.base.BaseActManager;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.user.databinding.ActivityUserLoginBinding;

/**
 * 登录页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/27
 */
@Route(path = ConRoute.USER.LOGIN)
public class LoginActivity extends BaseErrorActivity<ActivityUserLoginBinding> implements LoginContract.LoginView {
    final private String TAG = LoginActivity.class.getSimpleName();

    private final int COUNTING = 1;
    private int COUNT_NUM = 60;
    private final long DURATION = 1000;


    private Handler mHandler2 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == COUNTING) {
                int count = (int) msg.obj;
                binding.tvCountDown.setText(count + "s");
                if (count > 0) {
                    Message msg1 = mHandler2.obtainMessage();
                    msg1.what = COUNTING;
                    msg1.obj = count - 1;
                    mHandler2.sendMessageDelayed(msg1, DURATION);
                } else {
                    binding.tvCountDown.setVisibility(View.GONE);
                    binding.tvGetVerifyNum.setVisibility(View.VISIBLE);
                }
                return true;
            }
            return false;
        }
    });

    private LoginContract.LoginPresenter presenter;

    @Override
    protected void initialize(Bundle bundle) {

        PrefrenceUtil.getInstance(this).clear();

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

        binding.rlPasswordVisible.setOnClickListener(v -> {
            boolean isChecked = binding.cbPasswordVisible.isChecked();
            binding.cbPasswordVisible.setChecked(!isChecked);
        });

        binding.cbPasswordVisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.etPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    binding.etPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        binding.etPhoneInput.setOnFocusChangeListener((v, hasFocus) ->
                setInputLineBackground(hasFocus, binding.viewPhoneInputLine));

        binding.etPasswordInput.setOnFocusChangeListener((v, hasFocus) ->
                setInputLineBackground(hasFocus, binding.viewPasswordInputLine));

        binding.etVerifyNumInput.setOnFocusChangeListener((v, hasFocus) -> {
            setInputLineBackground(hasFocus, binding.viewVerifyNumInputLine);
            // TODO: 2021/6/2 需要监视软键盘状态，软件盘未弹出时并不会滚动
            if (hasFocus) {
                binding.nsvLogin.scrollTo(0, 150);
            } else {
                binding.nsvLogin.scrollTo(0, 0);
            }
        });

        // TODO: 2021/6/2 需要监视软键盘状态，软件盘未弹出时并不会滚动
        binding.etVerifyNumInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.nsvLogin.scrollTo(0, 150);
            }
        });


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
            mHandler2.sendMessageDelayed(message, 300);
        }
        if (type == 0) {
            PrefrenceUtil.getInstance(this).setString(ConShare.TOKEN, token);
            presenter.getUserInfo(false, token);
        }

        synCookies(ConRoute.WEB_URL.MSG);
    }

    @Override
    public void getUserInfo(UserInfoBean bean) {
        PrefrenceUtil.getInstance(this).setBean(ConShare.USERINFO, bean);
        ARouter.getInstance().build(ConRoute.RAILS.MAIN).navigation();
        BaseActManager.getInstance().clear();
        finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            BaseActManager.getInstance().clear();
            ARouter.getInstance().build(ConRoute.RAILS.MAIN).navigation();
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mHandler2 != null) {
            mHandler2.removeCallbacksAndMessages(null);
            mHandler2 = null;
        }
        super.onDestroy();

    }


    /**
     * 将cookie同步到WebView
     *
     * @param url WebView要加载的url
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public void synCookies(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.acceptCookie();
        /**
         * cookies是在HttpClient中获得的cookie
         */
        String token = PrefrenceUtil.getInstance(this).getString(ConShare.TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            return;
        }
        cookieManager.removeAllCookies(null);
        cookieManager.setCookie(url, "token" + "=" + token);
        /**
         *  判断系统当前版本，同步方式不一样
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.createInstance(getApplicationContext()).sync();
        }
        String cookie = cookieManager.getCookie("cookie");
        Logger.d(cookie);

    }

}

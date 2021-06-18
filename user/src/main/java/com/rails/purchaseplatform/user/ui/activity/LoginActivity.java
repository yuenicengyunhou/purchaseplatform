package com.rails.purchaseplatform.user.ui.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.orhanobut.logger.Logger;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.contract.LoginContract;
import com.rails.lib_data.contract.LoginPresneterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.base.BaseActManager;
import com.rails.purchaseplatform.framwork.utils.MD5Util;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.user.R;
import com.rails.purchaseplatform.user.databinding.ActivityUserLoginBinding;
import com.rails.purchaseplatform.user.ui.fragment.PhoneLoginFragment;
import com.rails.purchaseplatform.user.ui.fragment.RandomCodeLoginFragment;
import com.rails.purchaseplatform.user.utils.ViewPager2Util;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

/**
 * 登录页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/27
 */
@Route(path = ConRoute.USER.LOGIN)
public class LoginActivity extends BaseErrorActivity<ActivityUserLoginBinding>
        implements
        LoginContract.LoginView,
        PhoneLoginFragment.ScrollUpListener,
        RandomCodeLoginFragment.ScrollUpListener {
    final private String TAG = LoginActivity.class.getSimpleName();

    private final int COUNTING = 1;
    private int COUNT_NUM = 60;
    private final long DURATION = 1000;
    private final int VERIFY_CODE_RECEIVE = 0;

    private final Uri uri = Uri.parse("content://sms/");
    private String mVerifyCode;
    private String mRandInit = "";

    private int mPosition = 0;
    private RandomCodeLoginFragment mRandomCodeLoginFragment = new RandomCodeLoginFragment();
    private PhoneLoginFragment mPhoneLoginFragment = new PhoneLoginFragment();

    private Handler mHandler2 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == COUNTING) {
                int count = (int) msg.obj;
                binding.tvCountDown.setText(MessageFormat.format("{0}s", count));
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
            } else if (msg.what == VERIFY_CODE_RECEIVE) {
                if (null != mVerifyCode && null != clipboardManager) {
                    ClipData clipData = ClipData.newPlainText("text", mVerifyCode);
                    clipboardManager.setPrimaryClip(clipData);
                }
            }
            return false;
        }
    });

    private LoginContract.LoginPresenter presenter;
    private ClipboardManager clipboardManager;

    private TabLayoutMediator mediator;

    private final ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            mPosition = position;
            //可以来设置选中时tab的大小
            int tabCount = binding.tabLayout.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = binding.tabLayout.getTabAt(i);
                TextView tabView = (TextView) tab.getCustomView();
                if (tab.getPosition() == position) {
                    tabView.setTextSize(18);
                    tabView.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    tabView.setTextSize(14);
                    tabView.setTypeface(Typeface.DEFAULT);
                }
            }
        }
    };

    @Override
    protected void initialize(Bundle bundle) {

        PrefrenceUtil.getInstance(this).clear();

        presenter = new LoginPresneterImpl(this, this);
        mRandomCodeLoginFragment.setPresenter(presenter);
        mRandomCodeLoginFragment.setNeedScrollUpListener(this);
        mPhoneLoginFragment.setPresenter(presenter);
        mPhoneLoginFragment.setNeedScrollUpListener(this);

        String[] tabTitles = getResources().getStringArray(R.array.tab_titles);

        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(mRandomCodeLoginFragment);
        fragmentList.add(mPhoneLoginFragment);

        binding.viewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        binding.viewPager.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {
            @NonNull
            @NotNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return tabTitles.length;
            }
        });
        binding.viewPager.registerOnPageChangeCallback(changeCallback);
        ViewPager2Util.changeToNeverMode(binding.viewPager);

        mediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {
                //这里可以自定义TabView
                TextView tabView = new TextView(LoginActivity.this);

                int[][] states = new int[2][];
                states[0] = new int[]{android.R.attr.state_selected};
                states[1] = new int[]{};

                int[] colors = new int[]{
                        getResources().getColor(R.color.font_blue),
                        getResources().getColor(R.color.font_gray)};
                ColorStateList colorStateList = new ColorStateList(states, colors);
                tabView.setGravity(Gravity.CENTER);
                tabView.setText(tabTitles[position]);
                tabView.setTextSize(14);
                tabView.setTextColor(colorStateList);

                tab.setCustomView(tabView);
            }
        });
        mediator.attach();

//        String code = UUID.randomUUID().toString() + System.currentTimeMillis();
//        Log.d(TAG, "UUID + CurrentTime == " + code);
//        presenter.randomInit(MD5Util.MD5(code), true);

        presenter.randomInit(MD5Util.MD5(UUID.randomUUID().toString() + System.currentTimeMillis()), true);
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
            if (mPosition == 0) {
                ArrayList<String> infoList = mRandomCodeLoginFragment.getLoginInfo();
                presenter.randomCodeLogin(
                        infoList.get(0),
                        infoList.get(1),
                        mRandInit,
                        infoList.get(2),
                        infoList.get(3),
                        infoList.get(4),
                        true);
            } else {
                ArrayList<String> infoList = mPhoneLoginFragment.getLoginInfo();
                presenter.onLogin(
                        infoList.get(0),
                        infoList.get(1),
                        infoList.get(2));
            }
        });

        binding.tvForgetPassword.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(this, RetrievePasswordActivity.class);
            startActivity(intent);
        });

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
        BaseActManager.getInstance().clear();
        ARouter.getInstance().build(ConRoute.RAILS.MAIN).navigation();
        finish();
    }

    @Override
    public void setVerifyCode(String verifyCode) {
        this.mVerifyCode = verifyCode;
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        RxPermissions.getInstance(this).request(Manifest.permission.READ_SMS).subscribe(aBoolean -> {
            if (aBoolean) {
                SmsObserver smsObserver = new SmsObserver(mHandler2);
                getContentResolver().registerContentObserver(uri, true, smsObserver);
            }
        });

    }

    @Override
    public void onRandomInitSuccess(String randInit) {
        mRandInit = randInit;
        mRandomCodeLoginFragment.setRandInit(randInit);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            BaseActManager.getInstance().clear();
            ARouter.getInstance().build(ConRoute.RAILS.MAIN).navigation();
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void scrollUp() {
        binding.nsvLogin.scrollTo(0, 360);
    }

    @Override
    protected void onDestroy() {
        mediator.detach();
        binding.viewPager.unregisterOnPageChangeCallback(changeCallback);
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

    /**
     * What is this?
     */
    class SmsObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public SmsObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, @Nullable Uri uri) {
            super.onChange(selfChange, uri);
            if (null == uri) return;
            if (uri.toString().equals("content://sms/raw")) {
                return;
            }
            Uri inboxUri = Uri.parse("content://sms/inbox");
            Cursor cursor = getContentResolver().query(inboxUri, null, null, null, "date desc");
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    String address = cursor.getString(cursor.getColumnIndex("address"));
                    String body = cursor.getString(cursor.getColumnIndex("body"));
                    if (address.equals("95306") && body.contains(mVerifyCode)) {
                        Message message = new Message();
                        message.what = VERIFY_CODE_RECEIVE;
                        mHandler2.sendMessage(message);
                    }
                }
                cursor.close();
            }
        }
    }

}

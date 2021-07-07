package com.rails.purchaseplatform.user.ui.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
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
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BaseActManager;
import com.rails.purchaseplatform.framwork.utils.MD5Util;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.user.R;
import com.rails.purchaseplatform.user.adapter.LoginInfoAdapter;
import com.rails.purchaseplatform.user.databinding.ActivityUserLoginBinding;
import com.rails.purchaseplatform.user.ui.fragment.PhoneLoginFragment;
import com.rails.purchaseplatform.user.ui.fragment.RandomCodeLoginFragment;
import com.rails.purchaseplatform.user.utils.ViewPager2Util;

import org.jetbrains.annotations.NotNull;

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

    final private String
            LOGIN_ACCOUNT = "LOGIN_ACCOUNT",
            LOGIN_PHONE = "LOGIN_PHONE",
            ACCOUNT = "account",
            PHONE = "phone";

    private InputMethodManager mManager;

    private PopupWindow mLoginInfoListPop;
    private View mPopView;

    private SharedPreferences mAccountSp, mPhoneSp;
    private ArrayList<String> mAccountList = new ArrayList<>(), mPhoneList = new ArrayList<>();
    private String mCurrentAccount, mCurrentPhone;

    private LoginInfoAdapter mLoginInfoAdapter;

    private final int COUNTING = 1;
    private int COUNT_NUM = 60;
    private final long DURATION = 1000;
    private final int VERIFY_CODE_RECEIVE = 0;

    private String mScrollFlag = "";
    private boolean isScrolling;

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
                if (count >= 0) {
                    mPhoneLoginFragment.setCountDown(count);
                    Message msg1 = mHandler2.obtainMessage();
                    msg1.what = COUNTING;
                    msg1.obj = count - 1;
                    mHandler2.sendMessageDelayed(msg1, DURATION);
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
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            Log.d(TAG, "====== POSITION_OFFSET == " + positionOffset);
            isScrolling = positionOffset != 0F;
        }

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
    private SmsObserver smsObserver;

    @Override
    protected void initialize(Bundle bundle) {
        requestPermission();

        mManager = ((InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE));

        PrefrenceUtil.getInstance(this).clear();

        getLoginInfoFormSp();

        initPop();

        ViewGroup parentContent = findViewById(android.R.id.content);
        parentContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                Log.d(TAG, "ViewTreeObserver.OnGlobalLayoutListener#onGlobalLayout");
                Rect r = new Rect();
                parentContent.getWindowVisibleDisplayFrame(r);

                int displayHeight = r.bottom - r.top;
                int parentHeight = parentContent.getHeight();
                int softKeyHeight = parentHeight - displayHeight;

                if (softKeyHeight > 700) {
                    switch (mScrollFlag) {
                        case "account":
//                            Log.d(TAG, "==========================!!!!");
//                            binding.nsvLogin.scrollTo(0, 0);
//                            if (!mLoginInfoListPop.isShowing() && !isScrolling) {
                                mLoginInfoAdapter.updateData(mAccountList, 0, true);
//                                mRandomCodeLoginFragment.showLoginInfoList();
//                            }
                            break;
                        case "phone":
//                            Log.d(TAG, "==========================????");
//                            binding.nsvLogin.scrollTo(0, 0);
//                            if (!mLoginInfoListPop.isShowing() && !isScrolling) {
                                mLoginInfoAdapter.updateData(mPhoneList, 1, true);
//                                mPhoneLoginFragment.showLoginInfoList();
//                            }
                            break;
                        case "password":
                            binding.nsvLogin.scrollTo(0, 360);
                            break;
                        case "randomCode":
                        case "verifyCode":
                            binding.nsvLogin.scrollTo(0, 720);
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        presenter = new LoginPresneterImpl(this, this);
        mRandomCodeLoginFragment.setPresenter(presenter);
        mRandomCodeLoginFragment.setManager(mManager);
        mRandomCodeLoginFragment.setNeedScrollUpListener(this);
        mRandomCodeLoginFragment.setLoginAccountList(mAccountList);
        mRandomCodeLoginFragment.setPop(mLoginInfoListPop);
        mPhoneLoginFragment.setPresenter(presenter);
        mPhoneLoginFragment.setManager(mManager);
        mPhoneLoginFragment.setNeedScrollUpListener(this);
        mPhoneLoginFragment.setLoginPhoneList(mPhoneList);
        mPhoneLoginFragment.setPop(mLoginInfoListPop);

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

        // 请求随机码坐标
        presenter.randomInit(MD5Util.MD5(UUID.randomUUID().toString() + System.currentTimeMillis()), true);
    }

    private void getLoginInfoFormSp() {
        // 进入登录页面 获取登录过的账号信息
        mAccountSp = this.getSharedPreferences(LOGIN_ACCOUNT, MODE_PRIVATE);
        mPhoneSp = this.getSharedPreferences(LOGIN_PHONE, MODE_PRIVATE);
        if (mAccountSp.getAll().size() != 0) {
            mAccountList.clear();
            for (int i = 0; i < mAccountSp.getAll().size(); i++) {
                mAccountList.add(mAccountSp.getString(ACCOUNT + i, ""));
            }
        }
        if (mPhoneSp.getAll().size() != 0) {
            mPhoneList.clear();
            for (int i = 0; i < mPhoneSp.getAll().size(); i++) {
                mPhoneList.add(mPhoneSp.getString(PHONE + i, ""));
            }
        }
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
                mCurrentAccount = infoList.get(0);
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
                mCurrentPhone = infoList.get(0);
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

        binding.imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActManager.getInstance().clear();
                ARouter.getInstance().build(ConRoute.RAILS.MAIN).navigation();
                finish();
            }
        });

    }


    @Override
    public void onResult(int type, String msg, String token) {
        ToastUtil.showCenter(this, msg);
        if (type == 1) {
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
        switch (mPosition) {
            case 0:
                saveLogin(mAccountList, mCurrentAccount, mAccountSp, ACCOUNT);
                break;
            case 1:
                saveLogin(mPhoneList, mCurrentPhone, mPhoneSp, PHONE);
                break;
            default:
                break;
        }

        PrefrenceUtil.getInstance(this).setBean(ConShare.USERINFO, bean);
        BaseActManager.getInstance().clear();
        ARouter.getInstance().build(ConRoute.RAILS.MAIN).navigation();
        finish();
    }

    // 保存登录过的账号、手机号
    private void saveLogin(ArrayList<String> loginList, String currentLogin, SharedPreferences sp, String tag) {
        loginList.remove(currentLogin);
        loginList.add(0, currentLogin);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        for (int i = 0; i < loginList.size(); i++) {
            editor.putString(tag + i, loginList.get(i));
        }
        editor.apply();
    }

    // 初始化Pop
    private void initPop() {
        if (mLoginInfoListPop == null) {
            mLoginInfoListPop = new PopupWindow(this);
            mPopView = LayoutInflater.from(this)
                    .inflate(R.layout.pop_login_info_list, null);
            mLoginInfoListPop.setContentView(mPopView);
            mLoginInfoListPop.setOutsideTouchable(true);
            mLoginInfoListPop.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bg_corner_gray_5));
            BaseRecyclerView recyclerView = mPopView.findViewById(R.id.recycler_info);
            mLoginInfoAdapter = new LoginInfoAdapter(this);
            mLoginInfoAdapter.setFillAccountListener(mRandomCodeLoginFragment);
            mLoginInfoAdapter.setFillPhoneListener(mPhoneLoginFragment);
            recyclerView.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
            recyclerView.setAdapter(mLoginInfoAdapter);
        }
    }

    @Override
    public void setVerifyCode(String verifyCode) {
        this.mVerifyCode = verifyCode;
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//        RxPermissions.getInstance(this).request(Manifest.permission.READ_SMS).subscribe(aBoolean -> {
//            if (aBoolean) {
//                smsObserver = new SmsObserver(mHandler2);
//                getContentResolver().registerContentObserver(uri, true, smsObserver);
//            }
//        });

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
    public void scrollUp(String flag) {
        mScrollFlag = flag;
    }

    @Override
    protected void onDestroy() {
        mediator.detach();
        binding.viewPager.unregisterOnPageChangeCallback(changeCallback);
        if (mHandler2 != null) {
            mHandler2.removeCallbacksAndMessages(null);
            mHandler2 = null;
        }
//        if (null != smsObserver) {
//            getContentResolver().unregisterContentObserver(smsObserver);
//        }
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

    private void requestPermission() {
        Log.i(TAG, "requestPermission");
        // Here, thisActivity is the current activity
        if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "checkSelfPermission");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    0);
        }
    }

}

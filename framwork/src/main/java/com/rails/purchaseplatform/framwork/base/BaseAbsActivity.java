package com.rails.purchaseplatform.framwork.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.rails.purchaseplatform.framwork.bean.BusEvent;
import com.rails.purchaseplatform.framwork.loading.LoadingDialog;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * Created by wangchao on 2016/9/22.
 * bese class 页面传值 页面跳转
 */
public abstract class BaseAbsActivity extends AppCompatActivity {

    private Intent mIntent = null;
    private LoadingDialog mLoadingDialog;


//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getExtraEvent(extras);
        }

        if (isBindEventBus()) {
            EventBus.getDefault().register(this);
        }
    }


    /**
     * 获取EventBus发送的信息
     *
     * @param baseMessageEvent
     */
    @Subscribe
    public void onMessageEvent(BusEvent baseMessageEvent) {
    }

    /**
     * 获取页面传值
     */
    protected void getExtraEvent(Bundle extras) {
    }

    /**
     * 设置顶部颜色
     *
     * @return true ?透明:黑灰
     */
    protected abstract int getColor();

    /**
     * 是否设置systembar
     *
     * @return
     */
    protected abstract boolean isSetSystemBar();


    /**
     * is bind eventBus
     *
     * @return
     */
    protected abstract boolean isBindEventBus();

    /**
     * 启动页面跳转
     *
     * @param cls
     */
    public void startIntent(Class<?> cls) {
        this.startIntent(this, cls);
    }

    /**
     * 启动页面跳转
     *
     * @param cls
     */
    public void startIntent(Class<?> cls, Bundle bundle) {
        this.startIntent(this, cls, bundle);
    }

    /**
     * 启动页面跳转
     *
     * @param context
     * @param cls
     */
    public void startIntent(Context context, Class<?> cls) {
        this.startIntent(context, cls, null);
    }

    /**
     * 启动页面跳转
     *
     * @param context
     * @param cls
     * @param bundle
     */
    public void startIntent(Context context, Class<?> cls, Bundle bundle) {
        mIntent = new Intent(context, cls);
        if (bundle != null) {
            mIntent.putExtras(bundle);
        }
        context.startActivity(mIntent);
    }


    /**
     * 启动页面跳转，有返回
     *
     * @param context
     * @param cls
     * @param bundle
     * @param code
     */
    public void startIntent(Context context, Class<?> cls, Bundle bundle, int code) {
        mIntent = new Intent(context, cls);
        if (bundle != null) {
            mIntent.putExtras(bundle);
        }
        startActivityForResult(mIntent, code);
    }


    /**
     * 启动页面跳转，有返回
     *
     * @param context
     * @param cls
     * @param code
     */
    public void startIntent(Context context, Class<?> cls, int code) {
        mIntent = new Intent(context, cls);
        startActivityForResult(mIntent, code);
    }

    /**
     * toggle show loading
     *
     * @param
     */
    protected void toggleShowDialogLoading(String msg) {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            return;
        }
        try {
            mLoadingDialog = new LoadingDialog.Builder(this).setMessage(msg).create();
            mLoadingDialog.setCancelable(true);
            mLoadingDialog.show();
        } catch (Exception e) {

        }

    }


    protected void toggleDismissDialogLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }


    /**
     * 设置状态栏背景为白底模式
     */
    protected void setLightMode() {
        StatusBarUtil.StatusBarLightMode(this);
    }


    /**
     * 设置状态栏背景色彩
     *
     * @param color
     */
    protected void setupSystemBar(int color) {
//        StatusBarUtil.setStatusBarColor(this, color);
        StatusBarUtil.StatusBarMode(this, color);
    }

    @Override
    protected void onDestroy() {

        if (isBindEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        super.onDestroy();
    }


    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }


}

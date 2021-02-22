package com.rails.purchaseplatform.framwork.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rails.purchaseplatform.framwork.bean.BusEvent;
import com.rails.purchaseplatform.framwork.loading.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

/**
 * Fragment  基类
 * Created by wangchao on 2017/5/16.
 */

public abstract class BaseAbsFragment<T extends ViewBinding> extends Fragment implements BaseView {

    private Intent mIntent = null;
    private LoadingDialog mLoadingDialog;
    public Activity mActivity;
    public T binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isBindEventBus()) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            try {
                Class<T> cls = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
                Method method = cls.getMethod("inflate", LayoutInflater.class, ViewGroup.class, Boolean.TYPE);
                binding = (T) method.invoke(null, inflater, container, false);
            } catch (Exception e) {
                e.getCause();
            }
        }
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(getActivity());
    }

    /**
     * 获取EventBus发送的信息
     *
     * @param event
     */
    @Subscribe
    public void onMessageEvent(BusEvent event) {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setClickable(true);
        initialize(savedInstanceState);
    }


    /**
     * init object
     */
    protected abstract void initialize(Bundle bundle);


    /**
     * 是否绑定EventBus
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
        this.startIntent(getActivity(), cls);
    }

    /**
     * 启动页面跳转
     *
     * @param cls
     */
    public void startIntent(Class<?> cls, Bundle bundle) {
        this.startIntent(getActivity(), cls, bundle);
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


    @Override
    public void showDialog(String msg) {
        toggleShowDialogLoading(msg);
    }

    @Override
    public void showResDialog(int res) {
        toggleShowDialogLoading(getActivity().getResources().getString(res));
    }

    @Override
    public void dismissDialog() {
        toggleDismissDialogLoading();
    }

    /**
     * toggle show loading
     *
     * @param
     */
    protected void toggleShowDialogLoading(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new LoadingDialog.Builder(getActivity()).setMessage(msg).create();
        mLoadingDialog.show();
    }


    protected void toggleDismissDialogLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionAuthor();
    }

    /**
     * 权限授权之后，操作
     */
    protected void permissionAuthor() {

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBindEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

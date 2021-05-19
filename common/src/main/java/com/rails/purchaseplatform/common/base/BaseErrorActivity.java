package com.rails.purchaseplatform.common.base;


import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.lib_data.contract.UserToolContract;
import com.rails.lib_data.contract.UserToolPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.base.BaseActivity;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import androidx.viewbinding.ViewBinding;

import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_PASTDUE;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_TIMEOUT;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_UNLOAD;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_UNLOAD_2;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.HTTP_ERROR;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.UN_KNOWN_ERROR;

/**
 * 错误异常统一处理
 * author wangchao
 * date   on 2018/2/12.
 */

public abstract class BaseErrorActivity<T extends ViewBinding> extends BaseActivity<T> {

    private UserToolContract.UserToolPresenter toolPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolPresenter = new UserToolPresenterImpl(this, new UserToolContract.UserToolView() {
            @Override
            public void getUserStatictics(UserStatisticsBean bean) {

            }

            @Override
            public void getUserInfoStatictics(UserStatisticsBean bean) {

            }

            @Override
            public void checkPermissions(UserStatisticsBean bean) {

            }

            @Override
            public void onError(ErrorBean errorBean) {

            }

            @Override
            public void showDialog(String msg) {

            }

            @Override
            public void showResDialog(int res) {

            }

            @Override
            public void dismissDialog() {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserInfoBean bean = PrefrenceUtil.getInstance(this).getBean(ConShare.USERINFO, UserInfoBean.class);
        if (bean != null) {
            toolPresenter.queryResourceButton(bean.getId(), bean.getAccountType());
            toolPresenter.queryResource(bean.getId(), bean.getAccountType());
        }
    }

    @Override
    public void onError(ErrorBean errorBean) {
        String errorCode = errorBean.getCode();
        if (TextUtils.isEmpty(errorCode)) {
            ToastUtil.showCenter(this, errorBean.getMsg());
            return;
        }
        switch (errorCode) {
            case ERROR_PASTDUE:
            case ERROR_UNLOAD:
            case HTTP_ERROR:
            case ERROR_UNLOAD_2:
            case ERROR_TIMEOUT: {
                ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
            }
            break;
            case UN_KNOWN_ERROR: {

            }
            break;
            default:
                String msg = errorBean.getMsg();
                ToastUtil.showCenter(this, msg);
                break;
        }

    }
}

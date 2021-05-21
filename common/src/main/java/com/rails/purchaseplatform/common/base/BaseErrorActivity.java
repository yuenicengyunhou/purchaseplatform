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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

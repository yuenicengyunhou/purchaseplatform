package com.rails.purchaseplatform.common.base;


import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.base.BaseActivity;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import androidx.viewbinding.ViewBinding;

import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_UNLOAD;

/**
 * 错误异常统一处理
 * author wangchao
 * date   on 2018/2/12.
 */

public abstract class BaseErrorActivity<T extends ViewBinding> extends BaseActivity<T> {

    @Override
    public void onError(ErrorBean errorBean) {
        String errorCode = errorBean.getCode();
        switch (errorCode) {
            case ERROR_UNLOAD: {
                ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
            }
            break;

            default:
                String msg = errorBean.getMsg();
                ToastUtil.show(this, msg);
                break;
        }

    }
}

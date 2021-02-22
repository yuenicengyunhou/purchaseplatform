package com.rails.purchaseplatform.framwork.base;


import com.rails.purchaseplatform.framwork.bean.ErrorBean;

import androidx.viewbinding.ViewBinding;

/**
 * 错误异常统一处理
 * author wangchao
 * date   on 2018/2/12.
 */

public abstract class BaseErrorActivity<T extends ViewBinding> extends BaseActivity<T> {

    @Override
    public void onError(ErrorBean errorBean) {
        int errorCode = errorBean.getCode();
        String msg = errorBean.getMsg();
    }
}

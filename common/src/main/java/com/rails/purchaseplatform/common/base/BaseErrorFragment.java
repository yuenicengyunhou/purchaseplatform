package com.rails.purchaseplatform.common.base;

import com.rails.purchaseplatform.framwork.base.BaseAbsFragment;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;

import androidx.viewbinding.ViewBinding;


/**
 * fragment 错误页面
 * author:wangchao
 * date:2019/6/10
 */
public abstract class BaseErrorFragment<T extends ViewBinding> extends BaseAbsFragment<T> {


    @Override
    public void onError(ErrorBean errorBean) {

    }
}

package com.rails.purchaseplatform.common.base;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.base.BaseAbsFragment;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import androidx.viewbinding.ViewBinding;

import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.HTTP_ERROR;


/**
 * fragment 错误页面
 * author:wangchao
 * date:2019/6/10
 */
public abstract class BaseErrorFragment<T extends ViewBinding> extends BaseAbsFragment<T> {


    @Override
    public void onError(ErrorBean errorBean) {
        String errorCode = errorBean.getCode();
        switch (errorCode) {
            case HTTP_ERROR: {
                ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
            }
            break;
            default:
                String msg = errorBean.getMsg();
                ToastUtil.show(getActivity(), msg);
                break;
        }

    }
}

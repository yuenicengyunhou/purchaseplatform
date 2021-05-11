package com.rails.purchaseplatform.common.base;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.base.BaseAbsFragment;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import androidx.viewbinding.ViewBinding;

import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_PASTDUE;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_TIMEOUT;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_UNLOAD;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_UNLOAD_2;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.UN_KNOWN_ERROR;


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
            case ERROR_PASTDUE:
            case ERROR_UNLOAD:
            case ERROR_UNLOAD_2:
            case ERROR_TIMEOUT: {
                ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation();
            }
            break;
            case UN_KNOWN_ERROR:{

            }
            break;
            default:
                String msg = errorBean.getMsg();
                ToastUtil.showCenter(getActivity(), msg);
                break;
        }

    }
}

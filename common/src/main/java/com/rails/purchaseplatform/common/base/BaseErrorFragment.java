package com.rails.purchaseplatform.common.base;

import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.base.BaseAbsFragment;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import static android.app.Activity.RESULT_OK;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_PASTDUE;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_TIMEOUT;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_UNLOAD;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_UNLOAD_2;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.HTTP_ERROR;
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
        if (null != errorCode) {
            switch (errorCode) {
                case ERROR_PASTDUE:
                case ERROR_UNLOAD:
                case ERROR_UNLOAD_2:
                case ERROR_TIMEOUT: {
                    ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation(getActivity(), ConRoute.CODE.LOGIN_CODE);
                }
                break;
                case UN_KNOWN_ERROR: {

                }
                break;
                default:
                    String msg = errorBean.getMsg();
                    ToastUtil.showCenter(getActivity(), msg);
                    break;
            }
        } else {
            String msg = errorBean.getMsg();
            if (null==msg) return;
            ToastUtil.showCenter(getActivity(), msg);
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ConRoute.CODE.LOGIN_CODE) {
            reLoadData();
        }
    }


    /**
     * 重新加载数据
     */
    public void reLoadData() {

    }
}

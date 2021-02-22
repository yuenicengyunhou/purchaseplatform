package com.rails.purchaseplatform.framwork.base;

import android.app.Activity;
import android.os.Build;

/**
 * @param <T>
 */
public abstract class BasePresenter<T extends BaseView> {

    protected Activity mContext;
    protected T baseView;

    public BasePresenter(Activity mContext, T t) {
        this.mContext = mContext;
        this.baseView = t;
    }

    /**
     * @return
     */
    public boolean isCallBack() {
        if (mContext == null || mContext.isFinishing())
            return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (mContext.isDestroyed()) {
                return false;
            }
        }
        return true;
    }


}

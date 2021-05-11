package com.rails.lib_data.contract;

import com.rails.purchaseplatform.framwork.base.BaseView;

/**
 * 忘记密码/找回密码
 */
public interface RetrievePasswordContract {

    /**
     * View回调
     */
    interface RetrievePasswordView extends BaseView {
        void onRetrieveSuccess(String message);
    }

    /**
     * Presenter
     */
    interface RetrievePasswordPresenter {
        void retrievePassword(String userName, String email, boolean isDialog);
    }
}

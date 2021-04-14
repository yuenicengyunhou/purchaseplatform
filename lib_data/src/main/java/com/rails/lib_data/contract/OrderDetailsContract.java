package com.rails.lib_data.contract;

import com.rails.purchaseplatform.framwork.base.BaseView;

public interface OrderDetailsContract {

    interface DetailView extends BaseView {

        /**
         * 展示与隐藏 回到顶部按钮
         */
        void showOrHideBackButton(boolean show);
    }

    interface DetailPresenter {

    }
}

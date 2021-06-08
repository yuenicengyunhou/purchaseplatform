package com.rails.lib_data.contract;

import com.rails.purchaseplatform.framwork.base.BaseView;

public interface ProductDetailsContract2 {

    interface ProductDetailsView2 extends BaseView {
        void onProductInfoLoadCompleted();
    }

    interface ProductDetailsPresenter2 {
        void getProductDetailsStep1(String platformId,
                                    String itemId,
                                    String addressType,
                                    boolean isDialog);
    }
}

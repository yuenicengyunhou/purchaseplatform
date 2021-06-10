package com.rails.lib_data.contract;

import com.rails.lib_data.bean.forAppShow.ProductDetailsPageBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

public interface ProductDetailsContract2 {

    interface ProductDetailsView2 extends BaseView {
        /**
         * 全部请求完成时调用
         */
        void onProductInfoLoadCompleted(ProductDetailsPageBean finalProductBean);

        /**
         * 查询不到skuId时调用
         */
        void onHaveNoSkuId();
    }

    interface ProductDetailsPresenter2 {
        void getAllProductInfo(String platformId,
                               String itemId,
                               String addressType,
                               boolean isDialog);
    }
}

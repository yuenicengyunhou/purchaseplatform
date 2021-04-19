package com.rails.lib_data.contract;

import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.ProductDetailsBean;
import com.rails.purchaseplatform.framwork.base.BaseView;


/**
 * 商品详情
 */
public interface ProductDetailsContract {

    /**
     * 商品详情 View回调接口
     */
    interface ProductDetailsView extends BaseView {
        /**
         * 商品详情 请求成功后的回调方法
         *
         * @param success
         */
        void onGetProductDetailsSuccess(boolean success);
    }


    /**
     * 商品详情的 presenter接口
     */
    interface ProductDetailsPresenter {
        /**
         * 获取商品详情
         *
         * @param isDialog
         */
        void getProductDetails(long platformId, long itemId, long companyId, boolean isDialog);
    }
}

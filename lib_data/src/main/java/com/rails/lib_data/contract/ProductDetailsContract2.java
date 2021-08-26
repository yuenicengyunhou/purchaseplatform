package com.rails.lib_data.contract;

import com.rails.lib_data.bean.forAppShow.ProductDetailsPageBean;
import com.rails.lib_data.bean.forAppShow.ProductDetailsPopBean;
import com.rails.purchaseplatform.framwork.base.BaseView;


public interface ProductDetailsContract2 {


    interface ProductDetailsView2 extends BaseView {

        /**
         * 全部请求完成时调用
         *
         * @param finalProductBean
         */
        void onProductInfoLoadCompleted(ProductDetailsPageBean finalProductBean);

        /**
         * 切换sku请求数据成功时调用
         *
         * @param popBean
         */
        void onPopViewClick(ProductDetailsPopBean popBean);

        /**
         * 查询不到skuId时调用
         */
        void onHaveNoSkuId();
    }


    interface ProductDetailsPresenter2 {

        /**
         * 获取商品信息
         *
         * @param materialType
         * @param platformId
         * @param itemId
         * @param skuId
         * @param addressType
         * @param isDialog
         */
        void getAllProductInfo(String platformId,
                               String itemId,
                               String skuId,
                               String addressType,
                               boolean isDialog);

        /**
         * 获取弹窗内切换sku时需要更新的数据
         *
         * @param isDialog
         */
        void getProductSkuInfo(String platformId,
                               String cid,
                               String skuId,
                               String shopId,
                               String provinceId,
                               String cityId,
                               String countryId,
                               String address,
                               String skuNum,
                               boolean isDialog);
    }
}

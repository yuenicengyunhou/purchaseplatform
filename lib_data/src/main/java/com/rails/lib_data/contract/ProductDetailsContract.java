package com.rails.lib_data.contract;

import com.rails.lib_data.bean.forAppShow.RecommendItemsBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductPriceBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;


/**
 * 商品详情
 */
public interface ProductDetailsContract {

    /**
     * 商品详情 View回调接口
     */
    interface ProductDetailsView extends BaseView {

        /**
         * 商品详情
         */
        void onGetProductDetailsSuccess(ProductDetailsBean bean);

        /**
         * 商品评分
         */
        // TODO: 2021/4/22 参数
        void onGetProductPriceSuccess(ProductPriceBean bean);

        /**
         * 店铺推荐（热销商品）
         */
        void onGetHotSaleSuccess(ArrayList<RecommendItemsBean> beans);

        /**
         * 获取收藏状态
         */
        void onGetUserCollectSuccess(boolean isCollect);

        /**
         * 获取购物车内商品数量
         */
        void onGetCartCountSuccess(String count);
    }


    /**
     * 商品详情的 presenter接口
     */
    interface ProductDetailsPresenter {

        /**
         * 获取商品详情
         *
         * @param platformId
         * @param itemId
         * @param companyId
         * @param isDialog
         */
        void getProductDetails(long platformId, long itemId, long companyId, boolean isDialog);

        /**
         * 获取商品价格、评分
         *
         * @param platformId
         * @param skuId
         * @param isDialog
         */
        void getProductPrice(long platformId, int skuId, boolean isDialog);

        /**
         * 获取店铺推荐商品（热销商品）
         *
         * @param platformId
         * @param keyword
         * @param cid
         * @param pageNum
         * @param isDialog
         */
        void getHotSale(long platformId, String keyword, int cid, int pageNum, boolean isDialog);

        /**
         * 获取商品收藏状态
         *
         * @param skuId
         * @param isDialog
         */
        void getUserCollect(int skuId, boolean isDialog);

        /**
         * 获取购物车内商品数量
         *
         * @param platformId
         * @param organizeId
         * @param accountId
         * @param isDialog
         */
        void getCartCount(long platformId, String organizeId, String accountId, boolean isDialog);
    }
}

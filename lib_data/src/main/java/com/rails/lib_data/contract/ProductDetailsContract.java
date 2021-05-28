package com.rails.lib_data.contract;

import com.rails.lib_data.bean.DeliveryBean;
import com.rails.lib_data.bean.ProductServiceBean;
import com.rails.lib_data.bean.SkuStockBean;
import com.rails.lib_data.bean.forAppShow.ProductDetailsPackingBean;
import com.rails.lib_data.bean.forAppShow.RecommendItemsBean;
import com.rails.lib_data.bean.forAppShow.SpecificationPopBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemPicture;
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
        /**
         * 商品详情
         *
         * @param bean         商品详情信息
         * @param serviceBeans 售后服务bean
         * @param recCompanys  推荐企业列表
         */
        void onGetProductDetailsSuccess(ProductDetailsBean bean, ArrayList<ProductServiceBean> serviceBeans, ArrayList<ProductServiceBean> recCompanys, ArrayList<SpecificationPopBean> specificationPopBeanList);

        /**
         * 商品评分/价格/包装清单
         */
        // TODO: 2021/4/22 参数
        void onGetProductPriceSuccess(ProductPriceBean bean, ArrayList<ItemPicture> pics, ArrayList<ProductDetailsPackingBean> billBeans);

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


        /**
         * 获取邮费
         *
         * @param deliveryBean
         */
        void getDelivery(DeliveryBean deliveryBean);


        /**
         * 获取库存
         *
         * @param bean
         */
        void getSkuSaleStocks(SkuStockBean bean);
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
        void getProductDetails(String platformId, String itemId, String companyId, boolean isDialog);

        /**
         * 获取商品价格、评分
         *
         * @param platformId
         * @param skuId
         * @param isDialog
         */
        void getProductPrice(String platformId, String skuId, boolean isDialog);

        /**
         * 获取店铺推荐商品（热销商品）
         *
         * @param platformId
         * @param keyword
         * @param cid
         * @param pageNum
         * @param isDialog
         */
        void getHotSale(String platformId, String keyword, String cid, int pageNum, boolean isDialog);

        /**
         * 获取商品收藏状态
         *
         * @param skuId
         * @param isDialog
         */
        void getUserCollect(String skuId, boolean isDialog);

        /**
         * 获取购物车内商品数量
         *
         * @param platformId
         * @param organizeId
         * @param accountId
         * @param isDialog
         */
        void getCartCount(String platformId, String organizeId, String accountId, boolean isDialog);


        /**
         * 获取邮费
         *
         * @param shopId
         */
        void getProductDelivery(String shopId);


        /**
         * 浏览记录
         *
         * @param categoryId
         * @param skuId
         */
        void addSkuVisitTrack(String categoryId, String skuId, boolean isDialog);


        /**
         * 请求sku库存
         *
         * @param supplierId
         * @param provinceId
         * @param cityId
         * @param countryId
         * @param address
         * @param skuNum
         * @param skuId
         * @param isDialog
         */
        void querySkuSaleStocks(String supplierId, String provinceId, String cityId, String countryId,
                                String address, String skuNum, String skuId, boolean isDialog);
    }
}

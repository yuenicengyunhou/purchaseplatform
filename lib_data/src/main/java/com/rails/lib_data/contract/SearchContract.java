package com.rails.lib_data.contract;

import com.rails.lib_data.bean.forAppShow.ItemAttribute;
import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.forAppShow.ShopAttribute;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

/**
 * 搜索结果
 */
public interface SearchContract {


    interface SearchItemView extends BaseView {
        /**
         * 获取商品列表成功 回调
         *
         * @param searchResultBeans
         * @param hasMore
         * @param isClear
         */
        void onQueryItemListByKeywordSuccess(ArrayList<ItemAttribute> searchResultBeans, ArrayList<SearchFilterBean> filterResults, boolean hasMore, boolean isClear);

        /**
         * 搜索skuId成功 回调
         * 搜索skuId
         *
         * @param itemId
         */
        void onQueryItemListByKeywordSuccess2(String itemId, String skuId);

        /**
         * 获取商品列表成功 使用cid
         *
         * @param results
         * @param hasMore
         * @param isClear
         */
        void onQueryItemListByCidSuccess(ArrayList<ItemAttribute> results, ArrayList<SearchFilterBean> filterResults, boolean hasMore, boolean isClear);
    }


    interface SearchItemPresenter {

        @Deprecated
        void getItemListWithKeywordOnly(String orderColumn, String orderType, String keyword, int pageNum, boolean isDialog);

        @Deprecated
        void getItemListWithCid(String orderColumn, String orderType, String cid, int pageNum, boolean isDialog);


        /**
         * 使用cid搜索
         *
         * @param keyword
         * @param cid
         * @param orderColumn
         * @param orderType
         * @param brands
         * @param brandsString
         * @param categoryAttrValueIds
         * @param expandAttrValueIds
         * @param minPrice
         * @param maxPrice
         * @param pageNum
         * @param pageSize
         * @param isDialog
         */
        void queryItemListByCid(String materialType, String keyword, String cid,
                                String orderColumn, String orderType,
                                String brands, String brandsString,
                                String categoryAttrValueIds, String expandAttrValueIds,
                                String minPrice, String maxPrice,
                                int pageNum, int pageSize, boolean isDialog);


        /**
         * 使用keyword搜索
         *
         * @param materialType         0-普通物资，1-专用物资。
         * @param keyword
         * @param orderColumn
         * @param orderType
         * @param brands
         * @param brandsString
         * @param categoryAttrValueIds
         * @param expandAttrValueIds
         * @param minPrice
         * @param maxPrice
         * @param pageNum
         * @param pageSize
         * @param isDialog
         */
        void queryItemListByKeyword(String materialType, String keyword,
                                    String orderColumn, String orderType,
                                    String brands, String brandsString,
                                    String categoryAttrValueIds, String expandAttrValueIds,
                                    String minPrice, String maxPrice,
                                    int pageNum, int pageSize, boolean isDialog);

        /**
         * 商品搜索记录搜索关键字接口
         * <p>
         * 调用：
         * <p>
         * -1- 点击搜索按钮且搜索关键字不为空
         * <p>
         * -2- 点击搜索记录条目
         * <p>
         * -3- 点击热门搜索条目
         * <p>
         * -notice- 在搜索结果页面进行的各种排序搜索不做记录
         *
         * @param type
         * @param keyword
         */
        void searchRecord(String type, String keyword);
    }


    interface SearchShopView extends BaseView {
        /**
         * 获取商品列表成功 回调
         *
         * @param bean
         * @param hasMore
         * @param isClear
         */
        void getShopListWithKeywordOnly(ArrayList<ShopAttribute> bean, boolean hasMore, boolean isClear);
    }


    interface SearchShopPresenter {
        /**
         * 店铺列表
         *
         * @param keyword
         * @param pageNum
         * @param pageSize
         */
        void getShopListWithKeywordOnly(String materialType, String isBought, int pageNum, int pageSize, String keyword,
                                        String orderColumn, String orderType,
                                        String shopType, String saleArea, boolean isDialog);

    }
}

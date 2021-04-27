package com.rails.lib_data.contract;

import com.rails.lib_data.bean.forAppShow.ItemAttribute;
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
        void getItemListWithKeywordOnly(ArrayList<ItemAttribute> searchResultBeans, boolean hasMore, boolean isClear);

        /**
         * 获取商品列表成功 使用cid
         *
         * @param results
         * @param hasMore
         * @param isClear
         */
        void getItemListWithCid(ArrayList<ItemAttribute> results, boolean hasMore, boolean isClear);
    }


    interface SearchItemPresenter {
        /**
         * 商品列表 从搜索栏跳转 使用keyword
         *
         * @param orderColumn
         * @param orderType
         * @param keyword
         * @param pageNum
         * @param isDialog
         */
        void getItemListWithKeywordOnly(String orderColumn, String orderType, String keyword, int pageNum, boolean isDialog);

        /**
         * 商品列表 从首页金刚区跳转 使用cid搜索
         *
         * @param cid
         * @param pageNum
         * @param isDialog
         */
        void getItemListWithCid(String orderColumn, String orderType, String cid, int pageNum, boolean isDialog);
    }


    interface SearchShopView extends BaseView {
        /**
         * 获取商品列表成功 回调
         *
         * @param bean
         * @param hasMore
         * @param isClear
         */
        // TODO: 2021/04/19 修改参数
        void getShopListWithKeywordOnly(ArrayList<ShopAttribute> bean, boolean hasMore, boolean isClear);
    }


    interface SearchShopPresenter {
        /**
         * 店铺列表
         *
         * @param platformId
         * @param accountId
         * @param keyword
         * @param isBuy
         * @param pageNum
         * @param pageSize
         */
        void getShopListWithKeywordOnly(long platformId, long accountId, String keyword, boolean isBuy, int pageNum, int pageSize, boolean isDialog);

    }
}

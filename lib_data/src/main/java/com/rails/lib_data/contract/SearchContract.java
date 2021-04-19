package com.rails.lib_data.contract;

import com.rails.lib_data.bean.showOnApp.BaseItemAttribute;
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
        void getItemListWithKeywordOnly(ArrayList<BaseItemAttribute> searchResultBeans, boolean hasMore, boolean isClear);
    }


    interface SearchItemPresenter {
        /**
         * 商品列表
         *
         * @param isDialog
         * @param platformId
         * @param keyWord
         */
        void getItemListWithKeywordOnly(boolean isDialog, int page, long platformId, String keyWord);
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
        void getShopListWithKeywordOnly(ArrayList<Object> bean, boolean hasMore, boolean isClear);
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

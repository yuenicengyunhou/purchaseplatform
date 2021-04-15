package com.rails.lib_data.contract;

import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.SearchResultBean;
import com.rails.lib_data.bean.SearchResultByShopBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

/**
 * 搜索结果
 */
public interface SearchResultContract {

    interface SearchResultView extends BaseView {
        void getSearchResult(ArrayList<SearchResultBean> searchResultBeans, boolean hasMore, boolean isClear);


        /**
         * 获取商品列表
         *
         * @param productBeans
         * @param hasMore
         * @param isClear
         */
        void getProducts(ArrayList<ProductBean> productBeans, boolean hasMore, boolean isClear);

    }

    interface SearchResultPresenter {
        void getSearchResult(boolean isDialog, int page);


        /**
         * 商品列表
         *
         * @param isDialog
         * @param platformId
         * @param keyWord
         */
        void getSearchResultWithKeywordOnly(boolean isDialog, int page, long platformId, String keyWord);
    }


    /**
     * 获取商店搜索结果 View接口
     */
    interface SearchResultByShopView extends BaseView {
        void getSearchResultByShop(ArrayList<SearchResultByShopBean> searchResultByShopBeans, boolean hasMore, boolean isClear);
    }

    /**
     * 获取商店搜索结果 Presenter接口
     */
    interface SearchResultByShopPresenter {
        void getSearchResultByShop(boolean isDialog, int page);
    }
}

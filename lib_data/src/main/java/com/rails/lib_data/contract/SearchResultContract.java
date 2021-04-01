package com.rails.lib_data.contract;

import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.SearchResultBean;
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
        void getProducts(boolean isDialog,int page, String platformId, String keyWord);
    }
}

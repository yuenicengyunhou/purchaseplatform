package com.rails.lib_data.contract;

import com.rails.lib_data.bean.SearchResultBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

/**
 * 搜索结果
 */
public interface SearchResultContract {

    interface SearchResultView extends BaseView {
        void getSearchResult(ArrayList<SearchResultBean> searchResultBeans, boolean hasMore, boolean isClear);
    }

    interface SearchResultPresenter {
        void getSearchResult(boolean isDialog, int page);
    }
}

package com.rails.lib_data.contract;

import com.rails.lib_data.bean.HotSearchBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

/**
 * 热门搜索
 */
public interface HotSearchContract {

    /**
     * 热门搜索 View回调接口
     */
    interface HotSearchView extends BaseView {
        /**
         * 热门搜索 回调方法
         *
         * @param hotSearchBeans
         * @param hasMore
         * @param isClear
         */
        void getHotSearch(ArrayList<HotSearchBean> hotSearchBeans, boolean hasMore, boolean isClear);
    }


    /**
     * 热门搜索 presenter接口
     */
    interface HotSearchPresenter {
        /**
         * 热门搜索 获取热门搜索
         *
         * @param isDialog
         * @param page
         */
        void getHotSearch(boolean isDialog, int page);
    }
}

package com.rails.lib_data.contract;

import com.rails.lib_data.bean.RecommendItemsBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

public interface RecommendItemsContract {

    interface RecommendItemsView extends BaseView {
        void getRecommendItems(ArrayList<RecommendItemsBean> recommendItemsBeans, boolean hasMore, boolean isClear);
    }

    interface RecommendItemsPresenter {
        void getRecommendItems(boolean isDialog, int page);
    }
}

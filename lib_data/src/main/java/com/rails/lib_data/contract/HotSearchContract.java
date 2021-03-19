package com.rails.lib_data.contract;

import com.rails.lib_data.bean.HotSearchBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

public interface HotSearchContract {

    interface HotSearchView extends BaseView {
        void getHotSearch(ArrayList<HotSearchBean> hotSearchBeans, boolean hasMore, boolean isClear);
    }

    interface HotSearchPresenter {
        void getHotSearch(boolean isDialog, int page);
    }
}

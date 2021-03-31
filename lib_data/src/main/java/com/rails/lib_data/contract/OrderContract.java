package com.rails.lib_data.contract;

import com.rails.lib_data.bean.OrderBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

public interface OrderContract {
    interface OrderView extends BaseView {
        void getOrder(ArrayList<OrderBean> orderBeans, boolean hasMore, boolean isClear);
    }

    interface OrderPresenter {
        void getOrder(boolean isDialog, int page);
    }
}

package com.rails.lib_data.contract;

import com.rails.lib_data.bean.OrderInfoBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

public interface OrderContract {
    interface OrderView extends BaseView {
        void getOrder(ArrayList<OrderInfoBean> orderBeans, boolean hasMore, boolean isClear);
    }

    interface OrderPresenter {
        void getOrder(boolean isDialog, int page,int queryType,String squence,String content);
    }
}

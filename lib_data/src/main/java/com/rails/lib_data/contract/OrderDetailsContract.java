package com.rails.lib_data.contract;

import com.rails.lib_data.bean.OrderAuditBean;
import com.rails.lib_data.bean.OrderCreatedBean;
import com.rails.lib_data.bean.OrderDetailsBean;
import com.rails.lib_data.bean.OrderProcessBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;
import java.util.List;

public interface OrderDetailsContract {

    interface DetailView extends BaseView {

        /**
         * 展示与隐藏 回到顶部按钮
         */
        void showOrHideBackButton(boolean show);

        /**
         * 加载采购单信息
         */

        void loadOrderDetailsInfo(OrderDetailsBean bean);


        /**
         * 加载审核信息
         */
        void loadOrderAuditInfo(ArrayList<OrderAuditBean> list);

        void loadProcessInfo(ArrayList<OrderProcessBean> list);


        void loadCreatedInfo(ArrayList<OrderCreatedBean> list);

        void loadAuditInfo(ArrayList<OrderAuditBean> list);
    }

    interface DetailPresenter {

        /**
         * 获取采购单信息
         */
        void getOrderDetailsInfo();


        /**
         * 获取审核信息
         */
        void getAuditInfo();



    }
}

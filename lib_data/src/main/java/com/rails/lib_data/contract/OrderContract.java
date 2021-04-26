package com.rails.lib_data.contract;

import com.rails.lib_data.bean.BuyerBean;
import com.rails.lib_data.bean.OrderInfoBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

public interface OrderContract {
    interface OrderView extends BaseView {
        void getOrder(ArrayList<OrderInfoBean> orderBeans, boolean hasMore, boolean isClear);


        /**
         * 加载采购人用户列表/供应商名称列表
         */
        void loadConditionNameList(ArrayList<BuyerBean> list);


//        /**
//         * 加载供应商名称列表
//         */
//        void loadSupplierNameList(ArrayList<String> list);
    }

    interface OrderPresenter {
        void getOrder(boolean isDialog, int page,int queryType,String squence,String content);

        /**
         * 采购人 用户名列表
         */
        void getBuyerNameList(String nameLike, String findType);

        /**
         * 供应商名称列表
         */
        void getSupplierNameList(String supplierName);
    }
}

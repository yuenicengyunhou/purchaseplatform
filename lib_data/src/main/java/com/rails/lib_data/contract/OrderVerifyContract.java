package com.rails.lib_data.contract;

import com.rails.lib_data.bean.OrderPurchaseBean;
import com.rails.lib_data.bean.OrderVerifyBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

/**
 * 确认单约束类
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/28
 */
public interface OrderVerifyContract {

    interface OrderVerifyView extends BaseView {

        /**
         * 获取确认单
         *
         * @param bean
         */
        void getVerifyOrder(OrderVerifyBean bean);

        /**
         * 获取token
         *
         * @param token
         */
        void getOrderToken(String token);


        /**
         * 获取采购商列表
         *
         * @param purchaseBeans
         */
        void getPurchases(ArrayList<OrderPurchaseBean> purchaseBeans);


        /**
         * 提交返回结构
         *
         * @param msg
         */
        void getResult(String msg, String data);
    }


    interface OrderVerifyPresenter {


        /**
         * 获取确认单列表
         *
         * @param addressId 收货地址Id
         */
        void getVerifyOrder(String addressId);


        /**
         * 获取采购商列表
         */
        void getPurchases();


        /**
         * 获取提交采购单token
         */
        void getOrderToken();


        /**
         * 提交采购单
         *
         * @param token
         * @param json
         */
        void commitOrder(String token, String json);

    }
}

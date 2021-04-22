package com.rails.lib_data.contract;

import com.rails.lib_data.bean.OrderVerifyBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

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
    }


    interface OrderVerifyPresenter {


        /**
         * 获取确认单列表
         *
         * @param addressId 收货地址Id
         */
        void getVerifyOrder(String addressId);

    }
}

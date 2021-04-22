package com.rails.lib_data.contract;

import com.rails.lib_data.bean.AddressBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

/**
 * 多处地址使用contract
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/21
 */
public interface AddressToolContract {

    interface AddressToolView extends BaseView {


        /**
         * 获取专属地址列表
         *
         * @param addressBeans
         */
        void getAddress(ArrayList<AddressBean> addressBeans);


        /**
         * 获取默认地址
         *
         * @param bean
         */
        void getDefAddress(AddressBean bean);

    }


    interface AddressToolPresenter {


        /**
         * 获取专属地址列表
         *
         * @param paltId
         * @param addressType 1：收货地址  2：收发票地址
         */
        void getAddress(String paltId, String addressType);


        /**
         * 获取专属地址列表
         *
         * @param paltId
         * @param addressType 1：收货地址  2：收发票地址
         */
        void getDefAddress(String paltId, String addressType);

    }

}

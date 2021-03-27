package com.rails.lib_data.contract;

import com.rails.lib_data.bean.AddressBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

/**
 * 地址管理contract
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/26
 */
public interface AddressContract {


    interface AddressView extends BaseView {

        /**
         * 获取增删改查的变更信息
         *
         * @param type
         * @param msg
         */
        void getResult(int type, int position, String msg);


        /**
         * 获取地址列表
         *
         * @param addressBeans
         */
        void getAddresses(ArrayList<AddressBean> addressBeans);

    }


    interface AddressPresenter {


        /**
         * 获取地址列表
         *
         * @param isDialog 是否显示dialog
         */
        void getAddresses(Boolean isDialog);


        /**
         * 设置为默认地址
         *
         * @param id
         */
        void setDefAddress(String id, int position);


        /**
         * 删除地址
         *
         * @param id
         */
        void delAddress(String id, int position);


        /**
         * 新增地址
         *
         * @param men     联系人
         * @param phone   联系人电话
         * @param area    地区
         * @param address 联系地址
         * @param isDef   是否是默认地址
         */
        void addAddress(String men, String phone, String area, String address, boolean isDef);
    }
}

package com.rails.lib_data.contract;

import com.rails.lib_data.bean.address.AddressArea;
import com.rails.lib_data.bean.AddressBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

/**
 * 地址管理contract
 * <p>
 * author： sk_comic@163.com
 * date: 2021/3/26
 */
public interface AddressContract {


    interface AddressView extends BaseView {

        /**
         * 获取增删改查的变更信息
         * <p>
         * param type
         * param msg
         */
        void getResult(int type, int position, String msg);


        /**
         * 根据id 获取地址详情，显示地址详情
         */
        void loadAddressInfo(AddressBean addressInfo);


        /**
         * 获取地址列表
         * <p>
         * param addressBeans
         */
        void getAddresses(ArrayList<AddressBean> addressBeans, boolean isLastPage, int totalCount);


        /**
         * 获取地区信息
         */
        void getArea(ArrayList<AddressArea> list);


    }


    interface AddressPresenter {


        /**
         * 获取地址列表
         * <p>
         * param isDialog 是否显示dialog    type-搜索类型 收货人  手机号码 详细地址
         */
        void getAddresses(Boolean isDialog, int page,String type,String params);


        /**
         * 根据addressId获取地址详情
         */
        void getAddressInfo(String addressId);


//        /**
//         * 设置为默认地址
//         *
//         * param id
//         */
//        void setDefAddress(long id, int position,boolean isReceiveDef,boolean isInvoiceDef);


        /**
         * 删除地址
         * <p>
         * param id
         */
        void delAddress(String buyerAddressId, int position);


        /**
         * 新增地址
         * <p>
         * param men     联系人
         * param phone   联系人电话
         * param area    地区
         * param address 联系地址
         * param isDef   是否是默认地址
         */
        void addAddress(String men, String phone, String area, String address, boolean isDef, int isReceiAddress, int isInvoiceAddress, String addressId, String provinceCode, String cityCode, String countryCode);

//
//        /**
//         * 获取省市区
//         * 获取一级 parentCode: 0
//         * 获取二级 使用一级code
//         */
//        void getArea( String parentCode);


    }
}

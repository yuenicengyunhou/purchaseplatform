package com.rails.lib_data.contract;

import com.rails.lib_data.bean.BuyerBean;
import com.rails.lib_data.bean.OrderFilterBean;
import com.rails.lib_data.bean.OrderInfoBean;
import com.rails.lib_data.bean.orderdetails.DeliveredFile;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

public interface OrderContract {
    interface OrderView extends BaseView {

        /**
         * 加载采购单列表
         */
        void getOrder(ArrayList<OrderInfoBean> orderBeans,boolean firstPage,int totalCount);


        /**
         * 加载采购人用户列表/供应商名称列表
         */
        void loadConditionNameList(ArrayList<BuyerBean> list);


        /**
         * 加载妥投文件列表
         * param list
         */
        void loadDeliveredFileList(ArrayList<DeliveredFile> list);


//        /**
//         * 加载供应商名称列表
//         */
//        void loadSupplierNameList(ArrayList<String> list);
    }

    interface OrderPresenter {
        void getOrder(boolean isDialog, int page, int queryType, String squence, String content, OrderFilterBean filterBean);

        /**
         * 采购人 用户名列表
         */
        void getBuyerNameList(String nameLike, String findType);

        /**
         * 供应商名称列表
         */
        void getSupplierNameList(String supplierName);


        /**
         * 获取商品名称列表
         */
        void getSkuNameList(String skuName);

        /**
         * 获取品牌名称
         */
        void getBrandList(String brand);

        /**
         * 妥投测试
         */
        void getDelivered(String orderNo);
    }
}

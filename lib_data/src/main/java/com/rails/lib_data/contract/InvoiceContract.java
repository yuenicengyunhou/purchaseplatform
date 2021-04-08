package com.rails.lib_data.contract;

import com.rails.lib_data.bean.InvoiceContentBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

/**
 * 发票contract
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/8
 */
public interface InvoiceContract {

    interface InvoiceView extends BaseView {

        /**
         * 获取发票内容
         *
         * @param types
         * @param contents
         */
        void getInvoiceContents(ArrayList<InvoiceContentBean> types, ArrayList<InvoiceContentBean> contents);

    }


    interface InvoicePresenter {

        /**
         * 获取发票基本配置信息
         */
        void getInvoiceContents();


        /**
         * 获取发票抬头列表
         */
        void getInvoiceTitles();


        void onInvoice();
    }
}

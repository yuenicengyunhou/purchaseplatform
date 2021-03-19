package com.rails.lib_data.contract;

import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

/**
 * 购物车页面相关请求 contract
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/12
 */
public interface CartContract {

    interface CartView extends BaseView {

        /**
         * 获取购物车列表
         *
         * @param cartBean
         */
        void getCartInfo(CartBean cartBean);


    }


    interface CartPresenter {

        /**
         * @param isDialog 是否显示loading窗口
         *                 获取购物车列表
         */
        void getCarts(boolean isDialog);

    }
}

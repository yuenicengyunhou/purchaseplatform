package com.rails.lib_data.contract;

import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.ProductBean;
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
         * @param cartBeans
         */
        void getCarts(ArrayList<CartBean> cartBeans);


        /**
         * 获取推荐商品列表
         *
         * @param productBeans
         * @param hasMore      是否有更多
         * @param isClear      是否清除
         */
        void getRecProjects(ArrayList<ProductBean> productBeans, boolean hasMore, boolean isClear);
    }


    interface CartPresenter {

        /**
         * @param isDialog 是否显示loading窗口
         *                 获取购物车列表
         */
        void getCarts(boolean isDialog);


        /**
         * 获取推荐商品列表
         */
        /**
         * @param isDialog 是否显示loading窗口
         * @param page
         */
        void getRecProducts(boolean isDialog, int page);
    }
}

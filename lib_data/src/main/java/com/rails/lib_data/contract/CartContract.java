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


        /**
         * 获取产品当前数量
         *
         * @param num
         */
        void getProjectNumber(int num);


        /**
         * 删除，收藏操作结果
         *
         * @param type
         * @param msg
         */
        void getResult(int type, String msg);


    }

    interface DetailsCartView extends BaseView {

        /**
         * 商品详情页 添加商品到购物车
         */
        void addCartSuccess();
    }


    interface CartPresenter {

        /**
         * @param isDialog 是否显示loading窗口
         *                 获取购物车列表
         */
        void getCarts(boolean isDialog);


        /**
         * 商品添加
         *
         * @param num 传递的数量
         */
        void addProduct(int num);


        /**
         * 商品减少
         *
         * @param num
         */
        void reduceProduct(int num);

        /**
         * 编辑框输入的数量
         *
         * @param num
         */
        void editProduct(int num);


        /**
         * 删除商品
         *
         * @param id
         */
        void delProduct(String id);

        /**
         * 收藏商品
         *
         * @param id
         */
        void collectProduct(String id);

    }


    /**
     * 购物车相关
     */
    interface CartPresenter2 {
        /**
         * 在商品详情页添加商品到购物车
         */
        void addCart(long platformId, long organizeId, long accountId,
                     int accountType, String skuSaleNumJson, boolean isDialog);
    }
}

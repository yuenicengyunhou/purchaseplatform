package com.rails.lib_data.contract;

import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.CartShopProductBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;
import java.util.HashMap;

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
        void getProjectNumber(long num);


        /**
         * 提交认证
         *
         * @param type
         * @param msg
         */
        void getResult(int type, String msg);


        /**
         * 删除，收藏操作结果
         *
         * @param type
         * @param position
         * @param msg
         */
        void getResult(int type, int position, String msg);


        /**
         * 获取选中结果
         *
         * @param type 0：商品  1：店铺  2：全选
         */
        void getSelStatus(int type, Boolean isSel);


        /**
         * 获取库存不足列表
         */
        void getLimits(ArrayList<String> list, String msg);


    }

    interface DetailsCartView extends BaseView {

        /**
         * 商品详情页 添加商品到购物车
         */
        void addCartSuccess(boolean isComplete);


        /**
         * 收藏/取消收藏
         *
         * @param position  列表中的位置
         * @param isCollect
         */
        void onCollect(boolean isCollect, int position);
    }


    interface CartPresenter {

        /**
         * @param isDialog 是否显示loading窗口
         *                 获取购物车列表
         */
        void getCarts(boolean isDialog, String addressId);


        /**
         * 商品添加
         *
         * @param num 传递的数量
         */
        void addProduct(CartShopProductBean bean, long num);


        /**
         * 商品减少
         *
         * @param num
         */
        void reduceProduct(CartShopProductBean bean, long num);

        /**
         * 编辑框输入的数量
         *
         * @param num
         */
        void editProduct(CartShopProductBean bean, long num);


        /**
         * 删除商品
         *
         * @param map {"202003030109":["15963"]}
         */
        void delProduct(HashMap<String, ArrayList<String>> map, int position);

        /**
         * 收藏商品
         *
         * @param id
         */
        void collectProduct(String id, int position);


        /**
         * 单选按钮操作
         *
         * @param bean
         */
        void modifySel(CartShopProductBean bean);


        /**
         * 店铺选中
         *
         * @param shopId 店铺ID
         * @param skuIds 商品id  逗号间隔
         * @param isSel  选中状态
         */
        void modifyShopSel(String shopId, String skuIds, boolean isSel);


        /**
         * 全选操作
         *
         * @param isSel 是否选中
         */
        void modifySelAll(boolean isSel);


        /**
         * 确认审核单
         */
        void verifyCart(String addressId);

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


        /**
         * 加入收藏/取消收藏
         *
         * @param skuIds
         * @param collectionSource
         * @param isCollect
         */
        void onCollect(String skuIds, String collectionSource, boolean isCollect, int position);
    }
}

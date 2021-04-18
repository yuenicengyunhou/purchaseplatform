package com.rails.lib_data.model;

import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.CartService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;

/**
 * 购物车相关接口
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/15
 */
public class CartModel {


    /**
     * 获取购物车列表
     *
     * @param httpRxObserver
     */
    public void getCarts(HttpRxObserver httpRxObserver) {

//        https://shop.rails.cn/proxy/order/mall/cart/queryCart?v=0.7794941713598147&addressId=1001550

        HashMap<String, Object> params = new HashMap<>();
        params.put("platformId", "20");
//        params.put("addressId", "1001550");
        params.put("organizeId", "13");

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(CartService.class).getCarts(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 编辑商品数量
     *
     * @param num            商品数量
     * @param httpRxObserver
     */
    public void modifyProduct(int num, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("platformId", "20");

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(CartService.class).getCarts(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 加入购物车
     *
     * @param platformId
     * @param organizeId
     * @param accountId
     * @param accountType
     * @param skuSaleNumJson
     * @param httpRxObserver
     */
    public void addCart(
            long platformId, long organizeId, long accountId,
            int accountType, String skuSaleNumJson, HttpRxObserver httpRxObserver) {

        HashMap<String, Object> params = new HashMap<>();
//        params.put("platformId", platformId);
//        params.put("organizeId", organizeId);
//        params.put("accountId", accountId);
//        params.put("accountType", accountType);
        params.put("skuSaleNumJson", skuSaleNumJson);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(CartService.class)
                .addCart(params))
                .subscribe(httpRxObserver);

    }
}

package com.rails.lib_data.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.CartService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;
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
    public void getCarts(String addressId, HttpRxObserver httpRxObserver) {


        HashMap<String, Object> params = new HashMap<>();
        params.put("addressId", TextUtils.isEmpty(addressId)?"-1":addressId);
//        params.put("organizeId", organizeId);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(CartService.class).getCarts(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 删除购物车
     *
     * @param map
     * @param httpRxObserver
     */
    public void delCart(HashMap<String, ArrayList<String>> map, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        String json = JSONObject.toJSON(map).toString();
        params.put("shopSkuMapJson", json);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(CartService.class).delCart(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 编辑商品数量
     *
     * @param shopId
     * @param userId
     * @param skuId
     * @param organizeId
     * @param num            商品数量
     * @param httpRxObserver
     */
    public void modifyProduct(String shopId, String userId, String skuId, String organizeId, long num, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("platformId", "20");
        params.put("shopId", shopId);
        params.put("organizeId", organizeId);
        params.put("accountId", userId);
        params.put("skuId", skuId);
        params.put("skuNum", num);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(CartService.class).modifyProductNum(params))
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


    /**
     * 获取购物车总数量
     *
     * @return
     */

    public void getCartNumber(HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
//        params.put("platformId", platformId);
//        params.put("organizeId", organizeId);
//        params.put("accountId", accountId);
//        params.put("accountType", accountType);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(CartService.class)
                .getCartNumber(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 更改单个商品选中状态
     *
     * @return
     */
    public void modifySelect(String shopId, String skuIds, Boolean isSel, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("shopId", shopId);
        params.put("skuIds", skuIds);
        params.put("selected", isSel);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(CartService.class)
                .modifySelect(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 更改全部商品选中状态
     *
     * @return
     */
    public void modifyAllSelect(Boolean isSel, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("selected", isSel);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(CartService.class)
                .modifyAllSelect(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 校对购物车选中商品
     *
     * @return
     */
    public void verifyCart(String addressId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("addressId", addressId);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(CartService.class)
                .verifyCart(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 加入收藏/取消收藏
     *
     * @param skuIds collectionSource"  加入收藏需要的参数,value = "收藏来源，10：列表页，20：商详页，30：购物车
     */
    public void onCollect(String skuIds, String collectionSource, boolean isCollect, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("skuIds", skuIds);
        if (!isCollect) {
            params.put("collectionSource", collectionSource);
            HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                    .create(CartService.class)
                    .onCollect(params))
                    .subscribe(httpRxObserver);
        } else {
            HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                    .create(CartService.class)
                    .cancelCollect(params))
                    .subscribe(httpRxObserver);
        }

    }


}

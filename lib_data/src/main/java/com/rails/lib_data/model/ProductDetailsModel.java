package com.rails.lib_data.model;


import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.ProductService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;

/**
 * 请求商品详情
 */
public class ProductDetailsModel {

    /**
     * 请求商品详情
     *
     * @param platformId
     * @param itemId
     * @param companyId
     * @param httpRxObserver
     */

    public void getProductDetails(long platformId, long itemId, long companyId, HttpRxObserver httpRxObserver) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("platformId", platformId);
        params.put("itemId", itemId);
//        params.put("companyId", companyId);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .getProductDetails(params))
                .subscribe(httpRxObserver);
    }

    /**
     * 请求商品价格
     *
     * @param platformId
     * @param skuId
     * @param httpRxObserver
     */
    public void getProductPrice(long platformId, int skuId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("platformId", platformId);
        params.put("skuIds", skuId);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .getProductPrice(params))
                .subscribe(httpRxObserver);
    }

    /**
     * 请求店铺推荐（热销商品）
     *
     * @param platformId
     * @param keyword
     * @param cid
     * @param pageNum
     * @param httpRxObserver
     */
    public void getHotSale(long platformId, String keyword, int cid, int pageNum, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        // TODO: 2021/4/22 传入数据
        params.put("platformId", platformId);
//        params.put("keyword", "11");
        params.put("businessType", 1);
        params.put("pageNum", pageNum);
        params.put("cid", cid); // 1001047
//        params.put("shopId", 202003030108L); //202003030108L

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .getHotSale(params))
                .subscribe(httpRxObserver);
    }
}

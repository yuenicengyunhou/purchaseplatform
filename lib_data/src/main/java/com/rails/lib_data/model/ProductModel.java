package com.rails.lib_data.model;

import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.ProductService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;

/**
 * 商城--产品相关
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/8
 */
public class ProductModel {


    /**
     * 获取首页推荐产品列表
     *
     * @param httpRxObserver
     */
    public void getRecProducts(HttpRxObserver httpRxObserver) {

        HashMap<String, String> params = new HashMap<>();
        params.put("platformId", "20");
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class, 1).getRecProducts(params))
                .subscribe(httpRxObserver);

    }


    public void getHotProducts(HttpRxObserver httpRxObserver) {
        HashMap<String, String> params = new HashMap<>();
        params.put("platformId", "20");
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class, 1).getRecProducts(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 请求商品列表
     *
     * @param pageNum
     * @param platformId
     * @param keyword
     * @param httpRxObserver
     */
    public void getSearchResultWithKeywordOnly(int pageNum, long platformId, String keyword, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("platformId", platformId);
        params.put("pageNum", pageNum);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .getSearchResultWithKeywordOnly(params))
                .subscribe(httpRxObserver);
    }
}

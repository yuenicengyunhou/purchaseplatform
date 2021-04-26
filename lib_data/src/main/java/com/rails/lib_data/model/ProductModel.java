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


    /**
     * 获取热销产品
     *
     * @param httpRxObserver
     */
    public void getHotProducts(HttpRxObserver httpRxObserver) {
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class).getHotProducts())
                .subscribe(httpRxObserver);
    }

}

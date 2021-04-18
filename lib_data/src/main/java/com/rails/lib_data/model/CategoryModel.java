package com.rails.lib_data.model;

import android.util.SparseArray;

import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.CategoryService;
import com.rails.lib_data.service.NoticeService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;

/**
 * 商城分类model
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/3
 */
public class CategoryModel {

    public void getCategorys(HttpRxObserver httpRxObserver) {
        HashMap<String, String> params = new HashMap();
        params.put("platformId", "20");
        params.put("businessType", "1");

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(CategoryService.class).getCategorys(params))
                .subscribe(httpRxObserver);

    }
}

package com.rails.lib_data.service;

import com.rails.lib_data.bean.CategoryRootBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 商城分类service
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/3
 */
public interface CategoryService {


    /**
     * frontcategory/getAllCategoryList?platformId=20&businessType=1
     * 获取商城分类
     *
     * @return
     */
    @GET("item/mall/frontcategory/getAllCategoryList")
    Observable<HttpResult<ArrayList<CategoryRootBean>>> getCategorys(@QueryMap HashMap<String, String> params);

}

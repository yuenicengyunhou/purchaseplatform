package com.rails.lib_data.service;

import com.alibaba.fastjson.JSONObject;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 商场--- 产品网络接口
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/8
 */
public interface ProductService {


    /**
     * 获取商城首页推荐商品列表
     *
     * @return
     */
    @GET("platform/platform/floor/queryFloorSettingList")
    Observable<HttpResult<ArrayList<ProductRecBean>>> getRecProducts(@QueryMap HashMap<String, String> params);

}

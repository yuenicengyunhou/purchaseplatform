package com.rails.lib_data.service;

import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.ListBeen;
import com.rails.lib_data.bean.NavigationBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 商城服务首页
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public interface MarketIndexService {

    /**
     * 获取商城首页推荐商品列表
     *
     * @return
     */
    @GET("platform/platform/floor/queryFloorSettingList")
    Observable<HttpResult<ArrayList<ProductRecBean>>> getRecProducts(@QueryMap HashMap<String, String> params);


    /**
     * 获取商城banner列表
     *
     * @param params
     * @return
     */
    @GET("platform/platform/bannerPicture/queryBannerPictureSettingList")
    Observable<HttpResult<ArrayList<BannerBean>>> getBanners(@QueryMap HashMap<String, String> params);


    /**
     * 品牌
     *
     * @param params
     * @return
     */
    @GET("platform/platform/brandWall/queryBrandWallSettingList")
    Observable<HttpResult<ArrayList<BrandBean>>> getRecBrands(@QueryMap HashMap<String, String> params);


    /**
     * 获取品牌列表
     * @return
     */
    @GET("ds/itembrandranking/receive/queryItemBrandRanking")
    Observable<HttpResult<ListBeen<BrandBean>>> getBrands(@QueryMap HashMap<String, Object> params);


    /**
     * 获取导航
     *
     * @param params
     * @return
     */
    @GET("platform/platform/navigationBar/queryNavigationBarSettingList")
    Observable<HttpResult<ArrayList<NavigationBean>>> getNavigations(@QueryMap HashMap<String, String> params);
}

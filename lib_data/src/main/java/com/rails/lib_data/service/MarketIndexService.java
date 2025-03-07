package com.rails.lib_data.service;

import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.ListBeen;
import com.rails.lib_data.bean.NavigationBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    @Deprecated
    @GET("platform/platform/brandWall/queryBrandWallSettingList")
    Observable<HttpResult<ArrayList<BrandBean>>> getRecBrands(@QueryMap HashMap<String, String> params);

    /**
     * 获取导航
     *
     * @param params
     * @return
     */
    @GET("platform/platform/navigationBar/queryNavigationBarSettingList")
    Observable<HttpResult<ArrayList<NavigationBean>>> getNavigations(@QueryMap HashMap<String, String> params);

    /*下面是排行榜的接口*/

    /**
     * 获取商城首页推荐商品列表
     *
     * @return
     */
//    @GET("firstCategoryRank/floorList")
    @GET("firstCategoryRank/floorListByCache")
    Observable<HttpResult<ArrayList<ProductRecBean>>> getRecProducts();


    /**
     * 获取楼层单个品类排行列表
     *
     * @param params
     * @return
     */
//    @GET("firstCategoryRank/goodsRank")
    @GET("firstCategoryRank/goodsRankByCache")
    Observable<HttpResult<ListBeen<ProductBean>>> getFloorProducts(@QueryMap HashMap<String, Object> params);

    /**
     * 获取热门商品
     *
     * @param params
     * @return
     */
//    @GET("itemproductdispose/query/queryItemProductDispose")
    @GET("itemproductdispose/query/queryItemProductDispose1")
    Observable<HttpResult<ListBeen<ProductBean>>> getHotProducts(@QueryMap HashMap<String, Object> params);


    /**
     * 获取品牌列表
     *
     * @return
     */
    @GET("itembrandranking/receive/queryItemBrandRanking")
    Observable<HttpResult<ListBeen<BrandBean>>> getBrands(@QueryMap HashMap<String, Object> params);


}

package com.rails.lib_data.model;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.SearchService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;

/**
 * 搜素页 - 获取热门商品
 * 搜索结果页 - 获取商品搜索结果（keyword、价格销量排序）
 * 搜索结果页 - 获取店铺搜索结果（keyword、销量排序）
 */
public class SearchModel {


    /**
     * 已弃用
     * 请求商品列表
     *
     * @param orderColumn
     * @param orderType
     * @param keyword
     * @param pageNum
     * @param httpRxObserver
     */
    @Deprecated
    public void getItemListWithKeywordOnly(
            String orderColumn, String orderType, String keyword, int pageNum, HttpRxObserver httpRxObserver) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("pageNum", pageNum);
        if (orderColumn != null) params.put("orderColumn", orderColumn);
        if (orderType != null) params.put("orderType", orderType);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(SearchService.class)
                .getItemListWithKeywordOnly(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 获取店铺列表
     *
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @param orderColumn
     * @param orderType
     * @param httpRxObserver
     */
    public void getShopListWithKeywordOnly(String isBought, int pageNum, int pageSize, String keyword, String orderColumn, String orderType, String shopType, String saleArea, HttpRxObserver httpRxObserver) {
        boolean isBuy = false;
        if (isBought != null && isBought.equals("true")) isBuy = true;

        HashMap<String, Object> params = new HashMap<>();
        params.put("isBuy", isBuy);
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        if (!TextUtils.isEmpty(keyword)) params.put("keyword", keyword);
        if (orderColumn != null) params.put("orderColumn", orderColumn);
        if (orderType != null) params.put("orderType", orderType);
        if (!TextUtils.isEmpty(shopType)) params.put("shopType", shopType);
        if (!TextUtils.isEmpty(saleArea)) params.put("saleArea", saleArea);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(SearchService.class)
                .getShopListWithKeywordOnly(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 已弃用
     * 请求商品列表 cid
     *
     * @param orderColumn
     * @param orderType
     * @param cid
     * @param pageNum
     * @param httpRxObserver
     */
    @Deprecated
    public void getItemListWithCid(String orderColumn, String orderType, String cid, int pageNum, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("businessType", 1);
        params.put("cid", cid); // TODO 使用 cid = 1000207 调试筛选条件
        params.put("pageNum", pageNum);
        if (orderColumn != null) params.put("orderColumn", orderColumn);
        if (orderType != null) params.put("orderType", orderType);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(SearchService.class)
                .getItemListWithCid(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 请求商品搜索结果 仅使用keyword
     *
     * @param keyword
     * @param orderColumn
     * @param orderType
     * @param brands
     * @param brandsString
     * @param categoryAttrValueIds
     * @param expandAttrValueIds
     * @param minPrice
     * @param maxPrice
     * @param pageNum
     * @param pageSize
     * @param httpRxObserver
     */
    public void queryItemListByKeyword(String keyword,
                                       String orderColumn, String orderType,
                                       String brands, String brandsString,
                                       String categoryAttrValueIds, String expandAttrValueIds,
                                       String minPrice, String maxPrice,
                                       int pageNum, int pageSize, HttpRxObserver<JsonObject> httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("businessType", 1);
        if (keyword != null && !keyword.equals(""))
            params.put("keyword", keyword);
        if (brands != null && !brands.equals(""))
            params.put("brands", brands);
        if (brandsString != null && !brandsString.equals(""))
            params.put("brandsString", brandsString);
        if (orderColumn != null && !orderColumn.equals(""))
            params.put("orderColumn", orderColumn);
        if (orderType != null && !orderType.equals(""))
            params.put("orderType", orderType);
        if (categoryAttrValueIds != null && !categoryAttrValueIds.equals(""))
            params.put("categoryAttrValueIds", categoryAttrValueIds);
        if (expandAttrValueIds != null && !expandAttrValueIds.equals(""))
            params.put("expandAttrValueIds", expandAttrValueIds);
        if (minPrice != null && !minPrice.equals(""))
            params.put("minPrice", minPrice);
        if (maxPrice != null && !maxPrice.equals(""))
            params.put("maxPrice", maxPrice);
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(SearchService.class)
                .getItemListWithKeywordOnly(params))
                .subscribe(httpRxObserver);

    }


    /**
     * 搜索商品列表 使用Cid 或cid|keyword同时使用
     *
     * @param keyword
     * @param cid
     * @param orderColumn
     * @param orderType
     * @param brands
     * @param brandsString
     * @param categoryAttrValueIds
     * @param expandAttrValueIds
     * @param minPrice
     * @param maxPrice
     * @param pageNum
     * @param pageSize
     * @param httpRxObserver
     */
    public void queryItemListByCid(String keyword, String cid,
                                   String orderColumn, String orderType,
                                   String brands, String brandsString,
                                   String categoryAttrValueIds, String expandAttrValueIds,
                                   String minPrice, String maxPrice,
                                   int pageNum, int pageSize, HttpRxObserver httpRxObserver) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("businessType", 1);
        if (keyword != null && !keyword.equals(""))
            params.put("keyword", keyword);
        if (cid != null && !cid.equals(""))
            params.put("cid", cid);
        if (brands != null && !brands.equals(""))
            params.put("brands", brands);
        if (brandsString != null && !brandsString.equals(""))
            params.put("brandsString", brandsString);
        if (orderColumn != null && !orderColumn.equals(""))
            params.put("orderColumn", orderColumn);
        if (orderType != null && !orderType.equals(""))
            params.put("orderType", orderType);
        if (categoryAttrValueIds != null && !categoryAttrValueIds.equals(""))
            params.put("categoryAttrValueIds", categoryAttrValueIds);
        if (expandAttrValueIds != null && !expandAttrValueIds.equals(""))
            params.put("expandAttrValueIds", expandAttrValueIds);
        if (minPrice != null && !minPrice.equals(""))
            params.put("minPrice", minPrice);
        if (maxPrice != null && !maxPrice.equals(""))
            params.put("maxPrice", maxPrice);
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(SearchService.class)
                .getItemListWithCid(params))
                .subscribe(httpRxObserver);

    }


    /**
     * 商品搜索记录搜索关键字
     *
     * @param type
     * @param keyword
     * @param httpRxObserver
     */
    public void searchRecord(String type, String keyword, HttpRxObserver<String> httpRxObserver) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("sourceType", type);
        params.put("keywords", keyword);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(SearchService.class)
                .searchRecord(params))
                .subscribe(httpRxObserver);
    }

}

package com.rails.lib_data.model;

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
     * 请求商品列表
     *
     * @param pageNum
     * @param platformId
     * @param keyword
     * @param httpRxObserver
     */
    public void getItemListWithKeywordOnly(
            int pageNum, long platformId,
            String keyword, HttpRxObserver httpRxObserver) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("platformId", platformId);
        params.put("pageNum", pageNum);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(SearchService.class)
                .getItemListWithKeywordOnly(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 获取店铺列表
     *
     * @param pageNum
     * @param platformId
     * @param keyword
     * @param httpRxObserver
     */
    public void getShopListWithKeywordOnly(
            long platformId, long accountId, String keyword,
            boolean isBuy, int pageNum, int pageSize, HttpRxObserver httpRxObserver) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("platformId", 20L);
        params.put("accountId", 1);
        params.put("keyword", keyword);
        params.put("isBuy", false);
        params.put("pageNum", 1);
        params.put("pageSize", 30);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(SearchService.class)
                .getShopListWithKeywordOnly(params))
                .subscribe(httpRxObserver);
    }

    public void getItemListWithCid(String cid, int pageNum, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("cid", cid);
        params.put("pageNum", pageNum);
        params.put("businessType", 1);
        params.put("platformId", 20L);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(SearchService.class)
                .getItemListWithCid(params))
                .subscribe(httpRxObserver);
    }

}

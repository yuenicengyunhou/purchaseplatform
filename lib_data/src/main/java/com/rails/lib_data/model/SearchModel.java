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
     * @param orderColumn
     * @param orderType
     * @param keyword
     * @param pageNum
     * @param httpRxObserver
     */
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
    public void getShopListWithKeywordOnly(int pageNum, int pageSize, String keyword, String orderColumn, String orderType, HttpRxObserver httpRxObserver) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        params.put("keyword", keyword);
        if (orderColumn != null) params.put("orderColumn", orderColumn);
        if (orderType != null) params.put("orderType", orderType);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(SearchService.class)
                .getShopListWithKeywordOnly(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 请求商品列表 cid
     *
     * @param orderColumn
     * @param orderType
     * @param cid
     * @param pageNum
     * @param httpRxObserver
     */
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

}

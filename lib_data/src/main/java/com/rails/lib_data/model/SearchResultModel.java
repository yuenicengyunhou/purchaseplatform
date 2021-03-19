package com.rails.lib_data.model;

import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.SearchResultService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;

public class SearchResultModel {

    public void getSearchResult(HttpRxObserver httpRxObserver) {
        HashMap<String, String> params = new HashMap<>();
        params.put("platformId", "20");
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(SearchResultService.class, 1).getSearchResult(params))
                .subscribe(httpRxObserver);
    }
}

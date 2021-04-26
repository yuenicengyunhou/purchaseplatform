package com.rails.lib_data.bean.forNetRequest.searchResult.byShop;

import java.util.List;
import java.util.Map;

public class ShopResultBean {
    private List<ShopBean> resultList;
    private int pageSize;
    private int page;
    private int count;
    private Map customAggMap;
    private Map aggResultMap;

    public List<ShopBean> getResultList() {
        return resultList;
    }

    public void setResultList(List<ShopBean> resultList) {
        this.resultList = resultList;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Map getCustomAggMap() {
        return customAggMap;
    }

    public void setCustomAggMap(Map customAggMap) {
        this.customAggMap = customAggMap;
    }

    public Map getAggResultMap() {
        return aggResultMap;
    }

    public void setAggResultMap(Map aggResultMap) {
        this.aggResultMap = aggResultMap;
    }
}

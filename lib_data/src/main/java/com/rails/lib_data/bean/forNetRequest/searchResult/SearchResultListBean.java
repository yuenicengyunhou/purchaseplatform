package com.rails.lib_data.bean.forNetRequest.searchResult;

import java.util.List;

public class SearchResultListBean {
    private List<SearchItemBean> resultList;
    private int pageSize;
    private int page;
    private int count;
    private Object customAggMap;
    private Object aggResultMap;

    public List<SearchItemBean> getResultList() {
        return resultList;
    }

    public void setResultList(List<SearchItemBean> resultList) {
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

    public Object getCustomAggMap() {
        return customAggMap;
    }

    public void setCustomAggMap(Object customAggMap) {
        this.customAggMap = customAggMap;
    }

    public Object getAggResultMap() {
        return aggResultMap;
    }

    public void setAggResultMap(Object aggResultMap) {
        this.aggResultMap = aggResultMap;
    }
}

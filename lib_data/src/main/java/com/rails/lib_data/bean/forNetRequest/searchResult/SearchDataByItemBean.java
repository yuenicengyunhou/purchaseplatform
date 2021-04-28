package com.rails.lib_data.bean.forNetRequest.searchResult;

import java.util.List;

public class SearchDataByItemBean {
    private List<String> brands;
    private List selectedCAttr;
    private List selectedEAttr;
    private List<CategoryAttr> categoryAttrs;
    private SearchResultListBean itemList;
    private String totalCount;
    private List<ExpandAttr> expandAttrs;
    private List<CidBean> allCids;

    public List<String> getBrands() {
        return brands;
    }

    public void setBrands(List<String> brands) {
        this.brands = brands;
    }

    public List getSelectedCAttr() {
        return selectedCAttr;
    }

    public void setSelectedCAttr(List selectedCAttr) {
        this.selectedCAttr = selectedCAttr;
    }

    public List getSelectedEAttr() {
        return selectedEAttr;
    }

    public void setSelectedEAttr(List selectedEAttr) {
        this.selectedEAttr = selectedEAttr;
    }

    public List<CategoryAttr> getCategoryAttrs() {
        return categoryAttrs;
    }

    public void setCategoryAttrs(List<CategoryAttr> categoryAttrs) {
        this.categoryAttrs = categoryAttrs;
    }

    public SearchResultListBean getItemList() {
        return itemList;
    }

    public void setItemList(SearchResultListBean itemList) {
        this.itemList = itemList;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<ExpandAttr> getExpandAttrs() {
        return expandAttrs;
    }

    public void setExpandAttrs(List<ExpandAttr> expandAttrs) {
        this.expandAttrs = expandAttrs;
    }

    public List<CidBean> getAllCids() {
        return allCids;
    }

    public void setAllCids(List<CidBean> allCids) {
        this.allCids = allCids;
    }
}

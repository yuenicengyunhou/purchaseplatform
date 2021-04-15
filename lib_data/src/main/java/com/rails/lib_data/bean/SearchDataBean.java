package com.rails.lib_data.bean;

import java.util.List;

public class SearchDataBean {
    private Object[] brands;
    private Object[] selectedCAttr;
    private Object[] selectedEAttr;
    private Object[] categoryAttrs;
    private SearchResultListBean itemList;
    private String totalCount;
    private Object[] expandAttrs;
    private List<CidBean> allCids;

    public Object[] getBrands() {
        return brands;
    }

    public void setBrands(Object[] brands) {
        this.brands = brands;
    }

    public Object[] getSelectedCAttr() {
        return selectedCAttr;
    }

    public void setSelectedCAttr(Object[] selectedCAttr) {
        this.selectedCAttr = selectedCAttr;
    }

    public Object[] getSelectedEAttr() {
        return selectedEAttr;
    }

    public void setSelectedEAttr(Object[] selectedEAttr) {
        this.selectedEAttr = selectedEAttr;
    }

    public Object[] getCategoryAttrs() {
        return categoryAttrs;
    }

    public void setCategoryAttrs(Object[] categoryAttrs) {
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

    public Object[] getExpandAttrs() {
        return expandAttrs;
    }

    public void setExpandAttrs(Object[] expandAttrs) {
        this.expandAttrs = expandAttrs;
    }

    public List<CidBean> getAllCids() {
        return allCids;
    }

    public void setAllCids(List<CidBean> allCids) {
        this.allCids = allCids;
    }
}

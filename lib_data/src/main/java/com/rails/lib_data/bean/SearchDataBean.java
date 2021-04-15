package com.rails.lib_data.bean;

import java.util.List;

public class SearchDataBean {
    private List brands;
    private List selectedCAttr;
    private List selectedEAttr;
    private List categoryAttrs;
    private SearchResultListBean itemList;
    private String totalCount;
    private List expandAttrs;
    private List<CidBean> allCids;

    public List getBrands() {
        return brands;
    }

    public void setBrands(List brands) {
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

    public List getCategoryAttrs() {
        return categoryAttrs;
    }

    public void setCategoryAttrs(List categoryAttrs) {
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

    public List getExpandAttrs() {
        return expandAttrs;
    }

    public void setExpandAttrs(List expandAttrs) {
        this.expandAttrs = expandAttrs;
    }

    public List<CidBean> getAllCids() {
        return allCids;
    }

    public void setAllCids(List<CidBean> allCids) {
        this.allCids = allCids;
    }
}

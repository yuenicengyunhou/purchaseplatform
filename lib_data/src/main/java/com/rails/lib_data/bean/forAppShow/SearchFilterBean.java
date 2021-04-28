package com.rails.lib_data.bean.forAppShow;

import java.util.ArrayList;

public class SearchFilterBean {
    private String filterName;
    private String filterId;
    private ArrayList<SearchFilterValue> filterValues;


    // =================================================


    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public ArrayList<SearchFilterValue> getFilterValues() {
        return filterValues;
    }

    public void setFilterValues(ArrayList<SearchFilterValue> filterValues) {
        this.filterValues = filterValues;
    }
}

package com.rails.lib_data.bean.forAppShow;

import java.util.ArrayList;

public class SearchFilterBean {
    private String filterName;
    private String filterId;
    private boolean isMultiSelect = true; // 默认可多选 只有类目为单选
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

    public boolean isMultiSelect() {
        return isMultiSelect;
    }

    public void setMultiSelect(boolean multiSelect) {
        isMultiSelect = multiSelect;
    }

    public ArrayList<SearchFilterValue> getFilterValues() {
        return filterValues;
    }

    public void setFilterValues(ArrayList<SearchFilterValue> filterValues) {
        this.filterValues = filterValues;
    }
}

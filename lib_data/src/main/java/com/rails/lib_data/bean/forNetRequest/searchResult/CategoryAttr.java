package com.rails.lib_data.bean.forNetRequest.searchResult;

import java.util.List;

public class CategoryAttr {
    private String attrName;
    private List<CategoryAttrValue> attrValues;


    // =========================================================================


    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public List<CategoryAttrValue> getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(List<CategoryAttrValue> attrValues) {
        this.attrValues = attrValues;
    }
}

package com.rails.lib_data.bean.forNetRequest.searchResult;

import java.util.List;

public class ExpandAttr {
    private String attrName;
    private List<ExpandAttrValue> attrValues;


    // =================================


    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public List<ExpandAttrValue> getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(List<ExpandAttrValue> attrValues) {
        this.attrValues = attrValues;
    }
}

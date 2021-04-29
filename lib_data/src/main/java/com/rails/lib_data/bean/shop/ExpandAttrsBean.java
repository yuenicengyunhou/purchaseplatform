package com.rails.lib_data.bean.shop;

import java.util.List;

public class ExpandAttrsBean {

    private String attrId;
    private String attrName;
    /**
     * id : null
     * name : 鸭子
     * simpleCode : null
     * hasLeaf : null
     * idStr : null
     */

    private List<AttrValuesBean> attrValues;

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public List<AttrValuesBean> getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(List<AttrValuesBean> attrValues) {
        this.attrValues = attrValues;
    }
}

package com.rails.lib_data.bean.shop;

import java.util.List;

public class CategoryAttrsBean {

    private String attrId;
    private String attrName;
    /**
     * id : null
     * name : 1000张/包
     * simpleCode : null
     * hasLeaf : null
     * idStr : null
     */

    private List<AttrValuesBean> attrValues;


    public String getAttrName() {
        return attrName;
    }

    public List<AttrValuesBean> getAttrValues() {
        return attrValues;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }
}

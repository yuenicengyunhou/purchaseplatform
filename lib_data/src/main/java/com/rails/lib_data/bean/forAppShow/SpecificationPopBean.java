package com.rails.lib_data.bean.forAppShow;

import java.util.List;

public class SpecificationPopBean {
    private String attrName;
    private String attrId;
    private List<SpecificationValue> specificationValue;


    // ==========================================================


    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public List<SpecificationValue> getSpecificationValue() {
        return specificationValue;
    }

    public void setSpecificationValue(List<SpecificationValue> specificationValue) {
        this.specificationValue = specificationValue;
    }
}

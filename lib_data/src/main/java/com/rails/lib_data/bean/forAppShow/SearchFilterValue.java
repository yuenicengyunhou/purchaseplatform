package com.rails.lib_data.bean.forAppShow;

public class SearchFilterValue {
    private String valueName;
    private String valueId;
    private boolean isSelect = false;
    private int attrFlag = -1; // 0-brand，1-cid, 2-categoryAttrValue, 3-expandAttrValue


    // =============================================================================


    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getAttrFlag() {
        return attrFlag;
    }

    public void setAttrFlag(int attrFlag) {
        this.attrFlag = attrFlag;
    }
}

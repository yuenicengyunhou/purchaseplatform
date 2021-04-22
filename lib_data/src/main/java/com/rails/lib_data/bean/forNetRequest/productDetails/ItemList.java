package com.rails.lib_data.bean.forNetRequest.productDetails;

import java.util.List;

public class ItemList {
    private List<ItemResult> resultList;


    // ============================================================================


    public List<ItemResult> getResultList() {
        return resultList;
    }

    public void setResultList(List<ItemResult> resultList) {
        this.resultList = resultList;
    }
}

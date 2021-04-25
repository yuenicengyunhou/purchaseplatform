package com.rails.lib_data.bean.forNetRequest.searchResult.byShop;

public class SearchDataByShopBean {

    private ShopResultBean shopList;
    private String totalCount;


    // =========================================================


    public ShopResultBean getShopList() {
        return shopList;
    }

    public void setShopList(ShopResultBean shopList) {
        this.shopList = shopList;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}

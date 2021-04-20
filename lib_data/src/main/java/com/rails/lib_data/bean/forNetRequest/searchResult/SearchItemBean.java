package com.rails.lib_data.bean.forNetRequest.searchResult;

import java.util.List;

public class SearchItemBean {
    private long itemId;
    private List<SkuItemBean> item_sku;
    private String shopName;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public List<SkuItemBean> getItem_sku() {
        return item_sku;
    }

    public void setItem_sku(List<SkuItemBean> item_sku) {
        this.item_sku = item_sku;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}

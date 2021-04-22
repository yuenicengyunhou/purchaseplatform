package com.rails.lib_data.bean.forNetRequest.productDetails;

import java.util.List;

public class ItemResult {
    private int itemId;
    private List<ItemSku> item_sku;
    private String shopName;


    //===========================================================


    public List<ItemSku> getItem_sku() {
        return item_sku;
    }

    public void setItem_sku(List<ItemSku> item_sku) {
        this.item_sku = item_sku;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}

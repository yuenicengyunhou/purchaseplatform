package com.rails.lib_data.bean.forNetRequest.productDetails;

public class ItemDeliveryInfoVo {
    private int deliveryType;
    private int homeDelivery;
    private int id;
    private int itemId;
    private int platformId;
    private int sellerId;
    private int shopId;

    public int getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(int deliveryType) {
        this.deliveryType = deliveryType;
    }

    public int getHomeDelivery() {
        return homeDelivery;
    }

    public void setHomeDelivery(int homeDelivery) {
        this.homeDelivery = homeDelivery;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
}

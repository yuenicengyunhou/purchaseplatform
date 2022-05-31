package com.rails.lib_data.bean.forAppShow;


import java.util.List;

/**
 * 店铺基础属性
 */
public class ShopAttribute {
    private String shopName;
    private long shopId;
    private String shopPicture;
    private List<String> saleArea;
    private long shopSaleCount;
    private List<ItemAttribute> items;
    private String creditLevel;
    private String rate;
    private String totalPoints;


    // =========================================


    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
    }

    public List<ItemAttribute> getItems() {
        return items;
    }

    public void setItems(List<ItemAttribute> items) {
        this.items = items;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getShopPicture() {
        return shopPicture;
    }

    public void setShopPicture(String shopPicture) {
        this.shopPicture = shopPicture;
    }

    public List<String> getSaleArea() {
        return saleArea;
    }

    public void setSaleArea(List<String> saleArea) {
        this.saleArea = saleArea;
    }

    public long getShopSaleCount() {
        return shopSaleCount;
    }

    public void setShopSaleCount(long shopSaleCount) {
        this.shopSaleCount = shopSaleCount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
    }
}

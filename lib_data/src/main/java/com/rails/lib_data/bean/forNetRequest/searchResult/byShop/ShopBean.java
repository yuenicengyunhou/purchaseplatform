package com.rails.lib_data.bean.forNetRequest.searchResult.byShop;

import java.util.List;

public class ShopBean {
    private long shopSaleCount;
    private String supplierName;
    private String shopPicture;
    private List<String> saleArea;
    private String shopName;
    private long platformId;
    private long shopId;
    private int shopType;
    private List<ShopSkuBean> shop_sku;

    public long getShopSaleCount() {
        return shopSaleCount;
    }

    public void setShopSaleCount(long shopSaleCount) {
        this.shopSaleCount = shopSaleCount;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(long platformId) {
        this.platformId = platformId;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public int getShopType() {
        return shopType;
    }

    public void setShopType(int shopType) {
        this.shopType = shopType;
    }

    public List<ShopSkuBean> getShop_sku() {
        return shop_sku;
    }

    public void setShop_sku(List<ShopSkuBean> shop_sku) {
        this.shop_sku = shop_sku;
    }
}

package com.rails.lib_data.bean.forNetRequest.searchResult.byShop;

public class ShopSkuBean {
    private String skuName;
    private long itemId;
    private long upTime; // TODO: 2021/04/19 类型存疑
    private double sellPrice;
    private String skuPicture;
    private long skuId;
    private long cid;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getUpTime() {
        return upTime;
    }

    public void setUpTime(long upTime) {
        this.upTime = upTime;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getSkuPicture() {
        return skuPicture;
    }

    public void setSkuPicture(String skuPicture) {
        this.skuPicture = skuPicture;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }
}

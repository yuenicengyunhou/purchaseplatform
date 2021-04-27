package com.rails.lib_data.bean.shop;

public class ItemSkuBean {

    private String skuName;
    private long itemId;
    private long upTime;
    private String pictureUrl;
    private Object brandId;
    private double sellPrice;
    private long shopId;
    private int skuId;
    private Object contrastFlag;
    private int cid;

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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Object getBrandId() {
        return brandId;
    }

    public void setBrandId(Object brandId) {
        this.brandId = brandId;
    }

    public String getSellPrice() {
        return String.valueOf(sellPrice);
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public Object getContrastFlag() {
        return contrastFlag;
    }

    public void setContrastFlag(Object contrastFlag) {
        this.contrastFlag = contrastFlag;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}

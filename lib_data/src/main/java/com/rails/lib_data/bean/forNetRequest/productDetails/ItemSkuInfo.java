package com.rails.lib_data.bean.forNetRequest.productDetails;

public class ItemSkuInfo {
    private String id;
    private int itemId;
    private String attributes;
    private String pictureUrl;
    private String altImages;
    private double inventory;
    private String modelCode;
    private String skuName;
    private String packageDis;
    private long shopId;
    private String weightUnit;
    private int sourceSkuId;
    private int shortCode;
    private int saleStatus;
    private long upTime;
    private String barCode;
    private double weight;
    private String attributesName;
    private ItemSkuPriceResultVo itemSkuPriceResultVo;


    // ===========================================================================================


    public String getAttributesName() {
        return attributesName;
    }

    public void setAttributesName(String attributesName) {
        this.attributesName = attributesName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public ItemSkuPriceResultVo getItemSkuPriceResultVo() {
        return itemSkuPriceResultVo;
    }

    public void setItemSkuPriceResultVo(ItemSkuPriceResultVo itemSkuPriceResultVo) {
        this.itemSkuPriceResultVo = itemSkuPriceResultVo;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public int getSourceSkuId() {
        return sourceSkuId;
    }

    public void setSourceSkuId(int sourceSkuId) {
        this.sourceSkuId = sourceSkuId;
    }

    public int getShortCode() {
        return shortCode;
    }

    public void setShortCode(int shortCode) {
        this.shortCode = shortCode;
    }

    public int getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(int saleStatus) {
        this.saleStatus = saleStatus;
    }

    public long getUpTime() {
        return upTime;
    }

    public void setUpTime(long upTime) {
        this.upTime = upTime;
    }

    public double getInventory() {
        return inventory;
    }

    public void setInventory(double inventory) {
        this.inventory = inventory;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getPackageDis() {
        return packageDis;
    }

    public void setPackageDis(String packageDis) {
        this.packageDis = packageDis;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getAltImages() {
        return altImages;
    }

    public void setAltImages(String altImages) {
        this.altImages = altImages;
    }
}

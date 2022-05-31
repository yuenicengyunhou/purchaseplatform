package com.rails.lib_data.bean.forNetRequest.productDetails;

public class ItemPictureVo {
    private String id;
    private int platformId;
    private String itemId;
    private String skuId;
    private long shopId;
    private String sellerId;
    private String pictureUrl;
    private String altImages;
    private int sortNumber;
    private int annexType;


    // ===========================================================================================


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
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

    public int getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(int sortNumber) {
        this.sortNumber = sortNumber;
    }

    public int getAnnexType() {
        return annexType;
    }

    public void setAnnexType(int annexType) {
        this.annexType = annexType;
    }
}

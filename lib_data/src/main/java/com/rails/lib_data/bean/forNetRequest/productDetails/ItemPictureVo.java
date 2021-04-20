package com.rails.lib_data.bean.forNetRequest.productDetails;

public class ItemPictureVo {
    private int id;
    private int platformId;
    private int itemId;
    private int skuId;
    private int shopId;
//    private int sellerId;
    private String pictureUrl;
    private String altImages;
    private int sortNumber;
    private int annexType;


    // ===========================================================================================


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

//    public int getSellerId() {
//        return sellerId;
//    }
//
//    public void setSellerId(int sellerId) {
//        this.sellerId = sellerId;
//    }

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

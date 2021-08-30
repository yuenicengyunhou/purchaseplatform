package com.rails.lib_data.bean.forNetRequest.productDetails;

import android.graphics.Bitmap;

public class ItemPicture {


    private String id;
    private String platformId;
    private String itemId;
    private String shopId;
    private String sellerId;
    private String pictureUrl;
    private String altImages;
    private String sortNumber;
    private String created;
    private String modified;
    private String yn;
    private String skuId;
    private String annexType;
    private Bitmap bitmap;
    private String watermarkUrl;


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
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

    public String getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(String sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getYn() {
        return yn;
    }

    public void setYn(String yn) {
        this.yn = yn;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getAnnexType() {
        return annexType;
    }

    public void setAnnexType(String annexType) {
        this.annexType = annexType;
    }


}

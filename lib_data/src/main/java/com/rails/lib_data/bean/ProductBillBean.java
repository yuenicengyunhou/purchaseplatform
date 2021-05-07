package com.rails.lib_data.bean;

/**
 * 商品详情清单对象
 *
 * @author： sk_comic@163.com
 * @date: 2021/5/7
 */
public class ProductBillBean {


    private String id;
    private String platformId;
    private String itemId;
    private String skuId;
    private String shopId;
    private String annexType;
    private String annexUrl;
    private String annexName;
    private String altDesc;
    private String sortNumber;
    private String created;
    private String modified;
    private String yn;
    private String externalId;
    private String annexTypeList;


    public ProductBillBean(String annexName, String yn) {
        this.annexName = annexName;
        this.yn = yn;
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

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getAnnexType() {
        return annexType;
    }

    public void setAnnexType(String annexType) {
        this.annexType = annexType;
    }

    public String getAnnexUrl() {
        return annexUrl;
    }

    public void setAnnexUrl(String annexUrl) {
        this.annexUrl = annexUrl;
    }

    public String getAnnexName() {
        return annexName;
    }

    public void setAnnexName(String annexName) {
        this.annexName = annexName;
    }

    public String getAltDesc() {
        return altDesc;
    }

    public void setAltDesc(String altDesc) {
        this.altDesc = altDesc;
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

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getAnnexTypeList() {
        return annexTypeList;
    }

    public void setAnnexTypeList(String annexTypeList) {
        this.annexTypeList = annexTypeList;
    }
}

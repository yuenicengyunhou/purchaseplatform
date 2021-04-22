package com.rails.lib_data.bean.forNetRequest.productDetails;

import java.util.List;

public class ItemPublishVo {
    private int id;
    private int platformId;
    private int sellerId;
    private long shopId;
    private String shopName;
    private String itemName;
    private int cid;
    private int brandId;
    private String BrandName;
    private String origin;
    private String categoryAttributes;
    private String specAttributes;
    private int saleStatus;
    private String describeUrl;
    private String created;
    private String modified;
    private String listingTime;
    private String delistingTime;
    private ItemAfterSaleVo itemAfterSaleVo;
    private int publishuserId;
    private String categoryFullName;
    private List<String> specAttrNameArray;
    private int itemType;
    private String rejectReason;
    private SupplierInfoImportData supplierInfoImportData;
    private int orgId;
    private String creditLevel;


    // ===========================================================================================


    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public SupplierInfoImportData getSupplierInfoImportData() {
        return supplierInfoImportData;
    }

    public void setSupplierInfoImportData(SupplierInfoImportData supplierInfoImportData) {
        this.supplierInfoImportData = supplierInfoImportData;
    }

    public List<String> getSpecAttrNameArray() {
        return specAttrNameArray;
    }

    public void setSpecAttrNameArray(List<String> specAttrNameArray) {
        this.specAttrNameArray = specAttrNameArray;
    }

    public ItemAfterSaleVo getItemAfterSaleVo() {
        return itemAfterSaleVo;
    }

    public void setItemAfterSaleVo(ItemAfterSaleVo itemAfterSaleVo) {
        this.itemAfterSaleVo = itemAfterSaleVo;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public int getPublishuserId() {
        return publishuserId;
    }

    public void setPublishuserId(int publishuserId) {
        this.publishuserId = publishuserId;
    }

    public String getCategoryFullName() {
        return categoryFullName;
    }

    public void setCategoryFullName(String categoryFullName) {
        this.categoryFullName = categoryFullName;
    }

    public int getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(int saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getDescribeUrl() {
        return describeUrl;
    }

    public void setDescribeUrl(String describeUrl) {
        this.describeUrl = describeUrl;
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

    public String getListingTime() {
        return listingTime;
    }

    public void setListingTime(String listingTime) {
        this.listingTime = listingTime;
    }

    public String getDelistingTime() {
        return delistingTime;
    }

    public void setDelistingTime(String delistingTime) {
        this.delistingTime = delistingTime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCategoryAttributes() {
        return categoryAttributes;
    }

    public void setCategoryAttributes(String categoryAttributes) {
        this.categoryAttributes = categoryAttributes;
    }

    public String getSpecAttributes() {
        return specAttributes;
    }

    public void setSpecAttributes(String specAttributes) {
        this.specAttributes = specAttributes;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

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
}

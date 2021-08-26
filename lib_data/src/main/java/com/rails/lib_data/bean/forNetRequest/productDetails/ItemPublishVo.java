package com.rails.lib_data.bean.forNetRequest.productDetails;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class ItemPublishVo {
    private int id;
    private int platformId;
    private int sellerId;
    private String shopId;
    private String shopName;
    private String itemName;
    private int cid;
    private int brandId;
    private String brandName;
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
    private List<String> attrNameArray;
    private List<AttrNameValueReaultVo> attrNameValueReaultVos;
    private int itemType;
    private String rejectReason;
    private SupplierInfoImportData supplierInfoImportData;
    private int orgId;
    private String creditLevel;
    private int itemSaleCount;
    private JSONObject skuSpecMap;
    private String logoUrl;
    private String materialType;


    // ===========================================================================================


    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public JSONObject getSkuSpecMap() {
        return skuSpecMap;
    }

    public void setSkuSpecMap(JSONObject skuSpecMap) {
        this.skuSpecMap = skuSpecMap;
    }

    public List<AttrNameValueReaultVo> getAttrNameValueReaultVos() {
        return attrNameValueReaultVos;
    }

    public void setAttrNameValueReaultVos(List<AttrNameValueReaultVo> attrNameValueReaultVos) {
        this.attrNameValueReaultVos = attrNameValueReaultVos;
    }

    public List<String> getAttrNameArray() {
        return attrNameArray;
    }

    public void setAttrNameArray(List<String> attrNameArray) {
        this.attrNameArray = attrNameArray;
    }

    public int getItemSaleCount() {
        return itemSaleCount;
    }

    public void setItemSaleCount(int itemSaleCount) {
        this.itemSaleCount = itemSaleCount;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
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

package com.rails.lib_data.bean;

import androidx.databinding.ObservableField;

/**
 * 购物车--商铺---产品对象
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/19
 */
public class CartShopProductBean {


    /**
     * shopId : 202003030109
     * itemId : 1315833
     * itemName : 测试商品主图问题
     * skuName : 测试商品主图问题（红色）
     * skuId : 1427348
     * sourceSkuId :
     * categoryId : 1000887
     * shortCode : 1427348
     * attributes : [{"aid":"29187","vid":"134501"}]
     * attributesName : 笔杆颜色:红色;
     * pictureUrl : //xsky.rails.cn/mall/00d33dd688fc2d11bda13336e7e745c220210319112619632.jpg
     * saleStatus : 1
     * skuStatus :
     * unit :
     * skuNum : 2
     * marketPrice : 2.00
     * sellPrice : 1.00
     * subtotalPrice : 2.00
     * selected : false
     * collect : false
     * taxRate :
     * packageDis : **
     * packinglist :
     * weight :
     * lockout : false
     * materialCode :
     * recommendOrgId :
     * brandId :
     * brandName :
     * firstCategoryId :
     * firstCategoryName :
     * secondCategoryId :
     * secondCategoryName :
     * thirdCategoryId :
     * thirdCategoryName :
     */

    private Long shopId;
    private Integer itemId;
    private String itemName;
    private String skuName;
    private Integer skuId;
    private String sourceSkuId;
    private Integer categoryId;
    private String shortCode;
    private String attributes;
    private String attributesName;
    private String pictureUrl;
    private Integer saleStatus;
    private String skuStatus;
    private String unit;
    private Integer skuNum;
    private double marketPrice;
    private String sellPrice;
    private String subtotalPrice;
    private Boolean selected;
    private Boolean collect;
    private String taxRate;
    private String packageDis;
    private String packinglist;
    private String weight;
    private Boolean lockout;
    private String materialCode;
    private String recommendOrgId;
    private String brandId;
    private String brandName;
    private String firstCategoryId;
    private String firstCategoryName;
    private String secondCategoryId;
    private String secondCategoryName;
    private String thirdCategoryId;
    private String thirdCategoryName;

    public final ObservableField<Boolean> isSel = new ObservableField<>();
    public final ObservableField<Integer> num = new ObservableField<>();

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getSourceSkuId() {
        return sourceSkuId;
    }

    public void setSourceSkuId(String sourceSkuId) {
        this.sourceSkuId = sourceSkuId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getAttributesName() {
        return attributesName;
    }

    public void setAttributesName(String attributesName) {
        this.attributesName = attributesName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Integer getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(Integer saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getSkuStatus() {
        return skuStatus;
    }

    public void setSkuStatus(String skuStatus) {
        this.skuStatus = skuStatus;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(Integer skuNum) {
        num.set(skuNum);
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(String subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getPackageDis() {
        return packageDis;
    }

    public void setPackageDis(String packageDis) {
        this.packageDis = packageDis;
    }

    public String getPackinglist() {
        return packinglist;
    }

    public void setPackinglist(String packinglist) {
        this.packinglist = packinglist;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Boolean getLockout() {
        return lockout;
    }

    public void setLockout(Boolean lockout) {
        this.lockout = lockout;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getRecommendOrgId() {
        return recommendOrgId;
    }

    public void setRecommendOrgId(String recommendOrgId) {
        this.recommendOrgId = recommendOrgId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getFirstCategoryId() {
        return firstCategoryId;
    }

    public void setFirstCategoryId(String firstCategoryId) {
        this.firstCategoryId = firstCategoryId;
    }

    public String getFirstCategoryName() {
        return firstCategoryName;
    }

    public void setFirstCategoryName(String firstCategoryName) {
        this.firstCategoryName = firstCategoryName;
    }

    public String getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(String secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
    }

    public String getSecondCategoryName() {
        return secondCategoryName;
    }

    public void setSecondCategoryName(String secondCategoryName) {
        this.secondCategoryName = secondCategoryName;
    }

    public String getThirdCategoryId() {
        return thirdCategoryId;
    }

    public void setThirdCategoryId(String thirdCategoryId) {
        this.thirdCategoryId = thirdCategoryId;
    }

    public String getThirdCategoryName() {
        return thirdCategoryName;
    }

    public void setThirdCategoryName(String thirdCategoryName) {
        this.thirdCategoryName = thirdCategoryName;
    }
}

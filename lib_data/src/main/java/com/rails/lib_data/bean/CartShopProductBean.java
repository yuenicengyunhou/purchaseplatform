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

    //店铺id
    private String shopId;
    //商品id
    private String itemId;
    //spu名称
    private String itemName;
    // sku名称
    private String skuName;
    private String skuId;
    //第三方skuId
    private String sourceSkuId;
    private String categoryId;
    private String shortCode;
    private String attributes;
    private String attributesName;
    private String pictureUrl;
    //商品状态 0：无效 1：有效
    private int saleStatus;
    //sku状态 0：停用 1：启用
    private String skuStatus;
    //计量单位
    private String unit;
    private String unitName;
    //sku 采购数量
    private long skuNum;
    // 市场价格
    private double marketPrice;
    //销售价格
    private double sellPrice;
    // sku小计
    private double subtotalPrice;
    private Boolean selected;
    //是否收藏该商品 true：已收藏   false：未收藏
    private Boolean collect;
    // sku税率
    private String taxRate;
    private String packageDis;
    private String packinglist;
    private String weight;
    //供应商是否锁定
    private Boolean lockout;
    private String materialCode;
    //推荐路局id
    private String recommendOrgId;
    private String brandId;
    private String brandName;
    private String firstCategoryId;
    private String firstCategoryName;
    private String secondCategoryId;
    private String secondCategoryName;
    private String thirdCategoryId;
    private String thirdCategoryName;
    private Boolean limit;
    private Boolean canUser;
    private String tempShopId;


    public final ObservableField<Boolean> isSel = new ObservableField<>();
    public final ObservableField<Long> num = new ObservableField<>();
    public final ObservableField<Boolean> canReduce = new ObservableField<>();
    public final ObservableField<Boolean> canAdd = new ObservableField<>();
    public final ObservableField<Boolean> canSel = new ObservableField<>();
    public final ObservableField<Boolean> isLimit = new ObservableField<>();
    public final ObservableField<Boolean> isCollect = new ObservableField<>();

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
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

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSourceSkuId() {
        return sourceSkuId;
    }

    public void setSourceSkuId(String sourceSkuId) {
        this.sourceSkuId = sourceSkuId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
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

    public long getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(long skuNum) {
        this.skuNum = skuNum;
    }

    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(double subtotalPrice) {
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

    public Boolean getLimit() {
        return limit;
    }

    public void setLimit(Boolean limit) {
        this.limit = limit;
    }

    public Boolean getCanUser() {
        return canUser;
    }

    public void setCanUser(Boolean canUser) {
        this.canUser = canUser;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}

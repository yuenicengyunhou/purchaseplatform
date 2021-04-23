package com.rails.lib_data;

/**
 * author:wangqi
 * date:2021/4/22
 * 子订单详情列表
 */
public class SubSkuDemandInfoBean {


    /**
     * id : 1145493
     * key : 1145493
     * platformId : 20
     * pictureUrl : //xsky.rails.cn/mall/810f1ef5760afb5593297999408ed35420210408113214881.jpg
     * shortCode : 1427561
     * skuId : 1427561
     * sourceSkuId :
     * itemId : 1315988
     * itemName : 测试商品003
     * attributes :
     * sellPrice : 333.00
     * originalNum : 0
     * skuNums : 1
     * totalPrice : 333.00
     * marketPrice : 333.00
     * totalMarketPrice : 333.00
     * totalDifferPrice : 0.00
     * firstName :
     * twoName :
     * threeName :
     * brandName : 武夷山大红袍(zr01)
     * skuAnnexUrlName :
     * skuAnnexUrl :
     * subSkuDemandOrderInfo :
     * subSkuDemandNoList :
     * canAfs : false
     */

    private long id;
    private long key;
    private int platformId;//平台id ,
    private String pictureUrl;//sku图片 ,
    private String shortCode;
    private int skuId;
    private String sourceSkuId;
    private int itemId;
    private String itemName;// sku名称 ,
    private String attributes;//销售属性 ,
    private String sellPrice;// sku销售价格 ,
    private int originalNum;//初始数量 ,
    private int skuNums;// 最终数量 ,
    private String totalPrice;// 售价小计 ,
    private String marketPrice;// sku市场价
    private String totalMarketPrice;//市场价小计 ,
    private String totalDifferPrice;// 差价 ,
    private String firstName;//一级类目 ,
    private String twoName;//二级类目
    private String threeName;//三级类目 ,
    private String brandName;//品牌名称 ,
    private String skuAnnexUrlName;// SKU外部商城名称 ,
    private String skuAnnexUrl;//SKU外部商城链接 ,
    private String subSkuDemandOrderInfo;//子订单以及sku对应的需求详情
    private String subSkuDemandNoList;//子订单以及sku对应的需求单号列表 ,
    private boolean canAfs;//是否可以售后 ,

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public String getSourceSkuId() {
        return sourceSkuId;
    }

    public void setSourceSkuId(String sourceSkuId) {
        this.sourceSkuId = sourceSkuId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getOriginalNum() {
        return originalNum;
    }

    public void setOriginalNum(int originalNum) {
        this.originalNum = originalNum;
    }

    public int getSkuNums() {
        return skuNums;
    }

    public void setSkuNums(int skuNums) {
        this.skuNums = skuNums;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getTotalMarketPrice() {
        return totalMarketPrice;
    }

    public void setTotalMarketPrice(String totalMarketPrice) {
        this.totalMarketPrice = totalMarketPrice;
    }

    public String getTotalDifferPrice() {
        return totalDifferPrice;
    }

    public void setTotalDifferPrice(String totalDifferPrice) {
        this.totalDifferPrice = totalDifferPrice;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTwoName() {
        return twoName;
    }

    public void setTwoName(String twoName) {
        this.twoName = twoName;
    }

    public String getThreeName() {
        return threeName;
    }

    public void setThreeName(String threeName) {
        this.threeName = threeName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSkuAnnexUrlName() {
        return skuAnnexUrlName;
    }

    public void setSkuAnnexUrlName(String skuAnnexUrlName) {
        this.skuAnnexUrlName = skuAnnexUrlName;
    }

    public String getSkuAnnexUrl() {
        return skuAnnexUrl;
    }

    public void setSkuAnnexUrl(String skuAnnexUrl) {
        this.skuAnnexUrl = skuAnnexUrl;
    }

    public String getSubSkuDemandOrderInfo() {
        return subSkuDemandOrderInfo;
    }

    public void setSubSkuDemandOrderInfo(String subSkuDemandOrderInfo) {
        this.subSkuDemandOrderInfo = subSkuDemandOrderInfo;
    }

    public String getSubSkuDemandNoList() {
        return subSkuDemandNoList;
    }

    public void setSubSkuDemandNoList(String subSkuDemandNoList) {
        this.subSkuDemandNoList = subSkuDemandNoList;
    }

    public boolean isCanAfs() {
        return canAfs;
    }

    public void setCanAfs(boolean canAfs) {
        this.canAfs = canAfs;
    }
}

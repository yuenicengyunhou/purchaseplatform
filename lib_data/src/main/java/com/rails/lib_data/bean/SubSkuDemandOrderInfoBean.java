package com.rails.lib_data.bean;

public class SubSkuDemandOrderInfoBean {


    /**
     * id : 2014
     * key : 2014
     * platformId : 20
     * demandNo : 2210129113500003
     * itemName : 维德医疗（WELLDAY）一次性成人医用口罩50只/盒 无菌防尘防柳絮轻薄透气防雾霾防护三层挂耳式男
     * attributes : 包装:盒装;适用人群:成人;
     * sellPrice : 11.90
     * skuNum : 5
     * totalPrice : 59.50
     */

    private String id;
    private String key;
    private String platformId;
    private String demandNo;
    private String itemName;
    private String attributes;
    private String sellPrice;
    private int skuNum;
    private String totalPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getDemandNo() {
        return demandNo;
    }

    public void setDemandNo(String demandNo) {
        this.demandNo = demandNo;
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

    public int getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(int skuNum) {
        this.skuNum = skuNum;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}

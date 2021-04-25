package com.rails.lib_data.bean.forAppShow;


/**
 * 展示在商品详情页的参数弹窗
 */
public class ItemParams {

    /**
     * 品牌
     */
    private String brand;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品编码
     */
    private String productNum;

    /**
     * 商品产地
     */
    private String madeIn;

    /**
     * 单品编码
     */
    private String itemNum;

    /**
     * 规格型号
     */
    private String type;

    /**
     * 单品条码
     */
    private String itemBarCode;

    /**
     * 商品毛重
     */
    private String weight;

    /**
     * 重量单位
     */
    private String weightUnit;

    /**
     * 包装尺寸
     */
    private String size;

    /**
     * 商品单位
     */
    private String itemUnit;


    // =========================================================================================


    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getMadeIn() {
        return madeIn;
    }

    public void setMadeIn(String madeIn) {
        this.madeIn = madeIn;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemBarCode() {
        return itemBarCode;
    }

    public void setItemBarCode(String itemBarCode) {
        this.itemBarCode = itemBarCode;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }
}

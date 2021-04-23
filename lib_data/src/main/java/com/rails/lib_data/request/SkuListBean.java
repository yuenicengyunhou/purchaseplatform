package com.rails.lib_data.request;

/**
 * @author： sk_comic@163.com
 * @date: 2021/4/22
 */
public class SkuListBean {
    private String skuId;
    private long skuNum;
    private String itemId;
    private double sellPrice;
    private String itemName;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public long getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(long skuNum) {
        this.skuNum = skuNum;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}

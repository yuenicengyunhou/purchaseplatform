package com.rails.lib_data.bean.forNetRequest.productDetails;

import com.rails.lib_data.bean.ProductBillBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 在售价格
 * <p>
 * 市场价格
 * <p>
 * sku名称
 * <p>
 * sku图片
 * <p>
 * 评分
 * <p>
 * 销量
 * <p>
 * 包装清单
 */
public class ProductPriceBean {
    private String skuId;
    private long shopId;
    private int shopType;
    private double marketPrice;
    private double sellPrice;
    private String skuName;
    private String shopName;
    private List<ItemPicture> pictureUrl;
    private ArrayList<ProductBillBean> packinglist;
    private String packageDis;
    private double score;
    private int saleNum;
    private String creditLevel;


    // =======================================================================================


    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public int getShopType() {
        return shopType;
    }

    public void setShopType(int shopType) {
        this.shopType = shopType;
    }

    public double getMarketPrice() {
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

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<ItemPicture> getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(List<ItemPicture> pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public ArrayList<ProductBillBean> getPackinglist() {
        return packinglist;
    }

    public void setPackinglist(ArrayList<ProductBillBean> packinglist) {
        this.packinglist = packinglist;
    }

    public String getPackageDis() {
        return packageDis;
    }

    public void setPackageDis(String packageDis) {
        this.packageDis = packageDis;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
    }
}

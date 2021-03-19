package com.rails.lib_data.bean;

import java.util.List;

/**
 * 购物车对象
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/10
 */
public class CartBean {

    /**
     * platformId : 20
     * userId : 1000090104
     * organizeId : 13
     * goodsNum : 0
     * totalSkuNum : 2
     * totalPrice : 0
     * paymentPrice : 0
     * freightPrice : 0
     * selected : false
     * updateTime : 1616134017524
     */

    private Integer platformId;
    private Integer userId;
    private Integer organizeId;
    private Integer goodsNum;
    private Integer totalSkuNum;
    private String totalPrice;
    private String paymentPrice;
    private String freightPrice;
    private Boolean selected;
    private Long updateTime;
    private List<CartShopBean> shopList;


    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(Integer organizeId) {
        this.organizeId = organizeId;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Integer getTotalSkuNum() {
        return totalSkuNum;
    }

    public void setTotalSkuNum(Integer totalSkuNum) {
        this.totalSkuNum = totalSkuNum;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(String paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public String getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(String freightPrice) {
        this.freightPrice = freightPrice;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public List<CartShopBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<CartShopBean> shopList) {
        this.shopList = shopList;
    }
}

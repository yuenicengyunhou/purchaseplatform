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

    //平台id
    private String platformId;
    //用户id
    private String userId;
    //组织机构id
    private String organizeId;
    //选中了几种货品
    private String goodsNum;
    //选中的sku货品数量
    private String totalSkuNum;
    //sku总金额
    private String totalPrice;
    //应付总金额
    private String paymentPrice;
    //运费金额
    private String freightPrice;
    //是否全选 true：是  false：否
    private Boolean selected;
    // 购物车最后一次更新时间
    private String updateTime;
    // 店铺集合
    private List<CartShopBean> shopList;

//    private List<Fa>

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(String organizeId) {
        this.organizeId = organizeId;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getTotalSkuNum() {
        return totalSkuNum;
    }

    public void setTotalSkuNum(String totalSkuNum) {
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<CartShopBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<CartShopBean> shopList) {
        this.shopList = shopList;
    }

}

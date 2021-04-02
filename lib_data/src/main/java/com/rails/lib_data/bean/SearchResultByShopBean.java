package com.rails.lib_data.bean;

import java.util.ArrayList;

/**
 * 搜索结果Bean - 按商铺搜索
 */
public class SearchResultByShopBean {

    /**
     * 商店名称
     */
    private String shopName;

    /**
     * 商店风险
     */
    private String security;

    /**
     * 商店的产品们
     */
    private ArrayList<OrderItemBean> orderItemBeans;


    // ==========================================================================================


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public ArrayList<OrderItemBean> getOrderItemBeans() {
        return orderItemBeans;
    }

    public void setOrderItemBeans(ArrayList<OrderItemBean> orderItemBeans) {
        this.orderItemBeans = orderItemBeans;
    }
}

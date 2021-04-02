package com.rails.lib_data.bean;

import com.rails.purchaseplatform.framwork.adapter.MulInterface;

import java.util.List;

/**
 * 采购单 Bean
 */
public class OrderBean implements MulInterface {

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 订单生成时间
     */
    private String generateTime;

    /**
     * 供应商
     */
    private String provider;

    /**
     * 采购人
     */
    private String buyer;

    /**
     * 延迟收货时间
     */
    private String delayTime;

    /**
     * 订单内商品集合
     */
    private List<OrderItemBean> orderItemBeans;

    /**
     * 订单价格合计
     */
    private String totalPrice;


    // ========================================================================================


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(String generateTime) {
        this.generateTime = generateTime;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(String delayTime) {
        this.delayTime = delayTime;
    }

    public List<OrderItemBean> getOrderItemBeans() {
        return orderItemBeans;
    }

    public void setOrderItemBeans(List<OrderItemBean> orderItemBeans) {
        this.orderItemBeans = orderItemBeans;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public int getContentID() {
        return 0;
    }
}

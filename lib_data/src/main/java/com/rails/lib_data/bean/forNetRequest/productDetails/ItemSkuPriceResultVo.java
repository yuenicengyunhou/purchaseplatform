package com.rails.lib_data.bean.forNetRequest.productDetails;

public class ItemSkuPriceResultVo {

    /**
     * 当前价格
     */
    private double currentPrice;

    /**
     * 订单优惠金额
     */
    private double discountPrice;

    /**
     * 面价
     */
    private double marketPrice;

    /**
     * 订单满足金额
     */
    private double meetPrice;


    // ===================================================================================


    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public double getMeetPrice() {
        return meetPrice;
    }

    public void setMeetPrice(double meetPrice) {
        this.meetPrice = meetPrice;
    }
}

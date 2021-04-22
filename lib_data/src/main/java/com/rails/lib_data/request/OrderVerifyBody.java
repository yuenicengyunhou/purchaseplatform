package com.rails.lib_data.request;

import java.util.List;

/**
 * @author： sk_comic@163.com
 * @date: 2021/4/22
 */
public class OrderVerifyBody {


    private Integer platformId;
    private Integer accountId;
    private List<SkuListBean> skuList;
    private Integer organizeId;
    private OrderAddressBean orderAddress;
    private OrderInvoiceBean orderInvoice;
    private RemarkMapBean remarkMap;
    private Integer through;
    private Integer settleType;
    private String paymentPrice;
    private String accountingUnitId;
    private Integer paymentType;
    private String accountingType;
    private String delayFlag;

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public List<SkuListBean> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<SkuListBean> skuList) {
        this.skuList = skuList;
    }

    public Integer getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(Integer organizeId) {
        this.organizeId = organizeId;
    }

    public OrderAddressBean getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(OrderAddressBean orderAddress) {
        this.orderAddress = orderAddress;
    }

    public OrderInvoiceBean getOrderInvoice() {
        return orderInvoice;
    }

    public void setOrderInvoice(OrderInvoiceBean orderInvoice) {
        this.orderInvoice = orderInvoice;
    }

    public RemarkMapBean getRemarkMap() {
        return remarkMap;
    }

    public void setRemarkMap(RemarkMapBean remarkMap) {
        this.remarkMap = remarkMap;
    }

    public Integer getThrough() {
        return through;
    }

    public void setThrough(Integer through) {
        this.through = through;
    }

    public Integer getSettleType() {
        return settleType;
    }

    public void setSettleType(Integer settleType) {
        this.settleType = settleType;
    }

    public String getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(String paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public String getAccountingUnitId() {
        return accountingUnitId;
    }

    public void setAccountingUnitId(String accountingUnitId) {
        this.accountingUnitId = accountingUnitId;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getAccountingType() {
        return accountingType;
    }

    public void setAccountingType(String accountingType) {
        this.accountingType = accountingType;
    }

    public String getDelayFlag() {
        return delayFlag;
    }

    public void setDelayFlag(String delayFlag) {
        this.delayFlag = delayFlag;
    }
}

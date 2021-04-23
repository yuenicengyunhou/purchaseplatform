package com.rails.lib_data.request;

import com.alibaba.fastjson.JSONObject;
import com.rails.lib_data.bean.AddressBean;


import java.util.List;

/**
 * @author： sk_comic@163.com
 * @date: 2021/4/22
 */
public class OrderVerifyBody {


    private String platformId;
    private String accountId;
    private List<SkuListBean> skuList;
    private String organizeId;
    private OrderAddressBean orderAddress;
    private OrderInvoiceBean orderInvoice;
    private JSONObject remarkMap;
    private Integer through;
    private Integer settleType;
    private String paymentPrice;
    private String accountingUnitId;
    private Integer paymentType;
    private String accountingType;
    private String delayFlag;


    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(String organizeId) {
        this.organizeId = organizeId;
    }

    public List<SkuListBean> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<SkuListBean> skuList) {
        this.skuList = skuList;
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

    public JSONObject getRemarkMap() {
        return remarkMap;
    }

    public void setRemarkMap(JSONObject remarkMap) {
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

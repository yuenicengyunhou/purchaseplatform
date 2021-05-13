package com.rails.lib_data.request;

import com.alibaba.fastjson.JSONObject;
import com.rails.lib_data.bean.AddressBean;


import java.util.List;

/**
 * @author： sk_comic@163.com
 * @date: 2021/4/22
 */
public class OrderVerifyBody {


    //平台id
    private String platformId;
    //账户id
    private String accountId;
    //商品列表
    private List<SkuListBean> skuList;
    //机构id
    private String organizeId;
    //收货地址
    private OrderAddressBean orderAddress;
    //发票信息
    private OrderInvoiceBean orderInvoice;
    //评论集合
    private JSONObject remarkMap;
    /**
     * 是否直达 0否 1是
     */
    private Integer through;
    /**
     * 结算类型 10暂不选择 15运营物资 20其他物资
     */
    private Integer settleType;
    /**
     * 订单支付金额（包括运费）
     */
    private String paymentPrice;
    /**
     * 结算单位id
     */
    private String accountingUnitId;
    /**
     * 支付方式
     */
    private Integer paymentType;
    /**
     * 结算方式  0：本级结算，1：集中结算
     */
    private String accountingType;
    /**
     * 是否延迟收货 1：延迟收货 0：无延迟收货
     */
    private String delayFlag;

    private String delayReceiveTime;


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

    public String getDelayReceiveTime() {
        return delayReceiveTime;
    }

    public void setDelayReceiveTime(String delayReceiveTime) {
        this.delayReceiveTime = delayReceiveTime;
    }
}

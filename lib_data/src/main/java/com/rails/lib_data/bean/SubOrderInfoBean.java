package com.rails.lib_data.bean;

import com.rails.lib_data.SubSkuDemandInfoBean;

import java.util.ArrayList;

public class SubOrderInfoBean {

    private long id;
    private long key;
    private int platformId;//平台id ,
    private String shopId;//店铺id ,
    private String organizeId;//创建人所属组织机构id ,
    private int buyerId;//创建人用户id ,
    private long orderNo;//订单编号 ,
    private int parentNo;//0:第一级主单(拆单和未拆单都为0)，(!=0):当前订单的父单号（和order_split_type 配合查询订单） ,
    private String thirdOrderNo;//三方订单编号 ,
    private int orderSplitType;// 0:包含子单,1:叶子单（不包含子单）（和parent_order_no配合查询订单） ,
    private int orderSource;//订单来源：1、购物车订单 2、需求单转订单 ,
    private String settleType;//结算类型：10暂不选择 15运营物资 20其他物资 ,
    private String shopType;// 店铺类型 ,
    private int orderStatus;//订单状态：10待付款 15已付款 20待发货 30待收货 40已完成 60已驳回 70已取消 ,
    private String paymentType;// 支付类型：1：账期支付 ,
    private int commentStatus;//订单评价状态：10待评价 20部分评价 30已评价 ,
    private String paymentPrice;//订单实际支付金额 ,
    private String totalPrice;// 订单商品总金额 ,
    private String freightPrice;///订单总运费 ,
    private String orderTime;//订单提交时间 ,
    private String paymentTime;//订单支付时间
    private String partDeliverTime;//订单部分发货时间 ,
    private String deliverTime;//订单全部发货时间 ,
    private String estimateDeliverTime;//订单预计确认收货时间,自动收货使用
    private String finishTime;//订单完成(确认收货)时间 ,
    private String cancelTime;//订单取消时间 ,
    private String through;// 是否直达：0否 1是 ,
    private int delivered;//妥投状态：0否 1是 ,
    private int reconciliation;// 对账状态：0未对账 1已对账 ,
    private String billing;//开票状态：0未开票(不可开票) 1未开票(可开票) 2已开票 3开票中 ,
    private String refundStatus;// 售后状态：0无售后 1售后中 ,
    private String refundAmount;// 退款金额 ,
    private String repayment;// 还款状态：0未还款 1已还款 ,
    private String priceCheck;//金额核对状态：0无 1无差异 2有差异 ,
    private String dealWith;//差异处理状态：0无 1未处理 2已处理 ,
    private String snapshotUrl;///订单快照(存放oss) ,
    private String processInstanceId;//工作流的对象id ,
    private String cancelReason;//订单取消原因 ,
    private String cancelType;//订单取消类型(1:系统取消(供应商系统取消) 2:用户主动取消) ,
    private String remark;//订单备注 ,
    private String created;// 创建时间
    private String modified;//: 更新时间 ,
    private String yn;//是否有效1有效 0无效
    private String orderStatusView;// 订单状态：10待付款 15已付款 20待发货 30待收货 40已完成 60已驳回 70已取消 ,
    private String receiverName;// 收货人姓名 ,
    private String address;// 收货人完整地址(带省市县镇) ,
    private String mobile;//收货人电话号码 ,
    private String shopName;//供应商 ,
    private String buyerName;//采购人
    private String organizeName;
    private String invoiceType;//发票类型 ,
    private String invoiceInfo;//发票信息 ,
    private String logisticsInfo;//物流信息 ,
    private boolean isWuliu;//是否是物流平台发货的订单,默认设置不是物流平台 ,
    private String deliveredTime;
    private boolean oneself;
    private String accountingUnitName;
    private String applicationTime;//申请取消订单的时间 ,
    private String rebutTime;//驳回取消订单的时间 ,
    private int cancelStatus;// 订单取消状态 ,
    private String skuId;
    private String itemId;
    private String skuNum;
    private String sellPrice;
    private String marketPrice;
    private String itemName;
    private String orderLogisticsNo;
    private String delayFlag;//是否延迟收货 1：延迟 0：无延迟 ,
    private String delayReceiveTime;//: 延迟收货 ,
    private ArrayList<SubSkuDemandInfoBean> subSkuDemandInfo;//子订单详情列表

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(String organizeId) {
        this.organizeId = organizeId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
    }

    public int getParentNo() {
        return parentNo;
    }

    public void setParentNo(int parentNo) {
        this.parentNo = parentNo;
    }

    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
    }

    public int getOrderSplitType() {
        return orderSplitType;
    }

    public void setOrderSplitType(int orderSplitType) {
        this.orderSplitType = orderSplitType;
    }

    public int getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(int orderSource) {
        this.orderSource = orderSource;
    }

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public int getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(int commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(String paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(String freightPrice) {
        this.freightPrice = freightPrice;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPartDeliverTime() {
        return partDeliverTime;
    }

    public void setPartDeliverTime(String partDeliverTime) {
        this.partDeliverTime = partDeliverTime;
    }

    public String getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getEstimateDeliverTime() {
        return estimateDeliverTime;
    }

    public void setEstimateDeliverTime(String estimateDeliverTime) {
        this.estimateDeliverTime = estimateDeliverTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getThrough() {
        return through;
    }

    public void setThrough(String through) {
        this.through = through;
    }

    public int getDelivered() {
        return delivered;
    }

    public void setDelivered(int delivered) {
        this.delivered = delivered;
    }

    public int getReconciliation() {
        return reconciliation;
    }

    public void setReconciliation(int reconciliation) {
        this.reconciliation = reconciliation;
    }

    public String getBilling() {
        return billing;
    }

    public void setBilling(String billing) {
        this.billing = billing;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRepayment() {
        return repayment;
    }

    public void setRepayment(String repayment) {
        this.repayment = repayment;
    }

    public String getPriceCheck() {
        return priceCheck;
    }

    public void setPriceCheck(String priceCheck) {
        this.priceCheck = priceCheck;
    }

    public String getDealWith() {
        return dealWith;
    }

    public void setDealWith(String dealWith) {
        this.dealWith = dealWith;
    }

    public String getSnapshotUrl() {
        return snapshotUrl;
    }

    public void setSnapshotUrl(String snapshotUrl) {
        this.snapshotUrl = snapshotUrl;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getCancelType() {
        return cancelType;
    }

    public void setCancelType(String cancelType) {
        this.cancelType = cancelType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getYn() {
        return yn;
    }

    public void setYn(String yn) {
        this.yn = yn;
    }

    public String getOrderStatusView() {
        return orderStatusView;
    }

    public void setOrderStatusView(String orderStatusView) {
        this.orderStatusView = orderStatusView;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getOrganizeName() {
        return organizeName;
    }

    public void setOrganizeName(String organizeName) {
        this.organizeName = organizeName;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceInfo() {
        return invoiceInfo;
    }

    public void setInvoiceInfo(String invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }

    public String getLogisticsInfo() {
        return logisticsInfo;
    }

    public void setLogisticsInfo(String logisticsInfo) {
        this.logisticsInfo = logisticsInfo;
    }

    public boolean isIsWuliu() {
        return isWuliu;
    }

    public void setIsWuliu(boolean isWuliu) {
        this.isWuliu = isWuliu;
    }

    public String getDeliveredTime() {
        return deliveredTime;
    }

    public void setDeliveredTime(String deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    public boolean isOneself() {
        return oneself;
    }

    public void setOneself(boolean oneself) {
        this.oneself = oneself;
    }

    public String getAccountingUnitName() {
        return accountingUnitName;
    }

    public void setAccountingUnitName(String accountingUnitName) {
        this.accountingUnitName = accountingUnitName;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getRebutTime() {
        return rebutTime;
    }

    public void setRebutTime(String rebutTime) {
        this.rebutTime = rebutTime;
    }

    public int getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(int cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(String skuNum) {
        this.skuNum = skuNum;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOrderLogisticsNo() {
        return orderLogisticsNo;
    }

    public void setOrderLogisticsNo(String orderLogisticsNo) {
        this.orderLogisticsNo = orderLogisticsNo;
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

    public ArrayList<SubSkuDemandInfoBean> getSubSkuDemandInfo() {
        return subSkuDemandInfo;
    }

    public void setSubSkuDemandInfo(ArrayList<SubSkuDemandInfoBean> subSkuDemandInfo) {
        this.subSkuDemandInfo = subSkuDemandInfo;
    }
}

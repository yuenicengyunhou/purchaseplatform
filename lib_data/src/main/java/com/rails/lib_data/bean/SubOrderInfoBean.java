package com.rails.lib_data.bean;

import com.rails.lib_data.SubSkuDemandInfoBean;

import java.util.ArrayList;

public class SubOrderInfoBean {


    /**
     * id : 624323
     * key : 624323
     * platformId : 20
     * shopId :
     * organizeId :
     * buyerId : 1000090101
     * orderNo : 1210421104800004
     * parentNo : 0
     * thirdOrderNo : 1210421104800004
     * orderSplitType : 0
     * orderSource : 1
     * settleType :
     * shopType :
     * orderStatus : 23
     * paymentType :
     * commentStatus : 10
     * paymentPrice :
     * totalPrice :
     * freightPrice :
     * orderTime : 2021-04-21 10:48:46
     * paymentTime :
     * partDeliverTime :
     * deliverTime :
     * estimateDeliverTime :
     * finishTime :
     * cancelTime :
     * through :
     * delivered : 0
     * reconciliation : 0
     * billing :
     * refundStatus :
     * refundAmount :
     * repayment :
     * priceCheck :
     * dealWith :
     * snapshotUrl :
     * processInstanceId :
     * cancelReason :
     * cancelType :
     * remark :
     * created : 2021-04-21 10:46:08
     * modified :
     * yn :
     * orderStatusView : 部分发货
     * receiverName :
     * address :
     * mobile :
     * shopName :
     * buyerName :
     * organizeName :
     * invoiceType :
     * invoiceInfo :
     * logisticsInfo :
     * isWuliu : false
     * deliveredTime :
     * oneself : false
     * accountingUnitName :
     * applicationTime :
     * rebutTime :
     * subSkuDemandInfo : []
     * cancelStatus : 0
     * skuId :
     * itemId :
     * skuNum :
     * sellPrice : 0.00
     * marketPrice : 0.00
     * itemName :
     * orderLogisticsNo :
     * delayFlag :
     * delayReceiveTime :
     */

    private long id;
    private long key;
    private int platformId;
    private String shopId;
    private String organizeId;
    private int buyerId;
    private long orderNo;
    private int parentNo;
    private String thirdOrderNo;
    private int orderSplitType;
    private int orderSource;
    private String settleType;
    private String shopType;
    private int orderStatus;
    private String paymentType;
    private int commentStatus;
    private String paymentPrice;
    private String totalPrice;
    private String freightPrice;
    private String orderTime;
    private String paymentTime;
    private String partDeliverTime;
    private String deliverTime;
    private String estimateDeliverTime;
    private String finishTime;
    private String cancelTime;
    private String through;
    private int delivered;
    private int reconciliation;
    private String billing;
    private String refundStatus;
    private String refundAmount;
    private String repayment;
    private String priceCheck;
    private String dealWith;
    private String snapshotUrl;
    private String processInstanceId;
    private String cancelReason;
    private String cancelType;
    private String remark;
    private String created;
    private String modified;
    private String yn;
    private String orderStatusView;
    private String receiverName;
    private String address;
    private String mobile;
    private String shopName;
    private String buyerName;
    private String organizeName;
    private String invoiceType;
    private String invoiceInfo;
    private String logisticsInfo;
    private boolean isWuliu;
    private String deliveredTime;
    private boolean oneself;
    private String accountingUnitName;
    private String applicationTime;
    private String rebutTime;
    private int cancelStatus;
    private String skuId;
    private String itemId;
    private String skuNum;
    private String sellPrice;
    private String marketPrice;
    private String itemName;
    private String orderLogisticsNo;
    private String delayFlag;
    private String delayReceiveTime;
    private ArrayList<SubSkuDemandInfoBean> subSkuDemandInfo;

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

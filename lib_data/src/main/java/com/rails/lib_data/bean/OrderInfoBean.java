package com.rails.lib_data.bean;

import java.util.ArrayList;

public class OrderInfoBean {


    /**
     * id : 624323
     * key : 624323
     * platformId : 20
     * orderNo : 1210421104800004
     * orderNoStr : 1210421104800004
     * parentNo : 0
     * orderTime : 2021-04-21 10:48:46
     * deliverTime :
     * finishTime :
     * buyerId : 1000090101
     * buyerName : shcg01
     * organizeId : 114370
     * organizeName : 上海工务段
     * secondOrganizeId :
     * secondOrganizeName : 中国铁路上海局集团有限公司
     * settleType : 20
     * shopId : 202102240139
     * shopName : 峥嵘供应商001
     * totalPrice : 1665.00
     * paymentPrice : 1665.00
     * freightPrice : 0.00
     * orderSplitType : 0
     * orderStatus : 23
     * orderStatusView : 部分发货
     * commentStatus : 10
     * commentStatusView : 待评价
     * orderSource : 1
     * orderSourceView : 购物车订单
     * subOrderInfo : []
     * receiverName : 霖子
     * address : 河北省唐山市路北区嗯嗯嗯
     * mobile : 13854126542
     * accountingUnitId : 114370
     * accountingUnitName : 上海工务段
     * settleTypeView : 其他物资
     * demands :
     * isWuliu : false
     * cancelStatus : 0
     * processInstanceId : 9405171
     * cancelReason :
     * twoLevelOrgIdName : 中国铁路上海局集团有限公司
     * realName : ss
     * delayFlag : 0
     * delayReceiveTime :
     */

    private long id;
    private long key;
    private int platformId;
    private long orderNo;
    private String orderNoStr;
    private int parentNo;
    private String orderTime;
    private String deliverTime;
    private String finishTime;
    private int buyerId;
    private String buyerName;
    private int organizeId;
    private String organizeName;
    private String secondOrganizeId;
    private String secondOrganizeName;
    private int settleType;
    private long shopId;
    private String shopName;
    private String totalPrice;
    private String paymentPrice;
    private String freightPrice;
    private int orderSplitType;
    private int orderStatus;
    private String orderStatusView;
    private int commentStatus;
    private String commentStatusView;
    private int orderSource;
    private String orderSourceView;
    private String receiverName;
    private String address;
    private String mobile;
    private int accountingUnitId;
    private String accountingUnitName;
    private String settleTypeView;
    private String demands;
    private boolean isWuliu;
    private int cancelStatus;
    private String processInstanceId;
    private String cancelReason;
    private String twoLevelOrgIdName;
    private String realName;
    private int delayFlag;
    private String delayReceiveTime;
    private ArrayList<SubOrderInfoBean> subOrderInfo;

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

    public long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNoStr() {
        return orderNoStr;
    }

    public void setOrderNoStr(String orderNoStr) {
        this.orderNoStr = orderNoStr;
    }

    public int getParentNo() {
        return parentNo;
    }

    public void setParentNo(int parentNo) {
        this.parentNo = parentNo;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public int getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(int organizeId) {
        this.organizeId = organizeId;
    }

    public String getOrganizeName() {
        return organizeName;
    }

    public void setOrganizeName(String organizeName) {
        this.organizeName = organizeName;
    }

    public String getSecondOrganizeId() {
        return secondOrganizeId;
    }

    public void setSecondOrganizeId(String secondOrganizeId) {
        this.secondOrganizeId = secondOrganizeId;
    }

    public String getSecondOrganizeName() {
        return secondOrganizeName;
    }

    public void setSecondOrganizeName(String secondOrganizeName) {
        this.secondOrganizeName = secondOrganizeName;
    }

    public int getSettleType() {
        return settleType;
    }

    public void setSettleType(int settleType) {
        this.settleType = settleType;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public int getOrderSplitType() {
        return orderSplitType;
    }

    public void setOrderSplitType(int orderSplitType) {
        this.orderSplitType = orderSplitType;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusView() {
        return orderStatusView;
    }

    public void setOrderStatusView(String orderStatusView) {
        this.orderStatusView = orderStatusView;
    }

    public int getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(int commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getCommentStatusView() {
        return commentStatusView;
    }

    public void setCommentStatusView(String commentStatusView) {
        this.commentStatusView = commentStatusView;
    }

    public int getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(int orderSource) {
        this.orderSource = orderSource;
    }

    public String getOrderSourceView() {
        return orderSourceView;
    }

    public void setOrderSourceView(String orderSourceView) {
        this.orderSourceView = orderSourceView;
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

    public int getAccountingUnitId() {
        return accountingUnitId;
    }

    public void setAccountingUnitId(int accountingUnitId) {
        this.accountingUnitId = accountingUnitId;
    }

    public String getAccountingUnitName() {
        return accountingUnitName;
    }

    public void setAccountingUnitName(String accountingUnitName) {
        this.accountingUnitName = accountingUnitName;
    }

    public String getSettleTypeView() {
        return settleTypeView;
    }

    public void setSettleTypeView(String settleTypeView) {
        this.settleTypeView = settleTypeView;
    }

    public String getDemands() {
        return demands;
    }

    public void setDemands(String demands) {
        this.demands = demands;
    }

    public boolean isIsWuliu() {
        return isWuliu;
    }

    public void setIsWuliu(boolean isWuliu) {
        this.isWuliu = isWuliu;
    }

    public int getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(int cancelStatus) {
        this.cancelStatus = cancelStatus;
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

    public String getTwoLevelOrgIdName() {
        return twoLevelOrgIdName;
    }

    public void setTwoLevelOrgIdName(String twoLevelOrgIdName) {
        this.twoLevelOrgIdName = twoLevelOrgIdName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getDelayFlag() {
        return delayFlag;
    }

    public void setDelayFlag(int delayFlag) {
        this.delayFlag = delayFlag;
    }

    public String getDelayReceiveTime() {
        return delayReceiveTime;
    }

    public void setDelayReceiveTime(String delayReceiveTime) {
        this.delayReceiveTime = delayReceiveTime;
    }

    public ArrayList<SubOrderInfoBean> getSubOrderInfo() {
        return subOrderInfo;
    }

    public void setSubOrderInfo(ArrayList<SubOrderInfoBean> subOrderInfo) {
        this.subOrderInfo = subOrderInfo;
    }
}

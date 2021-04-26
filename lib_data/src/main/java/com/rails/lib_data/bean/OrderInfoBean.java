package com.rails.lib_data.bean;

import java.util.ArrayList;

public class OrderInfoBean {

    private long id;
    private long key;
    private int platformId;// 平台id ,
    private String orderNo;// 订单编号 ,
    private String orderNoStr;
    private long parentNo;//采购单编号 ,
    private String orderTime;//订单提交时间 ,
    private String deliverTime;//订单全部发货时间 ,
    private String finishTime;//订单完成(确认收货)时间 ,
    private int buyerId;//采购人id ,
    private String buyerName;//采购人名称 ,
    private int organizeId;//采购人所属组织机构id ,
    private String organizeName;//采购人所属组织机构名称 ,
    private String secondOrganizeId;// 采购人所属组织机构的二级机构的id ,
    private String secondOrganizeName;//采购人所属组织机构的二级机构的名称 ,
    private int settleType;//结算类型：10暂不选择 15运营物资 20其他物资 ,
    private long shopId;// 供应商id ,
    private String shopName;//: 供应商名称 ,
    private String totalPrice;//订单商品金额 ,
    private String paymentPrice;//订单实际支付金额 ,
    private String freightPrice;// 订单总运费 ,
    private int orderSplitType;//0:包含子单,1:叶子单（不包含子单） ,
    private int orderStatus;// 订单状态 ,
    private String orderStatusView;//: 订单状态：10待付款 15已付款 20待发货 30待收货 40已完成 60已驳回 70已取消 ,
    private int commentStatus;//10待评价 20部分评价 30已评价 ,
    private String commentStatusView;
    private int orderSource;//订单来源：1、购物车订单 2、需求单转订单
    private String orderSourceView;// 订单来源：1、购物车订单 2、需求单转订单 ,
    private String receiverName;// 收货人姓名 ,
    private String address;//收货人完整地址(带省市县镇) ,
    private String mobile;//: 收货人电话号码 ,
    private int accountingUnitId;//结算单位ID ,
    private String accountingUnitName;//结算单位名称 ,
    private String settleTypeView;//结算类型：10暂不选择 15运营物资 20其他物资 ,
    private String demands;
    private boolean isWuliu;//是否是物流平台发货的订单,默认设置不是物流平台 ,
    private int cancelStatus;//订单取消状态 ,
    private String processInstanceId;// 工作流实例id ,
    private String cancelReason;//订单取消原因 ,
    private String twoLevelOrgIdName;//采购单位（路局级）
    private String realName;//采购人真实姓名 ,
    private int delayFlag;//是否延迟收货 1:延迟收货 0：无延迟收货 ,
    private String delayReceiveTime;//延迟收货时间 ,
    private ArrayList<SubOrderInfoBean> subOrderInfo;//子订单详情列表 ,

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNoStr() {
        return orderNoStr;
    }

    public void setOrderNoStr(String orderNoStr) {
        this.orderNoStr = orderNoStr;
    }

    public long getParentNo() {
        return parentNo;
    }

    public void setParentNo(long parentNo) {
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

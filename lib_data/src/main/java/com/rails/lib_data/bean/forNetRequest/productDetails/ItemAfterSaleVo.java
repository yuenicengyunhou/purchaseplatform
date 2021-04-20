package com.rails.lib_data.bean.forNetRequest.productDetails;

public class ItemAfterSaleVo {
    //    private long id;
    private int platformId;
    private int itemId;
    private int sellerId;
    private int shopId;
    private int refundService;
    private int changeService;
    private int refundDuration;
    private int changeDuration;
    private int repaireDuration;
    //    private String afterSalesExplain; // TODO: 2021/04/20 类型存疑
//    private String specialDesc;
    private int specialShow;


    // ===========================================================================================


//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getRefundService() {
        return refundService;
    }

    public void setRefundService(int refundService) {
        this.refundService = refundService;
    }

    public int getChangeService() {
        return changeService;
    }

    public void setChangeService(int changeService) {
        this.changeService = changeService;
    }

    public int getRefundDuration() {
        return refundDuration;
    }

    public void setRefundDuration(int refundDuration) {
        this.refundDuration = refundDuration;
    }

    public int getChangeDuration() {
        return changeDuration;
    }

    public void setChangeDuration(int changeDuration) {
        this.changeDuration = changeDuration;
    }

    public int getRepaireDuration() {
        return repaireDuration;
    }

    public void setRepaireDuration(int repaireDuration) {
        this.repaireDuration = repaireDuration;
    }

//    public Object getAfterSalesExplain() {
//        return afterSalesExplain;
//    }
//
//    public void setAfterSalesExplain(Object afterSalesExplain) {
//        this.afterSalesExplain = afterSalesExplain;
//    }
//
//    public Object getSpecialDesc() {
//        return specialDesc;
//    }
//
//    public void setSpecialDesc(Object specialDesc) {
//        this.specialDesc = specialDesc;
//    }

    public int getSpecialShow() {
        return specialShow;
    }

    public void setSpecialShow(int specialShow) {
        this.specialShow = specialShow;
    }
}

package com.rails.lib_data.bean;

/**
 * 采购单内项目（物品） Bean
 */
public class OrderItemBean {
    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 订单生成时间
     */
    private String generateTime;
    /**
     * 订单状态
     */
    private String orderState;
    /**
     * 商品图片
     */
    private String pictureUrl;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 商品价格
     */
    private String itemPrice;
    /**
     * 商品型号
     */
    private String itemType;
    /**
     * 商品数量
     */
    private String itemCount;
    /**
     * 商品编码
     */
    private String itemNum;


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

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }
}

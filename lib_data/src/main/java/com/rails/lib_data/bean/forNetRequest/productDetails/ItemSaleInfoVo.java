package com.rails.lib_data.bean.forNetRequest.productDetails;

public class ItemSaleInfoVo {
    private int advancePercent;
    private int deliveryCycle;
    private String id;
    private int initialMount;
    private String itemId;
    private int platformId;
    private int salePriceType;
    private int saleType;
    private String sellerId;
    private long shopId;


    // =======================================================================================


    public int getAdvancePercent() {
        return advancePercent;
    }

    public void setAdvancePercent(int advancePercent) {
        this.advancePercent = advancePercent;
    }

    public int getDeliveryCycle() {
        return deliveryCycle;
    }

    public void setDeliveryCycle(int deliveryCycle) {
        this.deliveryCycle = deliveryCycle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getInitialMount() {
        return initialMount;
    }

    public void setInitialMount(int initialMount) {
        this.initialMount = initialMount;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public int getSalePriceType() {
        return salePriceType;
    }

    public void setSalePriceType(int salePriceType) {
        this.salePriceType = salePriceType;
    }

    public int getSaleType() {
        return saleType;
    }

    public void setSaleType(int saleType) {
        this.saleType = saleType;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }
}

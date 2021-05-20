package com.rails.lib_data.bean;

public class SkuStockBean {
    private String skuId;
    private String skuStock;
    private String saleState;
    private String cause;


    // ==============================================================================


    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuStock() {
        return skuStock;
    }

    public void setSkuStock(String skuStock) {
        this.skuStock = skuStock;
    }

    public String getSaleState() {
        return saleState;
    }

    public void setSaleState(String saleState) {
        this.saleState = saleState;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}

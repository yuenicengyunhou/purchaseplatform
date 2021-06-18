package com.rails.lib_data.bean.forNetRequest.productDetails;

import com.alibaba.fastjson.JSONObject;
import com.rails.lib_data.bean.DeliveryBean;
import com.rails.lib_data.bean.SkuStockBean;

import java.util.ArrayList;

public class ProductDetailsStep2Bean {
    private DeliveryBean deliveryBean;
    private boolean visitTrack;
    private ArrayList<ProductPriceBean> productPriceBeans;
    private JSONObject collect;
    private HotSaleBean hotSaleBean;
    private ArrayList<SkuStockBean> skuStockBeans;

    public DeliveryBean getDeliveryBean() {
        return deliveryBean;
    }

    public void setDeliveryBean(DeliveryBean deliveryBean) {
        this.deliveryBean = deliveryBean;
    }

    public boolean isVisitTrack() {
        return visitTrack;
    }

    public void setVisitTrack(boolean visitTrack) {
        this.visitTrack = visitTrack;
    }

    public ArrayList<ProductPriceBean> getProductPriceBeans() {
        return productPriceBeans;
    }

    public void setProductPriceBeans(ArrayList<ProductPriceBean> productPriceBeans) {
        this.productPriceBeans = productPriceBeans;
    }

    public JSONObject getCollect() {
        return collect;
    }

    public void setCollect(JSONObject collect) {
        this.collect = collect;
    }

    public HotSaleBean getHotSaleBean() {
        return hotSaleBean;
    }

    public void setHotSaleBean(HotSaleBean hotSaleBean) {
        this.hotSaleBean = hotSaleBean;
    }

    public ArrayList<SkuStockBean> getSkuStockBeans() {
        return skuStockBeans;
    }

    public void setSkuStockBeans(ArrayList<SkuStockBean> skuStockBeans) {
        this.skuStockBeans = skuStockBeans;
    }
}

package com.rails.lib_data.bean.forNetRequest.productDetails;

import com.alibaba.fastjson.JSONObject;
import com.rails.lib_data.bean.SkuStockBean;

import java.util.ArrayList;

public class ProductDetailsStep3Bean {
    private boolean visitTrack;
    private ArrayList<ProductPriceBean> productPriceBeans;
    private JSONObject collect;
    private ArrayList<SkuStockBean> skuStockBeans;

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

    public ArrayList<SkuStockBean> getSkuStockBeans() {
        return skuStockBeans;
    }

    public void setSkuStockBeans(ArrayList<SkuStockBean> skuStockBeans) {
        this.skuStockBeans = skuStockBeans;
    }
}

package com.rails.lib_data.bean.shop;

import java.util.List;

public class ResultListBean {

    private int itemId;
    private String shopName;
    /**
     * skuName : 得力30207封箱胶带48mm*150y*50um(6卷/筒)
     * itemId : 1001135
     * upTime : 1583462661000
     * pictureUrl : //xsky.rails.cn/mall/d3f41ca3059f5d6513c1eec21bcaf98920200306104307917.jpg
     * brandId : null
     * sellPrice : 53.6
     * shopId : 202003030111
     * skuId : 12495
     * contrastFlag : null
     * cid : 1000887
     */

    private List<ItemSkuBean> item_sku;

    public int getItemId() {
        return itemId;
    }

    public String getShopName() {
        return shopName;
    }

    public List<ItemSkuBean> getItem_sku() {
        return item_sku;
    }
}

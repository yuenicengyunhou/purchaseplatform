package com.rails.lib_data.bean.shop;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ItemListBean {
    private int pageSize;
    private int page;
    private int count;
    private Object customAggMap;
    private Object aggResultMap;
    /**
     * itemId : 1001135
     * item_sku : [{"skuName":"得力30207封箱胶带48mm*150y*50um(6卷/筒)","itemId":1001135,"upTime":1583462661000,"pictureUrl":"//xsky.rails.cn/mall/d3f41ca3059f5d6513c1eec21bcaf98920200306104307917.jpg","brandId":null,"sellPrice":53.6,"shopId":202003030111,"skuId":12495,"contrastFlag":null,"cid":1000887}]
     * shopName : 1111
     */

    private ArrayList<ResultListBean> resultList;

    public int getPageSize() {
        return pageSize;
    }

    public int getPage() {
        return page;
    }

    public int getCount() {
        return count;
    }

    public Object getCustomAggMap() {
        return customAggMap;
    }

    public Object getAggResultMap() {
        return aggResultMap;
    }

    public ArrayList<ResultListBean> getResultList() {
        return resultList;
    }
}

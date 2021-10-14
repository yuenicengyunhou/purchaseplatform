package com.rails.lib_data.model;

import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.StatisticService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;

/**
 * 流量统计model
 *
 * @author： sk_comic@163.com
 * @date: 2021/8/13
 */
public class StatisticModel {


    /**
     * 统计首页，商品详情页，搜索结果页面，下采购单页面
     *
     * @param platformId   平台id 默认20
     * @param visitorType  参观者类型      0：匿名用户   1：登陆用户
     * @param cookieFinger cookie指纹，就是当前时间戳
     * @param materialType 物质类型  0：通用   1：专用（商品详情用）
     * @param sourceType   来源信息 0：pc    1：app
     * @param itemShopId   店铺ID
     * @param skuId        skuID
     */
    public void getVisitors(
            String platformId,
            String visitorType,
            String cookieFinger,
            String materialType,
            String sourceType,
            String itemShopId,
            String skuId,
            HttpRxObserver httpRxObserver) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId); // 固定20
        map.put("visitorType", visitorType); // 登录为1 未登录为0
        map.put("cookieFinger", cookieFinger); // 时间戳
        map.put("materialType", materialType); // 是否为专用物资
        map.put("sourceType", sourceType); // 来源信息app
        if (itemShopId != null)
            map.put("itemShopId", itemShopId); // 商详页需要此字段
        if (skuId != null)
            map.put("skuId", skuId); // 商详页需要此字段
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(StatisticService.class).getVisitors(map))
                .subscribe(httpRxObserver);
    }
}

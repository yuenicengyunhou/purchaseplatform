package com.rails.lib_data.model;

import android.text.TextUtils;

import com.rails.lib_data.bean.address.AddressArea;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.DistributionAreaService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 配送区域
 */
public class DistributionAreaModel {


    /**
     * 获取各个级别配送区域Code
     * <p>
     * 获取provinceCode   (parentCode = 0)
     * <p>
     * 获取cityCode       (parentCode = provinceCode)
     * <p>
     * 获取countryCode    (parentCode = cityCode)
     *
     * @param platformId
     * @param parentCode
     * @param httpRxObserver
     */
    public void getDistributionArea(String platformId, String parentCode, HttpRxObserver<ArrayList<AddressArea>> httpRxObserver) {
        if (null == platformId || TextUtils.isEmpty(platformId)) {
            platformId = "20";
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
        map.put("parentCode", parentCode);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(DistributionAreaService.class)
                .getDistributionArea(map))
                .subscribe(httpRxObserver);
    }


}

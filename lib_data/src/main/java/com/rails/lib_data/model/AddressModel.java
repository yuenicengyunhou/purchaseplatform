package com.rails.lib_data.model;


import com.rails.lib_data.bean.address.AddressArea;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.AddressService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;
import java.util.List;

public class AddressModel {
    private final int pageSize = 200;

    /**
     * 获取维护地址列表
     */
    public void queryAddressList(long pageNum, String type, String params, HttpRxObserver httpRxObserver) {
//        if (null == platformId || TextUtils.isEmpty(platformId)) {
//            platformId = "20";
//        }
        HashMap<String, Object> map = new HashMap<>();
//        map.put("platformId", platformId);
//        map.put("accountId", accountId);
//        map.put("accountType", accountType);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        switch (type) {
            case "收货人":
                map.put("receiverName", params);
                break;
            case "手机号码":
                map.put("mobile", params);
                break;
            case "详细地址":
                map.put("fullAddress", params);
                break;
        }
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class).getAddressList(map))
                .subscribe(httpRxObserver);
    }


    /**
     * 根据addressId获取地址详情
     */
    public void getAddressInfo(String addressId, HttpRxObserver httpRxObserver) {
//        if (null == platformId || TextUtils.isEmpty(platformId)) {
//            platformId = "20";
//        }
        HashMap<String, Object> map = new HashMap<>();
//        map.put("platformId", platformId);
//        map.put("accountId", accountId);
        map.put("buyerAddressId", addressId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class).getAddressInfo(map))
                .subscribe(httpRxObserver);
    }


    /**
     * 添加地址
     */
    public void addAddress(String requestStr, HttpRxObserver httpRxObserver) {
//        if (null == platformId || TextUtils.isEmpty(platformId)) {
//            platformId = "20";
//        }
        HashMap<String, Object> map = new HashMap<>();
//        map.put("platformId", platformId);
//        map.put("accountId", accountId);
        map.put("requestStr", requestStr);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class).insertAddress(map))
                .subscribe(httpRxObserver);
    }

    /**
     * 地址编辑
     */
    public void editAddress(String addressId, String json, HttpRxObserver httpRxObserver) {
//        if (null == platformId || TextUtils.isEmpty(platformId)) {
//            platformId = "20";
//        }
        HashMap<String, Object> map = new HashMap<>();
//        map.put("platformId", platformId);
//        map.put("accountId", accountId);
        map.put("requestStr", json);
        map.put("buyerAddressId", addressId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class).updateAddress(map))
                .subscribe(httpRxObserver);
    }

    /**
     * 删除地址
     */
    public void deleteAddress(String buyerAddressId, HttpRxObserver httpRxObserver) {
//        if (null == platformId || TextUtils.isEmpty(platformId)) {
//            platformId = "20";
//        }
        HashMap<String, Object> map = new HashMap<>();
//        map.put("platformId", platformId);
//        map.put("accountId", accountId);
        map.put("buyerAddressId", buyerAddressId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class).deleteAddress(map))
                .subscribe(httpRxObserver);
    }

    /**
     * 收货地址--重置默认地址  type=0 ：收货地址     type=1:发票地址
     */
    public void updateDefaultReceiveAddress(long platformId, String accountId, long id, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
        map.put("accountId", accountId);
        map.put("id", id);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class).updateDefaultAddress(map))
                .subscribe(httpRxObserver);
    }

    /**
     * 发票地址--重置默认地址
     */
    public void updateDefaultInvoiceAddress(long platformId, String accountId, long id, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
        map.put("accountId", accountId);
        map.put("id", id);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class).updateDefaultInvoiceAddress(map))
                .subscribe(httpRxObserver);
    }


    /**
     * 获取地区
     */
    @Deprecated
    public void getArea(String parentCode, HttpRxObserver httpRxObserver) {
//        if (null == platformId || TextUtils.isEmpty(platformId)) {
//            platformId = "20";
//        }
        HashMap<String, Object> map = new HashMap<>();
//        map.put("platformId", platformId);
        map.put("parentCode", parentCode);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class).getAddressCode(map))
                .subscribe(httpRxObserver);
    }

    /**
     * 地区信息缓存
     */
    public void saveAreaInfo(List<AddressArea> areas) {

    }


    /**
     * 获取专属地址列表
     *
     * @param platformId
     * @param addressType    1：收货地址   2：收发票地址
     * @param httpRxObserver
     */
    public void getAddress(String platformId, String addressType, String userId, String userType, String type,String keyword, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
//        params.put("platformId", platformId);
        params.put("addressType", addressType);
        switch (type) {
            case "收货人":
                params.put("receiverName", keyword);
                break;
            case "手机号码":
                params.put("mobile", keyword);
                break;
            case "详细地址":
                params.put("fullAddress", keyword);
                break;
        }
//        params.put("accountId",userId);
//        params.put("accountType",userType);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class)
                .getAddress(params))
                .subscribe(httpRxObserver);
    }
}

package com.rails.lib_data.model;

import android.text.TextUtils;

import com.rails.lib_data.AddressArea;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.AddressService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;
import java.util.List;

import retrofit2.http.Query;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

public class AddressModel {
    private final int pageSize = 10;

    /**
     * 获取维护地址列表
     */
    public void queryAddressList(String platformId, String accountId, int accountType, long pageNum, HttpRxObserver httpRxObserver) {
        if (null == platformId|| TextUtils.isEmpty(platformId)) {
            platformId = "20";
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
        map.put("accountId", accountId);
        map.put("accountType", accountType);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class).getAddressList(map))
                .subscribe(httpRxObserver);
    }


    /**
     * 添加地址
     */
    public void addAddress(String platformId, String accountId, String requestStr, HttpRxObserver httpRxObserver) {
        if (null == platformId|| TextUtils.isEmpty(platformId)) {
            platformId = "20";
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
        map.put("accountId", accountId);
        map.put("requestStr", requestStr);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class).insertAddress(map))
                .subscribe(httpRxObserver);
    }

    /**
     * 删除地址
     */
    public void deleteAddress(String platformId, String accountId, long buyerAddressId, HttpRxObserver httpRxObserver) {
        if (null == platformId|| TextUtils.isEmpty(platformId)) {
            platformId = "20";
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
        map.put("accountId", accountId);
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
     * 地址编辑
     */
    public void editAddress(String platformId, String accountId, long addressId, String json, HttpRxObserver httpRxObserver) {
        if (null == platformId|| TextUtils.isEmpty(platformId)) {
            platformId = "20";
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
        map.put("accountId", accountId);
        map.put("requestStr", json);
        map.put("buyerAddressId", addressId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class).updateAddress(map))
                .subscribe(httpRxObserver);
    }


    /**
     * 获取地区
     */
    public void getArea(String platformId, String parentCode, HttpRxObserver httpRxObserver) {
        if (null == platformId|| TextUtils.isEmpty(platformId)) {
            platformId = "20";
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
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
    public void getAddress(String platformId, String addressType, String userId, String userType, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
//        params.put("platformId", platformId);
        params.put("addressType", addressType);
//        params.put("accountId",userId);
//        params.put("accountType",userType);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class)
                .getAddress(params))
                .subscribe(httpRxObserver);
    }
}

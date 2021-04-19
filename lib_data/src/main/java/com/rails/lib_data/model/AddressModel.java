package com.rails.lib_data.model;

import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.AddressService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

public class AddressModel {
    /**
     * 获取维护地址列表
     */
    public void queryAddressList(long platformId, long accountId, int accountType, long pageNum, int pageSize, HttpRxObserver httpRxObserver) {
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class).getAddressList(platformId,accountId,accountType,pageNum,pageSize))
                .subscribe(httpRxObserver);
    }


    /**
     * 添加地址
     */
    public void addAddress(long platformId, long accountId, String requestStr,HttpRxObserver httpRxObserver) {
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class).insertAddress(platformId,accountId,requestStr))
                .subscribe(httpRxObserver);
    }

    /**
     * 删除地址
     */
    public void deleteAddress(long platformId, long accountId, long buyerAddressId, HttpRxObserver httpRxObserver) {
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class).deleteAddress(platformId,accountId,buyerAddressId))
                .subscribe(httpRxObserver);
    }

    /**
     * 收货地址--重置默认地址  type=0 ：收货地址     type=1:发票地址
     */
    public void updateDefaultReceiveAddress(long platformId, long accountId, long id, HttpRxObserver httpRxObserver) {
            HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                    .create(AddressService.class).updateDefaultAddress(platformId, accountId, id))
                    .subscribe(httpRxObserver);
    }

    /**
     * 发票地址--重置默认地址
     */
    public void updateDefaultInvoiceAddress(long platformId, long accountId, long id, HttpRxObserver httpRxObserver) {
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class).updateDefaultInvoiceAddress(platformId,accountId,id))
                .subscribe(httpRxObserver);
    }

    /**
     * 地址编辑
     */
    public void editAddress(long platformId, long accountId, long addressId, String json, HttpRxObserver httpRxObserver) {
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class).updateAddress(platformId,accountId,json,addressId))
                .subscribe(httpRxObserver);
    }
}

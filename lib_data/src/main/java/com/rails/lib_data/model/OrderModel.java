package com.rails.lib_data.model;

import android.text.TextUtils;
import android.widget.TextView;

import com.rails.lib_data.bean.OrderFilterBean;
import com.rails.lib_data.bean.OrderStatusBean;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.OrderService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.HashMap;
import java.util.List;

public class OrderModel {

    public void getRecOrder(HttpRxObserver httpRxObserver) {

        HashMap<String, String> params = new HashMap<>();
        params.put("platformId", "20");
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class).getOrder(params))
                .subscribe(httpRxObserver);
    }

    public void getPurchasePageList(long platformId, String accountId, int queryType, int accountType, String squence, String content, int page, OrderFilterBean filterBean, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
        map.put("accountId", accountId);
        map.put("queryType", queryType);
        map.put("accountType", accountType);
        map.put(squence, content);
        map.put("pageSize", 10);
        map.put("pageNum", page);
        mergeMap(filterBean, map);
//        map.put("buyerName", buyerName);
//        map.put("shopName", shopName);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class).purchasePageList(map))
                .subscribe(httpRxObserver);
    }

    private void mergeMap(OrderFilterBean filterBean, HashMap<String, Object> map) {
        if (null==filterBean) return;
        String lowPrice = filterBean.getLowPrice();
        String highPrice = filterBean.getHighPrice();
        String startDate = filterBean.getStartDate();
        String endDate = filterBean.getEndDate();
        List<OrderStatusBean> statusBeans = filterBean.getStatusBeans();
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < statusBeans.size(); i++) {
//            OrderStatusBean statusBean = statusBeans.get(i);
//            if (statusBean.isChecked()) {
//                String statusCode = statusBean.getStatusCode();
//                builder.append(statusCode).append(",");
//            }
//        }
        if (null != startDate && !TextUtils.isEmpty(startDate)) {
            map.put("orderTimeBegin", startDate);
        }
        if (null != endDate && !TextUtils.isEmpty(endDate)) {
            map.put("orderTimeEnd", endDate);
        }
        if (null != lowPrice && !TextUtils.isEmpty(lowPrice)) {
            map.put("paymentPriceBegin", lowPrice);
        }
        if (null != highPrice && !TextUtils.isEmpty(highPrice)) {
            map.put("paymentPriceEnd", highPrice);
        }
        if (null != statusBeans) {
            String code = "";
            for (int i = 0; i < statusBeans.size(); i++) {
                OrderStatusBean statusBean = statusBeans.get(i);
                if (statusBean.isChecked()) {
                    code = statusBean.getStatusCode();
                    break;
                }
            }
            map.put("orderStatus", code);
        }
    }

    public void getBuyerNames(String like, String findType, String organizeId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("nameLike", like);
        map.put("findType", findType);
        map.put("organizeId", organizeId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class).getBuyerList(map))
                .subscribe(httpRxObserver);

    }

    public void getSupplierNames(String supplierName, String accountId, String accountType, String organizeName, String organizeId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("supplierName", supplierName);
        map.put("accountId", accountId);
        map.put("accountType", accountType);
        map.put("organizeName", organizeName);
        map.put("organizeId", organizeId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class).getSupplierNames(map))
                .subscribe(httpRxObserver);

    }


}

package com.rails.lib_data.model;

import android.text.TextUtils;
import android.util.Log;

import com.rails.lib_data.bean.OrderFilterBean;
import com.rails.lib_data.bean.OrderStatusBean;
import com.rails.lib_data.bean.orderdetails.DeliveredFile;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.OrderService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class OrderModel {

    private int defaltPageSize = 20;

    public void getRecOrder(HttpRxObserver httpRxObserver) {

        HashMap<String, String> params = new HashMap<>();
        params.put("platformId", "20");
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class).getOrder(params))
                .subscribe(httpRxObserver);
    }

    public void getPurchasePageList(int queryType, String squence, String content, int page, OrderFilterBean filterBean, HttpRxObserver httpRxObserver) {
//        if (null == platformId) {
//            platformId = "20";
//        }
        HashMap<String, Object> map = new HashMap<>();
//        map.put("platformId", platformId);
//        map.put("accountId", accountId);
        map.put("queryType", queryType);
//        map.put("accountType", accountType);
        if (!TextUtils.isEmpty(content)) {
            map.put(squence, content);
        }
        map.put("pageSize", defaltPageSize);
        map.put("pageNum", page);
        mergeMap(filterBean, map);
//        map.put("buyerName", buyerName);
//        map.put("shopName", shopName);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class).purchasePageList(map))
                .subscribe(httpRxObserver);
    }

//    public OrderFilterBean getFilterBean() {
//        OrderFilterBean filterBean = new OrderFilterBean();
//        Type type = new TypeToken<ArrayList<OrderStatusBean>>() {
//        }.getType();
//        beans = JsonUtil.parseJson( "orderStatus.json", type);
//    }

    private void mergeMap(OrderFilterBean filterBean, HashMap<String, Object> map) {
        if (null == filterBean) return;
//        String lowPrice = filterBean.getLowPrice();
//        String highPrice = filterBean.getHighPrice();
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
//        if (null != lowPrice && !TextUtils.isEmpty(lowPrice)) {
//            map.put("paymentPriceBegin", lowPrice);
//        }
//        if (null != highPrice && !TextUtils.isEmpty(highPrice)) {
//            map.put("paymentPriceEnd", highPrice);
//        }
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
//        map.put("accountId", accountId);
//        map.put("accountType", accountType);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class).getBuyerList(map))
                .subscribe(httpRxObserver);

    }

    public void getSupplierNames(String supplierName, String organizeName, String organizeId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("supplierName", supplierName);
//        map.put("accountId", accountId);
//        map.put("accountType", accountType);
        map.put("organizeName", organizeName);
        map.put("organizeId", organizeId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class).getSupplierNames(map))
                .subscribe(httpRxObserver);

    }

    public void getSkuNameList(String skuName, String organizeName, String organizeId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("skuName", skuName);
//        map.put("accountId", accountId);
//        map.put("accountType", accountType);
        map.put("organizeName", organizeName);
        map.put("organizeId", organizeId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class).querySkuIdListByName(map))
                .subscribe(httpRxObserver);

    }

    public void getBrandList(String keyWord, String organizeName, String organizeId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("keyWord", keyWord);
//        map.put("accountId", accountId);
//        map.put("accountType", accountType);
        map.put("organizeName", organizeName);
        map.put("organizeId", organizeId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class).queryBrandList(map))
                .subscribe(httpRxObserver);

    }

    /**
     * 获取妥投文件
     */
    public void getDeliverFiles(String platformId, String orderNo, HttpRxObserver httpRxObserver) {
        if (null == platformId) {
            platformId = "20";
        }
//        orderNo = "1200806112300005";
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
        map.put("orderNo", orderNo);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class).getDeliveredFiles(map))
                .subscribe(httpRxObserver);
    }

    /**
     * 处理妥投文件数据，按，分隔链接和文件名
     */
    public ArrayList<DeliveredFile> getFileList(String fileLinks, String fileNames) {

        ArrayList<DeliveredFile> deliveredFiles = new ArrayList<>();
        if (TextUtils.isEmpty(fileLinks)||TextUtils.isEmpty(fileNames)) {
            return deliveredFiles;
        }
        List<String> linkList = Arrays.asList(fileLinks.split(","));
        List<String> nameList = Arrays.asList(fileNames.split(","));
        String name;
        for (int i = 0; i < linkList.size(); i++) {
            String s = linkList.get(i);
            if (i < nameList.size()) {
                name = nameList.get(i);
            } else {
                name = "";
            }
            DeliveredFile deliveredFile = new DeliveredFile();
            deliveredFile.setUrl(s);
            deliveredFile.setFileName(name);
            deliveredFiles.add(deliveredFile);
        }
        return deliveredFiles;
    }


}

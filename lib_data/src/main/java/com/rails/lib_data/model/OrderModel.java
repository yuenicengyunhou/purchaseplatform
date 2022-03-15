package com.rails.lib_data.model;

import android.text.TextUtils;

import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.rails.lib_data.bean.OrderFilterBean;
import com.rails.lib_data.bean.OrderStatusBean;
import com.rails.lib_data.bean.orderdetails.DeliveredFile;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.OrderService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class OrderModel {

    private final int defaltPageSize = 20;

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
        if (null == filterBean) {
//            map.put("materialType", "0");//默认通用物资
            return;
        }
        String startDate = filterBean.getStartDate();
        String endDate = filterBean.getEndDate();
//        String goodsType = filterBean.getGoodsType();
        List<OrderStatusBean> statusBeans = filterBean.getStatusBeans();
        if (null != startDate && !TextUtils.isEmpty(startDate)) {
            map.put("orderTimeBegin", startDate);
        }
        if (null != endDate && !TextUtils.isEmpty(endDate)) {
            map.put("orderTimeEnd", endDate);
        }
//        if (null != goodsType) {
//            map.put("materialType", goodsType);
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
    public ArrayList<DeliveredFile> getFileList(String fileLinks, String orderNo) {
        String mSinglePath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "GTSC"
                + File.separator;
        ArrayList<DeliveredFile> deliveredFiles = new ArrayList<>();
        if (TextUtils.isEmpty(fileLinks)) {
            return deliveredFiles;
        }
        List<String> linkList = Arrays.asList(fileLinks.split(","));
        for (int i = 0; i < linkList.size(); i++) {
            String s = linkList.get(i);
            DeliveredFile deliveredFile = new DeliveredFile();
            String extension = s.substring(s.lastIndexOf("."));
            String filePath = mSinglePath + orderNo + "_" + (i + 1) + extension;
            deliveredFile.setProgress(0);
            deliveredFile.setDownloadState(checkFileDownloadState(filePath));//判断文件是否已经下载
            if (s.contains("http")) {
                deliveredFile.setUrl(s);
            } else {
                deliveredFile.setUrl(MessageFormat.format("https:{0}", s));
            }
            deliveredFile.setFileName(MessageFormat.format("妥投证明{0}", i + 1));
            deliveredFiles.add(deliveredFile);
        }
        return deliveredFiles;
    }

    public int checkFileDownloadState(String filePath) {
        File file = new File(filePath);
        boolean exists = file.exists();
        return exists ? 2 : 0;
    }


}

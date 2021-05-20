package com.rails.lib_data.model;


import com.rails.lib_data.bean.SkuStockBean;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.ProductService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 请求商品详情
 */
public class ProductDetailsModel {

    /**
     * 请求商品详情
     *
     * @param platformId
     * @param itemId
     * @param companyId
     * @param httpRxObserver
     */

    public void getProductDetails(String platformId, String itemId, String companyId, HttpRxObserver httpRxObserver) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("platformId", platformId);
        params.put("itemId", itemId); // 使用 1014721 调试规格选择
        params.put("areaId", -1);
//        params.put("companyId", companyId);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .getProductDetails(params))
                .subscribe(httpRxObserver);
    }

    /**
     * 请求商品价格
     *
     * @param platformId
     * @param skuId
     * @param httpRxObserver
     */
    public void getProductPrice(String platformId, String skuId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("platformId", platformId);
        params.put("skuIds", skuId);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .getProductPrice(params))
                .subscribe(httpRxObserver);
    }

    /**
     * 请求店铺推荐（热销商品）
     *
     * @param platformId
     * @param keyword
     * @param cid
     * @param pageNum
     * @param httpRxObserver
     */
    public void getHotSale(String platformId, String keyword, String cid, int pageNum, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        // TODO: 2021/4/22 传入数据
        params.put("platformId", platformId);
//        params.put("keyword", "11");
        params.put("businessType", 1);
        params.put("pageNum", pageNum);
        params.put("cid", cid); // 1001047
//        params.put("shopId", 202003030108L); //202003030108L

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .getHotSale(params))
                .subscribe(httpRxObserver);
    }

    /**
     * 获取 商品收藏状态
     *
     * @param skuId
     * @param httpRxObserver
     */
    public void getUserCollect(String skuId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("skuIds", skuId);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .getUserCollect(params))
                .subscribe(httpRxObserver);
    }

    /**
     * 获取购物车内商品数量
     *
     * @param platformId
     * @param organizeId
     * @param accountId
     * @param httpRxObserver
     */
    public void getCartCount(String platformId, String organizeId, String accountId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
//        params.put("platformId", platformId);
//        params.put("organizeId", organizeId);
//        params.put("accountId", accountId);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .getCartCount(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 获取店铺详情邮费
     * https://shop.rails.cn/proxy/platform/mall/delivery/queryFreightAmountShopId?shopId=202003030108
     *
     * @param shopId
     * @param httpRxObserver
     */
    public void getProductDelivery(String shopId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("shopId", shopId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .getProductDelivery(params))
                .subscribe(httpRxObserver);
    }

    /**
     * 添加浏览记录
     *
     * @param categoryId
     * @param skuId
     * @param httpRxObserver
     */
    public void addSkuVisitTrack(String categoryId, String skuId, HttpRxObserver<Boolean> httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("skuId", skuId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .addSkuVisitTrack(params))
                .subscribe(httpRxObserver);
    }


    public void querySkuSaleStocks(String supplierId, String provinceId, String cityId, String countryId,
                                   String address, String skuNum, String skuId, HttpRxObserver<ArrayList<SkuStockBean>> httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("supplierId", supplierId);
        params.put("provinceId", provinceId);
        params.put("cityId", cityId);
        params.put("countyId", countryId);
        params.put("address", address);
        params.put("skuNum", skuNum);
//        params.put("skuNumList", );
//        params.put("shopId", );
        params.put("skuId", skuId);
//        params.put("skuIdList", );

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .querySkuSaleStocks(params))
                .subscribe(httpRxObserver);

    }
}

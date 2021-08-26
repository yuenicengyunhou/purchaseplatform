package com.rails.lib_data.model;

import com.alibaba.fastjson.JSONObject;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.DeliveryBean;
import com.rails.lib_data.bean.SkuStockBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.HotSaleBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsStep1Bean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsStep2Bean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsStep3Bean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductPriceBean;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.AddressService;
import com.rails.lib_data.service.ProductService;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.functions.Function6;
import io.reactivex.schedulers.Schedulers;

public class ProductDetailsModel2 {


    /**
     * 请求商品详情
     */
    public Observable<HttpResult<ProductDetailsBean>> getProductDetails(
            String platformId,
            String itemId) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("platformId", platformId);
        params.put("itemId", itemId);
        params.put("areaId", -1);

        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class).getProductDetails(params));
    }


    /**
     * 获取购物车内商品数量
     *
     * @param platformId
     * @param organizeId
     * @param accountId
     */
    public Observable<HttpResult<String>> getCartCount(String platformId, String organizeId, String accountId) {
        HashMap<String, Object> params = new HashMap<>();
//        params.put("platformId", platformId);
//        params.put("organizeId", organizeId);
//        params.put("accountId", accountId);

        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class).getCartCount(params));
    }


    /**
     * 获取专属地址列表
     *
     * @param platformId
     * @param addressType 1：收货地址   2：收发票地址
     */
    public Observable<HttpResult<ArrayList<AddressBean>>> getAddress(
            String platformId,
            String addressType) {

        HashMap<String, Object> params = new HashMap<>();
//        params.put("platformId", platformId);
        params.put("addressType", addressType);
//        params.put("accountId",userId);
//        params.put("accountType",userType);

        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class).getAddress(params));
    }


    /**
     * 请求商品信息和地址列表
     *
     * @param platformId
     * @param itemId
     * @param addressType
     * @param httpRxObserver
     */
    public void getProductDetailsStep1(String platformId,
                                       String itemId,
                                       String addressType,
                                       HttpRxObserver httpRxObserver) {

        Observable productDetails
                = getProductDetails(platformId, itemId).subscribeOn(Schedulers.io());
        Observable addressList
                = getAddress(platformId, addressType).subscribeOn(Schedulers.io());
        Observable cartCount
                = getCartCount(platformId, "", "");

        Observable.zip(
                productDetails, addressList, cartCount,
                new Function3<
                        ProductDetailsBean,
                        ArrayList<AddressBean>,
                        String,
                        ProductDetailsStep1Bean>() {

                    @NotNull
                    @Override
                    public ProductDetailsStep1Bean apply(
                            @NotNull ProductDetailsBean productDetailsBean,
                            @NotNull ArrayList<AddressBean> addressBeans,
                            @NotNull String cartCount) {

                        ProductDetailsStep1Bean bean = new ProductDetailsStep1Bean();
                        bean.setProductDetailsBean(productDetailsBean);
                        bean.setAddressBeanList(addressBeans);
                        bean.setCartCount(cartCount);

                        return bean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpRxObserver);

    }


    /**
     * 获取店铺详情邮费
     *
     * @param shopId
     */
    public Observable<HttpResult<DeliveryBean>> getProductDelivery(String shopId) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("shopId", shopId);

        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .getProductDelivery(params));
    }

    /**
     * 添加浏览记录
     *
     * @param categoryId
     * @param skuId
     */
    public Observable<HttpResult<Boolean>> addSkuVisitTrack(
            String categoryId,
            String skuId) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("skuId", skuId);

        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .addSkuVisitTrack(params));
    }


    /**
     * 请求商品价格
     *
     * @param platformId
     * @param skuId
     */
    public Observable<HttpResult<ArrayList<ProductPriceBean>>> getProductPrice(
            String platformId,
            String skuId) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("platformId", platformId);
        params.put("skuIds", skuId);

        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .getProductPrice(params));
    }


    /**
     * 获取 商品收藏状态
     *
     * @param skuId
     */
    public Observable<HttpResult<JSONObject>> getUserCollect(String skuId) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("skuIds", skuId);

        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .getUserCollect(params));
    }


    /**
     * 请求店铺推荐（热销商品）
     *
     * @param platformId
     * @param cid
     * @param pageNum
     */
    public Observable<HttpResult<HotSaleBean>> getHotSale(
            String materialType,
            String platformId,
            String cid,
            int pageNum) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("materialType", materialType);
        params.put("platformId", platformId);
//        params.put("keyword", "11");
        params.put("businessType", 1);
        params.put("pageNum", pageNum);
        params.put("cid", cid); // 1001047
//        params.put("shopId", 202003030108L); //202003030108L

        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .getHotSale(params));
    }


    /**
     * 请求商品库存
     *
     * @param supplierId
     * @param provinceId
     * @param cityId
     * @param countryId
     * @param address
     * @param skuNum
     * @param skuId
     */
    public Observable<HttpResult<ArrayList<SkuStockBean>>> querySkuSaleStocks(
            String supplierId,
            String provinceId,
            String cityId,
            String countryId,
            String address,
            String skuNum,
            String skuId) {

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

        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ProductService.class)
                .querySkuSaleStocks(params));

    }

    /**
     * 请求商品信息
     *
     * @param materialType
     * @param platformId
     * @param shopId
     * @param cid
     * @param skuId
     * @param provinceId
     * @param cityId
     * @param countryId
     * @param address
     * @param skuNum
     * @param httpRxObserver
     */
    public void getProductDetailsStep2(String materialType,
                                       String platformId,
                                       String shopId,
                                       String cid,
                                       String skuId,
                                       String provinceId,
                                       String cityId,
                                       String countryId,
                                       String address,
                                       String skuNum,
                                       HttpRxObserver httpRxObserver) {

        Observable deliver
                = getProductDelivery(shopId).subscribeOn(Schedulers.io());
        Observable visit
                = addSkuVisitTrack(cid, skuId).subscribeOn(Schedulers.io());
        Observable productPrice
                = getProductPrice(platformId, skuId);
        Observable userCollect
                = getUserCollect(skuId);
        Observable hotSale
                = getHotSale(materialType, platformId, cid, 1);
        Observable skuStock
                = querySkuSaleStocks(shopId, provinceId, cityId, countryId, address, skuNum, skuId);

        Observable.zip(
                deliver, visit, productPrice, userCollect, hotSale, skuStock,
                new Function6<
                        DeliveryBean,
                        Boolean,
                        ArrayList<ProductPriceBean>,
                        JSONObject,
                        HotSaleBean,
                        ArrayList<SkuStockBean>,
                        ProductDetailsStep2Bean>() {

                    @NotNull
                    @Override
                    public ProductDetailsStep2Bean apply(
                            @NotNull DeliveryBean deliveryBean,
                            @NotNull Boolean aBoolean,
                            @NotNull ArrayList<ProductPriceBean> productPriceBeans,
                            @NotNull JSONObject jsonObject,
                            @NotNull HotSaleBean hotSaleBean,
                            @NotNull ArrayList<SkuStockBean> skuStockBeans) throws Exception {

                        ProductDetailsStep2Bean bean = new ProductDetailsStep2Bean();
                        bean.setDeliveryBean(deliveryBean);
                        bean.setVisitTrack(aBoolean);
                        bean.setProductPriceBeans(productPriceBeans);
                        bean.setCollect(jsonObject);
                        bean.setHotSaleBean(hotSaleBean);
                        bean.setSkuStockBeans(skuStockBeans);
                        return bean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpRxObserver);

    }


    /**
     * 请求商品信息
     *
     * @param platformId
     * @param cid
     * @param skuId
     * @param shopId
     * @param provinceId
     * @param cityId
     * @param countryId
     * @param address
     * @param skuNum
     * @param httpRxObserver
     */
    public void getProductDetailsPop(String platformId,
                                     String cid,
                                     String skuId,
                                     String shopId,
                                     String provinceId,
                                     String cityId,
                                     String countryId,
                                     String address,
                                     String skuNum,
                                     HttpRxObserver httpRxObserver) {

        Observable visit
                = addSkuVisitTrack(cid, skuId).subscribeOn(Schedulers.io());
        Observable productPrice
                = getProductPrice(platformId, skuId);
        Observable userCollect
                = getUserCollect(skuId);
        Observable skuStock
                = querySkuSaleStocks(shopId, provinceId, cityId, countryId, address, skuNum, skuId);

        Observable.zip(
                visit, productPrice, userCollect, skuStock,
                new Function4<
                        Boolean,
                        ArrayList<ProductPriceBean>,
                        JSONObject,
                        ArrayList<SkuStockBean>,
                        ProductDetailsStep3Bean>() {

                    @NotNull
                    @Override
                    public ProductDetailsStep3Bean apply(
                            @NotNull Boolean aBoolean,
                            @NotNull ArrayList<ProductPriceBean> productPriceBeans,
                            @NotNull JSONObject jsonObject,
                            @NotNull ArrayList<SkuStockBean> skuStockBeans) throws Exception {

                        ProductDetailsStep3Bean bean = new ProductDetailsStep3Bean();
                        bean.setVisitTrack(aBoolean);
                        bean.setProductPriceBeans(productPriceBeans);
                        bean.setCollect(jsonObject);
                        bean.setSkuStockBeans(skuStockBeans);
                        return bean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpRxObserver);
    }

}

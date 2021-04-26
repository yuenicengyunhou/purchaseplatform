package com.rails.lib_data.service;

import com.rails.lib_data.bean.BuyerBean;
import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.InvoiceTitleBean;
import com.rails.lib_data.bean.ListBeen;
import com.rails.lib_data.bean.OrderBean;
import com.rails.lib_data.bean.OrderBudgetBean;
import com.rails.lib_data.bean.OrderInfoBean;
import com.rails.lib_data.bean.OrderPurchaseBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface OrderService {

    @GET("platform/platform/floor/queryFloorSettingList")
    Observable<HttpResult<ArrayList<OrderBean>>> getOrder(@QueryMap HashMap<String, String> params);

    @GET("app-order-service/app/v1/buyer/order/purchasePageList")
    Observable<HttpResult<ListBeen<OrderInfoBean>>> purchasePageList(@QueryMap HashMap<String, Object> params);


    /**
     * 核对信息--获取订单信息核对单商品
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("app-order-service/app/v1/mall/cart/queryCartByOrder")
    Observable<HttpResult<CartBean>> getOrderCarts(@FieldMap HashMap<String, Object> params);


    /**
     * 核对信息--获取预算总额
     *
     * @return
     */
    @GET("app-order-service/app/v1/mall/cart/queryBudget")
    Observable<HttpResult<OrderBudgetBean>> getBudget();


    /**
     * 核对信息--获取结算单位
     *
     * @param params
     * @return
     */
    @GET("settlement/buyer/accounting/queryAccountingUnitByOrgId")
    Observable<HttpResult<ArrayList<OrderPurchaseBean>>> getOrderCompanys(@QueryMap HashMap<String, Object> params);


    /**
     * 提交采购单调用 ---获取一个token
     *
     * @param params
     * @return
     */
    @GET("platform/mall/token/generateToken")
    Observable<HttpResult<String>> getOrderToken(@QueryMap HashMap<String, Object> params);


    /**
     * @param params
     * @return
     */
    @POST("app-order-service/app/v1/mall/order/orderByCart")
    Observable<HttpResult<ArrayList<String>>> commitOrder(@QueryMap HashMap<String, Object> params);


    /**
     * 获取发票抬头
     */
    @GET("settlement/mall/invoice/getInvoiceTitle")
    Observable<HttpResult<ListBeen<InvoiceTitleBean>>> getInvoiceTitle(@QueryMap HashMap<String, Object> params);


    /**
     * 获取采购人用户名列表
     */

    @GET("app-user-service/app/v1/buyer/user/queryPurchaserList")
    Observable<HttpResult<ArrayList<BuyerBean>>> getBuyerList(@QueryMap HashMap<String, Object> params);


    /**
     * 获取供应商名称列表
     */
    @GET("app-user-service/app/v1/buyer/supplierInfoImportData/querySupplierBySupplierName")
    Observable<HttpResult<ArrayList<BuyerBean>>> getSupplierNames(@QueryMap HashMap<String, Object> params);
}

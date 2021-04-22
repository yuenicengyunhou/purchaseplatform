package com.rails.lib_data.model;

import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.InvoiceTitleBean;
import com.rails.lib_data.bean.ListBeen;
import com.rails.lib_data.bean.OrderBudgetBean;
import com.rails.lib_data.bean.OrderPurchaseBean;
import com.rails.lib_data.bean.OrderVerifyBean;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.AddressService;
import com.rails.lib_data.service.OrderService;
import com.rails.lib_data.service.ProductService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.functions.Function5;
import io.reactivex.schedulers.Schedulers;

/**
 * 核对信息订单model
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/22
 */
public class OrderVerifyModel {


    /**
     * 获取专属地址列表
     *
     * @param addressType 1：收货地址   2：收发票地址
     */
    private Observable getAddress(String addressType) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("addressType", addressType);
        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(AddressService.class)
                .getAddress(params))
                .subscribeOn(Schedulers.io());
    }


    /**
     * 获取结算单位
     */
    private Observable getPurchaseCompanys() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("addressType", "20");
        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class)
                .getOrderCompanys(params))
                .subscribeOn(Schedulers.io());
    }


    /**
     * 核对订单信息---获取预算总额
     *
     * @return
     */
    private Observable getBudget() {
        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class)
                .getBudget())
                .subscribeOn(Schedulers.io());
    }


    /**
     * 获取订单确认信息商品列表
     *
     * @param addressId
     */
    private Observable getOrderCarts(String addressId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("addressId", addressId);
        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class)
                .getOrderCarts(params))
                .subscribeOn(Schedulers.io());
    }


    /**
     * 获取订单确认信息商品列表
     */
    private Observable getInvoiceTitle() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageSize", "20");
        params.put("invoiceType", "2");
        params.put("pageNum", 1);

        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class)
                .getInvoiceTitle(params))
                .subscribeOn(Schedulers.io());
    }


    /**
     * 获取订单核对信息页面数据
     *
     * @param addressId
     * @param httpRxObserver
     */
    public void getOrderVerifyInfo(String addressId, HttpRxObserver httpRxObserver) {

        Observable carts = getOrderCarts(addressId);
        Observable verifys = getAddress("2");
        Observable budget = getBudget();
        Observable companys = getPurchaseCompanys();
        Observable invoices = getInvoiceTitle();


        Observable.zip(carts, verifys, budget, companys, invoices, (Function5<CartBean, ArrayList<AddressBean>, OrderBudgetBean, ArrayList<OrderPurchaseBean>, ListBeen<InvoiceTitleBean>, OrderVerifyBean>)
                (cartBean, addressBeans, budgetBean, purchaseBeans, listBeen) -> {
                    OrderVerifyBean verifyBean = new OrderVerifyBean();

                    verifyBean.setCart(cartBean);

                    if (!addressBeans.isEmpty()) {
                        verifyBean.setInvoiceAddress(addressBeans.get(0));
                    }

                    verifyBean.setBudgetBean(budgetBean);
                    if (!purchaseBeans.isEmpty())
                        verifyBean.setCompany(purchaseBeans.get(0));

                    if (listBeen != null && !listBeen.getList().isEmpty())
                        verifyBean.setInvoice(listBeen.getList().get(0));

                    return verifyBean;
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpRxObserver);

    }


    /**
     * 提交采购单需要token
     *
     * @param httpRxObserver
     */
    public void getOrderToken(HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class)
                .getOrderToken(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 获取订单确认信息商品列表
     */
    public void commitOrder(String obj, String busToken, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderRequestVo", obj);
        params.put("businessToken", busToken);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class)
                .commitOrder(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 获取订单确认信息商品列表
     */
    public void getInvoiceTitle(int page, int pageSize, String invoiceType, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageSize", pageSize);
        params.put("invoiceType", invoiceType);
        params.put("pageNum", page);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class)
                .getInvoiceTitle(params))
                .subscribe(httpRxObserver);
    }

}

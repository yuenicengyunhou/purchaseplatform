package com.rails.lib_data.model;

import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.OrderBudgetBean;
import com.rails.lib_data.bean.OrderVerifyBean;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.AddressService;
import com.rails.lib_data.service.OrderService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function3;
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
//        HashMap<String, Object> params = new HashMap<>();
//        params.put("addressType", "20");
        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(OrderService.class)
                .getOrderCompanys(null))
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


        Observable.zip(carts, verifys, budget, (Function3<CartBean, ArrayList<AddressBean>, OrderBudgetBean, OrderVerifyBean>)
                (cartBean, addressBeans, budgetBean) -> {
                    OrderVerifyBean verifyBean = new OrderVerifyBean();

                    verifyBean.setCart(cartBean);
                    for (AddressBean bean : addressBeans) {
                        if (bean.getHasDefault() == 1) {
                            verifyBean.setInvoiceAddress(bean);
                        }
                    }

                    verifyBean.setBudgetBean(budgetBean);
//                    if (!purchaseBeans.isEmpty())
//                        verifyBean.setCompany(purchaseBeans.get(0));

                    return verifyBean;
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpRxObserver);

    }


}

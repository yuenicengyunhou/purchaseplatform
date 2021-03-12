package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.purchaseplatform.framwork.base.BasePresenter;

import java.util.ArrayList;

/**
 * 购物车架构
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/12
 */
public class CartPresenterImpl extends BasePresenter<CartContract.CartView> implements CartContract.CartPresenter {

    public CartPresenterImpl(Activity mContext, CartContract.CartView cartView) {
        super(mContext, cartView);
    }

    @Override
    public void getCarts(boolean isDialog) {
        if (isDialog)
            baseView.showResDialog(R.string.loading);

        ArrayList<CartBean> cartBeans = new ArrayList<>();
        cartBeans.add(new CartBean("三只松鼠"));
        cartBeans.add(new CartBean("良品铺子"));
        cartBeans.add(new CartBean("味多美"));
        cartBeans.add(new CartBean("好利来"));
        cartBeans.add(new CartBean("汉巴味德"));
        if (isCallBack()) {
            baseView.dismissDialog();
            baseView.getCarts(cartBeans);
        }


    }

    @Override
    public void getRecProducts(boolean isDialog, int page) {
        if (isDialog)
            baseView.showResDialog(R.string.loading);

        ArrayList<ProductBean> productBeans = new ArrayList<>();
        productBeans.add(new ProductBean());
        productBeans.add(new ProductBean());
        productBeans.add(new ProductBean());
        productBeans.add(new ProductBean());
        productBeans.add(new ProductBean());
        productBeans.add(new ProductBean());

        if (isCallBack()) {
            baseView.dismissDialog();
            boolean isClear = page <= 1;
            baseView.getRecProjects(productBeans, true, isClear);
        }
    }
}

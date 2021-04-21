package com.rails.lib_data.contract;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.CartShopBean;
import com.rails.lib_data.bean.CartShopProductBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.model.CartModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 购物车架构
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/12
 */
public class CartPresenterImpl extends BasePresenter<CartContract.CartView> implements CartContract.CartPresenter {

    CartModel model;
    private String userId;


    public CartPresenterImpl(Activity mContext, CartContract.CartView cartView) {
        super(mContext, cartView);
        model = new CartModel();
        UserInfoBean userInfoBean = PrefrenceUtil.getInstance(mContext).getBean(ConShare.USERINFO, UserInfoBean.class);
        userId = userInfoBean.getId();
    }

    @Override
    public void getCarts(boolean isDialog) {
        if (isDialog)
            baseView.showResDialog(R.string.loading);

        model.getCarts(new HttpRxObserver<CartBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(CartBean cartBean) {
                baseView.dismissDialog();
                if (isCallBack()) {

                    for (CartShopBean shopBean : cartBean.getShopList()) {
                        for (CartShopProductBean productBean : shopBean.getSkuList()) {
                            productBean.num.set(productBean.getSkuNum());
                            productBean.isSel.set(productBean.getSelected());

                            productBean.canSel.set(productBean.getSaleStatus() == 1 ? true : false);
                            productBean.canAdd.set(true);

                            if (productBean.num.get() <= 1) {
                                productBean.canReduce.set(false);
                            } else
                                productBean.canReduce.set(true);

                            productBean.isLimit.set(productBean.getLimit());

                        }
                    }

                    baseView.getCartInfo(cartBean);
                }

            }

        });
    }

    @Override
    public void addProduct(CartShopProductBean bean, long num) {
        if (bean == null)
            return;
        num++;

        baseView.showResDialog(R.string.loading);
        long finalNum = num;
        model.modifyProduct(bean.getShopId(), userId, bean.getSkuId(), bean.getRecommendOrgId(), num, new HttpRxObserver<Boolean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(Boolean response) {
                baseView.dismissDialog();
                baseView.getProjectNumber(finalNum);
            }
        });
    }

    @Override
    public void reduceProduct(CartShopProductBean bean, long num) {
        if (bean == null)
            return;
        if (num < 1)
            return;
        num--;
        baseView.showResDialog(R.string.loading);
        long finalNum = num;
        model.modifyProduct(bean.getShopId(), userId, bean.getSkuId(), bean.getRecommendOrgId(), num, new HttpRxObserver<Boolean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(Boolean response) {
                baseView.dismissDialog();
                if (isCallBack())
                    baseView.getProjectNumber(finalNum);
            }
        });


    }

    @Override
    public void editProduct(CartShopProductBean bean, long num) {
        if (bean == null)
            return;
        baseView.showResDialog(R.string.loading);
        model.modifyProduct(bean.getShopId(), userId, bean.getSkuId(), bean.getRecommendOrgId(), num, new HttpRxObserver<Boolean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(Boolean response) {
                baseView.dismissDialog();
                if (isCallBack())
                    baseView.getProjectNumber(num);
            }
        });
    }


    @Override
    public void delProduct(String id) {
        baseView.getResult(0, "删除成功");
    }

    @Override
    public void collectProduct(String id) {
        baseView.getResult(1, "收藏成功");
    }

}

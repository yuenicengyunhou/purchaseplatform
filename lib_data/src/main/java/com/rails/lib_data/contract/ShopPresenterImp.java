package com.rails.lib_data.contract;

import android.app.Activity;
import android.util.Log;

import com.rails.lib_data.bean.shop.ItemListBean;
import com.rails.lib_data.bean.shop.ResultListBean;
import com.rails.lib_data.bean.shop.ShopInfoBean;
import com.rails.lib_data.bean.shop.ShopRecommendBean;
import com.rails.lib_data.model.ShopModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;

public class ShopPresenterImp extends BasePresenter<ShopContract.ShopView> implements ShopContract.ShopPresenter {

    private final ShopModel model;

    public ShopPresenterImp(Activity mContext, ShopContract.ShopView shopView) {
        super(mContext, shopView);
        model = new ShopModel();
    }

    @Override
    public void getShopDetails(long id) {
        model.getShopInfo(20, id, new HttpRxObserver<ShopInfoBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ShopInfoBean response) {
                baseView.loadShopInfo(response);
            }
        });
    }

    @Override
    public void getShopItemList(long platformId, long shopInfoId, int page, int pageSize) {
        model.getShopItemList(platformId, "302102040108", page, pageSize, new HttpRxObserver<ShopRecommendBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ShopRecommendBean response) {
                ItemListBean itemList = response.getItemList();
                ArrayList<ResultListBean> resultList = itemList.getResultList();
                baseView.loadShopProductList(resultList);
            }
        });
    }
}

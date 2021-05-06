package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.shop.ItemListBean;
import com.rails.lib_data.bean.shop.ResultListBean;
import com.rails.lib_data.bean.shop.ShopInfoBean;
import com.rails.lib_data.bean.shop.ShopRecommendBean;
import com.rails.lib_data.model.ShopModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;
import java.util.List;

public class ShopPresenterImp extends BasePresenter<ShopContract.ShopView> implements ShopContract.ShopPresenter {

    private final ShopModel model;

    public ShopPresenterImp(Activity mContext, ShopContract.ShopView shopView) {
        super(mContext, shopView);
        model = new ShopModel();
    }

    @Override
    public void getShopDetails(String id) {
        baseView.showResDialog(R.string.loading);
        model.getShopInfo(20, id, new HttpRxObserver<ShopInfoBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ShopInfoBean response) {
                baseView.dismissDialog();
                baseView.loadShopInfo(response);
            }
        });
    }

    @Override
    public void getShopItemList( String shopInfoId, int page, int pageSize, String orderColumn, String orderType,ArrayList<SearchFilterBean> list) {
        model.getShopItemList(20L, shopInfoId, page, pageSize, orderColumn, orderType,list, new HttpRxObserver<ShopRecommendBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ShopRecommendBean response) {
                ItemListBean itemList = response.getItemList();
                ArrayList<ResultListBean> resultList = itemList.getResultList();
                int count = itemList.getCount();
                baseView.loadShopProductList(resultList,count);
                if (page < 1) {
                    ArrayList<SearchFilterBean> filterBeans = model.getFilterBeans(response);
                    baseView.loadFilter(filterBeans);
                }
            }
        });
    }


}

package com.rails.lib_data.contract;

import android.app.Activity;
import android.util.Log;

import com.rails.lib_data.bean.ShopVO;
import com.rails.lib_data.model.ShopModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

public class ShopPresenterImp extends BasePresenter<ShopContract.ShopView> implements ShopContract.ShopPresenter {


    private final ShopModel model;

    public ShopPresenterImp(Activity mContext, ShopContract.ShopView shopView) {
        super(mContext, shopView);
        model = new ShopModel();
    }

    /**
     * 店铺信息详情
     */
    @Override
    public void getShopDetails(long id) {
        baseView.showDialog("正在加载...");
        model.getShopInfo(20, id, new HttpRxObserver<HttpResult<ShopVO>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
                Log.e("WQ", "fail");
            }

            @Override
            protected void onSuccess(HttpResult<ShopVO> response) {
                Log.e("WQ", "success");
            }
        });
    }
}

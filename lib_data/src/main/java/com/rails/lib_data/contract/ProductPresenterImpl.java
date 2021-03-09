package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.model.ProductModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;

/**
 * 商品presenter 中间业务逻辑处理
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/8
 */
public class ProductPresenterImpl extends BasePresenter<ProductContract.ProductView> implements ProductContract.ProductPresenter {

    private ProductModel model;

    public ProductPresenterImpl(Activity mContext, ProductContract.ProductView productView) {
        super(mContext, productView);
        model = new ProductModel();
    }

    @Override
    public void getRectProducts(boolean isDialog) {
        if (isDialog)
            baseView.showDialog("加载中...");

        model.getRecProducts(new HttpRxObserver<ArrayList<ProductRecBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);

            }

            @Override
            protected void onSuccess(ArrayList<ProductRecBean> beans) {
                baseView.dismissDialog();
                baseView.getRecProducts(beans);
            }
        });
    }
}

package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.R;
import com.rails.lib_data.model.CartModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

/**
 * 商品详情页 请求添加到购物车
 */
public class CartToolPresenterImpl extends BasePresenter<CartContract.DetailsCartView> implements CartContract.CartPresenter2 {

    private CartModel mModel;

    public CartToolPresenterImpl(Activity mContext, CartContract.DetailsCartView cartView) {
        super(mContext, cartView);
        mModel = new CartModel();
    }

    @Override
    public void addCart(long platformId, long organizeId, long accountId, int accountType, String skuSaleNumJson, boolean isDialog) {

//        if (isDialog) baseView.showResDialog(R.string.loading);

        mModel.addCart(platformId, organizeId, accountId, accountType, skuSaleNumJson,
                new HttpRxObserver() {
                    @Override
                    protected void onError(ErrorBean e) {
                        baseView.dismissDialog();
                        String msg = e.getMsg();
                        if (msg.contains("[") && msg.contains("]")) {
                            e.setMsg("您购买的商品实际库存不足，请修改购买数量或选择其他商品！");
                        }
                        baseView.onError(e);
                    }

                    @Override
                    protected void onSuccess(Object response) {
                        baseView.dismissDialog();
                        // TODO: 2021/04/19 处理数据（data字段返回值为true）并传递返回值
                        baseView.addCartSuccess(true);
                    }
                });
    }


    /**
     * @param skuIds
     * @param collectionSource 加入收藏参数 value = "收藏来源，10：列表页，20：商详页，30：购物车
     * @param isCollect        是否已经收藏
     */
    @Override
    public void onCollect(String skuIds, String collectionSource, boolean isCollect, int position) {
        baseView.showResDialog(R.string.loading);
        mModel.onCollect(skuIds, collectionSource, isCollect, new HttpRxObserver<Boolean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(Boolean response) {
                baseView.dismissDialog();
                if (isCallBack()) {
                    baseView.onCollect(!isCollect, position);
                }
            }
        });
    }
}

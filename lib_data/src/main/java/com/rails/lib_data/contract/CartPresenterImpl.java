package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.R;
import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

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

        CartBean cartBean = JsonUtil.parseJson(mContext, "cart.json", CartBean.class);

        if (isCallBack()) {
            baseView.dismissDialog();
            baseView.getCartInfo(cartBean);
        }


    }

}

package com.rails.lib_data.contract;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.CategorySubBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.model.ProductModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

import java.lang.reflect.Type;
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
    public void getHotProducts(boolean isDialog, int page) {
        if (isDialog)
            baseView.showResDialog(R.string.loading);

        Type type = new TypeToken<ArrayList<ProductBean>>() {
        }.getType();
        ArrayList<ProductBean> beans = JsonUtil.parseJson(mContext, "product_hot.json", type);

        if (isCallBack()) {
            baseView.dismissDialog();
            boolean isClear = page <= 1;
            baseView.getHotProducts(beans, false, isClear);
        }
    }
}

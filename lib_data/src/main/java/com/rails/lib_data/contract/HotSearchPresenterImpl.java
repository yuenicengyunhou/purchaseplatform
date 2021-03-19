package com.rails.lib_data.contract;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.HotSearchBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.model.HotSearchModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HotSearchPresenterImpl extends BasePresenter<HotSearchContract.HotSearchView> implements HotSearchContract.HotSearchPresenter {

    private HotSearchModel model;

    public HotSearchPresenterImpl(Activity mContext, HotSearchContract.HotSearchView hotSearchView) {
        super(mContext, hotSearchView);
        model = new HotSearchModel();
    }

    @Override
    public void getHotSearch(boolean isDialog, int page) {
        if (isDialog)
            baseView.showResDialog(R.string.loading);


        Type type = new TypeToken<ArrayList<ProductBean>>() {
        }.getType();
        ArrayList<HotSearchBean> beans = JsonUtil.parseJson(mContext, "product_hot.json", type);

        if (isCallBack()) {
            baseView.dismissDialog();
            boolean isClear = page <= 1;
            baseView.getHotSearch(beans, false, isClear);
        }
    }
}

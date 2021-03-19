package com.rails.lib_data.contract;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.RecommendItemsBean;
import com.rails.lib_data.model.RecommendItemsModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RecommendItemsPresenterImpl extends BasePresenter<RecommendItemsContract.RecommendItemsView> implements RecommendItemsContract.RecommendItemsPresenter {

    private RecommendItemsModel model;


    public RecommendItemsPresenterImpl(Activity mContext, RecommendItemsContract.RecommendItemsView recommendItemsView) {
        super(mContext, recommendItemsView);
        model = new RecommendItemsModel();
    }

    @Override
    public void getRecommendItems(boolean isDialog, int page) {
        if (isDialog)
            baseView.showResDialog(R.string.loading);

        Type type = new TypeToken<RecommendItemsBean>() {
        }.getType();

        ArrayList<RecommendItemsBean> beans = JsonUtil.parseJson(mContext, "recommendThings.json", type);

        if (isCallBack()) {
            baseView.dismissDialog();
            boolean isClear = page <= 1;
            baseView.getRecommendItems(beans, false, isClear);
        }
    }
}

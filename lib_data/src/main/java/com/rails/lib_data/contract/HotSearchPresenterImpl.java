package com.rails.lib_data.contract;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.HotSearchBean;
import com.rails.lib_data.model.HotSearchModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
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
        if (isDialog) baseView.showResDialog(R.string.loading);

        // TODO: 2021/04/19 添加参数
        model.getHotSearch(new HttpRxObserver() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(Object response) {
                baseView.dismissDialog();

                // TODO: 2021/04/19 处理返回数据
                Type type = new TypeToken<ArrayList<HotSearchBean>>() {
                }.getType();
                ArrayList<HotSearchBean> beans = JsonUtil.parseJson(mContext, "hotSearch.json", type);
                boolean isClear = page <= 1;

                baseView.getHotSearch(beans, false, isClear);
            }
        });
    }
}

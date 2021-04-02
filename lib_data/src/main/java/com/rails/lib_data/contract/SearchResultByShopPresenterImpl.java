package com.rails.lib_data.contract;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.SearchResultByShopBean;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SearchResultByShopPresenterImpl extends BasePresenter<SearchResultContract.SearchResultByShopView>
        implements SearchResultContract.SearchResultByShopPresenter {


    public SearchResultByShopPresenterImpl(Activity mContext, SearchResultContract.SearchResultByShopView searchResultByShopView) {
        super(mContext, searchResultByShopView);
    }

    @Override
    public void getSearchResultByShop(boolean isDialog, int page) {
        if (isDialog)
            baseView.showResDialog(R.string.loading);

        Type type = new TypeToken<ArrayList<SearchResultByShopBean>>() {
        }.getType();
        ArrayList<SearchResultByShopBean> beans = JsonUtil.parseJson(mContext, "searchResultByShop.json", type);

        if (isCallBack()) {
            baseView.dismissDialog();
            boolean isClear = page <= 1;
            baseView.getSearchResultByShop(beans, false, isClear);
        }
    }
}

package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.purchaseplatform.framwork.base.BasePresenter;

/**
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public class MarKetIndexPresenterImpl extends BasePresenter<MarketIndexContract.MarketIndexView> implements MarketIndexContract.MarketIndexPresenter {


    public MarKetIndexPresenterImpl(Activity mContext, MarketIndexContract.MarketIndexView marketIndexView) {
        super(mContext, marketIndexView);
    }

    @Override
    public void getIndexInfo() {

    }
}

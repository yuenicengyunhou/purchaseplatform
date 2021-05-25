package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.AddressArea;
import com.rails.lib_data.R;
import com.rails.lib_data.model.DistributionAreaModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;


/**
 * 实现类
 */
public class DistributionAreaImpl
        extends BasePresenter<DistributionAreaContract.DistributionAreaView>
        implements DistributionAreaContract.DistributionAreaPresenter {

    private DistributionAreaModel mModel;


    public DistributionAreaImpl(Activity mContext, DistributionAreaContract.DistributionAreaView distributionAreaView) {
        super(mContext, distributionAreaView);
        mModel = new DistributionAreaModel();
    }


    @Override
    public void getDistributionArea(String parentCode, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);
        mModel.getDistributionArea("20", parentCode, new HttpRxObserver<ArrayList<AddressArea>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(ArrayList<AddressArea> response) {
                baseView.onGetDistributionAreaSuccess(response);
                baseView.dismissDialog();
            }
        });
    }

}

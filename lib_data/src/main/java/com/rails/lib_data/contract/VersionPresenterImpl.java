package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.bean.VersionBean;
import com.rails.lib_data.model.VersionModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;

/**
 * 版本号更新
 *
 * @author： sk_comic@163.com
 * @date: 2021/5/21
 */
public class VersionPresenterImpl extends BasePresenter<VersionContract.VersionView> implements VersionContract.VersionPresenter {

    private VersionModel model;

    public VersionPresenterImpl(Activity mContext, VersionContract.VersionView versionView) {
        super(mContext, versionView);
        model = new VersionModel();
    }

    @Override
    public void getVersion() {
        model.getVersion(new HttpRxObserver<ArrayList<VersionBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ArrayList<VersionBean> response) {
                if (isCallBack()) {
                    if (response != null && !response.isEmpty()) {
                        baseView.getVersion(response.get(0));
                    }
                }
            }
        });
    }
}

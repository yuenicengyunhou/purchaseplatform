package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.lib_data.model.UserToolModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

/**
 * 用户相关工具
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/20
 */
public class UserToolPresenterImpl extends BasePresenter<UserToolContract.UserToolView> implements UserToolContract.UserToolPresenter {

    UserToolModel model;

    public UserToolPresenterImpl(Activity mContext, UserToolContract.UserToolView userToolView) {
        super(mContext, userToolView);
        model = new UserToolModel();
    }

    @Override
    public void getUserStatictics(String userId, String userType) {
        model.getUserStatictics(userId, userType, new HttpRxObserver<UserStatisticsBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(UserStatisticsBean bean) {
                if (isCallBack()) {
                    baseView.getUserStatictics(bean);
                }
            }
        });
    }

    @Override
    public void getUserInfoStatictics(String userId, String userType) {
        model.getUserInfoStatictics(userId, userType, new HttpRxObserver<UserStatisticsBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(UserStatisticsBean bean) {
                if (isCallBack()) {
                    baseView.getUserInfoStatictics(bean);
                }
            }
        });
    }

    @Override
    public void checkPermissions(String userId, String userType) {
        model.checkPermissions(userId, userType, new HttpRxObserver<UserStatisticsBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(UserStatisticsBean bean) {
                if (isCallBack()) {
                    baseView.checkPermissions(bean);
                }
            }
        });
    }
}

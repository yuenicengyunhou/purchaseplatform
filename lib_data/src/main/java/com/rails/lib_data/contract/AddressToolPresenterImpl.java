package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.model.AddressModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;

import java.util.ArrayList;

/**
 * 公共部分调用地址
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/21
 */
public class AddressToolPresenterImpl extends BasePresenter<AddressToolContract.AddressToolView> implements AddressToolContract.AddressToolPresenter {

    private AddressModel model;
    private String userId;
    private String userType;

    public AddressToolPresenterImpl(Activity mContext, AddressToolContract.AddressToolView addressToolView) {
        super(mContext, addressToolView);
        model = new AddressModel();
        UserInfoBean userInfoBean = PrefrenceUtil.getInstance(mContext).getBean(ConShare.USERINFO, UserInfoBean.class);
        if (userInfoBean != null) {
            userId = userInfoBean.getId();
            userType = userInfoBean.getAccountType();
        }

    }

    @Override
    public void getAddress(String paltId, String addressType, String type, String keyword) {
        model.getAddress(paltId, addressType, userId, userType, type, keyword, new HttpRxObserver<ArrayList<AddressBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ArrayList<AddressBean> addressBeans) {
                baseView.getAddress(addressBeans);
            }
        });
    }

    @Override
    public void getDefAddress(String paltId, String addressType, String type, String keyword) {
        model.getAddress(paltId, addressType, userId, userType, type, keyword, new HttpRxObserver<ArrayList<AddressBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ArrayList<AddressBean> addressBeans) {
//                for (AddressBean bean : addressBeans) {
//                    if (bean.getHasDefault() == 1) {
//                        baseView.getDefAddress(bean);
//                        return;
//                    }
//                }
                if (isCallBack()) {
                    if (null != addressBeans && !addressBeans.isEmpty())
                        baseView.getDefAddress(addressBeans.get(0));
                    else
                        baseView.getDefAddress(null);
                }

            }
        });
    }
}

package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.ConShare;
import com.rails.lib_data.R;
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

    /**
     * 获取地址，传递一个是否需要加载条的参数，showLoading.用途：购物车初始获取时不显示loading,当点击购物车顶部地址条时，
     * 再次请求这个接口，这时显示loading。
     * 为什么要这么再次请求这个接口？解决app处在购物车界面时，pc端手动编辑收货地址后，点击顶部地址条，展示的地址列表未更新的问题
     */
    @Override
    public void getAddress(String paltId, String addressType, String type, String keyword, boolean showLoading) {

        if (showLoading) baseView.showResDialog(R.string.loading);

        model.getAddress(paltId, addressType, userId, userType, type, keyword, new HttpRxObserver<ArrayList<AddressBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                if (showLoading) baseView.dismissDialog();

                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ArrayList<AddressBean> addressBeans) {
                if (showLoading) baseView.dismissDialog();

                baseView.getAddress(addressBeans,showLoading);
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

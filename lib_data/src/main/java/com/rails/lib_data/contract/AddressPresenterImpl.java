package com.rails.lib_data.contract;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.rails.lib_data.model.UserModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.framwork.utils.VerificationUtil;


/**
 * 地址管理presneter
 * <p>
 * author： sk_comic@163.com
 * date: 2021/3/27
 */
public class AddressPresenterImpl extends BasePresenter<AddressContract.AddressView> implements AddressContract.AddressPresenter {

    private final UserModel model;

    public AddressPresenterImpl(Activity mContext, AddressContract.AddressView addressView) {
        super(mContext, addressView);
        model = new UserModel();
    }

    @Override
    public void getAddresses(Boolean isDialog) {
//        String token = PrefrenceUtil.getInstance(mContext).getString("token", "");
//        long accountId = Long.parseLong(token);
        model.queryAddressList(20, 111, 2, 1, 10, new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(String response) {
                Log.e("WQ", "onSuccess: " + response);
            }
        });

//        if (isDialog)
//            baseView.showResDialog(R.string.loading);
//
//        if (isCallBack()) {
//            baseView.dismissDialog();
//            Type type = new TypeToken<ArrayList<AddressBean>>() {
//            }.getType();
//            ArrayList<AddressBean> beans = JsonUtil.parseJson(mContext, "address.json", type);
//            for (AddressBean bean : beans)
//                bean.isSel.set(bean.getIsdefault());
//            baseView.getAddresses(beans);
//        }

    }

    @Override
    public void setDefAddress(String id, int position) {
        baseView.getResult(1, position, "更改成功");
    }

    @Override
    public void delAddress(String id, int position) {
        baseView.getResult(0, position, "删除成功");
    }

    @Override
    public void addAddress(String men, String phone, String area, String address, boolean isDef) {
        if (TextUtils.isEmpty(men)) {
            ToastUtil.show(mContext, "请输入收货人");
            return;
        }

        if (VerificationUtil.isMobile(phone)) {
            ToastUtil.show(mContext, "请输入收货人手机号");
            return;
        }

        if (TextUtils.isEmpty(area)) {
            ToastUtil.show(mContext, "请输入省市区县、乡镇等");
            return;
        }

        if (TextUtils.isEmpty(address)) {
            ToastUtil.show(mContext, "请输入省街道、楼牌号等");
            return;
        }

        baseView.getResult(0, 0, "添加成功");
    }
}

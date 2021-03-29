package com.rails.lib_data.contract;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.AddressBean;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.framwork.utils.VerificationUtil;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 地址管理presneter
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/27
 */
public class AddressPresenterImpl extends BasePresenter<AddressContract.AddressView> implements AddressContract.AddressPresenter {

    public AddressPresenterImpl(Activity mContext, AddressContract.AddressView addressView) {
        super(mContext, addressView);
    }

    @Override
    public void getAddresses(Boolean isDialog) {

        if (isDialog)
            baseView.showResDialog(R.string.loading);

        if (isCallBack()) {
            baseView.dismissDialog();
            Type type = new TypeToken<ArrayList<AddressBean>>() {
            }.getType();
            ArrayList<AddressBean> beans = JsonUtil.parseJson(mContext, "address.json", type);
            for (AddressBean bean : beans)
                bean.isSel.set(bean.getIsdefault());
            baseView.getAddresses(beans);
        }

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

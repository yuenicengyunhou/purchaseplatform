package com.rails.purchaseplatform.address.ui;

import android.os.Bundle;
import android.view.View;

import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.contract.AddressContract;
import com.rails.lib_data.contract.AddressPresenterImpl;
import com.rails.purchaseplatform.address.R;
import com.rails.purchaseplatform.address.databinding.ActivityAddressAddBinding;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import java.util.ArrayList;


public class AddressAddActivity extends ToolbarActivity<ActivityAddressAddBinding> implements AddressContract.AddressView {

    private AddressBean bean;
    private AddressContract.AddressPresenter presenter;

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        bean = (AddressBean) extras.getSerializable("bean");
    }

    @Override
    protected int getColor() {
        return android.R.color.white;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected void initialize(Bundle bundle) {
        binding.titleBar.setImgLeftRes(R.drawable.svg_back_black)
                .setTitleBar(R.string.address_add).setShowLine(true);

        if (bean != null) {
            setDetail(bean);
        }

        presenter = new AddressPresenterImpl(this, this);

    }


    /**
     * 设置内容
     *
     * @param bean
     */
    private void setDetail(AddressBean bean) {
        barBinding.etName.setContent(bean.getName());
        barBinding.etPhone.setContent(bean.getPhone());
        barBinding.etArea.setContent(bean.getAddress());
        barBinding.etRemark.setText(bean.getAddress());
    }


    @Override
    protected void onClick() {
        super.onClick();
        barBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
    }


    /**
     * 添加/更改地址
     */
    private void commit() {
        String men = barBinding.etName.getContent().trim();
        String phone = barBinding.etPhone.getContent().trim();
        String area = barBinding.etArea.getContent().trim();
        String remark = barBinding.etRemark.getText().toString().trim();
        presenter.addAddress(men, phone, area, remark, false);
    }

    @Override
    public void getResult(int type, int position, String msg) {
        ToastUtil.showCenter(this, msg);
        finish();
    }

    @Override
    public void getAddresses(ArrayList<AddressBean> addressBeans) {

    }
}
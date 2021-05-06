package com.rails.purchaseplatform.address.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.services.core.PoiItem;
import com.rails.lib_data.AddressArea;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.contract.AddressContract;
import com.rails.lib_data.contract.AddressPresenterImpl;
import com.rails.purchaseplatform.address.R;
import com.rails.purchaseplatform.address.databinding.ActivityAddressAddBinding;
import com.rails.purchaseplatform.address.ui.pop.AreaPop;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.utils.LocationUtil;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.StringUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.Nullable;

/**
 * 添加地址
 */
public class AddressAddActivity extends ToolbarActivity<ActivityAddressAddBinding> implements AddressContract.AddressView {

    private AddressBean bean;
    private AddressContract.AddressPresenter presenter;
    private long addressId = 0;

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
            addressId = bean.getAddressId();
        } else {
            loadData();
        }

        presenter = new AddressPresenterImpl(this, this);
//        presenter.getArea(20,"0");

    }


    /**
     * 设置内容
     * <p>
     * bean
     */
    private void setDetail(AddressBean bean) {
        barBinding.etName.setContent(bean.getReceiverName());
        barBinding.etPhone.setContent(bean.getPhone());
        barBinding.etArea.setContent(bean.getFullAddress());
        barBinding.etRemark.setText(bean.getFullAddress());
        barBinding.cbReceive.setChecked(bean.getReceivingAddress() == 1);
        barBinding.cbInvoice.setChecked(bean.getInvoiceAddress() == 1);
    }


    @Override
    protected void onClick() {
        super.onClick();
        barBinding.btnAdd.setOnClickListener(v -> commit());

        barBinding.btnLocation.setOnClickListener(v -> ARouter.getInstance().build(ConRoute.ADDRESS.ADDRESS_MAP).navigation(AddressAddActivity.this, 0));

        barBinding.etArea.setOnClickListener(v -> {
            AreaPop pop = new AreaPop();
            pop.setGravity(Gravity.BOTTOM);
            pop.setType(BasePop.MATCH_WRAP);
            pop.setListener(area -> barBinding.etArea.setContent(area));
            pop.show(getSupportFragmentManager(), "area");
        });
    }


    protected void loadData() {
        RxPermissions.getInstance(this).request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(aBoolean -> {
            if (aBoolean)
                initLocation();
        });
    }


    /**
     * 定位当前的位置
     */
    private void initLocation() {
        LocationUtil.getLocation(this, mapLocation -> {
            if (null != mapLocation) {
                barBinding.etRemark.setText(mapLocation.getAddress());
                barBinding.etArea.setContent(mapLocation.getProvince() + " " + mapLocation.getCity() + " " + mapLocation.getDistrict());
            } else
                ToastUtil.show(AddressAddActivity.this, "定位失败");
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            PoiItem poiItem = data.getParcelableExtra("poi");
            barBinding.etRemark.setText(MessageFormat.format("{0}{1}{2}", poiItem.getCityName(), poiItem.getAdName(), poiItem.getSnippet()));
            barBinding.etArea.setContent(poiItem.getProvinceName() + " " + poiItem.getCityName() + " " + poiItem.getAdName());
        }
    }


    /**
     * 添加/更改地址
     */
    private void commit() {
        String men = barBinding.etName.getContent().trim();
        String phone = barBinding.etPhone.getContent().trim();
        String area = barBinding.etArea.getContent().trim();
        String remark = Objects.requireNonNull(barBinding.etRemark.getText()).toString().trim();
        int isReceivingAddress = barBinding.cbReceive.isChecked() ? 1 : 0;
        int isInvoiceAddress = barBinding.cbInvoice.isChecked() ? 1 : 0;
        verify(men, phone, area, remark, isReceivingAddress, isInvoiceAddress);

    }

    /**
     * 条件判断
     */
    private void verify(String men, String phone, String area, String remark, int isReceivingAddress, int isInvoiceAddress) {
        if ((isReceivingAddress + isInvoiceAddress) < 1) {
            ToastUtil.showCenter(this, "请选择地址类型");
            return;
        }
        presenter.addAddress(men, phone, area, remark, false, isReceivingAddress, isInvoiceAddress, addressId);
    }

    @Override
    public void getResult(int type, int position, String msg) {
        ToastUtil.showCenter(this, msg);
        finish();
    }

    @Override
    public void getAddresses(ArrayList<AddressBean> addressBeans) {

    }

    @Override
    public void getArea(ArrayList<AddressArea> list) {

    }

}
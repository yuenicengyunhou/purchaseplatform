package com.rails.purchaseplatform.address.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.address.AddressArea;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.contract.AddressContract;
import com.rails.lib_data.contract.AddressPresenterImpl;
import com.rails.purchaseplatform.address.R;
import com.rails.purchaseplatform.address.databinding.ActivityAddressAddBinding;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.pop.AreaPop;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.StringUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.Nullable;

/**
 * 添加地址
 */
public class AddressAddActivity extends ToolbarActivity<ActivityAddressAddBinding> implements AddressContract.AddressView {

    private AddressBean bean;
    private AddressContract.AddressPresenter presenter;
    private String addressId = "";
    private String provinceCode = "";
    private String cityCode = "";
    private String countryCode = "";

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
        return true;
    }

    @Override
    protected void initialize(Bundle bundle) {
        binding.titleBar.setImgLeftRes(R.drawable.svg_back_black)
                .setTitleBar(R.string.address_add).setShowLine(true);

        presenter = new AddressPresenterImpl(this, this);

//        if (null==bean){
//            Intent intent = getIntent();
//            Bundle extras = intent.getExtras();
//            this.bean = (AddressBean) extras.getSerializable("bean");
//        }
        if (bean != null) {
            binding.titleBar.setTitle("编辑地址");
            addressId = bean.getAddressId();
            if (null != bean.getId() && !TextUtils.isEmpty(bean.getId())) {
//                addressId = bean.getId();
                loadAddressInfo(bean);
            } else {
                presenter.getAddressInfo(addressId);
            }
        } else {
//            loadData();
        }

//        presenter.getArea(20,"0");

    }


    @Override
    public void loadAddressInfo(AddressBean addressInfo) {
        barBinding.etName.setContent(addressInfo.getReceiverName());
        barBinding.etPhone.setContent(addressInfo.getMobile());
        String fullAddress = addressInfo.getFullAddress();
        String attachAddress = addressInfo.getAttachAddress();
        String cleanAddress = StringUtil.delStringTail(fullAddress, attachAddress);
        provinceCode = addressInfo.getProvinceCode();
        cityCode = addressInfo.getCityCode();
        countryCode = addressInfo.getCountryCode();
        barBinding.etArea.setContent(cleanAddress);
        barBinding.etRemark.setText(attachAddress);
        barBinding.cbReceive.setChecked(addressInfo.getReceivingAddress() == 1);
        barBinding.cbInvoice.setChecked(addressInfo.getInvoiceAddress() == 1);
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
            pop.setListener((area, provinceCode, cityCode, countryCode) -> {
                barBinding.etArea.setContent(area);
                this.provinceCode = provinceCode;
                this.cityCode = cityCode;
                this.countryCode = countryCode;
            });
            pop.show(getSupportFragmentManager(), "area");
        });
    }


//    protected void loadData() {
//        RxPermissions.getInstance(this).request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(aBoolean -> {
//            if (aBoolean)
//                initLocation();
//        });
//    }


    /**
     * 定位当前的位置
     */
    private void initLocation() {
//        LocationUtil.getLocation(this, mapLocation -> {
//            if (null != mapLocation) {//adCode相当于接口的coutryCode，这个是一致的
//                barBinding.etRemark.setText(mapLocation.getAddress());
//                String s = mapLocation.getProvince() + " " + mapLocation.getCity() + " " + mapLocation.getDistrict();
//                String adCode = mapLocation.getAdCode();
//                String cityCode = mapLocation.getCityCode();
//                this.provinceCode = "";
//                this.cityCode = cityCode;
//                this.countryCode = adCode;
//                barBinding.etArea.setContent(s);
//            } else
//                ToastUtil.show(AddressAddActivity.this, "定位失败");
//        });
//        LocationUtil.getLocation(this, new LocationUtil.CallBack() {
//            @Override
//            public void getMapLocation(AMapLocation mapLocation) {
//
//            }
//        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            PoiItem poiItem = Objects.requireNonNull(data).getParcelableExtra("poi");
//            barBinding.etRemark.setText(MessageFormat.format("{0}{1}{2}", poiItem.getCityName(), poiItem.getAdName(), poiItem.getSnippet()));
//            barBinding.etArea.setContent(poiItem.getProvinceName() + " " + poiItem.getCityName() + " " + poiItem.getAdName());
//        }
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
        presenter.addAddress(men, phone, area, remark, false, isReceivingAddress, isInvoiceAddress, addressId, provinceCode, cityCode, countryCode);

    }

    @Override
    public void getResult(int type, int position, String msg) {
        ToastUtil.showCenter(this, msg);
        finish();
    }

    @Override
    public void getAddresses(ArrayList<AddressBean> addressBeans, boolean isLastPage, int totalCount) {

    }

    @Override
    public void getArea(ArrayList<AddressArea> list) {

    }

}
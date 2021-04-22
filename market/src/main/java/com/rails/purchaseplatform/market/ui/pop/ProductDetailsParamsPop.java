package com.rails.purchaseplatform.market.ui.pop;

import android.os.Bundle;

import com.rails.lib_data.bean.forAppShow.ItemParams;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.market.databinding.PopProductDetailsParamsBinding;

public class ProductDetailsParamsPop extends BasePop<PopProductDetailsParamsBinding> {

    private ItemParams mParams;

    public ProductDetailsParamsPop() {
        super();
    }

    public ProductDetailsParamsPop(ItemParams params) {
        super();
        mParams = params;
    }

    @Override
    protected void initialize(Bundle bundle) {
        binding.tvBrandValue.setText(
                mParams.getBrand() == null ? "暂无" : mParams.getBrand());
        binding.tvNameValue.setText(
                mParams.getName() == null ? "暂无" : mParams.getName());
        binding.tvProductNumValue.setText(
                mParams.getProductNum() == null ? "暂无" : mParams.getProductNum());
        binding.tvMadeInValue.setText(
                mParams.getMadeIn() == null ? "暂无" : mParams.getMadeIn());
        binding.tvItemNumValue.setText(
                mParams.getItemNum() == null ? "暂无" : mParams.getItemNum());
        binding.tvTypeValue.setText(
                mParams.getType() == null ? "暂无" : mParams.getType());
        binding.tvItemBarCodeValue.setText(
                mParams.getItemBarCode() == null ? "暂无" : mParams.getItemBarCode());
        String weight
                = mParams.getWeight() == null ? "暂无" : mParams.getWeight();
        String weightUnit
                = mParams.getWeightUnit() == null
                ? "暂无"
                : mParams.getWeightUnit();
        String x = weight.equals("暂无") && weight.equals("暂无")
                ? "暂无"
                : weight + weightUnit;
        binding.tvWeightValue.setText(
                x);
        binding.tvSizeValue.setText(
                mParams.getSize() == null ? "暂无" : mParams.getSize());
        binding.tvItemUnitValue.setText(
                mParams.getItemUnit() == null ? "暂无" : mParams.getItemUnit());
    }
}

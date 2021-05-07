package com.rails.purchaseplatform.market.ui.pop;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

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
        // 品牌
        setTextOrGone(mParams.getBrand(), binding.tvBrandKey, binding.tvBrandValue);

        // 商品名称
        setTextOrGone(mParams.getName(), binding.tvNameKey, binding.tvNameValue);

        // 商品编码
        setTextOrGone(mParams.getProductNum(), binding.tvProductNumKey, binding.tvProductNumValue);

        // 商品产地
        setTextOrGone(mParams.getMadeIn(), binding.tvMadeInKey, binding.tvMadeInValue);

        // 单品编码
        setTextOrGone(mParams.getItemNum(), binding.tvItemNumKey, binding.tvItemNumValue);

        // 规格型号
        setTextOrGone(mParams.getType(), binding.tvTypeKey, binding.tvTypeValue);

        // 单品条码
        setTextOrGone(mParams.getItemBarCode(), binding.tvItemBarCodeKey, binding.tvItemBarCodeValue);

        // 商品毛重
        setTextOrGone(mParams.getWeight(), mParams.getWeightUnit(), binding.tvWeightKey, binding.tvWeightValue);

        // 包装尺寸
        setTextOrGone(mParams.getSize(), binding.tvSizeKey, binding.tvSizeValue);

        // 商品单位
        setTextOrGone(mParams.getItemUnit(), binding.tvItemUnitKey, binding.tvItemUnitValue);

        // 关闭按钮
        binding.ibClose.setOnClickListener(v -> {
            dismiss();
        });

        binding.btnOk.setOnClickListener(v -> dismiss());

    }

    private void setTextOrGone(String value, TextView viewKey, TextView viewValue) {
        if (TextUtils.isEmpty(value)) {
            viewKey.setVisibility(View.GONE);
            viewValue.setVisibility(View.GONE);
        } else {
            viewValue.setText(value);
        }
    }

    private void setTextOrGone(String value1, String value2, TextView viewKey, TextView viewValue) {
        if (TextUtils.isEmpty(value1)) {
            viewKey.setVisibility(View.GONE);
            viewValue.setVisibility(View.GONE);
        } else {
            viewValue.setText(String.format("%s %s", value1, value2));
        }
    }
}

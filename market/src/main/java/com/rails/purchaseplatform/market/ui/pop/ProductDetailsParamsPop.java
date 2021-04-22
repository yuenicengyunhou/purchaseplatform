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
        binding.tvBrandValue.setText("测试品牌类型");
    }
}

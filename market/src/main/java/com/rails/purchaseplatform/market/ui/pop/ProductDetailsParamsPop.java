package com.rails.purchaseplatform.market.ui.pop;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.forAppShow.ProductSpecificParameter;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.market.adapter.ProDetailsParamsAdapter;
import com.rails.purchaseplatform.market.databinding.PopProductDetailsParamsBinding;

import java.util.ArrayList;

public class ProductDetailsParamsPop extends BasePop<PopProductDetailsParamsBinding> {
    private final String TAG = ProductDetailsParamsPop.class.getSimpleName();

    private ArrayList<ProductSpecificParameter> mParameters;
    private ArrayList<ProductSpecificParameter> mSpecParameters;

    private ProDetailsParamsAdapter adapter;
    private ProDetailsParamsAdapter adapterSpec;

    public ProductDetailsParamsPop() {
        super();
    }

    public ProductDetailsParamsPop(ArrayList<ProductSpecificParameter> parameters, ArrayList<ProductSpecificParameter> specParameters) {
        super();
        mParameters = parameters;
        mSpecParameters = specParameters;
    }

    @Override
    protected void initialize(Bundle bundle) {
        adapter = new ProDetailsParamsAdapter(getActivity());
        binding.brvProductParams.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        binding.brvProductParams.setAdapter(adapter);
        adapter.update(mParameters, true);

        adapterSpec = new ProDetailsParamsAdapter(getActivity());
        binding.brvProductSpecParams.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        binding.brvProductSpecParams.setAdapter(adapterSpec);
        adapterSpec.update(mSpecParameters, true);

        // 关闭按钮
        binding.ibClose.setOnClickListener(v -> dismiss());
        // 确定按钮
        binding.tvConfirm.setOnClickListener(v -> dismiss());

    }

    public void setParameters(ArrayList<ProductSpecificParameter> parameters) {
        this.mParameters = parameters;
        adapter.update(mParameters, true);
    }

    public void setSpecParameters(ArrayList<ProductSpecificParameter> specParameters) {
        this.mSpecParameters = specParameters;
        adapterSpec.update(mSpecParameters, true);
    }
}

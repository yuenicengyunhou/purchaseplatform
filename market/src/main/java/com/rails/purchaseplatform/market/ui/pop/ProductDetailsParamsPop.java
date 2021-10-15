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
        adapter.setMaxKeyLength(getKeyMaxLength(mParameters));
        binding.brvProductParams.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        binding.brvProductParams.setAdapter(adapter);
        adapter.update(mParameters, true);

        adapterSpec = new ProDetailsParamsAdapter(getActivity());
        adapterSpec.setMaxKeyLength(getKeyMaxLength(mSpecParameters));
        binding.brvProductSpecParams.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        binding.brvProductSpecParams.setAdapter(adapterSpec);
        adapterSpec.update(mSpecParameters, true);

        // 关闭按钮
        binding.ibClose.setOnClickListener(v -> dismiss());
        // 确定按钮
        binding.tvConfirm.setOnClickListener(v -> dismiss());

    }

    /**
     * 获取集合中左边字符串，最大的长度值。
     *
     * @param parameters
     * @return
     */
    private int getKeyMaxLength(ArrayList<ProductSpecificParameter> parameters) {
        int keyLength = 0;
        for (ProductSpecificParameter params : parameters) {
//            Log.d(TAG, params.getParamKey() + "----- 长度是 = " + params.getParamKey().length());
            int currentKeyLength = params.getParamKey().length();
            if (currentKeyLength > keyLength) {
                keyLength = currentKeyLength;
            }
        }
        return keyLength;
    }

    public void setParameters(ArrayList<ProductSpecificParameter> parameters) {
        this.mParameters = parameters;
        adapter.setMaxKeyLength(getKeyMaxLength(mParameters));
        adapter.update(mParameters, true);
    }

    public void setSpecParameters(ArrayList<ProductSpecificParameter> specParameters) {
        this.mSpecParameters = specParameters;
        adapter.setMaxKeyLength(getKeyMaxLength(mSpecParameters));
        adapterSpec.update(mSpecParameters, true);
    }
}

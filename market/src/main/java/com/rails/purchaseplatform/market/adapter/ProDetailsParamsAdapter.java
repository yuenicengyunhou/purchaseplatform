package com.rails.purchaseplatform.market.adapter;

import android.content.Context;

import com.rails.lib_data.bean.forAppShow.ProductSpecificParameter;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemProductParamsInpopBinding;

public class ProDetailsParamsAdapter extends BaseRecyclerAdapter<ProductSpecificParameter, ItemProductParamsInpopBinding> {
    private int mMaxKeyLength;

    public ProDetailsParamsAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_product_params_inpop;
    }

    @Override
    protected void onBindItem(ItemProductParamsInpopBinding binding, ProductSpecificParameter productSpecificParameter, int position) {
        binding.setParams(productSpecificParameter);
        if (mMaxKeyLength >= 7) mMaxKeyLength = 6;
        binding.tvParamsKey.setEms(mMaxKeyLength);
    }

    public void setMaxKeyLength(int maxKeyLength) {
        mMaxKeyLength = maxKeyLength;
//        Log.d("===========", "=========" + mMaxKeyLength);
    }
}

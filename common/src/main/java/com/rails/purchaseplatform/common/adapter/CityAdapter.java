package com.rails.purchaseplatform.common.adapter;

import android.content.Context;

import com.rails.lib_data.AddressArea;
import com.rails.purchaseplatform.common.R;
import com.rails.purchaseplatform.common.databinding.ItemAddressCityBinding;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;

/**
 * 城市列表
 *
 * author： sk_comic@163.com
 * date: 2021/4/6
 */
public class CityAdapter extends BaseRecyclerAdapter<AddressArea, ItemAddressCityBinding> {

    public CityAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_address_city;
    }

    @Override
    protected void onBindItem(ItemAddressCityBinding binding, AddressArea bean, int position) {
        binding.tvName.setText(bean.getName());

        binding.getRoot().setOnClickListener(v -> {
            if (positionListener != null) {
                positionListener.onPosition(bean, position);
            }
        });
    }
}

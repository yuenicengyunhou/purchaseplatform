package com.rails.purchaseplatform.address.adapter;

import android.content.Context;
import android.view.View;

import com.rails.purchaseplatform.address.R;
import com.rails.purchaseplatform.address.databinding.ItemAddressCityBinding;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;

/**
 * 城市列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/6
 */
public class CityAdapter extends BaseRecyclerAdapter<String, ItemAddressCityBinding> {

    public CityAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_address_city;
    }

    @Override
    protected void onBindItem(ItemAddressCityBinding binding, String s, int position) {
        binding.tvName.setText(s);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionListener != null) {
                    positionListener.onPosition(s, position);
                }
            }
        });
    }
}

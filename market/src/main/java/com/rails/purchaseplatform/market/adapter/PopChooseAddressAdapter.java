package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.view.View;

import com.rails.lib_data.bean.AddressBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemPopProductDetailsChooseAddressBinding;

import java.util.Objects;

public class PopChooseAddressAdapter extends BaseRecyclerAdapter<AddressBean, ItemPopProductDetailsChooseAddressBinding> {

    private Context mContext;
    private String mCurrentAddress;

    public PopChooseAddressAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public PopChooseAddressAdapter(Context context, String currentAddress) {
        super(context);
        mContext = context;
        mCurrentAddress = currentAddress;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_pop_product_details_choose_address;
    }

    @Override
    protected void onBindItem(ItemPopProductDetailsChooseAddressBinding binding, AddressBean addressBean, int position) {
        if (Objects.equals(addressBean.getFullAddress(), mCurrentAddress)) {
            binding.cbCheck.setVisibility(View.VISIBLE);
        }
        binding.setChooseAddress(addressBean);
        binding.llAddress.setOnClickListener(v -> {
            if (positionListener != null)
                positionListener.onPosition(addressBean, position);
        });
    }
}

package com.rails.purchaseplatform.market.adapter;

import android.content.Context;

import com.rails.lib_data.bean.AddressBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemPopProductDetailsChooseAddressBinding;

public class PopChooseAddressAdapter extends BaseRecyclerAdapter<AddressBean, ItemPopProductDetailsChooseAddressBinding> {

    private Context mContext;

    public PopChooseAddressAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_pop_product_details_choose_address;
    }

    @Override
    protected void onBindItem(ItemPopProductDetailsChooseAddressBinding binding, AddressBean addressBean, int position) {
        // TODO: 2021/4/2 这个Bean要更改为地址列表Bean
        binding.setChooseAddress(addressBean);
        binding.llAddress.setOnClickListener(v -> {
            // TODO: 2021/4/2 选中地址，更改CheckBox状态，然后返回选中的地址信息给Pop
        });
    }
}

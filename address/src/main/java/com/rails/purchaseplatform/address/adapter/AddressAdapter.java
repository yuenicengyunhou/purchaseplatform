package com.rails.purchaseplatform.address.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rails.lib_data.bean.AddressBean;
import com.rails.purchaseplatform.address.R;
import com.rails.purchaseplatform.address.databinding.ItemAddressBinding;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 地址列表adapter
 */
public class AddressAdapter extends BaseRecyclerAdapter<AddressBean, ItemAddressBinding> {

    public AddressAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_address;
    }

    @Override
    protected void onBindItem(ItemAddressBinding binding, AddressBean addressBean, int position) {
        binding.setAddress(addressBean);
    }

}

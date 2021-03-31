package com.rails.purchaseplatform.address.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;

import com.amap.api.services.core.PoiItem;
import com.rails.purchaseplatform.address.R;
import com.rails.purchaseplatform.address.databinding.ItemAddressPoiBinding;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;

public class PoiAdapter extends BaseRecyclerAdapter<PoiItem, ItemAddressPoiBinding> {


    private SparseBooleanArray selects;
    private int lastPosition = -1;


    public PoiAdapter(Context context) {
        super(context);
        selects = new SparseBooleanArray();
    }

    @Override
    protected int getContentID() {
        return R.layout.item_address_poi;
    }

    @Override
    protected void onBindItem(ItemAddressPoiBinding binding, PoiItem poiItem, int position) {
        binding.tvTitle.setText(poiItem.getTitle());
        binding.tvAddress.setText(poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet());

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selects.put(lastPosition, false);
                selects.put(position, true);
                lastPosition = position;
                notifyDataSetChanged();
                if (positionListener != null)
                    positionListener.onPosition(poiItem, position);
            }
        });
    }

}

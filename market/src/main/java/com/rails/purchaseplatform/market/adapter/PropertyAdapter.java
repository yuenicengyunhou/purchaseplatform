package com.rails.purchaseplatform.market.adapter;

import android.content.Context;

import com.rails.purchaseplatform.common.widget.tags.FlowTagLayout;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemProductPropertyBinding;

import java.util.ArrayList;

/**
 * 产品规格adapter
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/8
 */
public class PropertyAdapter extends BaseRecyclerAdapter<String, ItemProductPropertyBinding> {
    public PropertyAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_product_property;
    }

    @Override
    protected void onBindItem(ItemProductPropertyBinding binding, String s, int position) {
        binding.tvName.setText(s);
        PropertySubAdapter adapter = new PropertySubAdapter(mContext);
        if (position % 2 == 0)
            binding.flow.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        else
            binding.flow.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        binding.flow.setAdapter(adapter);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("灰色");
        tags.add("灰色");
        tags.add("灰色");
        tags.add("灰色");
        adapter.update(tags);
    }
}

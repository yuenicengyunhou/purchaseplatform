package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.widget.CompoundButton;

import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.forAppShow.SearchFilterValue;
import com.rails.purchaseplatform.common.widget.tags.FlowTagLayout;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemProductPropertyBinding;

import java.util.ArrayList;

public class SearchItemFilterAdapter extends BaseRecyclerAdapter<SearchFilterBean, ItemProductPropertyBinding> {

    public SearchItemFilterAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_product_property;
    }

    @Override
    protected void onBindItem(ItemProductPropertyBinding binding, SearchFilterBean searchFilterBean, int p) {
        binding.tvName.setText(searchFilterBean.getFilterName());
        SearchItemFilterSubAdapter adapter = new SearchItemFilterSubAdapter(mContext, searchFilterBean.isMultiSelect());
        if (searchFilterBean.isMultiSelect()) {
            binding.flow.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        } else {
            binding.flow.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        }

        binding.cbExpand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.flow.setAdapter(adapter);
                ArrayList<SearchFilterValue> tags;
                if (isChecked) {
                    tags = new ArrayList<>(searchFilterBean.getFilterValues());
                } else {
                    tags = new ArrayList<>();
                }
                adapter.update(tags);
            }
        });

        binding.cbExpand.setChecked(p < 2);
    }

    public ArrayList getData() {
        return mDataSource;
    }
}

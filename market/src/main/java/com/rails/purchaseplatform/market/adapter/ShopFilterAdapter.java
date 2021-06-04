package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.forAppShow.SearchFilterValue;
import com.rails.purchaseplatform.common.widget.tags.FlowTagLayout;
import com.rails.purchaseplatform.common.widget.tags.OnTagClickListener;
import com.rails.purchaseplatform.common.widget.tags.OnTagSelectListener;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemProductPropertyBinding;

import java.util.ArrayList;
import java.util.List;

public class ShopFilterAdapter extends BaseRecyclerAdapter<SearchFilterBean, ItemProductPropertyBinding> {

    public ShopFilterAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_product_property;
    }

    @Override
    protected void onBindItem(ItemProductPropertyBinding binding, SearchFilterBean searchFilterBean, int position) {
        binding.tvName.setText(searchFilterBean.getFilterName());
        ShopFilterChildAdapter adapter = new ShopFilterChildAdapter(mContext);
        boolean multiSelect = searchFilterBean.isMultiSelect();
        if (multiSelect) {
            binding.flow.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        } else {
            binding.flow.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        }
        binding.flow.setOnChildLayoutListener(needShowMore -> binding.cbExpand.setVisibility(needShowMore ? View.VISIBLE : View.GONE));
        binding.flow.setAdapter(adapter);
        ArrayList<SearchFilterValue> tags = new ArrayList<>(searchFilterBean.getFilterValues());
        adapter.update(tags);
        binding.flow.setOnTagClickListener((parent, view, position1) -> {
            SearchFilterValue value = tags.get(position1);
            if (multiSelect) {
                value.setSelect(view.isSelected());
            } else {
                for (int i = 0; i < tags.size(); i++) {
                    if (i == position1) {
                        tags.get(i).setSelect(view.isSelected());
                    } else {
                        tags.get(i).setSelect(false);
                    }
                }
//                for (int i = 0; i < tags.size(); i++) {
//                    if (i == position1) {
//                        value.setSelect(view.isSelected());
//                    } else {
//                        value.setSelect(false);
//                    }
//                }

            }
        });
        binding.flow.setShowMore(true);
        binding.cbExpand.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.flow.setShowMore(isChecked);
            adapter.update(tags);
        });
        binding.cbExpand.setSelected(true);
        binding.cbExpand.setChecked(true);
//        binding.flow.setOnTagSelectListener((parent, selectedList) -> {
//            for (int i = 0; i < selectedList.size(); i++) {
//                Integer integer = selectedList.get(i);
//
//            }
//        });
    }

    public ArrayList<SearchFilterBean> getDataSource() {
        return mDataSource;
    }

    public void resetSelectState() {
        for (SearchFilterBean filterBean : mDataSource) {
            ArrayList<SearchFilterValue> filterValues = filterBean.getFilterValues();
            for (SearchFilterValue value : filterValues) {
                value.setSelect(false);
            }
        }
        notifyDataSetChanged();
    }
}

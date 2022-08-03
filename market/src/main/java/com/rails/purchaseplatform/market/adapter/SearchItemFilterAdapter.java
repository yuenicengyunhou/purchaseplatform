package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;

import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.forAppShow.SearchFilterValue;
import com.rails.purchaseplatform.common.widget.tags.FlowTagLayout;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.loading.LoadingDialog;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemProductPropertyBinding;

import java.util.ArrayList;

public class SearchItemFilterAdapter extends BaseRecyclerAdapter<SearchFilterBean, ItemProductPropertyBinding> {
    final private String TAG = SearchItemFilterAdapter.class.getSimpleName();

    private LoadingDialog mLoadingDialog;

    public SearchItemFilterAdapter(Context context) {
        super(context);
    }


    public SearchItemFilterAdapter(Context context, LoadingDialog dialog) {
        super(context);
        mLoadingDialog = dialog;
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    protected int getContentID() {
        return R.layout.item_product_property;
    }

    @Override
    protected void onBindItem(ItemProductPropertyBinding binding, SearchFilterBean searchFilterBean, int p) {
        if (mDataSource == null || mDataSource.size() <= 4) {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
        } else {
            if (p == 4) {
                if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
            }
        }

        binding.tvName.setText(searchFilterBean.getFilterName());
        SearchItemFilterSubAdapter adapter = new SearchItemFilterSubAdapter(
                mContext,
                searchFilterBean.isMultiSelect(),
                mDataSource.size() - 1 == p);
        ArrayList<SearchFilterValue> tags = new ArrayList<>(searchFilterBean.getFilterValues());

        binding.flow.setTagCheckedMode(searchFilterBean.isMultiSelect()
                ? FlowTagLayout.FLOW_TAG_CHECKED_MULTI
                : FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        binding.flow.setOnChildLayoutListener(needShowMore -> binding.cbExpand.setVisibility(needShowMore ? View.VISIBLE : View.GONE));
        binding.flow.setAdapter(adapter);
        binding.flow.setShowMore(true);

        binding.cbExpand.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.flow.setShowMore(isChecked);
            adapter.update(tags);
        });
        binding.cbExpand.setSelected(true);
        binding.cbExpand.setChecked(true);
        adapter.update(tags);
    }

    public ArrayList getData() {
        return mDataSource;
    }
}

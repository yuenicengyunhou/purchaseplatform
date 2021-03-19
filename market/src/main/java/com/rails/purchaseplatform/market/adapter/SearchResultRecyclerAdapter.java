package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.SearchResultBean;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.BaseMulRecyclerAdapter;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemSearchResultBinding;
import com.rails.purchaseplatform.market.ui.activity.ItemDetailsActivity;

public class SearchResultRecyclerAdapter extends BaseRecyclerAdapter<SearchResultBean, ItemSearchResultBinding> {

    private Context mContext;

    public SearchResultRecyclerAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_search_result;
    }

    @Override
    protected void onBindItem(ItemSearchResultBinding binding, SearchResultBean searchResultBean, int position) {
        binding.setResult(searchResultBean);

//        binding.ivIcon.setImageURI(searchResultBean.getIconUrl());
//        binding.tvName.setText(searchResultBean.getName());
//        binding.tvShop.setText(searchResultBean.getShop());
//        binding.tvPrice.setText(searchResultBean.getPrice());
        binding.llItems.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, ItemDetailsActivity.class));
        });

    }
}

package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;

import com.rails.lib_data.bean.CategorySubBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemCategorySub2Binding;

import java.util.ArrayList;

/**
 * @author： sk_comic@163.com
 * @date: 2021/3/2
 */
public class CategorySubAdapter extends BaseRecyclerAdapter<CategorySubBean, ItemCategorySub2Binding> {

    private boolean isExpand = false;

    public CategorySubAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_category_sub_2;
    }

    @Override
    protected void onBindItem(ItemCategorySub2Binding binding, CategorySubBean categoryBean, int position) {
        binding.setSub(categoryBean);

        String fid = categoryBean.getFcid();
        if (TextUtils.isEmpty(fid)) {
            Drawable right;
            if (isExpand) {
                right = mContext.getResources().getDrawable(R.drawable.ic_category_down);
            } else {
                right = mContext.getResources().getDrawable(R.drawable.ic_category_up);
            }
            binding.tvName.setCompoundDrawablesWithIntrinsicBounds(null, null, right, null);
        }else{
            binding.tvName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionListener != null) {
                    positionListener.onPosition(categoryBean, position);
                }
            }
        });
    }


    public void updateData(ArrayList<CategorySubBean> itemDatas, boolean isClear, boolean isExpand) {
        this.isExpand = isExpand;
        if (itemDatas == null)
            return;
        if (isClear)
            this.mDataSource.clear();

        if (itemDatas.isEmpty()) {
            this.notifyDataSetChanged();
            return;
        }
        this.mDataSource.addAll(itemDatas);
        this.notifyDataSetChanged();
    }
}
package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.view.View;

import com.rails.lib_data.bean.CategorySubBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemCategorySub2Binding;

/**
 * @author： sk_comic@163.com
 * @date: 2021/3/2
 */
public class CategorySub2Adapter extends BaseRecyclerAdapter<CategorySubBean, ItemCategorySub2Binding> {
    public CategorySub2Adapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_category_sub_2;
    }

    @Override
    protected void onBindItem(ItemCategorySub2Binding binding, CategorySubBean categoryBean, int position) {
        binding.setSub(categoryBean);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionListener != null) {
                    positionListener.onPosition(categoryBean, position);
                }
            }
        });
    }

}

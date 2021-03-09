package com.rails.purchaseplatform.market.adapter;

import android.content.Context;

import com.rails.lib_data.bean.CategorySubBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemCategorySubBinding;

/**
 * @author： sk_comic@163.com
 * @date: 2021/3/2
 */
public class CategorySubAdapter extends BaseRecyclerAdapter<CategorySubBean, ItemCategorySubBinding> {
    public CategorySubAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_category_sub;
    }

    @Override
    protected void onBindItem(ItemCategorySubBinding binding, CategorySubBean categoryBean, int position) {
        binding.setSub(categoryBean);
    }

}

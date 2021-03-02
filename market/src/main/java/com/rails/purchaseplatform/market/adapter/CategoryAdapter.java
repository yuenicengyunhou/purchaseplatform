package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.view.View;

import com.rails.lib_data.bean.CategoryBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemCategoryBinding;

/**
 * 一级分类列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/26
 */
public class CategoryAdapter extends BaseRecyclerAdapter<CategoryBean, ItemCategoryBinding> {

    CategoryBean lastBean;

    public CategoryAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_category;
    }

    @Override
    protected void onBindItem(ItemCategoryBinding binding, CategoryBean categoryBean, int position) {
        binding.setCategory(categoryBean);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastBean != null) {
                    lastBean.isSel.set(false);
                }
                categoryBean.isSel.set(true);
                lastBean = categoryBean;

                if (positionListener != null){
                    positionListener.onPosition(categoryBean,position);
                }
            }
        });
    }
}

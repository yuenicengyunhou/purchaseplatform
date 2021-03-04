package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.view.View;

import com.rails.lib_data.bean.CategoryBean;
import com.rails.lib_data.bean.CategoryRootBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemCategoryRootBinding;

import java.util.ArrayList;

/**
 * 一级分类列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/26
 */
public class CategoryRootAdapter extends BaseRecyclerAdapter<CategoryRootBean, ItemCategoryRootBinding> {

    CategoryRootBean lastBean;

    public CategoryRootAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_category_root;
    }

    @Override
    protected void onBindItem(ItemCategoryRootBinding binding, CategoryRootBean categoryBean, int position) {
        binding.setCategory(categoryBean);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastBean != null) {
                    lastBean.isSel.set(false);
                }
                categoryBean.isSel.set(true);
                lastBean = categoryBean;

                if (positionListener != null) {
                    positionListener.onPosition(categoryBean, position);
                }
            }
        });
    }


    public void setLastBean(int position) {
        if (position < mDataSource.size()) {
            if (lastBean != null)
                lastBean.isSel.set(false);
            lastBean = mDataSource.get(position);
            lastBean.isSel.set(true);
        }
    }


    @Override
    public void update(ArrayList itemDatas, boolean isClear) {
        if (!itemDatas.isEmpty()) {
            lastBean = (CategoryRootBean) itemDatas.get(0);
            lastBean.isSel.set(true);
        }
        super.update(itemDatas, isClear);

    }
}

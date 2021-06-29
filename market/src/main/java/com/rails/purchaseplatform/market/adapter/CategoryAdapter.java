package com.rails.purchaseplatform.market.adapter;

import android.content.Context;

import com.rails.lib_data.bean.CategoryBean;
import com.rails.lib_data.bean.CategorySubBean;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemCategoryBinding;
import com.rails.purchaseplatform.market.widget.CategoryDecoration;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 分类
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/2
 */
public class CategoryAdapter extends BaseRecyclerAdapter<CategoryBean, ItemCategoryBinding> {


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
        CategorySubAdapter adapter = new CategorySubAdapter(mContext);
        binding.recycler.setAdapter(adapter);
        adapter.update(categoryBean.getThirdPlatformCategoryList(), true);
        adapter.setListener(new PositionListener<CategorySubBean>() {
            @Override
            public void onPosition(CategorySubBean bean, int position) {
                if (positionListener != null)
                    positionListener.onPosition(bean, position);
            }
        });

    }


    @Override
    protected void onBindView(ItemCategoryBinding binding) {
        super.onBindView(binding);
        binding.recycler.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 3);
        binding.recycler.addItemDecoration(new CategoryDecoration(mContext));
    }
}

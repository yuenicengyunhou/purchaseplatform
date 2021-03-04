package com.rails.purchaseplatform.market.adapter;

import android.content.Context;

import com.rails.lib_data.bean.CategoryBean;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceGirdWeightDecoration;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemCategoryBinding;

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
        binding.recycler.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 3);
//        binding.recycler.addItemDecoration(new SpaceGirdWeightDecoration(mContext,10,10,10,10,android.R.color.white));
        binding.recycler.setAdapter(adapter);
        adapter.update(categoryBean.getThirdPlatformCategoryList(), true);

    }

}

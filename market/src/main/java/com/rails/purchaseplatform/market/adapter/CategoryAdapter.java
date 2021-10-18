package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseBooleanArray;

import com.rails.lib_data.bean.CategoryBean;
import com.rails.lib_data.bean.CategorySubBean;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemCategoryBinding;
import com.rails.purchaseplatform.market.widget.CategoryDecoration;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 分类
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/2
 */
public class CategoryAdapter extends BaseRecyclerAdapter<CategoryBean, ItemCategoryBinding> {

    private SparseBooleanArray sels;

    public CategoryAdapter(Context context) {
        super(context);
        sels = new SparseBooleanArray();
    }


    @Override
    protected int getContentID() {
        return R.layout.item_category;
    }

    @Override
    protected void onBindItem(ItemCategoryBinding binding, CategoryBean categoryBean, int item) {
        binding.setCategory(categoryBean);
        CategorySubAdapter adapter = new CategorySubAdapter(mContext);
        binding.recycler.setAdapter(adapter);


        ArrayList<CategorySubBean> categorySubBeans = categoryBean.getThirdPlatformCategoryList();
        adapter.updateData(getChilds(categorySubBeans, sels.get(item)), true, !sels.get(item));
        adapter.setListener(new PositionListener<CategorySubBean>() {
            @Override
            public void onPosition(CategorySubBean bean, int position) {
                if (positionListener != null) {
                    if (!TextUtils.isEmpty(bean.getFcid())) {
                        positionListener.onPosition(bean, position);
                    } else {
                        adapter.updateData(getChilds(categorySubBeans, !sels.get(item)), true, sels.get(item));
                        sels.put(item, !sels.get(item));
                    }
                }

            }
        });

    }


    @Override
    protected void onBindView(ItemCategoryBinding binding) {
        super.onBindView(binding);
        binding.recycler.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 3);
        binding.recycler.addItemDecoration(new CategoryDecoration(mContext));
    }


    /**
     * @param totalBeans
     * @param isExpand   是否展开
     * @return
     */
    private ArrayList<CategorySubBean> getChilds(ArrayList<CategorySubBean> totalBeans, boolean isExpand) {
        ArrayList<CategorySubBean> childBeans = new ArrayList<>();
        if (totalBeans == null) {
            return childBeans;
        }
        if (totalBeans.size() > 9) {
            if (isExpand) {
                childBeans.addAll(totalBeans);
                childBeans.add(new CategorySubBean("收起", R.drawable.ic_category_up));
            } else {
                for (int i = 0; i < 8; i++) {
                    childBeans.add(totalBeans.get(i));
                }
                childBeans.add(new CategorySubBean("展开", R.drawable.ic_category_down));
            }

        } else {
            childBeans.addAll(totalBeans);
        }
        return childBeans;
    }


}

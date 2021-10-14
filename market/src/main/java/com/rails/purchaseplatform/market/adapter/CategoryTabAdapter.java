package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.rails.lib_data.bean.CategoryBean;
import com.rails.lib_data.bean.CategoryRootBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemCategoryTabBinding;

import java.util.ArrayList;

/**
 * 分类标签列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/2
 */
public class CategoryTabAdapter extends BaseRecyclerAdapter<CategoryBean, ItemCategoryTabBinding> {

    CategoryBean lastBean;

    public CategoryTabAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_category_tab;
    }

    @Override
    protected void onBindItem(ItemCategoryTabBinding binding, CategoryBean categoryBean, int position) {
        binding.setCategory(categoryBean);

//        try{
//            if (categoryBean.isSel.get()){
//                binding.tvName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//            }else{
//                binding.tvName.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//            }
//        }catch (Exception e){
//
//        }


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


    public void updateData(ArrayList<CategoryBean> itemDatas, boolean isClear) {
        if (itemDatas ==null)
            return;
        if (!itemDatas.isEmpty()) {
            for (int i = 0; i < itemDatas.size(); i++) {
                if (i == 0) {
                    itemDatas.get(i).isSel.set(true);
                    lastBean = itemDatas.get(0);
                } else {
                    itemDatas.get(i).isSel.set(false);
                }
            }
        }
        super.update(itemDatas, isClear);
    }
}

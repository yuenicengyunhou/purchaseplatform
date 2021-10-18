package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.view.View;

import com.rails.lib_data.bean.ProductRecBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemFloorTabBinding;

import java.util.ArrayList;

/**
 * 分类标签列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/2
 */
public class FloorTabAdapter extends BaseRecyclerAdapter<ProductRecBean, ItemFloorTabBinding> {

    ProductRecBean lastBean;

    public FloorTabAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_floor_tab;
    }

    @Override
    protected void onBindItem(ItemFloorTabBinding binding, ProductRecBean productRecBean, int position) {
        binding.setFloor(productRecBean);


        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastBean != null) {
                    lastBean.isSel.set(false);
                }
                productRecBean.isSel.set(true);
                lastBean = productRecBean;

                if (positionListener != null) {
                    positionListener.onPosition(productRecBean, position);
                }
            }
        });
    }


    public void setLastBean(int position) {
        try{
            if (position < mDataSource.size()) {
                if (lastBean != null)
                    lastBean.isSel.set(false);
                lastBean = mDataSource.get(position);
                lastBean.isSel.set(true);
            }
        }catch (Exception e){}

    }


    public void updateData(ArrayList<ProductRecBean> itemDatas, boolean isClear) {
        if (itemDatas == null)
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

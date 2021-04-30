package com.rails.purchaseplatform.market.adapter;

import android.content.Context;
import android.view.View;

import com.rails.lib_data.bean.forAppShow.SpecificationPopBean;
import com.rails.lib_data.bean.forAppShow.SpecificationValue;
import com.rails.purchaseplatform.common.widget.tags.FlowTagLayout;
import com.rails.purchaseplatform.common.widget.tags.OnTagClickListener;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ItemProductPropertyBinding;

import java.util.ArrayList;

/**
 * 产品规格adapter
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/8
 */
public class PropertyAdapter extends BaseRecyclerAdapter<SpecificationPopBean, ItemProductPropertyBinding> {

    private ArrayList<SpecificationPopBean> mBeans;

    public PropertyAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_product_property;
    }

    @Override
    protected void onBindItem(ItemProductPropertyBinding binding, SpecificationPopBean specificationPopBean, int p) {
        binding.tvName.setText(specificationPopBean.getAttrName());
        PropertySubAdapter adapter = new PropertySubAdapter(mContext);
        binding.flow.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        binding.flow.setAdapter(adapter);
        ArrayList<SpecificationValue> tags = new ArrayList<>(specificationPopBean.getSpecificationValue());
        adapter.update(tags);

        for (SpecificationValue valueBean : tags) {
            if (valueBean.isSelect())
                binding.flow.selectSingleCheck(tags.indexOf(valueBean));
        }


        binding.flow.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                boolean b = tags.get(position).isSelect(); // 点击位置选中状态
                int checkedIndex = 0; // 默认选中的位置
                for (SpecificationValue valueBean : tags) {
                    if (valueBean.isSelect())
                        checkedIndex = tags.indexOf(valueBean);
                }
                if (b) {
                    mBeans.get(p).getSpecificationValue().get(position).setSelect(false);
                } else {
                    mBeans.get(p).getSpecificationValue().get(position).setSelect(true);
                    mBeans.get(p).getSpecificationValue().get(checkedIndex).setSelect(false);
                }
            }
        });
    }

    public void setData(ArrayList<SpecificationPopBean> beans) {
        mBeans = beans;
    }

    public ArrayList<SpecificationPopBean> getData() {
        return mBeans;
    }
}

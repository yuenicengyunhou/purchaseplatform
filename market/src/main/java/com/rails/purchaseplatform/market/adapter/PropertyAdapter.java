package com.rails.purchaseplatform.market.adapter;

import android.content.Context;

import com.rails.lib_data.bean.forAppShow.SpecificationPopBean;
import com.rails.lib_data.bean.forAppShow.SpecificationValue;
import com.rails.purchaseplatform.common.widget.tags.FlowTagLayout;
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
    public PropertyAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_product_property;
    }

    @Override
    protected void onBindItem(ItemProductPropertyBinding binding, SpecificationPopBean specificationPopBean, int position) {
        binding.tvName.setText(specificationPopBean.getAttrName());
        PropertySubAdapter adapter = new PropertySubAdapter(mContext);
        binding.flow.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        binding.flow.setAdapter(adapter);
        ArrayList<SpecificationValue> tags = new ArrayList<>(specificationPopBean.getSpecificationValue());
        adapter.update(tags);
    }
}

package com.rails.purchaseplatform.order.adapter;

import android.content.Context;

import com.rails.lib_data.bean.BuyerBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemConditionBinding;


public class ConditionAdapter extends BaseRecyclerAdapter<BuyerBean, ItemConditionBinding> {

    private int type = 0;
    private ChooseListener chooseListener;

    public void setChooseListener(ChooseListener chooseListener) {
        this.chooseListener = chooseListener;
    }

    public ConditionAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_condition;
    }

    @Override
    protected void onBindItem(ItemConditionBinding binding, BuyerBean buyerBean, int position) {
        String realName = buyerBean.getAccountName();
        String supplierName = buyerBean.getSupplierName();
        String skuName = buyerBean.getSkuName();
        String brandNameCh = buyerBean.getBrandNameCh();
        String brandNameEn = buyerBean.getBrandNameEn();
        String name = "";
        String id = "";
        if (type == 1) {
            name = realName;
            id = buyerBean.getId();
        } else if (type == 2) {
            name = supplierName;
            id = buyerBean.getSupplierId();
        } else if (type == 5) {
            name = skuName;
            id = buyerBean.getSkuId();
        } else {
            name = brandNameCh + "(" + brandNameEn + ")";
            id = buyerBean.getId();
        }
//        String name = null == supplierName ? realName : supplierName;
        binding.tvName.setText(name);

        String finalName = name;
        String finalId = id;
        binding.tvName.setOnClickListener(v -> {
            if (null != chooseListener) {
                chooseListener.onChoosing(finalName, finalId);
            }
        });
    }

    public void setType(int type) {
        this.type = type;
    }

    public interface ChooseListener{
        void onChoosing(String name, String id);
    }
}

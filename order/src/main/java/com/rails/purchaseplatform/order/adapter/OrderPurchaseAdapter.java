package com.rails.purchaseplatform.order.adapter;

import android.content.Context;

import com.rails.lib_data.bean.OrderPurchaseBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemOrderPurchaseBinding;

import java.util.ArrayList;

/**
 * 采购商列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/28
 */
public class OrderPurchaseAdapter extends BaseRecyclerAdapter<OrderPurchaseBean, ItemOrderPurchaseBinding> {

    private OrderPurchaseBean lastBean;

    public OrderPurchaseAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_order_purchase;
    }

    @Override
    protected void onBindItem(ItemOrderPurchaseBinding binding, OrderPurchaseBean orderPurchaseBean, int position) {
        binding.setPurchase(orderPurchaseBean);
        binding.tvNuit.setText(orderPurchaseBean.getAccountingType()== 1?"集团公司集中结算":"本单位结算");

        binding.getRoot().setOnClickListener(v -> {
            if (positionListener != null)
                positionListener.onPosition(orderPurchaseBean, position);
        });

        binding.btnParent.setOnClickListener(v -> {
            if (positionListener != null)
                positionListener.onPosition(orderPurchaseBean, position);
        });
    }


    @Override
    public void update(ArrayList itemDatas, boolean isClear) {
        super.update(itemDatas, isClear);
        if (isClear && !itemDatas.isEmpty()) {
            lastBean = (OrderPurchaseBean) itemDatas.get(0);
            lastBean.isSel.set(true);
        }
    }


    public void setSelPosition(OrderPurchaseBean bean) {
        for (OrderPurchaseBean addressBean : mDataSource) {
            if (bean.getId().equals(addressBean.getId()) && (bean.getAccountingType() == addressBean.getAccountingType())) {
                addressBean.isSel.set(true);
                break;
            } else {
                addressBean.isSel.set(false);
            }
        }
    }

}

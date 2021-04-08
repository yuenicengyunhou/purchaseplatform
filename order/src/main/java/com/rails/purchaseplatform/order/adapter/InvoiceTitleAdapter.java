package com.rails.purchaseplatform.order.adapter;

import android.content.Context;
import android.view.View;

import com.rails.lib_data.bean.InvoiceTitleBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemOrderInvoiceTitleBinding;

/**
 * 发票title列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/8
 */
public class InvoiceTitleAdapter extends BaseRecyclerAdapter<InvoiceTitleBean, ItemOrderInvoiceTitleBinding> {
    public InvoiceTitleAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_order_invoice_title;
    }

    @Override
    protected void onBindItem(ItemOrderInvoiceTitleBinding binding, InvoiceTitleBean invoiceTitleBean, int position) {
        binding.setContent(invoiceTitleBean);
        if (position != 0)
            binding.tvModule.setVisibility(View.GONE);
        else
            binding.tvModule.setVisibility(View.VISIBLE);

        if (position == mDataSource.size() - 1) {
            binding.line.setVisibility(View.GONE);
        } else {
            binding.line.setVisibility(View.VISIBLE);
        }
    }

}

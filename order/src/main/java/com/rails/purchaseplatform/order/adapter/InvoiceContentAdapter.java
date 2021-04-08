package com.rails.purchaseplatform.order.adapter;

import android.content.Context;
import android.view.View;

import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.lib_data.bean.InvoiceContentBean;
import com.rails.purchaseplatform.order.databinding.ItemOrderInvoiceRadioBinding;

/**
 * 发票页面顶部内容选择adapter
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/8
 */
public class InvoiceContentAdapter extends BaseRecyclerAdapter<InvoiceContentBean, ItemOrderInvoiceRadioBinding> {
    public InvoiceContentAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_order_invoice_radio;
    }

    @Override
    protected void onBindItem(ItemOrderInvoiceRadioBinding binding, InvoiceContentBean bean, int position) {
        binding.setContent(bean);
        if (position != 0)
            binding.tvModule.setVisibility(View.GONE);
        else
            binding.tvModule.setVisibility(View.VISIBLE);
    }


}

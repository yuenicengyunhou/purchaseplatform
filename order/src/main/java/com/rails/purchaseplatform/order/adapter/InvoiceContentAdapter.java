package com.rails.purchaseplatform.order.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;

import com.rails.lib_data.bean.InvoiceTitleBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.lib_data.bean.InvoiceContentBean;
import com.rails.purchaseplatform.order.databinding.ItemOrderInvoiceRadioBinding;

import java.util.ArrayList;

/**
 * 发票页面顶部内容选择adapter
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/8
 */
public class InvoiceContentAdapter extends BaseRecyclerAdapter<InvoiceContentBean, ItemOrderInvoiceRadioBinding> {


    private InvoiceContentBean lastBean;

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

        if (position == mDataSource.size() - 1) {
            binding.line.setVisibility(View.GONE);
        } else {
            binding.line.setVisibility(View.VISIBLE);
        }

        binding.imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastBean != bean) {
                    lastBean.isSel.set(false);
                    bean.isSel.set(true);
                    lastBean = bean;
                } else {
                    bean.isSel.set(true);
                }

            }
        });

    }


    @Override
    public void update(ArrayList itemDatas, boolean isClear) {
        super.update(itemDatas, isClear);
        InvoiceContentBean bean;
        if (isClear && !itemDatas.isEmpty()) {
            lastBean = (InvoiceContentBean) itemDatas.get(0);
            lastBean.isSel.set(true);
        }
    }


    public InvoiceContentBean getLastBean() {
        return lastBean;
    }
}

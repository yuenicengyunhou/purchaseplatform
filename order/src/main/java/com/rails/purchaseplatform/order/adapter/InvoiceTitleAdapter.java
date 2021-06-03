package com.rails.purchaseplatform.order.adapter;

import android.content.Context;
import android.view.View;

import com.rails.lib_data.bean.InvoiceContentBean;
import com.rails.lib_data.bean.InvoiceTitleBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemOrderInvoiceTitleBinding;

import java.util.ArrayList;

/**
 * 发票title列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/8
 */
public class InvoiceTitleAdapter extends BaseRecyclerAdapter<InvoiceTitleBean, ItemOrderInvoiceTitleBinding> {

    private InvoiceTitleBean lastBean;

    public InvoiceTitleAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_order_invoice_title;
    }

    @Override
    protected void onBindItem(ItemOrderInvoiceTitleBinding binding, InvoiceTitleBean bean, int position) {
        binding.setContent(bean);
        if (position != 0)
            binding.tvModule.setVisibility(View.GONE);
        else {
            binding.tvModule.setVisibility(View.VISIBLE);
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


        if (position == mDataSource.size() - 1) {
            binding.line.setVisibility(View.GONE);
        } else {
            binding.line.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void update(ArrayList itemDatas, boolean isClear) {
        super.update(itemDatas, isClear);
        lastBean = null;
        if (isClear && !itemDatas.isEmpty()) {
            lastBean = (InvoiceTitleBean) itemDatas.get(0);
            lastBean.isSel.set(true);
        }
    }


    public InvoiceTitleBean getLastBean() {
        return lastBean;
    }
}

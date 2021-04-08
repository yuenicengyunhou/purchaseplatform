package com.rails.purchaseplatform.order.activity;

import android.os.Bundle;

import com.rails.lib_data.contract.InvoiceContract;
import com.rails.lib_data.contract.InvoicePresenterImpl;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.adapter.InvoiceContentAdapter;
import com.rails.lib_data.bean.InvoiceContentBean;
import com.rails.purchaseplatform.order.databinding.ActivityOrderInvoiceBinding;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 发票页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/7
 */
public class InvoiceActivity extends ToolbarActivity<ActivityOrderInvoiceBinding> implements InvoiceContract.InvoiceView {

    private InvoiceContentAdapter typeAdapter, contentAdapter;
    private InvoiceContract.InvoicePresenter presenter;

    @Override
    protected void initialize(Bundle bundle) {

        binding.titleBar.setTitleBar(R.string.order_invoice)
                .setImgLeftRes(R.drawable.svg_back_black)
                .setShowLine(true);


        typeAdapter = new InvoiceContentAdapter(this);
        barBinding.typeRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        barBinding.typeRecycler.setAdapter(typeAdapter);

        contentAdapter = new InvoiceContentAdapter(this);
        barBinding.contentRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        barBinding.contentRecycler.setAdapter(contentAdapter);


        presenter = new InvoicePresenterImpl(this, this);
        presenter.getInvoiceContents();
    }

    @Override
    protected int getColor() {
        return android.R.color.white;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void getInvoiceContents(ArrayList<InvoiceContentBean> types, ArrayList<InvoiceContentBean> contents) {
        typeAdapter.update(types, true);
        contentAdapter.update(contents, true);
    }
}

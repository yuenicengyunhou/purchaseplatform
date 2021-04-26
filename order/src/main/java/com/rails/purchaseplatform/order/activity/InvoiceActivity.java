package com.rails.purchaseplatform.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.InvoiceContentBean;
import com.rails.lib_data.bean.InvoiceTitleBean;
import com.rails.lib_data.bean.ListBeen;
import com.rails.lib_data.contract.AddressToolContract;
import com.rails.lib_data.contract.AddressToolPresenterImpl;
import com.rails.lib_data.contract.InvoiceContract;
import com.rails.lib_data.contract.InvoicePresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.adapter.InvoiceContentAdapter;
import com.rails.purchaseplatform.order.adapter.InvoiceTitleAdapter;
import com.rails.purchaseplatform.order.databinding.ActivityOrderInvoiceBinding;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 发票页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/7
 */
public class InvoiceActivity extends ToolbarActivity<ActivityOrderInvoiceBinding> implements
        InvoiceContract.InvoiceView, AddressToolContract.AddressToolView {

    private InvoiceContentAdapter typeAdapter, contentAdapter;
    private InvoiceTitleAdapter titleAdapter;
    private InvoiceContract.InvoicePresenter presenter;
    private AddressToolContract.AddressToolPresenter addressToolPresenter;
    private int DEF_PAGE = 1;
    private int page = DEF_PAGE;
    private AddressBean addressBean;
    //1：普通发票   2：增值税发票
    private int invoiceType = 2;
    private String addressType = "2";


    @Override
    protected void initialize(Bundle bundle) {

        binding.titleBar.setTitleBar(R.string.order_invoice)
                .setImgLeftRes(R.drawable.svg_back_black)
                .setShowLine(true);

        barBinding.smart.setEnableLoadMore(false);
        barBinding.smart.setEnableRefresh(false);

        typeAdapter = new InvoiceContentAdapter(this);
        typeAdapter.setListener(new PositionListener<InvoiceContentBean>() {
            @Override
            public void onPosition(InvoiceContentBean bean, int position) {
                if (bean.getType() == 1)
                    presenter.getInvoiceContents(true);
                else
                    presenter.getInvoiceContents(false);
                presenter.getInvoiceTitles(true, page, bean.getType());
            }
        });
        barBinding.typeRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        barBinding.typeRecycler.setAdapter(typeAdapter);

        contentAdapter = new InvoiceContentAdapter(this);
        barBinding.contentRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        contentAdapter.setListener(new PositionListener<InvoiceContentBean>() {
            @Override
            public void onPosition(InvoiceContentBean bean, int position) {

            }
        });
        barBinding.contentRecycler.setAdapter(contentAdapter);

        titleAdapter = new InvoiceTitleAdapter(this);
        barBinding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        barBinding.recycler.setAdapter(titleAdapter);


        presenter = new InvoicePresenterImpl(this, this);
        addressToolPresenter = new AddressToolPresenterImpl(this, this);

        addressToolPresenter.getDefAddress("20", addressType);

        presenter.getInvoiceContents();
        presenter.getInvoiceContents(false);
        presenter.getInvoiceTitles(true, page, invoiceType);

        if (addressBean != null) {
            setAddress(addressBean);
        }
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
        if (types != null)
            typeAdapter.update(types, true);

        if (contents != null)
            contentAdapter.update(contents, true);
    }

    @Override
    public void getInvoiceTitles(ListBeen<InvoiceTitleBean> listBeen) {
        titleAdapter.update(listBeen.getList(), true);
    }


    /**
     * 设置收货地址
     *
     * @param bean
     */
    private void setAddress(AddressBean bean) {
        if (bean == null) {
            barBinding.btnAddress.setVisibility(View.VISIBLE);
            barBinding.llAddress.setVisibility(View.INVISIBLE);
        } else {
            barBinding.btnAddress.setVisibility(View.GONE);
            barBinding.llAddress.setVisibility(View.VISIBLE);

            barBinding.tvArea.setText(bean.getFullAddress());
            barBinding.tvAddress.setText(bean.getFullAddress());
            barBinding.tvPhone.setText(String.format(getResources().getString(R.string.order_verify_np), bean.getReceiverName(), bean.getPhone()));
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            if (data == null)
                return;
            AddressBean bean = (AddressBean) data.getExtras().getSerializable("bean");
            setAddress(bean);
        }
    }


    @Override
    protected void onClick() {
        super.onClick();
        barBinding.llAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ConRoute.ADDRESS.ADDRESS_SEL).withString("type", addressType).navigation(InvoiceActivity.this, 0);
            }
        });

        barBinding.btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ConRoute.ADDRESS.ADDRESS_SEL).navigation(InvoiceActivity.this, 0);
            }
        });

        barBinding.btnAdd.setOnClickListener(v -> {
            InvoiceTitleBean bean = titleAdapter.getLastBean();
            if (bean == null) {
                ToastUtil.showCenter(InvoiceActivity.this, "请选择发票抬头");
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("invoiceBean", bean);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    @Override
    public void getAddress(ArrayList<AddressBean> addressBeans) {

    }

    @Override
    public void getDefAddress(AddressBean bean) {
        setAddress(bean);
    }
}

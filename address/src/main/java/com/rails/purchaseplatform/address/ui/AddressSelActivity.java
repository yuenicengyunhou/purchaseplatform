package com.rails.purchaseplatform.address.ui;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.contract.AddressContract;
import com.rails.lib_data.contract.AddressPresenterImpl;
import com.rails.purchaseplatform.address.R;
import com.rails.purchaseplatform.address.adapter.AddressAdapter;
import com.rails.purchaseplatform.address.databinding.ActivityAddressSelBinding;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.framwork.adapter.listener.MulPositionListener;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 选择地址
 *
 * author： sk_comic@163.com
 * date: 2021/3/28
 */
@Route(path = ConRoute.ADDRESS.ADDRESS_SEL)
public class AddressSelActivity extends ToolbarActivity<ActivityAddressSelBinding> implements
        AddressContract.AddressView, MulPositionListener<AddressBean>, PositionListener<AddressBean> {

    private AddressAdapter addressAdapter;
    private AddressContract.AddressPresenter presenter;

    @Override
    protected void initialize(Bundle bundle) {

        binding.titleBar
                .setTitleBar(R.string.address_main)
                .setShowLine(true)
                .setImgLeftRes(R.drawable.svg_back_black);


        barBinding.smart.setEnableLoadMore(false);
        addressAdapter = new AddressAdapter(this);
        addressAdapter.setMulPositionListener(this);
        addressAdapter.setListener(this);
        barBinding.recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        barBinding.recycler.setAdapter(addressAdapter);


        presenter = new AddressPresenterImpl(this, this);
        onRefresh();

    }


    /**
     * 刷新请求
     */
    private void onRefresh() {
        barBinding.smart.setOnRefreshListener(refreshLayout -> {
            barBinding.smart.finishRefresh();
            presenter.getAddresses(false);
        });
        presenter.getAddresses(true);
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
    public void getResult(int type, int position, String msg) {

    }

    @Override
    public void getAddresses(ArrayList<AddressBean> addressBeans) {
        addressAdapter.update(addressBeans, true);
    }

    @Override
    public void deleteAddressSuccess(int position) {

    }

    @Override
    public void onPosition(AddressBean bean, int position, int... params) {
        Intent intent = new Intent();
        intent.putExtra("bean", bean);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onPosition(AddressBean bean, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        startIntent(AddressAddActivity.class, bundle);
    }


    @Override
    protected void onClick() {
        super.onClick();
        barBinding.btnAdd.setOnClickListener(v -> startIntent(AddressAddActivity.class));
    }
}

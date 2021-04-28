package com.rails.purchaseplatform.order.pop;

import android.os.Bundle;
import android.view.View;

import com.rails.lib_data.bean.OrderPurchaseBean;
import com.rails.lib_data.bean.OrderVerifyBean;
import com.rails.lib_data.contract.OrderVerifyContract;
import com.rails.lib_data.contract.OrderVerifyPresenterImpl;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.order.adapter.OrderPurchaseAdapter;
import com.rails.purchaseplatform.order.databinding.ItemOrderRecyclerTitleBinding;
import com.rails.purchaseplatform.order.databinding.PopVerifyCompanyBinding;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 收货单位弹窗
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/29
 */
public class CompanyPop extends BasePop<PopVerifyCompanyBinding> implements OrderVerifyContract.OrderVerifyView, PositionListener<OrderPurchaseBean> {

    private OrderVerifyContract.OrderVerifyPresenter presenter;
    private OrderPurchaseAdapter adapter;

    private OrderPurchaseBean purchaseBean;
    private CompanyListener listener;

    public void setListener(CompanyListener listener) {
        this.listener = listener;
    }

    @Override
    protected void initialize(Bundle bundle) {
        purchaseBean = getArguments().getParcelable("bean");

        adapter = new OrderPurchaseAdapter(getActivity());
        adapter.setListener(this);
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.recycler.setAdapter(adapter);

        presenter = new OrderVerifyPresenterImpl(getActivity(), this);
        presenter.getPurchases();
        onClick();
    }


    void onClick() {
        binding.btnClose.setOnClickListener(v -> {
            dismiss();
        });
    }

    @Override
    public void getVerifyOrder(OrderVerifyBean bean) {

    }

    @Override
    public void getOrderToken(String token) {

    }

    @Override
    public void getPurchases(ArrayList<OrderPurchaseBean> purchaseBeans) {
        adapter.update(purchaseBeans, true);
        if (purchaseBean != null) {
            adapter.setSelPosition(purchaseBean);
        }
    }

    @Override
    public void getResult(String msg, String data) {

    }

    @Override
    public void onError(ErrorBean errorBean) {

    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void showResDialog(int res) {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void onPosition(OrderPurchaseBean bean, int position) {
        if (listener != null)
            listener.setBean(bean);
        dismiss();
    }


    public interface CompanyListener {

        void setBean(OrderPurchaseBean bean);
    }
}

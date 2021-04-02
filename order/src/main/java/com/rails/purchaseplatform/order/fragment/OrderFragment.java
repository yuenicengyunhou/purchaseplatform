package com.rails.purchaseplatform.order.fragment;

import com.rails.lib_data.bean.OrderBean;
import com.rails.lib_data.contract.OrderContract;
import com.rails.lib_data.contract.OrderPresenterImpl;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.order.adapter.OrderRecyclerAdapter;
import com.rails.purchaseplatform.order.databinding.FragmentMyBinding;
import com.rails.purchaseplatform.order.databinding.FragmentOrderBinding;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 采购单列表
 */
public class OrderFragment extends LazyFragment<FragmentOrderBinding> implements OrderContract.OrderView {

    private OrderRecyclerAdapter mAdapter;
    private OrderContract.OrderPresenter mPresenter;

    private static int status;

    private OrderFragment(int status) {
        this.status = status;
    }

    public static OrderFragment getInstance(int status) {
        return new OrderFragment(status);

    }

    @Override
    protected void loadData() {

        mAdapter = new OrderRecyclerAdapter(getActivity());
        mPresenter = new OrderPresenterImpl(getActivity(), this);
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        binding.recycler.setAdapter(mAdapter);
        mPresenter.getOrder(false, 1);
    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void getOrder(ArrayList<OrderBean> orderBeans, boolean hasMore, boolean isClear) {
        mAdapter.update(orderBeans, true);
    }
}
package com.rails.purchaseplatform.order.fragment;

import com.rails.lib_data.bean.OrderParentBean;
import com.rails.lib_data.contract.OrderContract;
import com.rails.lib_data.contract.OrderPresenterImpl;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.adapter.order.OrderParentAdapter;
import com.rails.purchaseplatform.order.databinding.FragmentOrderBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 采购单列表
 */
public class OrderFragment extends LazyFragment<FragmentOrderBinding> implements OrderContract.OrderView {

    private final int DEF_PAGE = 1;
    private int page = DEF_PAGE;
    private OrderParentAdapter mAdapter;
    private OrderContract.OrderPresenter presenter;

    private static int status;

    private OrderFragment(int status) {
        this.status = status;
    }

    public static OrderFragment getInstance(int status) {
        return new OrderFragment(status);

    }

    @Override
    protected void loadData() {

        mAdapter = new OrderParentAdapter(getActivity());
        presenter = new OrderPresenterImpl(getActivity(), this);
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        binding.recycler.addItemDecoration(new SpaceDecoration(getActivity(),10, R.color.line_gray));
        binding.recycler.setAdapter(mAdapter);
        onRefresh();
    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    /**
     * 刷新效果
     */
    private void onRefresh() {
        binding.swipe.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                binding.swipe.finishRefresh();
                page = DEF_PAGE;
                notifyData(false, page);
            }
        });

        binding.swipe.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                notifyData(false, page);
            }
        });
        notifyData(false, page);
    }


    /**
     * 请求推荐商品列表
     *
     * @param isDialog
     * @param page
     */
    private void notifyData(boolean isDialog, int page) {
        presenter.getOrder(isDialog, page);
    }

    @Override
    public void getOrder(ArrayList<OrderParentBean> orderBeans, boolean hasMore, boolean isClear) {
        mAdapter.update(orderBeans, true);
    }
}
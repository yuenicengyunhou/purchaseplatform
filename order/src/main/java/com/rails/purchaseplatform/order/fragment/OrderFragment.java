package com.rails.purchaseplatform.order.fragment;


import com.rails.lib_data.bean.BuyerBean;
import com.rails.lib_data.bean.OrderFilterBean;
import com.rails.lib_data.bean.OrderInfoBean;
import com.rails.lib_data.contract.OrderContract;
import com.rails.lib_data.contract.OrderPresenterImpl;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.adapter.order.OrderParentAdapter;
import com.rails.purchaseplatform.order.databinding.FragmentOrderBinding;

import java.util.ArrayList;

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

    private String squence = "purchaseNo";//默认采购单编号搜索
    private int searchType = 0;
    private String searchContent = "";
    private OrderFilterBean filterBean;

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
        binding.recycler.addItemDecoration(new SpaceDecoration(getActivity(), 10, R.color.line_gray));
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
        binding.swipe.setOnRefreshListener(refreshLayout -> {
            binding.swipe.finishRefresh();
            page = DEF_PAGE;
            notifyData(true, page, searchType, searchContent,filterBean);
        });

        binding.swipe.setOnLoadMoreListener(refreshLayout -> {
            page++;
            notifyData(false, page,searchType,searchContent,filterBean);
        });
        notifyData(true, page,searchType,searchContent,filterBean);
    }


    /**
     * 请求推荐商品列表
     * <p>
     * param isDialog
     * param page
     */
    private void notifyData(boolean isDialog, int page, int searchType, String searchContent,OrderFilterBean filterBean) {
        int queryType = status == 0 ? 1 : 0;
        squence = getQuestSquence(searchType);
        presenter.getOrder(isDialog, page, queryType, squence, searchContent,filterBean);
    }

    /**
     * Search type, default 0.
     * 0 - 采购单号
     * 1 - 采购人用户名
     * 2 - 供应商名称
     */
    public void notifyData(int searchType, String searchContent, OrderFilterBean filterBean) {
        page = DEF_PAGE;
        this.searchType = searchType;
        this.searchContent = searchContent;
        this.filterBean = filterBean;
        notifyData(true, page, searchType, searchContent,filterBean);
    }

    private String getQuestSquence(int searchType) {
        if (searchType == 0) {
            return "purchaseNo";
        } else if (searchType == 1) {
            return "buyerAccountId";
        } else {
            return "shopId";
        }
    }

    @Override
    public void getOrder(ArrayList<OrderInfoBean> orderBeans, boolean hasMore, boolean isClear) {
        binding.swipe.finishLoadMore();
        mAdapter.update(orderBeans, page == DEF_PAGE);
    }

    @Override
    public void loadConditionNameList(ArrayList<BuyerBean> list) {

    }
}
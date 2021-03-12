package com.rails.purchaseplatform.market.ui.fragment;

import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.contract.CartContract;
import com.rails.lib_data.contract.CartPresenterImpl;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.LoadMoreRecyclerView;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.CartAdapter;
import com.rails.purchaseplatform.market.adapter.ProductRecAdapter;
import com.rails.purchaseplatform.market.databinding.FrmCartBinding;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * 购物车
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public class CartFrm extends LazyFragment<FrmCartBinding> implements CartContract.CartView, LoadMoreRecyclerView.LoadMoreListener {

    final int DEF_PAGE = 1;
    int page = DEF_PAGE;

    CartAdapter cartAdapter;
    ProductRecAdapter recAdapter;
    CartContract.CartPresenter presenter;

    @Override
    protected void loadData() {

        binding.titleBar
                .setBtnRightContent(R.string.market_cart_manager)
                .setTitle(R.string.market_cart);

        cartAdapter = new CartAdapter(getActivity());
        binding.cartRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.cartRecycler.setAdapter(cartAdapter);
        binding.empty.setDescEmpty("暂无数据").setImgEmpty(R.drawable.svg_market_scan).setMarginTop(60);
        binding.cartRecycler.setEmptyView(binding.empty);

        recAdapter = new ProductRecAdapter(getActivity());
        binding.recRecycler.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        binding.recRecycler.setLoadMoreListener(this);
        binding.recRecycler.setAdapter(recAdapter);


        presenter = new CartPresenterImpl(getActivity(), this);

    }

    @Override
    protected void loadPreVisitData() {
        onRefresh();
    }


    /**
     * 刷新效果
     */
    private void onRefresh() {
        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipe.setRefreshing(false);
                page =DEF_PAGE;
                presenter.getCarts(false);
                notifyData(false,page);
            }
        });
        presenter.getCarts(true);
        notifyData(false,page);
    }


    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void getCarts(ArrayList<CartBean> cartBeans) {
        cartAdapter.update(cartBeans, true);
    }

    @Override
    public void getRecProjects(ArrayList<ProductBean> productBeans, boolean hasMore, boolean isClear) {
        recAdapter.update(productBeans, isClear);
        binding.recRecycler.notifyMoreFinish(hasMore);
    }


    /**
     * 请求推荐商品列表
     *
     * @param isDialog
     * @param page
     */
    private void notifyData(boolean isDialog, int page) {
        presenter.getRecProducts(isDialog, page);
    }

    @Override
    public void onLoadMore() {
        page++;
        notifyData(false, page);
    }
}

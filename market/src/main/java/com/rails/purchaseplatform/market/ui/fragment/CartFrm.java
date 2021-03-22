package com.rails.purchaseplatform.market.ui.fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.contract.CartContract;
import com.rails.lib_data.contract.CartPresenterImpl;
import com.rails.lib_data.contract.ProductContract;
import com.rails.lib_data.contract.ProductPresenterImpl;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.LoadMoreRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.common.widget.SpaceGirdWeightDecoration;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.CartAdapter;
import com.rails.purchaseplatform.market.adapter.ProductHotAdapter;
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
public class CartFrm extends LazyFragment<FrmCartBinding> implements CartContract.CartView,
        LoadMoreRecyclerView.LoadMoreListener, ProductContract.ProductView {

    final int DEF_PAGE = 1;
    int page = DEF_PAGE;

    CartAdapter cartAdapter;
    ProductHotAdapter recAdapter;
    CartContract.CartPresenter presenter;
    ProductContract.ProductPresenter productPresenter;


    @Override
    protected void loadData() {

        int height = -ScreenSizeUtil.dp2px(getActivity(), 45);
        binding.bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (i >= height) {
                    binding.swipe.setEnabled(true);
                } else {
                    binding.swipe.setEnabled(false);

                }
            }
        });

        cartAdapter = new CartAdapter(getActivity());
        binding.cartRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.cartRecycler.addItemDecoration(new SpaceDecoration(getActivity(), 10, R.color.line_gray));
        binding.cartRecycler.setAdapter(cartAdapter);
        binding.empty.setDescEmpty(R.string.market_cart_null).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        binding.cartRecycler.setEmptyView(binding.empty);

        recAdapter = new ProductHotAdapter(getActivity());
        binding.recRecycler.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        binding.recRecycler.setLoadMoreListener(this);
        binding.recRecycler.setAdapter(recAdapter);


        presenter = new CartPresenterImpl(getActivity(), this);
        productPresenter = new ProductPresenterImpl(getActivity(), this);
        onRefresh();
    }

    @Override
    protected void loadPreVisitData() {
        StatusBarUtil.StatusBarLightMode(getActivity());
    }


    /**
     * 刷新效果
     */
    private void onRefresh() {
        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipe.setRefreshing(false);
                page = DEF_PAGE;
                presenter.getCarts(false);
                notifyData(false, page);
            }
        });
        presenter.getCarts(true);
        notifyData(false, page);
    }


    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    /**
     * 请求推荐商品列表
     *
     * @param isDialog
     * @param page
     */
    private void notifyData(boolean isDialog, int page) {
        productPresenter.getHotProducts(isDialog, page);
    }

    @Override
    public void onLoadMore() {
        page++;
        notifyData(false, page);
    }

    @Override
    public void getHotProducts(ArrayList<ProductBean> productBeans, boolean hasMore, boolean isClear) {
        recAdapter.update(productBeans, isClear);
        binding.recRecycler.notifyMoreFinish(hasMore);
    }

    @Override
    public void getCartInfo(CartBean cartBean) {
        cartAdapter.update((ArrayList) cartBean.getShopList(), true);
    }
}

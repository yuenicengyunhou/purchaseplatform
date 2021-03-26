package com.rails.purchaseplatform.market.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.CartShopBean;
import com.rails.lib_data.bean.CartShopProductBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.contract.CartContract;
import com.rails.lib_data.contract.CartPresenterImpl;
import com.rails.lib_data.contract.ProductContract;
import com.rails.lib_data.contract.ProductPresenterImpl;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.AlphaScrollView;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.LoadMoreRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.adapter.listener.MulPositionListener;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;
import com.rails.purchaseplatform.framwork.utils.DecimalUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.CartAdapter;
import com.rails.purchaseplatform.market.adapter.ProductHotAdapter;
import com.rails.purchaseplatform.market.databinding.FrmCartBinding;
import com.rails.purchaseplatform.market.ui.activity.ProductDetailsActivity;
import com.rails.purchaseplatform.market.ui.activity.ShopDetailActivity;
import com.rails.purchaseplatform.market.ui.pop.CartEditDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * 购物车
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public class CartFrm extends LazyFragment<FrmCartBinding> implements CartContract.CartView,
        LoadMoreRecyclerView.LoadMoreListener, ProductContract.ProductView,
        MulPositionListener<CartShopProductBean>, PositionListener<CartShopBean> {

    final int DEF_PAGE = 1;
    int page = DEF_PAGE;

    CartAdapter cartAdapter;
    ProductHotAdapter recAdapter;
    private GridLayoutManager hotManager;

    CartContract.CartPresenter presenter;
    ProductContract.ProductPresenter productPresenter;


    private CartShopProductBean lastBean;


    @Override
    protected void loadData() {

        binding.tvTotal.setText(DecimalUtil.formatStrColor(getResources().getString(R.string.market_cart_total),
                DecimalUtil.formatDouble(0.0), "", getResources().getColor(R.color.font_red)));

        cartAdapter = new CartAdapter(getActivity());
        cartAdapter.setListener(this);
        cartAdapter.setMulPositionListener(this);
        binding.cartRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.cartRecycler.addItemDecoration(new SpaceDecoration(getActivity(), 10, R.color.line_gray));
        binding.cartRecycler.setAdapter(cartAdapter);
        binding.empty.setDescEmpty(R.string.market_cart_null).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        binding.cartRecycler.setEmptyView(binding.empty);

        recAdapter = new ProductHotAdapter(getActivity(), 0);
        hotManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        binding.recRecycler.setLayoutManager(hotManager);
        binding.recRecycler.setLoadMoreListener(this);
        recAdapter.setListener(new PositionListener<ProductBean>() {
            @Override
            public void onPosition(ProductBean bean, int position) {
                startIntent(ProductDetailsActivity.class);
            }
        });
        binding.recRecycler.setAdapter(recAdapter);


        binding.scroll.setScrollViewListener(new AlphaScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(AlphaScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (y > 800) {
                    binding.imgTop.setVisibility(View.VISIBLE);
                } else
                    binding.imgTop.setVisibility(View.GONE);
            }
        });


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
        binding.swipe.setEnableLoadMore(false);
        binding.swipe.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                binding.swipe.finishRefresh();
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

    @Override
    public void getProjectNumber(int num) {
        if (lastBean != null) {
            lastBean.num.set(num);

            isNumberUser(lastBean);
            userTotal(lastBean);
        }
    }


    @Override
    protected void onClick() {
        super.onClick();
        binding.imgTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = binding.imgTotal.isChecked();
                cartAdapter.checkAll(isChecked);
                binding.tvTotal.setText(DecimalUtil.formatStrColor(getResources().getString(R.string.market_cart_total),
                        DecimalUtil.formatDouble(cartAdapter.totalPrice()), "", getResources().getColor(R.color.font_red)));
            }
        });

        binding.imgTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.scroll.smoothScrollTo(0, 0);
            }
        });
    }

    @Override
    public void onPosition(CartShopProductBean bean, int position, int... params) {
        // TODO: 2021/3/22 商品的点击
        lastBean = bean;
        int type = params[0];
        if (type == CartAdapter.CHECK) {
            // TODO: 2021/3/22 更改选中按钮，计算总价
            setTotal();
        } else if (type == CartAdapter.ADD) {
            presenter.addProduct(bean.num.get());
        } else if (type == CartAdapter.REDUCE) {
            presenter.reduceProduct(bean.num.get());
        } else if (type == CartAdapter.EDIT) {
            // TODO: 2021/3/23 显示编辑窗口 ，更改产品数量
            showDialog();
        }
    }

    @Override
    public void onPosition(CartShopBean bean, int type) {
        // TODO: 2021/3/22 店铺的点击
        if (type == CartAdapter.SHOP) {
            //跳转商铺页面
            Bundle bundle = new Bundle();
            startIntent(ShopDetailActivity.class, bundle);

        } else {
            // TODO: 2021/3/22 更改选中按钮，计算总价
            setTotal();
        }
    }


    /**
     * 设置底部总计
     */
    private void setTotal() {
        binding.imgTotal.setChecked(cartAdapter.isAll());
        binding.tvTotal.setText(DecimalUtil.formatStrColor(getResources().getString(R.string.market_cart_total),
                DecimalUtil.formatDouble(cartAdapter.totalPrice()), "", getResources().getColor(R.color.font_red)));
    }


    /**
     * 是否要用
     *
     * @param bean 商品
     */
    private void userTotal(CartShopProductBean bean) {
        if (bean == null)
            return;
        if (bean.isSel.get() == null)
            return;
        if (bean.isSel.get())
            setTotal();

    }


    /**
     * 增减按钮是否可以用
     *
     * @param bean
     */
    private void isNumberUser(CartShopProductBean bean) {
        if (bean.num.get() > 99) {
            bean.canAdd.set(false);
        } else
            bean.canAdd.set(true);

        if (bean.num.get() <= 1) {
            bean.canReduce.set(false);
        } else
            bean.canReduce.set(true);
    }


    private void showDialog() {
        new CartEditDialog.Builder()
                .context(getActivity())
                .title("输入您要购买的数量")
                .setDialogListener(new CartEditDialog.DialogListener() {
                    @Override
                    public void onLeft() {

                    }

                    @Override
                    public void onRight(String paw) {
                        presenter.editProduct(Integer.parseInt(paw));
                    }
                }).builder().show();
    }
}

package com.rails.purchaseplatform.market.ui.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.CartShopBean;
import com.rails.lib_data.bean.CartShopProductBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.contract.AddressToolContract;
import com.rails.lib_data.contract.AddressToolPresenterImpl;
import com.rails.lib_data.contract.CartContract;
import com.rails.lib_data.contract.CartPresenterImpl;
import com.rails.lib_data.contract.ProductContract;
import com.rails.lib_data.contract.ProductPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.AlphaScrollView;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.recycler.LoadMoreRecycler;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.adapter.listener.MulPositionListener;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;
import com.rails.purchaseplatform.framwork.utils.DecimalUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.CartAdapter;
import com.rails.purchaseplatform.market.adapter.ProductHotAdapter;
import com.rails.purchaseplatform.market.databinding.FrmCartBinding;
import com.rails.purchaseplatform.market.ui.activity.ProductDetailsActivity;
import com.rails.purchaseplatform.market.ui.activity.ShopDetailActivity;
import com.rails.purchaseplatform.market.ui.pop.CartEditDialog;
import com.rails.purchaseplatform.market.ui.pop.PropertyPop;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 购物车
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public class CartFrm extends LazyFragment<FrmCartBinding> implements CartContract.CartView,
        LoadMoreRecycler.LoadMoreListener, ProductContract.ProductView, AddressToolContract.AddressToolView,
        MulPositionListener<CartShopProductBean>, PositionListener<CartShopBean> {

    int type = 0;//是否显示标题
    final int DEF_PAGE = 1;
    int page = DEF_PAGE;
    AddressBean addressBean;

    CartAdapter cartAdapter;
    ProductHotAdapter recAdapter;
    private GridLayoutManager hotManager;

    CartContract.CartPresenter presenter;
    AddressToolContract.AddressToolPresenter addressPresenter;
    ProductContract.ProductPresenter productPresenter;


    private CartShopProductBean lastBean;


    private CartFrm(int type) {
        this.type = type;
    }


    public static CartFrm newInstance(int type) {
        return new CartFrm(type);
    }


    @Override
    protected void loadData() {
        if (type == 1) {
            binding.btnBack.setVisibility(View.VISIBLE);
        }

        binding.tvTotal.setText(DecimalUtil.formatStrSize("¥ ",
                DecimalUtil.formatDouble(0.0), "", 18));

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
        addressPresenter = new AddressToolPresenterImpl(getActivity(), this);
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
                addressPresenter.getDefAddress("20", "1");
                notifyData(false, page);
            }
        });
        addressPresenter.getDefAddress("20", "1");
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
        setDefTotal(cartBean);
    }

    @Override
    public void getProjectNumber(long num) {
        if (lastBean != null) {
            lastBean.num.set(num);

            isNumberUser(lastBean);
            userTotal(lastBean);
        }
    }

    @Override
    public void getResult(int type, String msg) {
        ToastUtil.show(getActivity(), msg);
        ARouter.getInstance().build(ConRoute.ORDER.ORDER_VERITY).withSerializable("address", addressBean).navigation();
    }

    /**
     * @param type 0：商品  1：店铺  2：全选
     */
    @Override
    public void getSelStatus(int type, Boolean isSel) {
        if (type == 0) {

        } else if (type == 1) {

        } else if (type == 2) {
            cartAdapter.checkAll(isSel);
            binding.imgTotal.setSelected(isSel);
            binding.tvTotal.setText(DecimalUtil.formatStrSize("¥ ", DecimalUtil.formatDouble(cartAdapter.totalPrice()), "", 18));
        }
    }


    @Override
    protected void onClick() {
        super.onClick();
        binding.imgTotal.setOnClickListener(v -> {

            boolean isChecked = binding.imgTotal.isSelected();
            presenter.modifySelAll(!isChecked);

        });

        binding.imgTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.scroll.smoothScrollTo(0, 0);
            }
        });

        binding.btnCommit.setOnClickListener(v -> {
            if (addressBean == null)
                return;
            presenter.verifyCart(String.valueOf(addressBean.getId()));
        });


        binding.btnBack.setOnClickListener(v -> {
            getActivity().finish();
        });

        binding.tvManager.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.tvManager.setText(R.string.market_cart_complement);
                binding.llOperate.setVisibility(View.VISIBLE);
                binding.llPrice.setVisibility(View.GONE);
            } else {
                binding.tvManager.setText(R.string.market_cart_manager);
                binding.llOperate.setVisibility(View.GONE);
                binding.llPrice.setVisibility(View.VISIBLE);
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
            presenter.modifySel(bean);
            setTotal();
        } else if (type == CartAdapter.ADD) {
            presenter.addProduct(bean, bean.num.get());
        } else if (type == CartAdapter.REDUCE) {
            presenter.reduceProduct(bean, bean.num.get());
        } else if (type == CartAdapter.EDIT) {
            // TODO: 2021/3/23 显示编辑窗口 ，更改产品数量
            showEditDialog(bean);
        } else if (type == CartAdapter.SUB_COLLECT) {
            // TODO: 2021/3/28   调用收藏接口
        } else if (type == CartAdapter.SUB_DEL) {
            // TODO: 2021/3/28 调用删除接口
        } else if (type == CartAdapter.PROPERTY) {
            PropertyPop pop = new PropertyPop();
            pop.setGravity(Gravity.BOTTOM);
            pop.setType(BasePop.MATCH_WRAP);
            pop.show(getChildFragmentManager(), "property");
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
        binding.imgTotal.setSelected(cartAdapter.isAll());
        binding.tvTotal.setText(DecimalUtil.formatStrSize("¥ ",
                DecimalUtil.formatDouble(cartAdapter.totalPrice()), "", 18));
    }


    /**
     * 设置底部默认状态
     *
     * @param bean
     */
    private void setDefTotal(CartBean bean) {
        if (bean == null)
            return;
        binding.imgTotal.setSelected(bean.getSelected());
        binding.tvTotal.setText(DecimalUtil.formatStrSize("¥ ",
                DecimalUtil.formatDouble(Double.parseDouble(bean.getTotalPrice())), "", 18));
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

        if (bean.num.get() <= 1) {
            bean.canReduce.set(false);
        } else
            bean.canReduce.set(true);
    }


    private void showEditDialog(CartShopProductBean bean) {
        if (bean == null)
            return;
        new CartEditDialog.Builder()
                .context(getActivity())
                .title("输入您要购买的数量")
                .setDialogListener(new CartEditDialog.DialogListener() {
                    @Override
                    public void onLeft() {

                    }

                    @Override
                    public void onRight(String paw) {
                        presenter.editProduct(bean, Long.parseLong(paw));

                    }
                }).builder().show();
    }


    @Override
    public void getAddress(ArrayList<AddressBean> addressBeans) {

    }

    @Override
    public void getDefAddress(AddressBean bean) {
        if (bean == null)
            return;
        addressBean = bean;
        presenter.getCarts(true, String.valueOf(bean.getId()));
    }
}

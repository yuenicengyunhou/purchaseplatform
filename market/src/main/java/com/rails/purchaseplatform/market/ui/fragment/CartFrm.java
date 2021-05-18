package com.rails.purchaseplatform.market.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.CartShopBean;
import com.rails.lib_data.bean.CartShopProductBean;
import com.rails.lib_data.bean.CategorySubBean;
import com.rails.lib_data.bean.MarketIndexBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.contract.AddressToolContract;
import com.rails.lib_data.contract.AddressToolPresenterImpl;
import com.rails.lib_data.contract.CartContract;
import com.rails.lib_data.contract.CartPresenterImpl;
import com.rails.lib_data.contract.CartToolPresenterImpl;
import com.rails.lib_data.contract.MarKetIndexPresenterImpl;
import com.rails.lib_data.contract.MarketIndexContract;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.pop.AlterDialog;
import com.rails.purchaseplatform.common.widget.AlphaScrollView;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.common.widget.recycler.LoadMoreRecycler;
import com.rails.purchaseplatform.framwork.adapter.listener.MulPositionListener;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;
import com.rails.purchaseplatform.framwork.utils.DecimalUtil;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
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
import java.util.HashMap;

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
        LoadMoreRecycler.LoadMoreListener, MarketIndexContract.MarketIndexView, AddressToolContract.AddressToolView,
        MulPositionListener<CartShopProductBean>, PositionListener<CartShopBean>, CartContract.DetailsCartView {

    int type = 0;//是否显示标题
    final int DEF_PAGE = 1;
    int page = DEF_PAGE;
    AddressBean addressBean;

    CartAdapter cartAdapter;
    ProductHotAdapter recAdapter;
    private GridLayoutManager hotManager;

    CartContract.CartPresenter presenter;
    AddressToolContract.AddressToolPresenter addressPresenter;
    MarketIndexContract.MarketIndexPresenter productPresenter;
    CartContract.CartPresenter2 toolPresenter;


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
                if (TextUtils.isEmpty(bean.getItemId())) {
                    ToastUtil.showCenter(getActivity(), "商品不存在或已下架");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("itemId", bean.getItemId());
                ARouter.getInstance().build(ConRoute.MARKET.PRODUCT_DETAIL).with(bundle).navigation();
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
        toolPresenter = new CartToolPresenterImpl(getActivity(), this);
        productPresenter = new MarKetIndexPresenterImpl(getActivity(), this);
        addressPresenter = new AddressToolPresenterImpl(getActivity(), this);
    }

    @Override
    protected void loadPreVisitData() {
        StatusBarUtil.StatusBarLightMode(getActivity());
        onRefresh();
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
        productPresenter.getRectProducts(false, true);
    }

    @Override
    public void onLoadMore() {
        page++;
        notifyData(false, page);
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
        if (type == 0) {
            ARouter.getInstance().build(ConRoute.ORDER.ORDER_VERITY).withSerializable("address", addressBean).navigation();
        }
        ToastUtil.show(getActivity(), msg);

    }

    @Override
    public void getResult(int type, int position, String msg) {
        ToastUtil.showCenter(getActivity(), "删除成功");
        if (type == 1 && position != -1) {
            cartAdapter.updateSubAdater(position);
        } else {
            presenter.getCarts(false, addressBean.getId());
        }
    }

    /**
     * @param type 0：商品  1：店铺  2：全选
     */
    @Override
    public void getSelStatus(int type, Boolean isSel) {
        if (type == 0) {

        } else if (type == 1) {
            setTotal();
        } else if (type == 2) {
            cartAdapter.checkAll(isSel);
            binding.imgTotal.setSelected(isSel);
            binding.tvTotal.setText(DecimalUtil.formatStrSize("¥ ", DecimalUtil.formatDouble(cartAdapter.totalPrice()), "", 18));
        }
    }

    @Override
    public void getLimits(ArrayList<String> list, String msg) {
        for (String itemId : list) {
            for (CartShopBean bean : cartAdapter.getBeans()) {
                for (CartShopProductBean productBean : bean.getSkuList()) {
                    if (itemId.equals(productBean.getSkuId())) {
                        productBean.isLimit.set(true);
                        break;
                    }
                }
            }
        }
        ToastUtil.showCenter(getActivity(), msg);
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

        binding.btnDel.setOnClickListener(v -> {
            //全部删除
            delAllParams(cartAdapter.getBeans());
        });


        binding.btnCollect.setOnClickListener(v -> {
            collectAllParams(cartAdapter.getBeans());
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
            toolPresenter.onCollect(bean.getSkuId(), "30", bean.isCollect.get(), position);
        } else if (type == CartAdapter.SUB_DEL) {
            // TODO: 2021/3/28 调用删除接口
            delParams(bean, position);
        } else if (type == CartAdapter.PROPERTY) {
//            PropertyPop pop = new PropertyPop();
//            pop.setGravity(Gravity.BOTTOM);
//            pop.setType(BasePop.MATCH_WRAP);
//            pop.show(getChildFragmentManager(), "property");
        }
    }

    @Override
    public void onPosition(CartShopBean bean, int type) {
        // TODO: 2021/3/22 店铺的点击
        if (type == CartAdapter.SHOP) {
            //跳转商铺页面
            Bundle bundle = new Bundle();
            bundle.putString("shopInfoId", bean.getShopId());
            startIntent(ShopDetailActivity.class, bundle);

        } else {
            // TODO: 2021/3/22 更改选中按钮，计算总价
            presenter.modifyShopSel(bean.getShopId(), bean.getItemIds(), !bean.isSel.get());
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
        if (bean == null) {
            Boolean isAddressManager = PrefrenceUtil.getInstance(getActivity()).getBoolean(ConShare.MENU_ADDRESS, false);
            if (!isAddressManager) {
                ToastUtil.showCenter(getActivity(), getResources().getString(R.string.common_author_null));
                return;
            } else {
                showAddressDialog();
            }
            cartAdapter.update(new ArrayList(), true);
            return;
        }
        addressBean = bean;
        presenter.getCarts(true, String.valueOf(bean.getId()));
    }


    /**
     * 单个商品删除
     *
     * @param bean
     */
    private void delParams(CartShopProductBean bean, int position) {
        if (bean == null)
            return;
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> skuIds = new ArrayList<>();
        skuIds.add(bean.getSkuId());
        map.put(String.valueOf(bean.getShopId()), skuIds);
        presenter.delProduct(map, position);
    }


    /**
     * 全部删除
     *
     * @param shopBeans
     */
    private void delAllParams(ArrayList<CartShopBean> shopBeans) {
        if (shopBeans == null)
            return;
        if (shopBeans.isEmpty())
            return;
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        for (CartShopBean shopBean : shopBeans) {
            ArrayList<String> skuIds = new ArrayList<>();
            for (CartShopProductBean bean : shopBean.getSkuList()) {
                if (bean.isSel.get() && bean.canSel.get())
                    skuIds.add(bean.getSkuId());
            }
            map.put(String.valueOf(shopBean.getShopId()), skuIds);
        }
        presenter.delProduct(map, -1);

    }


    /**
     * 收藏
     *
     * @param shopBeans
     */
    private void collectAllParams(ArrayList<CartShopBean> shopBeans) {
        try {
            if (shopBeans == null)
                return;
            if (shopBeans.isEmpty())
                return;
            ArrayList<String> skuIds = new ArrayList<>();
            StringBuffer buffer = new StringBuffer();
            for (CartShopBean shopBean : shopBeans) {
                for (CartShopProductBean bean : shopBean.getSkuList()) {
                    if (!bean.isCollect.get() && bean.isSel.get()) {
                        skuIds.add(bean.getSkuId());
                        buffer.append(bean.getSkuId() + ",");
                    }

                }
            }
            if (TextUtils.isEmpty(buffer.toString()))
                return;
            toolPresenter.onCollect(buffer.toString().substring(0, buffer.length() - 1), "30", false, -1);
        } catch (Exception e) {

        }

    }


    @Override
    public void addCartSuccess(boolean isComplete) {

    }

    @Override
    public void onCollect(boolean isCollect, int position) {
        ToastUtil.showCenter(getActivity(), "操作成功");
        if (position != -1)
            lastBean.isCollect.set(isCollect);
        else {
            presenter.getCarts(false, addressBean.getId());
        }
    }

    @Override
    public void getRecProducts(ArrayList<ProductRecBean> beans) {
        if (beans.isEmpty())
            return;
        recAdapter.update(beans.get(0).getFloorList(), true);
        binding.recRecycler.notifyMoreFinish(false);
    }

    @Override
    public void getBanners(ArrayList<BannerBean> bannerBeans) {

    }

    @Override
    public void getBrands(ArrayList<BrandBean> brandBeans) {

    }

    @Override
    public void getRecCategorys(ArrayList<CategorySubBean> beans) {

    }

    @Override
    public void getIndexInfo(MarketIndexBean bean) {

    }


    /**
     * 显示地址弹窗
     */
    private void showAddressDialog() {
        new AlterDialog.Builder().context(getActivity())
                .title("提示")
                .msg("购物车需要收货地址判断商品库存\n立即去设置")
                .leftBtn("确定")
                .rightBtn("取消")
                .setDialogListener(new AlterDialog.DialogListener() {
                    @Override
                    public void onLeft() {
                        ARouter.getInstance().build(ConRoute.ADDRESS.ADDRESS_MAIN).navigation();
                        dismissDialog();
                    }

                    @Override
                    public void onRight() {
                        dismissDialog();
                    }
                })
                .builder().show();
    }
}

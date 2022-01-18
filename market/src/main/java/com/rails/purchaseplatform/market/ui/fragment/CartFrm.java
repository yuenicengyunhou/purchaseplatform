package com.rails.purchaseplatform.market.ui.fragment;

import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_PASTDUE;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_TIMEOUT;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_UNLOAD;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_UNLOAD_2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.CartShopBean;
import com.rails.lib_data.bean.CartShopProductBean;
import com.rails.lib_data.bean.MarketIndexBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.bean.UserInfoBean;
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
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.common.widget.SpaceGirdWeightDecoration;
import com.rails.purchaseplatform.common.widget.recycler.LoadMoreRecycler;
import com.rails.purchaseplatform.framwork.MyDefaultItemAnimator;
import com.rails.purchaseplatform.framwork.adapter.listener.MulPositionListener;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.bean.BusEvent;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;
import com.rails.purchaseplatform.framwork.utils.DecimalUtil;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.framwork.utils.SystemUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.CartAdapter;
import com.rails.purchaseplatform.market.adapter.ProductHotAdapter;
import com.rails.purchaseplatform.market.databinding.FrmCartBinding;
import com.rails.purchaseplatform.market.ui.activity.ShopDetailActivity;
import com.rails.purchaseplatform.market.ui.pop.CartAddressPop;
import com.rails.purchaseplatform.market.ui.pop.CartEditDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 购物车
 * <p>
 * author： sk_comic@163.com
 * date: 2021/3/9
 */
public class CartFrm extends LazyFragment<FrmCartBinding> implements CartContract.CartView,
        LoadMoreRecycler.LoadMoreListener, MarketIndexContract.MarketIndexView, AddressToolContract.AddressToolView,
        MulPositionListener<CartShopProductBean>, PositionListener<CartShopBean>, CartContract.DetailsCartView {

    int type = 0;//是否显示标题
    final int DEF_PAGE = 1;
    int page = DEF_PAGE;

    //默认选中第一个地址
    AddressBean mAddress;
    //地址列表
    ArrayList<AddressBean> addressBeans;

    CartAdapter cartAdapter;
    ProductHotAdapter recAdapter;

    CartContract.CartPresenter presenter;
    AddressToolContract.AddressToolPresenter addressPresenter;
    MarketIndexContract.MarketIndexPresenter productPresenter;
    CartContract.CartPresenter2 toolPresenter;


    private CartShopProductBean lastBean;

    private int size;

    public CartFrm(){}


    public static CartFrm newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        CartFrm cartFrm = new CartFrm();
        cartFrm.setArguments(bundle);
        return cartFrm;
    }


    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        type = getArguments().getInt("type");
    }

    @Override
    protected void loadData() {

        if (type == 1) {
            binding.btnBack.setVisibility(View.VISIBLE);
        }


        if (SystemUtil.isPad(Objects.requireNonNull(getActivity()))) {
            size = ScreenSizeUtil.sp2px(14, getActivity());
        } else {
            size = 18;
        }

        DecimalUtil.formatStrSize("¥ ",
                DecimalUtil.formatDouble(0.0), "", size);

        cartAdapter = new CartAdapter(getActivity());
        cartAdapter.setListener(this);
        cartAdapter.setMulPositionListener(this);
        cartAdapter.setCleanInvalidSkuListener(shopBean -> cleanAllInvalidSkus(shopBean));
        binding.cartRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.cartRecycler.addItemDecoration(new SpaceDecoration(getActivity(), 10, R.color.line_gray));
        binding.cartRecycler.setItemAnimator(new MyDefaultItemAnimator());
        binding.cartRecycler.setAdapter(cartAdapter);
        binding.empty.setDescEmpty(R.string.market_cart_null).setImgEmpty(R.drawable.ic_cart_null)
                .setBtnGobuy("")
                .setMarginTop(80)
                .setBtnEmpty("")
                .setGoBuyListener(v -> EventBus.getDefault().post(new BusEvent<>(null, "cartToHome")))
                .setBtnListener(v -> ARouter.getInstance().build(ConRoute.USER.LOGIN).navigation());
        binding.empty.setVisibility(View.VISIBLE);
        recAdapter = new ProductHotAdapter(getActivity(), 0);
        GridLayoutManager hotManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        binding.recRecycler.setLayoutManager(hotManager);
        binding.recRecycler.addItemDecoration(new SpaceGirdWeightDecoration(getActivity(), 8, 5, 0, 8, R.color.white));
        binding.recRecycler.setLoadMoreListener(this);
        recAdapter.setListener((PositionListener<ProductBean>) (bean, position) -> {
            //跳转商品详情
            if (TextUtils.isEmpty(bean.getItemId())) {
                ToastUtil.showCenter(getActivity(), "商品不存在或已下架");
                return;
            }
            if (hasToken()) {
                Bundle bundle = new Bundle();
                bundle.putString("itemId", bean.getItemId());
                ARouter.getInstance().build(ConRoute.MARKET.PRODUCT_DETAIL).with(bundle).navigation();
            } else {
                ARouter.getInstance()
                        .build(ConRoute.USER.LOGIN)
                        .navigation();
            }
        });
        binding.recRecycler.setAdapter(recAdapter);


        binding.scroll.setScrollViewListener((scrollView, x, y, oldx, oldy) -> {
            if (y > 800) {
                binding.imgTop.setVisibility(View.VISIBLE);
            } else
                binding.imgTop.setVisibility(View.GONE);
        });


        presenter = new CartPresenterImpl(getActivity(), this);
        toolPresenter = new CartToolPresenterImpl(getActivity(), this);
        productPresenter = new MarKetIndexPresenterImpl(getActivity(), this);
        addressPresenter = new AddressToolPresenterImpl(getActivity(), this);

        addressPresenter.getAddress("20", "1", "", "", false);
    }

    /**
     * 重置总计金额，到0.00
     */
    private void clearTotalPrice() {

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
        binding.swipe.setOnRefreshListener(refreshLayout -> {
            binding.swipe.finishRefresh();
            page = DEF_PAGE;
            addressPresenter.getAddress("20", "1", "", "", false);
            notifyData(page);
        });
        notifyData(page);
    }


    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    /**
     * 请求推荐商品列表
     * 刷新购物车商品
     * <p>
     * param isDialog
     * param page
     */
    private void notifyData(int page) {
        presenter.getCarts(true, mAddress == null ? "-1" : String.valueOf(mAddress.getId()));
        productPresenter.getHotProducts(false, page, "10");
    }

    @Override
    public void onLoadMore() {
        page++;
        notifyData(page);
    }

    @Override
    public void onResume() {
//        if (!isFristLoad&&(null!=presenter)) {
//            presenter.getCarts(true, mAddress == null ? "-1" : String.valueOf(mAddress.getId()));
//        }
        super.onResume();
        if (null != addressPresenter) {
            addressPresenter.getAddress("20", "1", "", "", false);
        }
    }

    @Override
    public void getCartInfo(CartBean cartBean) {
        ArrayList<CartShopBean> shopBeans = (ArrayList<CartShopBean>) cartBean.getShopList();
        UserInfoBean bean = PrefrenceUtil.getInstance(mActivity).getBean(ConShare.USERINFO, UserInfoBean.class);
        if (shopBeans.isEmpty() && (null != bean)) {
            binding.empty.setVisibility(View.VISIBLE);
        } else {
            binding.empty.setVisibility(View.GONE);
        }
        if (!binding.empty.isBtnEmptyVisible()) {
            binding.empty.setBtnGobuy("去采购");
        }
        cartAdapter.update(shopBeans, true);
        setDefTotal(cartBean);
//        binding.cartRecycler.setEmptyView(binding.empty);
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
            ARouter.getInstance().build(ConRoute.ORDER.ORDER_VERITY).withSerializable("address", mAddress).navigation();
        }
        ToastUtil.show(getActivity(), msg);

    }

    @Override
    public void getResult(int type, int position, String msg) {
        ToastUtil.showCenter(getActivity(), "删除成功");
        if (type == 1 && position != -1) {
            cartAdapter.updateSubAdater(position);
            setTotal();
            if (cartAdapter.getItemCount() == 0) {//解决bug，当用户左滑删除条目，到最后，没有出现空空如也,所以刷新一下列表
                onRefresh();
            }
        } else {

            presenter.getCarts(false, "-1");
        }
    }

    /**
     * param type 0：商品  1：店铺  2：全选
     */
    @Override
    public void getSelStatus(int type, Boolean isSel) {
        if (type == 0) {

        } else if (type == 1) {
            setTotal();
        } else if (type == 2) {
            cartAdapter.checkAll(isSel);
            binding.imgTotal.setSelected(isSel);
            cartAdapter.updateShopPrice();
            String price = DecimalUtil.formatDouble(cartAdapter.totalPrice());
            setTotalPrice(price);
        }
    }

    @Override
    public void getLimits(ArrayList<String> list, String msg) {
        for (String itemId : list) {
            for (CartShopBean bean : cartAdapter.getBeans()) {
                for (CartShopProductBean productBean : bean.getSkuList()) {
                    if (itemId.equals(productBean.getSkuId()) && productBean.canSel.get()) {
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


        binding.imgTop.setOnClickListener(v -> {
            binding.scroll.smoothScrollTo(0, 0);
        });

        binding.btnCommit.setOnClickListener(v -> {
            if (mAddress == null) {
                showAddAddressDialog();
                return;
            }
            if (cartAdapter.isNext())
                presenter.verifyCart(String.valueOf(mAddress.getId()));
            else
                ToastUtil.showCenter(getActivity(), "您有商品未满足下单条件请核对");
        });

        binding.btnDel.setOnClickListener(v -> {
            //全部删除
            delAllParams(cartAdapter.getBeans(), true);
        });


        //选中收藏操作
        binding.btnCollect.setOnClickListener(v -> {
            collectAllParams(cartAdapter.getBeans());
        });

        //返回按钮
        binding.btnBack.setOnClickListener(v -> {
            Objects.requireNonNull(getActivity()).finish();
        });

        //管理操作
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


        //地址列表弹窗
        binding.tvAddress.setOnClickListener(v -> {
            if (mAddress == null) {
                showAddAddressDialog();//添加新地址的弹窗
            } else {
                addressPresenter.getAddress("20", "1", "", "", true);
//                showSelAddressDialog();
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
        if ((null != bean.getShopName()) && (bean.getShopName().equals("失效商品"))) {
            return;
        }
        if (type == CartAdapter.SHOP) {
            //跳转商铺页面
            Bundle bundle = new Bundle();
            bundle.putString("shopInfoId", bean.getShopId());
            startIntent(ShopDetailActivity.class, bundle);

        } else {
            // TODO: 2021/3/22 更改选中按钮，计算总价
            presenter.modifyShopSel(bean.getShopId(), bean.getItemIds(), bean.isSel.get());
        }
    }


    /**
     * 设置底部总计
     */
    private void setTotal() {
        cartAdapter.updateShopPrice();
        binding.imgTotal.setSelected(cartAdapter.isAll());
        String price = DecimalUtil.formatDouble(cartAdapter.totalPrice());
        setTotalPrice(price);
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
        setTotalPrice(bean.getTotalPrice());
    }

    private void setTotalPrice(String price) {
        String bigPrice;
        String smallPrice;
        if (price.equals("0")) {
            bigPrice = "0";
            smallPrice = ".00";
        } else {
            bigPrice = price.substring(0, price.indexOf("."));
            smallPrice = price.substring(price.indexOf("."));
        }
        binding.tvTotalBig.setText(DecimalUtil.formatStrSize("¥ ", bigPrice, "", size));
        binding.tvTotalSmall.setText(smallPrice);
    }


    /**
     * 是否要用
     *
     * @param bean 商品
     */
    private void userTotal(CartShopProductBean bean) {
        if (bean == null)
            return;
        if (bean.isSel.get() == null) {
            return;
        }
        if (bean.isSel.get()) {
            setTotal();
        }

    }


    /**
     * 增减按钮是否可以用
     * <p>
     * param bean
     */
    private void isNumberUser(CartShopProductBean bean) {

        if (bean.num.get() <= 1) {
            bean.canReduce.set(false);
            bean.canAdd.set(true);
        } else if (bean.num.get() > 1 && bean.num.get() < 999999999) {
            bean.canReduce.set(true);
            bean.canAdd.set(true);
        } else {
            bean.canReduce.set(true);
            bean.canAdd.set(false);
        }
    }


    private void showEditDialog(CartShopProductBean bean) {
        if (bean == null)
            return;
        new CartEditDialog.Builder()
                .context(getActivity())
                .title("输入您要购买的数量").content(String.valueOf(bean.num.get()))
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

    /**
     * 2021/10/20修改了这个方法 by王琪
     * 解决点击顶部地址，地址弹窗内容不实时，用户勾选的位置不回显的问题
     * 1传入showAddressPop参数，来区分是弹出地址框还是第一次加载页面
     * 2用旧的address(用户选择的address)的addressId比对新列表中的，没有则再次默认选第一个。有则不做处理（用户已经选择的address可能在pc删掉）
     */
    @Override
    public void getAddress(ArrayList<AddressBean> addressBeans, boolean showAddressPop) {
        if (null != addressBeans && !addressBeans.isEmpty()) {
            this.addressBeans = addressBeans;
            boolean oldAddressExists = true;//旧的地址存在
            if (null != mAddress) {//如果没选择地址，则
                String addressId = mAddress.getAddressId();
                for (AddressBean bean : addressBeans) {
                    if (bean.getAddressId().equals(addressId)) {
                        oldAddressExists = true;//如果存在跳出循环
                        break;
                    } else {
                        oldAddressExists = false;//如果不存在变为false，继续循环
                    }
                }
                if (!oldAddressExists) {//如果不存在，重置为第一个
                    mAddress = addressBeans.get(0);
                }
            } else {
                mAddress = addressBeans.get(0);
            }
        }

        binding.empty.setBtnEmpty("");
        if (!binding.empty.isBtnEmptyVisible()) {
            binding.empty.setBtnGobuy("去采购");
        }
        if (mAddress != null) {
//            cartAdapter.update(new ArrayList(), true);
//            return;
            binding.tvAddress.setText(mAddress.getFullAddress());
        } else {

        }

        if (showAddressPop) {
            showSelAddressDialog();
        }
    }

    @Override
    public void getDefAddress(AddressBean bean) {

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
     * <p>
     * param shopBeans
     */
    private void delAllParams(ArrayList<CartShopBean> shopBeans, boolean needAnalyze) {
        if (shopBeans == null)
            return;
        if (shopBeans.isEmpty())
            return;

        CartShopBean cartShopBean = shopBeans.get(shopBeans.size() - 1);
        if (needAnalyze && (cartShopBean.getShopName().equals("失效商品"))) {
            ArrayList<CartShopBean> cartShopBeans = generateInvalidShops(cartShopBean, false);
            shopBeans.remove(cartShopBean);
            shopBeans.addAll(cartShopBeans);
        }


//        CartShopBean invalidBean = new CartShopBean();
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        for (CartShopBean shopBean : shopBeans) {
            String shopName = shopBean.getShopName();
//            if ((!cleanData) && (null != shopName) && (shopName.equals("失效商品"))) {
//                cleanAllInvalidSkus(shopBean);
//            }
            ArrayList<String> skuIds = new ArrayList<>();
            for (CartShopProductBean bean : shopBean.getSkuList()) {
                if (bean.isSel.get())
                    skuIds.add(bean.getSkuId());
            }
            if (!skuIds.isEmpty())
                map.put(String.valueOf(shopBean.getShopId()), skuIds);
        }
        if (map.isEmpty())
            return;
        presenter.delProduct(map, -1);

    }

    /**
     * 清空失效商品
     */
    private void cleanAllInvalidSkus(CartShopBean shopBean) {
        ArrayList<CartShopBean> mShopBeans = generateInvalidShops(shopBean, true);
        delAllParams(mShopBeans, false);
    }

    /**
     * 清空失效商品
     */
    private ArrayList<CartShopBean> generateInvalidShops(CartShopBean shopBean, boolean cleanAll) {
        List<CartShopProductBean> skuList = shopBean.getSkuList();
        ArrayList<CartShopBean> mShopBeans = new ArrayList<>();
        String tempShopId = "";
        CartShopBean cartShopBean = null;
        for (int i = 0; i < skuList.size(); i++) {
            CartShopProductBean skuBean = skuList.get(i);
            if (cleanAll) {
                skuBean.isSel.set(true);
            }
            String shopId = skuBean.getShopId();
            if (tempShopId.equals(shopId)) {
                if ((null != cartShopBean) && (null != cartShopBean.getSkuList())) {
                    cartShopBean.getSkuList().add(skuBean);
                }
            } else {
                cartShopBean = new CartShopBean();
                List<CartShopProductBean> newSkuList = new ArrayList<>();
                newSkuList.add(skuBean);
                cartShopBean.setShopId(shopId);
                cartShopBean.setSkuList(newSkuList);
                mShopBeans.add(cartShopBean);
                tempShopId = shopId;
            }
        }
        return mShopBeans;
    }


    /**
     * 收藏
     * <p>
     * param shopBeans
     */
    private void collectAllParams(ArrayList<CartShopBean> shopBeans) {
        try {
            if (shopBeans == null)
                return;
            if (shopBeans.isEmpty())
                return;
//            ArrayList<String> skuIds = new ArrayList<>();
            StringBuilder buffer = new StringBuilder();
            int selectedCount = 0;
            for (CartShopBean shopBean : shopBeans) {
                for (CartShopProductBean bean : shopBean.getSkuList()) {
                    if (bean.isSel.get()) {
                        selectedCount++;
                    }
                    if (!bean.isCollect.get() && bean.isSel.get()) {
//                        skuIds.add(bean.getSkuId());
                        buffer.append(bean.getSkuId()).append(",");
                    }

                }
            }
            if (TextUtils.isEmpty(buffer.toString())) {
                if (selectedCount > 0) {
                    ToastUtil.showCenter(mActivity, "商品已收藏");
                } else {
                    ToastUtil.showCenter(mActivity, "请选择商品");
                }
                return;
            }
            toolPresenter.onCollect(buffer.toString().substring(0, buffer.length() - 1), "30", false, -1);
        } catch (Exception e) {
            e.printStackTrace();
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
            presenter.getCarts(false, "-1");
        }
    }

    @Override
    public void getHotProducts(ArrayList<ProductBean> beans) {
        if (beans == null)
            return;
        if (beans.isEmpty())
            return;
        recAdapter.update(beans, true);
        binding.recRecycler.notifyMoreFinish(false);
    }

    @Override
    public void getIndexInfo(MarketIndexBean bean) {

    }

    @Override
    public void getBrands(ArrayList<BrandBean> brandBeans, boolean hasMore, boolean isClear) {

    }

    @Override
    public void getFloorProducts(ArrayList<ProductBean> productBeans, boolean hasMore, boolean isClear) {

    }

    @Override
    public void getFloors(ArrayList<ProductRecBean> productBeans) {

    }


    /**
     * 显示地址弹窗
     */
    private void showAddAddressDialog() {
        new AlterDialog.Builder().context(getActivity())
                .title("提示")
                .msg("购物车需要收货地址判断商品库存\n立即去设置")
                .leftBtn("确定")
                .rightBtn("取消")
                .setDialogListener(new AlterDialog.DialogListener() {
                    @Override
                    public void onLeft() {
                        boolean isAddressManager = PrefrenceUtil.getInstance(getActivity()).getBoolean(ConShare.MENU_ADDRESS, false);
                        if (!isAddressManager) {
                            ToastUtil.showCenter(getActivity(), getResources().getString(R.string.common_author_null));
                            return;
                        } else {
                            ARouter.getInstance().build(ConRoute.ADDRESS.ADDRESS_MAIN).navigation();
                        }

                        dismissDialog();
                    }

                    @Override
                    public void onRight() {
                        dismissDialog();
                    }
                })
                .builder().show();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onError(ErrorBean errorBean) {
        String errorCode = errorBean.getCode();
        switch (errorCode) {
            case ERROR_PASTDUE:
            case ERROR_UNLOAD:
            case ERROR_UNLOAD_2:
            case ERROR_TIMEOUT: {
                cartAdapter.update(new ArrayList(), true);
                binding.empty.setVisibility(View.VISIBLE);
                binding.empty.setBtnEmpty("立即登录");
                binding.empty.setBtnGobuy("");
                binding.bottom.setVisibility(View.GONE);
                binding.tvManager.setVisibility(View.INVISIBLE);
                binding.tvAddress.setVisibility(View.INVISIBLE);
            }
            break;
            default:
                String msg = errorBean.getMsg();
                ToastUtil.showCenter(getActivity(), msg);
                break;
        }
    }


    /**
     * token是否存在
     * <p>
     * return
     */
    private boolean hasToken() {
        String token = PrefrenceUtil.getInstance(getActivity()).getString(ConShare.TOKEN, "");
        return !TextUtils.isEmpty(token);
    }


    /**
     * 显示选择地址的弹窗
     */
    void showSelAddressDialog() {
        if (addressBeans == null || addressBeans.isEmpty()) {
            ToastUtil.showCenter(mActivity, "无地址信息");
            return;
        }
        CartAddressPop addressPop = new CartAddressPop(getActivity(), addressBeans, mAddress.getAddressId());
        addressPop.setType(BasePop.MATCH_CUSTOM);
        addressPop.setGravity(Gravity.BOTTOM);
        addressPop.setY(ScreenSizeUtil.getScreenHeight(mActivity) * 4 / 5);
        addressPop.setListener(bean -> {
            mAddress = bean;
            binding.tvAddress.setText(bean.getFullAddress());
        });

        addressPop.show(getChildFragmentManager(), "address");
    }

    private void removeRecyclerAnimation(RecyclerView recyclerView) {
//        recyclerView.getItemAnimator().setAddDuration(0);
        recyclerView.getItemAnimator().setChangeDuration(5000);
//        recyclerView.getItemAnimator().setMoveDuration(0);
//        recyclerView.getItemAnimator().setRemoveDuration(0);
//        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

    }
}

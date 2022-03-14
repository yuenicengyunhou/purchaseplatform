package com.rails.purchaseplatform.market.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.MarketIndexBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.contract.MarKetIndexPresenterImpl;
import com.rails.lib_data.contract.MarketIndexContract;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.framwork.utils.file.FileCacheUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.ProductHotAdapter;
import com.rails.purchaseplatform.market.adapter.RankBrandAdapter;
import com.rails.purchaseplatform.market.adapter.RankProductAdapter;
import com.rails.purchaseplatform.market.databinding.FragmentMarketRankBinding;
import com.rails.purchaseplatform.market.ui.activity.ShopsActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 楼层当个品类排行列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/6/9
 */
public class RankFragment extends LazyFragment<FragmentMarketRankBinding> implements MarketIndexContract.MarketIndexView {

    private String categoryId;

    private String pageSize = "10";
    private final int DEF_PAGE = 1;
    private int page = DEF_PAGE;
    private MarketIndexContract.MarketIndexPresenter presenter;
    private RankBrandAdapter brandAdapter;
    private RankProductAdapter productAdapter;

    public RankFragment() {

    }

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        categoryId = extras.getString("categoryId");
    }

    public static RankFragment getInstance(String categoryId) {
        Bundle bundle = new Bundle();
        bundle.putString("categoryId",categoryId);
        RankFragment rankFragment = new RankFragment();
        rankFragment.setArguments(bundle);
        return rankFragment;

    }


    @Override
    protected void loadData() {
        binding.empty.setDescEmpty(R.string.market_cart_null).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        binding.cartRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        if (TextUtils.isEmpty(categoryId)) {
            brandAdapter = new RankBrandAdapter(getActivity());
            binding.cartRecycler.setAdapter(brandAdapter);
            brandAdapter.setListener(new PositionListener<BrandBean>() {
                @Override
                public void onPosition(BrandBean bean, int position) {
                    String brandId = bean.getBrandId();
                    Bundle bundle = new Bundle();
                    if (TextUtils.isEmpty(brandId))
                        return;
                    try {
                        bundle.putString("brandId", brandId);
                        startIntent(ShopsActivity.class, bundle);
                    } catch (Exception e) {

                    }
                }
            });
            ArrayList<BrandBean> brandBeans = (ArrayList<BrandBean>) FileCacheUtil.getInstance(getActivity()).readObject("brand");
            if (brandBeans != null)
                brandAdapter.update(brandBeans, true);

        } else {
            productAdapter = new RankProductAdapter(getActivity());
            binding.cartRecycler.setAdapter(productAdapter);
            productAdapter.setListener(new PositionListener<ProductBean>() {
                @Override
                public void onPosition(ProductBean bean, int position) {
                    if (TextUtils.isEmpty(bean.getItemId())) {
                        ToastUtil.showCenter(getActivity(), "商品不存在或已下架");
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("itemId", bean.getItemId());
                    goLogin(null, ConRoute.MARKET.PRODUCT_DETAIL, bundle);
                }

            });
        }
        binding.cartRecycler.setEmptyView(binding.empty);

        presenter = new MarKetIndexPresenterImpl(getActivity(), this);
        onRefresh();
    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void getHotProducts(ArrayList<ProductBean> beans) {

    }

    @Override
    public void getIndexInfo(MarketIndexBean bean) {

    }

    @Override
    public void getBrands(ArrayList<BrandBean> brandBeans, boolean hasMore, boolean isClear) {
        if (TextUtils.isEmpty(categoryId)) {
            binding.swipe.finishLoadMore();
            binding.swipe.setEnableLoadMore(hasMore);
            brandAdapter.update(brandBeans, isClear);
        }
    }

    @Override
    public void getFloorProducts(ArrayList<ProductBean> productBeans, boolean hasMore, boolean isClear) {
        if (!TextUtils.isEmpty(categoryId)) {
            binding.swipe.finishLoadMore();
            binding.swipe.setEnableLoadMore(hasMore);
            productAdapter.update(productBeans, isClear);
        }
    }

    @Override
    public void getFloors(ArrayList<ProductRecBean> productBeans) {

    }


    /**
     * 刷新效果
     */
    private void onRefresh() {
        binding.swipe.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                binding.swipe.finishRefresh();
                binding.swipe.setEnableLoadMore(true);
                page = DEF_PAGE;
                notifyData(false, page);
            }
        });
        binding.swipe.setOnLoadMoreListener(refreshLayout -> {
            page++;
            notifyData(false, page);
        });
        notifyData(true, page);
    }


    /**
     * @param isDialog
     * @param page
     */
    private void notifyData(boolean isDialog, int page) {
        presenter.getRanks(isDialog, page, pageSize, "0", "", categoryId);
    }


    /**
     * token是否存在
     *
     * @return
     */
    private boolean hasToken() {
        String token = PrefrenceUtil.getInstance(getActivity()).getString(ConShare.TOKEN, "");
        if (TextUtils.isEmpty(token))
            return false;
        else
            return true;
    }


    /**
     * 跳转页面
     *
     * @param cls
     * @param aPath
     */
    private void goLogin(Class cls, String aPath, Bundle bundle) {
        if (!hasToken()) {
            ARouter.getInstance()
                    .build(ConRoute.USER.LOGIN)
                    .navigation();
        } else {
            if (cls == null) {
                ARouter.getInstance().build(aPath).with(bundle).navigation();
            } else
                startIntent(cls);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            binding.cartRecycler.detachedObserver();
        } catch (Exception e) {

        }

    }

}

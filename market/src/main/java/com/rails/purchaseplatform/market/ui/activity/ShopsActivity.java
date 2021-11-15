package com.rails.purchaseplatform.market.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.MarketIndexBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.contract.MarKetIndexPresenterImpl;
import com.rails.lib_data.contract.MarketIndexContract;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.RankShopAdapter;
import com.rails.purchaseplatform.market.databinding.ActivityShopsBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 店铺列表activity
 *
 * @author： sk_comic@163.com
 * @date: 2021/11/8
 */
public class ShopsActivity extends ToolbarActivity<ActivityShopsBinding> implements MarketIndexContract.MarketIndexView {

    private String pageSize = "10";
    private final int DEF_PAGE = 1;
    private int page = DEF_PAGE;
    private MarketIndexContract.MarketIndexPresenter presenter;

    private String brandId;
    private RankShopAdapter adapter;

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        brandId = extras.getString("brandId");
    }


    @Override
    protected int getColor() {
        return android.R.color.white;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected void initialize(Bundle bundle) {
        binding.titleBar.setImgLeftRes(R.drawable.svg_back_black)
                .setTitleBar(R.string.market_rank_shops);
        presenter = new MarKetIndexPresenterImpl(this, this);

        barBinding.empty.setDescEmpty(R.string.market_cart_null).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        barBinding.cartRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        adapter = new RankShopAdapter(this);
        barBinding.cartRecycler.setAdapter(adapter);
        barBinding.cartRecycler.setEmptyView(barBinding.empty);

        adapter.setListener(new PositionListener<BrandBean>() {
            @Override
            public void onPosition(BrandBean bean, int position) {
                String shopId = bean.getShopid();
                Bundle bundle = new Bundle();
                if (TextUtils.isEmpty(shopId))
                    return;
                try {
                    bundle.putString("shopInfoId", shopId);
                    goLogin(null, ConRoute.MARKET.SHOP_DETAILS, bundle);
                } catch (Exception e) {

                }
            }
        });

        onRefresh();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    /**
     * 刷新效果
     */
    private void onRefresh() {
        barBinding.swipe.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                barBinding.swipe.finishRefresh();
                barBinding.swipe.setEnableLoadMore(true);
                page = DEF_PAGE;
                notifyData(false, page);
            }
        });
        barBinding.swipe.setOnLoadMoreListener(refreshLayout -> {
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
        presenter.getRanks(isDialog, page, pageSize, "1", brandId, "");
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


    /**
     * token是否存在
     *
     * @return
     */
    private boolean hasToken() {
        String token = PrefrenceUtil.getInstance(this).getString(ConShare.TOKEN, "");
        if (TextUtils.isEmpty(token))
            return false;
        else
            return true;
    }

    @Override
    public void getHotProducts(ArrayList<ProductBean> beans) {

    }

    @Override
    public void getIndexInfo(MarketIndexBean bean) {

    }

    @Override
    public void getBrands(ArrayList<BrandBean> brandBeans, boolean hasMore, boolean isClear) {
        barBinding.swipe.finishLoadMore();
        barBinding.swipe.setEnableLoadMore(hasMore);
        adapter.update(brandBeans, isClear);
    }

    @Override
    public void getFloorProducts(ArrayList<ProductBean> productBeans, boolean hasMore, boolean isClear) {

    }
}

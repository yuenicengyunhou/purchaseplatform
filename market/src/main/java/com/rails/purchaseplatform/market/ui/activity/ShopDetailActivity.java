package com.rails.purchaseplatform.market.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.rails.lib_data.bean.shop.ResultListBean;
import com.rails.lib_data.bean.shop.ShopInfoBean;
import com.rails.lib_data.contract.ShopContract;
import com.rails.lib_data.contract.ShopPresenterImp;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.ShopAdapter;
import com.rails.purchaseplatform.market.databinding.ActivityMarketShopBinding;
import com.rails.purchaseplatform.market.ui.pop.FilterShopPop;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;


import static com.rails.purchaseplatform.market.config.MarketConfig.SHOP_RECOMMEND_PAGE_SIZE;

/**
 * 店铺详情
 * <p>
 * author： sk_comic@163.com
 * date: 2021/3/23
 */
@Route(path = ConRoute.MARKET.SHOP_DETAILS)
public class ShopDetailActivity extends ToolbarActivity<ActivityMarketShopBinding> implements ShopContract.ShopView {

    //    private SearchResultRecyclerAdapter adapter;
    private ShopAdapter adapter;
    private FilterShopPop filterPop;
    private ShopPresenterImp presenter;
    private long shopInfoId;
    private final int PAGE_DEF = 0;
    private int mPage = PAGE_DEF;
    private long platformId = 20L;

    /**
     * 排序：
     * <p>
     * 销量向下
     * orderColumn=saleCount&orderType=desc
     * 销量向上
     * orderColumn=saleCount&orderType=asc
     * <p>
     * 价格向上
     * orderColumn=sellPrice&orderType=asc
     * 价格向下
     * orderColumn=sellPrice&orderType=desc
     */


    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        shopInfoId = extras.getLong("shopInfoId");
        shopInfoId = 202003030111L;
    }

    @Override
    protected void initialize(Bundle bundle) {
        binding.titleBar.setImgLeftRes(R.drawable.svg_back_black)
                .setTitleBar(R.string.market_shop);

        barBinding.swipe.setEnableLoadMore(false);

        adapter = new ShopAdapter(this);

        barBinding.recRecycler.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        barBinding.recRecycler.setAdapter(adapter);
        barBinding.swipe.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            presenter.getShopItemList(platformId, shopInfoId, mPage, SHOP_RECOMMEND_PAGE_SIZE, "", "");
        });
        presenter = new ShopPresenterImp(this, this);
        presenter.getShopDetails(shopInfoId);
        presenter.getShopItemList(platformId, shopInfoId, mPage, SHOP_RECOMMEND_PAGE_SIZE, "", "");


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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onClick() {
        super.onClick();
        //true向上 false向下
        barBinding.rbSale.setOnClickListener(v -> {
                    setSelected(false, true, false, false);
                    mPage = PAGE_DEF;
                    String type = barBinding.rbSale.isChecked() ? "asc" : "desc";
                    presenter.getShopItemList(20, 10L, mPage, SHOP_RECOMMEND_PAGE_SIZE, "saleCount", type);
                }
        );

        barBinding.rbPrice.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    setSelected(false, false, true, false);
                    mPage = PAGE_DEF;
                    String type = barBinding.rbPrice.isChecked() ? "asc" : "desc";
                    presenter.getShopItemList(20, 10L, mPage, SHOP_RECOMMEND_PAGE_SIZE, "sellPrice", type);
                }

        );

        barBinding.rbAll.setOnClickListener(v -> {
            setSelected(true, false, false, false);
            presenter.getShopItemList(20, 10L, mPage, SHOP_RECOMMEND_PAGE_SIZE, "", "");
        });

        barBinding.rbSel.setOnClickListener(v -> showPop());

        barBinding.swipe.setEnableLoadMore(true);
    }


    /**
     * 设置选中的view
     * <p>
     * param booleans
     */
    private void setSelected(Boolean... booleans) {
        int len = booleans.length;
        if (len <= 0)
            return;
        barBinding.rbAll.setSelected(booleans[0]);
        barBinding.rbSale.setSelected(booleans[1]);
        barBinding.rbPrice.setSelected(booleans[2]);
        barBinding.rbSel.setSelected(booleans[3]);
    }


    private void showPop() {
        if (filterPop == null) {
            filterPop = new FilterShopPop();
            filterPop.setType(BasePop.MATCH_WRAP);
            filterPop.setGravity(Gravity.BOTTOM);
        }
        filterPop.show(getSupportFragmentManager(), "shop");

    }


    @Override
    public void loadShopInfo(ShopInfoBean shop) {
        barBinding.setShopInfo(shop);
    }

    @Override
    public void loadShopProductList(ArrayList<ResultListBean> list) {
//        barBinding.swipe.loadmo
        barBinding.swipe.finishLoadMore();
        if (!list.isEmpty()) {
            adapter.update(list, mPage == PAGE_DEF);
        }
    }

    /**/
}

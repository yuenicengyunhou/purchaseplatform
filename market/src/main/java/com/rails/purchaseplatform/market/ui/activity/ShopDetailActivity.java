package com.rails.purchaseplatform.market.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.shop.ResultListBean;
import com.rails.lib_data.bean.shop.ShopInfoBean;
import com.rails.lib_data.contract.ShopContract;
import com.rails.lib_data.contract.ShopPresenterImp;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.ShopAdapter;
import com.rails.purchaseplatform.market.databinding.ActivityMarketShopBinding;
import com.rails.purchaseplatform.market.ui.pop.FilterShopPop;

import java.text.MessageFormat;
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
    private ShopPresenterImp presenter;
    private String shopInfoId;
    private final int PAGE_DEF = 1;
    private int mPage = PAGE_DEF;
    private ArrayList<SearchFilterBean> filterList = null;
    //    private ArrayList<SearchFilterBean> FILTER_DEF = null;
    private String orderColumn = "";//默认的排序方式为空字符
    private String orderType = "";//默认的排序顺序为空字符
    private FilterShopPop mPop;
    private int userScrollY;//用户滑动距离

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
        shopInfoId = extras.getString("shopInfoId");
//        shopInfoId = 202003030111L;
    }

    @Override
    protected void initialize(Bundle bundle) {
        binding.titleBar.setImgLeftRes(R.drawable.svg_back_black)
                .setTitleBar(R.string.market_shop);

        barBinding.swipe.setEnableLoadMore(true);

        adapter = new ShopAdapter(this);

        barBinding.recRecycler.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        barBinding.empty.setDescEmpty(R.string.market_cart_null).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        barBinding.empty.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        barBinding.recRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                userScrollY = userScrollY + dy;
                if (userScrollY > 1000) {
                    barBinding.imgTop.setVisibility(View.VISIBLE);
                } else {
                    barBinding.imgTop.setVisibility(View.GONE);
                }
            }
        });
        barBinding.recRecycler.setAdapter(adapter);
        barBinding.recRecycler.setEmptyView(barBinding.empty);

        barBinding.imgTop.setOnClickListener(v -> {
            barBinding.recRecycler.scrollToPosition(0);
            barBinding.imgTop.setVisibility(View.GONE);
            userScrollY = 0;
        });

        barBinding.ivMakePhone.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + barBinding.tvPhone.getText().toString()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });


        //上拉加载
        barBinding.swipe.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            queryShopProductList();
        });

        //下拉刷新
        barBinding.swipe.setOnRefreshListener(refreshLayout -> {
//            clearAllConditions();
            mPage = PAGE_DEF;
            queryShopInfo();
            queryShopProductList();
        });

//        clearAllConditions();
        setSelected(true, false, false, false);


        presenter = new ShopPresenterImp(this, this);
        queryShopInfo();//获取店铺信息
        queryShopProductList();//获取店铺商品列表

    }

    private void queryShopInfo() {
        presenter.getShopDetails(shopInfoId);
    }

    private void queryShopProductList() {
        presenter.getShopItemList(mPage == PAGE_DEF, shopInfoId, mPage, SHOP_RECOMMEND_PAGE_SIZE, orderColumn, orderType, filterList);
    }

    /**
     * 清除所有筛选条件
     */
//    private void clearAllConditions() {
////        setSelected(true, false, false, false);
//        mPage = PAGE_DEF;
////        orderType = "";
////        orderColumn = "";
////        filterList = null;
////        if (null != mPop) {
////            mPop = null;
////        }
//    }
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
                    orderType = barBinding.rbSale.isChecked() ? "asc" : "desc";
                    orderColumn = "saleCount";
                    presenter.getShopItemList(true, shopInfoId, mPage, SHOP_RECOMMEND_PAGE_SIZE, orderColumn, orderType, filterList);
                }
        );

        barBinding.rbPrice.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    setSelected(false, false, true, false);
                    mPage = PAGE_DEF;
                    orderType = barBinding.rbPrice.isChecked() ? "asc" : "desc";
                    orderColumn = "sellPrice";
                    presenter.getShopItemList(true, shopInfoId, mPage, SHOP_RECOMMEND_PAGE_SIZE, orderColumn, orderType, filterList);
                }

        );

        barBinding.rbAll.setOnClickListener(v -> {
            setSelected(true, false, false, false);
            orderColumn = "";
            orderType = "";
            mPage = PAGE_DEF;
            presenter.getShopItemList(true, shopInfoId, mPage, SHOP_RECOMMEND_PAGE_SIZE, orderColumn, orderType, filterList);
        });

        barBinding.rbSel.setOnClickListener(v -> showPop());

//        barBinding.swipe.setEnableLoadMore(true);
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

    /**
     * 筛选弹窗
     */
    private void showPop() {
        if (null == filterList) return;
        if (null == mPop) {
            mPop = new FilterShopPop(filterList, FilterShopPop.MODE_4);
            mPop.setCompleteListener(filterbeans -> {
                filterList = filterbeans;
                mPage = PAGE_DEF;
                presenter.getShopItemList(true, shopInfoId, mPage, SHOP_RECOMMEND_PAGE_SIZE, orderColumn, orderType, filterList);
            });
            mPop.setGravity(Gravity.BOTTOM);
        }
        mPop.setType(BasePop.MATCH_WRAP);
        mPop.show(getSupportFragmentManager(), "property");
    }


    @Override
    public void loadShopInfo(ShopInfoBean shop) {
        barBinding.setShopInfo(shop);
        if (null != shop) {
            barBinding.tvPhone.setText(MessageFormat.format("联系电话：{0}", shop.getMobile()));
            barBinding.tvOrganizeName.setText(MessageFormat.format("供应商：{0}", shop.getOrganizeName()));
            if (TextUtils.isEmpty(shop.getMobile())) {
                barBinding.ivMakePhone.setVisibility(View.GONE);
            } else {
                barBinding.ivMakePhone.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void loadShopProductList(ArrayList<ResultListBean> list, int totalCount) {
        barBinding.swipe.finishLoadMore();
        barBinding.swipe.finishRefresh();
        if (mPage == PAGE_DEF) {
            adapter.update(list, true);
        } else {
            if (adapter.getItemCount() < totalCount) {
                adapter.update(list, false);
            }
        }
    }


    /**
     * 筛选
     */
    @Override
    public void loadFilter(ArrayList<SearchFilterBean> filterBeans) {
        if (null == filterList) {
//            FILTER_DEF = filterBeans;
//            filterList = FILTER_DEF;
            filterList = filterBeans;
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (null != mPop) {
            mPop = null;
        }
    }
}

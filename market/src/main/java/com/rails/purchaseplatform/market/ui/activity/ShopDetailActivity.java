package com.rails.purchaseplatform.market.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.shop.ResultListBean;
import com.rails.lib_data.bean.shop.ShopInfoBean;
import com.rails.lib_data.contract.ShopContract;
import com.rails.lib_data.contract.ShopPresenterImp;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
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
public class ShopDetailActivity extends BaseErrorActivity<ActivityMarketShopBinding> implements ShopContract.ShopView {

    private ShopAdapter adapter;
    private ShopPresenterImp presenter;
    private String shopInfoId = "";
    private final int PAGE_DEF = 1;
    private int mPage = PAGE_DEF;
    /**
     * 搜索
     */
    private ArrayList<SearchFilterBean> filterList = null;
    private String orderColumn = "";//默认的排序方式为空字符
    private String orderType = "";//默认的排序顺序为空字符
    private String mLowPrice = "";
    private String mHighPrice = "";
//    private FilterShopPop mPop;

    private int userScrollY;//用户滑动距离
    private FilterShopPop mPop;
    //    private int materialType;

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
        if (null == shopInfoId) {
            ToastUtil.showCenter(this, "店铺id为空");
        }
//        shopInfoId = 202003030111L;
    }

    @Override
    protected void initialize(Bundle bundle) {
//        binding.titleBar.setImgLeftRes(R.drawable.svg_back_black)
//                .setTitleBar(R.string.market_shop);
        setSupportActionBar(binding.toolbar);

        binding.swipe.setEnableLoadMore(true);

        adapter = new ShopAdapter(this);

        binding.recRecycler.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        binding.empty.setDescEmpty(R.string.market_cart_null).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        binding.empty.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        binding.recRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                userScrollY = userScrollY + dy;
                if (userScrollY > 1000) {
                    binding.imgTop.setVisibility(View.VISIBLE);
                } else {
                    binding.imgTop.setVisibility(View.GONE);
                }
            }
        });
        binding.recRecycler.setAdapter(adapter);
        binding.recRecycler.setEmptyView(binding.empty);

        binding.imgTop.setOnClickListener(v -> {
            binding.recRecycler.scrollToPosition(0);
            binding.imgTop.setVisibility(View.GONE);
            userScrollY = 0;
        });

        binding.ivMakePhone.setOnClickListener(v -> {
            String phoneText = binding.tvPhone.getText().toString();
            String substring = phoneText.substring(phoneText.indexOf("：") + 1);
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + substring));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        binding.ibBack.setOnClickListener(v -> finish());
        //上拉加载
        binding.swipe.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            queryShopProductList();
        });

        //下拉刷新
        binding.swipe.setOnRefreshListener(refreshLayout -> {
//            clearAllConditions();
            mPage = PAGE_DEF;
            queryShopInfo();
            queryShopProductList();
        });

        //店铺内搜索
        binding.tvSearch.setOnClickListener(v -> toSearch());
        //搜索确认按键监听
        binding.editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                toSearch();
                return true;
            }
            return false;
        });

//        clearAllConditions();
        setSelected(true, false, false, false);

        presenter = new ShopPresenterImp(this, this);
        queryShopInfo();//获取店铺信息


    }

    private void toSearch() {
        mPage = PAGE_DEF;
        queryShopProductList();
    }

    private void queryShopInfo() {
        presenter.getShopDetails(shopInfoId);
    }

    private void queryShopProductList() {
        presenter.getShopItemList(mPage == PAGE_DEF, shopInfoId, mPage, SHOP_RECOMMEND_PAGE_SIZE, orderColumn, orderType, filterList, binding.editText.getText().toString().trim(), mLowPrice, mHighPrice);
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
        binding.rbSale.setOnClickListener(v -> {
                    setSelected(false, true, false, false);
                    mPage = PAGE_DEF;
                    orderType = binding.rbSale.isChecked() ? "asc" : "desc";
                    orderColumn = "saleCount";
                    presenter.getShopItemList(true, shopInfoId, mPage, SHOP_RECOMMEND_PAGE_SIZE, orderColumn, orderType, filterList, binding.editText.getText().toString().trim(), mLowPrice, mHighPrice);
                }
        );

        binding.rbPrice.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    setSelected(false, false, true, false);
                    mPage = PAGE_DEF;
                    orderType = binding.rbPrice.isChecked() ? "asc" : "desc";
                    orderColumn = "sellPrice";
                    presenter.getShopItemList(true, shopInfoId, mPage, SHOP_RECOMMEND_PAGE_SIZE, orderColumn, orderType, filterList, binding.editText.getText().toString().trim(), mLowPrice, mHighPrice);
                }

        );

        binding.rbAll.setOnClickListener(v -> {
            setSelected(true, false, false, false);
            orderColumn = "";
            orderType = "";
            mPage = PAGE_DEF;
            presenter.getShopItemList(true, shopInfoId, mPage, SHOP_RECOMMEND_PAGE_SIZE, orderColumn, orderType, filterList, binding.editText.getText().toString().trim(), mLowPrice, mHighPrice);
        });

        binding.rbSel.setOnClickListener(v -> showPop());

//        binding.swipe.setEnableLoadMore(true);
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
        binding.rbAll.setSelected(booleans[0]);
        binding.rbSale.setSelected(booleans[1]);
        binding.rbPrice.setSelected(booleans[2]);
        binding.rbSel.setSelected(booleans[3]);
    }

    /**
     * 筛选弹窗
     */
    private void showPop() {
        if (null == filterList) return;
        if (null == mPop) {
            mPop = new FilterShopPop(filterList, FilterShopPop.MODE_4);
            mPop.setCompleteListener((filterbeans, lowPrice, highPrice) -> {
                filterList = filterbeans;
                mPage = PAGE_DEF;
                mLowPrice = lowPrice;
                mHighPrice = highPrice;
                presenter.getShopItemList(true, shopInfoId, mPage, SHOP_RECOMMEND_PAGE_SIZE, orderColumn, orderType, filterList, binding.editText.getText().toString().trim(), mLowPrice, mHighPrice);
            });
            mPop.setGravity(Gravity.BOTTOM);
            mPop.setType(BasePop.MATCH_CUSTOM);
            mPop.setY(ScreenSizeUtil.getScreenHeight(this) * 4 / 5);
        }
        mPop.show(getSupportFragmentManager(), "property");
    }


    @Override
    public void loadShopInfo(ShopInfoBean shop) {
        binding.setShopInfo(shop);
        String mobile = shop.getMobile();
        binding.tvPhone.setText(MessageFormat.format("联系电话：{0}", mobile));
        binding.tvOrganizeName.setText(MessageFormat.format("供应商：{0}", shop.getOrganizeName()));
        int shopStatus = shop.getShopStatus();
        if (shopStatus == 2) {
            binding.empty.setDescEmpty(R.string.market_shop_locked).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        }else {
            binding.empty.setDescEmpty(R.string.market_cart_null).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        }
        if (TextUtils.isEmpty(mobile)) {
            binding.ivMakePhone.setVisibility(View.GONE);
        } else {
            binding.ivMakePhone.setVisibility(View.VISIBLE);
        }
        queryShopProductList();//获取店铺商品列表
    }

    @Override
    public void loadShopProductList(ArrayList<ResultListBean> list, int totalCount) {
        binding.swipe.finishLoadMore();
        binding.swipe.finishRefresh();
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
    public void loadFilter(ArrayList<SearchFilterBean> filterBeans, boolean resetFilter) {
        if (resetFilter || null == filterList) {
            filterList = filterBeans;
        }
    }

}

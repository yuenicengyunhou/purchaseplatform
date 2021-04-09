package com.rails.purchaseplatform.market.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.SearchResultBean;
import com.rails.lib_data.contract.ProductContract;
import com.rails.lib_data.contract.SearchResultContract;
import com.rails.lib_data.contract.SearchResultPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.pop.OrderSearchFilterPop;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.market.adapter.SearchResultRecyclerAdapter;
import com.rails.purchaseplatform.market.adapter.SearchResultViewPagerAdapter;
import com.rails.purchaseplatform.market.databinding.ActivitySearchResultBinding;
import com.rails.purchaseplatform.market.ui.fragment.SearchResultByProductFragment;
import com.rails.purchaseplatform.market.ui.fragment.SearchResultByShopFragment;
import com.rails.purchaseplatform.market.ui.pop.SearchResultFilterPop;

import java.util.ArrayList;

/**
 * 搜索结果页
 * 1 - 展示搜索结果
 * 2 - 按照所选类型排序（综合、价格、时间、销量）
 * 3 - 可改变搜索结果展示方式（图片GridView，列表ListView）
 * 4 - 点击商品进入商品详情页面
 * 5 - 取消搜索类型时，应退回到搜索页面并设焦点在输入框
 * 6 - 展示搜索结果时，跳转到此页面之前应该保存本地搜索记录
 * 7 - 下拉刷新搜索结果，上拉加载更多搜索结果
 */
@Route(path = "/market/SearchResultActivity")
public class SearchResultActivity extends BaseErrorActivity<ActivitySearchResultBinding> implements
        SearchResultContract.SearchResultView, ProductContract.ProductView {

    private int mSearchType = 0;
    private String mSearchKey = "";

    private SearchResultRecyclerAdapter mSearchResultRecyclerAdapter;
    private SearchResultContract.SearchResultPresenter presenter;

    private ArrayList<Fragment> mFragments;
    private SearchResultViewPagerAdapter mPagerAdapter;


    private boolean salesSortFlag = true; // false 降序排列
    private boolean priceSortFlag = true; // true  升序排列

    private int mModuleFlag = 0;

    private OrderSearchFilterPop mFilterPop;

    @Override
    protected int getColor() {
        return 0;
    }

    @Override
    protected boolean isSetSystemBar() {
        return false;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected void initialize(Bundle bundle) {

        mSearchResultRecyclerAdapter = new SearchResultRecyclerAdapter(this);
        presenter = new SearchResultPresenterImpl(this, this);
        binding.brvSearchResult.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        binding.brvSearchResult.setAdapter(mSearchResultRecyclerAdapter);
        presenter.getSearchResult(false, 1);

        presenter.getProducts(true, 1, "20", "");


        mFragments = new ArrayList<>();
        mFragments.add(new SearchResultByProductFragment());
        mFragments.add(new SearchResultByShopFragment());
        mPagerAdapter = new SearchResultViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mFragments);


        binding.svpSearchResultPager.setAdapter(mPagerAdapter);
        binding.svpSearchResultPager.setOffscreenPageLimit(1);


        binding.tvTypeName.setText(mSearchType == 0 ? "商品" : "店铺");
        binding.cbPriceSort.setVisibility(mSearchType == 0 ? View.VISIBLE : View.INVISIBLE);
        binding.svpSearchResultPager.setCurrentItem(mSearchType);


        // 判断如果取不到到传过来的搜索Key就隐藏View，否则显示搜索Key
        if (mSearchKey.equals("") || mSearchKey == null) {
            binding.clSearchKey.setVisibility(View.GONE);
        } else {
            binding.tvSearchKey.setText(mSearchKey);
        }

        binding.cbCommonSort.setSelected(true);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onClick() {
        super.onClick();
        // 左上角的返回按钮
        binding.ibBack.setOnClickListener(v -> {
            finish();
        });

        // 筛选器
        binding.rlFilter.setOnClickListener(v -> {
            Toast.makeText(this, "暂时没有过滤规则", Toast.LENGTH_SHORT).show();
            if (mFilterPop == null) {
                mFilterPop = new OrderSearchFilterPop();
                mFilterPop.setType(BasePop.MATCH_WRAP);
                mFilterPop.setGravity(Gravity.BOTTOM);
            }
            mFilterPop.show(getSupportFragmentManager(), "searchFilter");
            // TODO: 2021/4/9 自定义筛选器
        });
        // TODO: 2021/3/25 筛选规则

        // 点击搜索关键字
        binding.tvSearchKey.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("search_key", binding.tvSearchKey.getText().toString());
            ARouter.getInstance().build(ConRoute.COMMON.SEARCH).with(bundle).navigation(this);
        });

        // 点击关键字后面的叉叉
        binding.ivSearchCancel.setOnClickListener(v -> {
            ARouter.getInstance().build(ConRoute.COMMON.SEARCH).navigation(this);
        });

        // 点击综合排序
        binding.cbCommonSort.setOnClickListener(v -> {
            setSelected(true, false, false);
            // TODO: 2021/3/27 发送请求 - 综合排序
        });

        // 点击销量排序
        binding.cbSaleSort.setOnClickListener(v -> {
            setSelected(false, true, false);
            salesSortFlag = !salesSortFlag;
            // TODO: 2021/3/27 发送请求 - 按销量升序或降序排列
        });

        // 点击价格排序
        binding.cbPriceSort.setOnClickListener(v -> {
            setSelected(false, false, true);
            priceSortFlag = !priceSortFlag;
            // TODO: 2021/3/27 发送请求 - 按价格升序或降序排列
        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @Override
    public void getSearchResult(ArrayList<SearchResultBean> searchResultBeans, boolean hasMore, boolean isClear) {
        mSearchResultRecyclerAdapter.update(searchResultBeans, isClear);
    }

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        mSearchType = extras.getInt("search_type");
        mSearchKey = extras.getString("search_key");
    }

    /**
     * 点击排序按钮
     *
     * @param booleans 被点击的为true否则为false
     */
    private void setSelected(Boolean... booleans) {
        int length = booleans.length;
        if (length <= 0) return;
        binding.cbCommonSort.setSelected(booleans[0]);
        binding.cbSaleSort.setSelected(booleans[1]);
        binding.cbPriceSort.setSelected(booleans[2]);
    }

    @Override
    public void getHotProducts(ArrayList<ProductBean> productBeans, boolean hasMore, boolean isClear) {

    }

    @Override
    public void getProducts(ArrayList<ProductBean> productBeans, boolean hasMore, boolean isClear) {

    }
}

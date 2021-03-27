package com.rails.purchaseplatform.market.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.bean.SearchResultBean;
import com.rails.lib_data.contract.SearchResultContract;
import com.rails.lib_data.contract.SearchResultPresenterImpl;
import com.rails.purchaseplatform.common.activity.SearchActivityX;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.SearchResultRecyclerAdapter;
import com.rails.purchaseplatform.market.databinding.ActivitySearchResultBinding;

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
public class SearchResultActivity extends BaseErrorActivity<ActivitySearchResultBinding> implements SearchResultContract.SearchResultView, View.OnClickListener {

    private String mSearchKey;

    private SearchResultRecyclerAdapter mSearchResultRecyclerAdapter;
    private SearchResultContract.SearchResultPresenter mSearchResultPresenter;

    private boolean salesSortFlag = true; // false 降序排列
    private boolean priceSortFlag = true; // true  升序排列

    private int mModuleFlag = 0;

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
        mSearchResultPresenter = new SearchResultPresenterImpl(this, this);
        binding.searchResultRecycler.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        binding.searchResultRecycler.setAdapter(mSearchResultRecyclerAdapter);
        mSearchResultPresenter.getSearchResult(false, 1);

        binding.tvSearchKey.setText(mSearchKey);
    }

    @Override
    protected void onClick() {
        super.onClick();
        // 左上角的返回按钮
        binding.ibBack.setOnClickListener(this);
        // 筛选器
        binding.ibFilter.setOnClickListener(this);
        // TODO: 2021/3/25 筛选规则
        // 点击搜索关键字
        binding.tvSearchKey.setOnClickListener(this);
        // 点击关键字后面的叉叉
        binding.ivCancel.setOnClickListener(this);

        // 点击综合排序
        binding.tvSynthesis.setOnClickListener(this);
        // 点击销量排序
        binding.rlSales.setOnClickListener(this);
        // 点击价格排序
        binding.rlPrice.setOnClickListener(this);

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
        mSearchKey = extras.getString("search_key");
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        // 点击返回按钮
        if (id == R.id.ib_back) {
            finish();
        }
        // 点击筛选按钮
        else if (id == R.id.ib_filter) {
            Toast.makeText(this, "暂时没有过滤规则", Toast.LENGTH_SHORT).show();
        }

        // 点击关键字灰色Layout
        // else if (id == R.id.ll_cancel) { // 这个不要了
        //     SearchResultActivity.this.startActivity(new Intent(SearchResultActivity.this, SearchActivityX.class));
        // }

        // 点击搜索关键字
        else if (id == R.id.tv_search_key) { // 这个按到文字，传入文字
            Bundle bundle = new Bundle();
            bundle.putString("search_key", binding.tvSearchKey.getText().toString());
            Intent intent = new Intent(this, SearchActivityX.class);
            intent.putExtra("search_key", bundle);
            startActivity(intent);
        }
        // 点击搜索关键字后面的叉叉
        else if (id == R.id.iv_cancel) { // 这个按到叉叉，不传文字
            startActivity(new Intent(this, SearchActivityX.class));
        }
        // 点击综合排序
        else if (id == R.id.tv_synthesis) {
            binding.tvSynthesis.setTextColor(R.color.font_blue);

            binding.tvSortOderSales.setTextColor(R.color.font_red);
            binding.tvSortOderPrice.setTextColor(R.color.font_red);

            binding.ivSaleTriangle.setVisibility(View.INVISIBLE);
            binding.ivPriceTriangle.setVisibility(View.INVISIBLE);

            // TODO: 2021/3/27 发送请求 - 综合排序
        }
        // 点击销量排序
        else if (id == R.id.rl_sales) {
            binding.tvSortOderSales.setTextColor(R.color.font_blue);

            binding.tvSynthesis.setTextColor(R.color.font_red);
            binding.tvSortOderPrice.setTextColor(R.color.font_red);

            binding.ivSaleTriangle.setVisibility(View.VISIBLE);
            binding.ivPriceTriangle.setVisibility(View.INVISIBLE);

            if (salesSortFlag) {
                binding.ivSaleTriangle.setImageResource(R.mipmap.icon_up);
            } else {
                binding.ivSaleTriangle.setImageResource(R.mipmap.icon_down);
            }

            salesSortFlag = !salesSortFlag;

            // TODO: 2021/3/27 发送请求 - 按销量升序或降序排列
        }
        // 点击价格排序
        else if (id == R.id.rl_price) {
            binding.tvSortOderPrice.setTextColor(R.color.font_blue);

            binding.tvSynthesis.setTextColor(R.color.font_red);
            binding.tvSortOderSales.setTextColor(R.color.font_red);

            binding.ivSaleTriangle.setVisibility(View.INVISIBLE);
            binding.ivPriceTriangle.setVisibility(View.VISIBLE);

            if (priceSortFlag) {
                binding.ivPriceTriangle.setImageResource(R.mipmap.icon_up);
            } else {
                binding.ivPriceTriangle.setImageResource(R.mipmap.icon_down);
            }

            priceSortFlag = !priceSortFlag;

            // TODO: 2021/3/27 发送请求 - 按价格升序或降序排列
        }
    }
}

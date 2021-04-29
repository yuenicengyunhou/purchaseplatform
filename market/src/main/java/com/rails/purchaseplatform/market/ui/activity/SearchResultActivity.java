package com.rails.purchaseplatform.market.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.SearchResultViewPagerAdapter;
import com.rails.purchaseplatform.market.databinding.ActivitySearchResultBinding;
import com.rails.purchaseplatform.market.ui.fragment.SearchResultByProductFragment;
import com.rails.purchaseplatform.market.ui.fragment.SearchResultByShopFragment;
import com.rails.purchaseplatform.market.ui.pop.PropertyPop;

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
public class SearchResultActivity extends BaseErrorActivity<ActivitySearchResultBinding>
        implements View.OnClickListener {

    private int mSearchType = 0;
    private String mSearchKey = "";
    private String mCid = "";

    private boolean salesSortFlag = true; // false 降序排列
    private boolean priceSortFlag = true; // true  升序排列

    SearchResultByProductFragment fragment1;
    SearchResultByShopFragment fragment2;

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
        Bundle fragmentBundle = new Bundle();
        fragmentBundle.putString("search_key", mSearchKey);
        fragmentBundle.putString("cid", mCid);
        fragment1 = new SearchResultByProductFragment();
        fragment1.setArguments(fragmentBundle);
        fragment2 = new SearchResultByShopFragment();
        fragment2.setArguments(fragmentBundle);
        ArrayList<Fragment> mFragments = new ArrayList<>();
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        SearchResultViewPagerAdapter mPagerAdapter = new SearchResultViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mFragments);


        binding.svpSearchResultPager.setAdapter(mPagerAdapter);
        binding.svpSearchResultPager.setOffscreenPageLimit(1);


        binding.tvTypeName.setText(mSearchType == 0 ? "商品" : "店铺");
        binding.cbPriceSort.setVisibility(mSearchType == 0 ? View.VISIBLE : View.GONE);
        binding.svpSearchResultPager.setCurrentItem(mSearchType);


        // 判断如果取不到到传过来的搜索Key就隐藏View，否则显示搜索Key
        if (mSearchKey == null || mSearchKey.equals("")) {
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
            ArrayList<SearchFilterBean> filterBeans = fragment1.getFilterData(); // 筛选条件
            if (filterBeans == null || filterBeans.size() == 0) {
                ToastUtil.showCenter(this, "没有过滤条件");
                return;
            }
            PropertyPop<SearchFilterBean> mPop = new PropertyPop<>(filterBeans, 3);
            mPop.setGravity(Gravity.BOTTOM);
            mPop.setType(BasePop.MATCH_WRAP);
            mPop.show(getSupportFragmentManager(), "property");

        });

        binding.tvSearchKey.setOnClickListener(this); // 点击搜索关键字
        binding.ivSearchCancel.setOnClickListener(this); // 点击关键字后面的叉叉
        binding.clSearchBar.setOnClickListener(this); // 点击搜索背景
        binding.tvTypeName.setOnClickListener(this); // 点击搜索类型文字

        // 点击综合排序
        binding.cbCommonSort.setOnClickListener(v -> {
            setSelected(true, false, false);
            // TODO: 2021/3/27 发送请求 - 综合排序
            if (mSearchType == 0) {
                fragment1.sort(null, null, mSearchKey, mCid);
            } else {
                fragment2.sort(null, null, mSearchKey, null);
            }
        });

        // 点击销量排序
        binding.cbSaleSort.setOnClickListener(v -> {
            setSelected(false, true, false);
            salesSortFlag = !salesSortFlag;
            // TODO: 2021/3/27 发送请求 - 按销量升序或降序排列
            if (mSearchType == 0) {
                fragment1.sort("saleCount", salesSortFlag ? "desc" : "asc", mSearchKey, mCid);
            } else {
                fragment2.sort("shopSaleCount", salesSortFlag ? "desc" : "asc", mSearchKey, null);
            }
        });

        // 点击价格排序
        binding.cbPriceSort.setOnClickListener(v -> {
            setSelected(false, false, true);
            priceSortFlag = !priceSortFlag;
            // TODO: 2021/3/27 发送请求 - 按价格升序或降序排列
            if (mSearchType == 0) {
                fragment1.sort("sellPrice", priceSortFlag ? "desc" : "asc", mSearchKey, mCid);
            } else {
//              fragment2.sort("sellPrice", priceSortFlag ? "desc" : "asc", mSearchKey, null);
            }
        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        mSearchType = extras.getInt("search_type");
        mSearchKey = extras.getString("search_key");
        mCid = extras.getString("cid");
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
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_searchKey) {
            Bundle bundle = new Bundle();
            bundle.putString("search_key", binding.tvSearchKey.getText().toString());
            ARouter.getInstance().build(ConRoute.COMMON.SEARCH).with(bundle).navigation(this);
        }
        if (id == R.id.iv_searchCancel || id == R.id.cl_searchBar || id == R.id.tv_type_name) {
            ARouter.getInstance().build(ConRoute.COMMON.SEARCH).navigation(this);
        }
    }


    public interface OnSortClick {

        /**
         * 排序
         *
         * @param orderColumn
         * @param orderType
         * @param keyword
         * @param cid
         */
        void sort(String orderColumn, String orderType, String keyword, String cid);

        /**
         * 筛选条件数据
         *
         * @return
         */
        ArrayList<SearchFilterBean> getFilterData();

    }
}

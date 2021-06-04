package com.rails.purchaseplatform.market.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.loading.LoadingDialog;
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
@Route(path = ConRoute.MARKET.SEARCH_RESULT)
public class SearchResultActivity extends BaseErrorActivity<ActivitySearchResultBinding>
        implements View.OnClickListener {

    private static int COUNTING = 1;
    private static LoadingDialog mLoadingDialog = null;

    private int mSearchType = 0;
    private String mSearchKey = "";
    private String mCid = "";
    private String mMode = "";

    private boolean salesSortFlag = false; // false 降序排列
    private boolean priceSortFlag = true; // true  升序排列

    private String mMinPrice = "", mMaxPrice = "";

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
        SearchResultViewPagerAdapter mPagerAdapter = new SearchResultViewPagerAdapter(
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                mFragments);


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

    @Override
    protected void onClick() {
        super.onClick();
        // 左上角的返回按钮
        binding.ibBack.setOnClickListener(v -> {
            finish();
        });

        // 筛选器
        binding.rlFilter.setOnClickListener(v -> {
            /* 触发点击事件弹出Loading, 在pop中最后一个元素加载完成后取消Loading */
            if (mLoadingDialog == null) {
                mLoadingDialog = new LoadingDialog
                        .Builder(SearchResultActivity.this,true)
                        .setMessage("Loading")
                        .createWithBackground();
                mLoadingDialog.setCancelable(true);
                mLoadingDialog.show();
            }
            // 商品筛选弹窗
            if (mSearchType == 0) {
                ArrayList<SearchFilterBean> filterBeans = fragment1.getFilterData(); // 筛选条件
                if (filterBeans == null || filterBeans.size() == 0) {
                    ToastUtil.showCenter(this, "没有过滤条件");
                    return;
                }
                PropertyPop<SearchFilterBean> mPop = new PropertyPop<>(filterBeans, 3, mMinPrice, mMaxPrice);
                mPop.setGravity(Gravity.BOTTOM);
                mPop.setType(BasePop.MATCH_WRAP);
                mPop.setFilterListener(new PropertyPop.DoFilter() {
                    @Override
                    public void doFilter(String brand, String cid, String categoryAttr, String expandAttr, String minPrice, String maxPrice) {
                        fragment1.sendFilterData(new String[]{brand, cid, categoryAttr, expandAttr, minPrice, maxPrice}, 1);
                        mMinPrice = minPrice;
                        mMaxPrice = maxPrice;
                    }
                });
                mPop.show(getSupportFragmentManager(), "property");
            }
            // 店铺筛选弹窗
            else {
                ArrayList<SearchFilterBean> filterBeans = fragment2.getFilterData();
                if (filterBeans == null || filterBeans.size() == 0) {
                    ToastUtil.showCenter(this, "没有过滤条件");
                    return;
                }
                PropertyPop<SearchFilterBean> mPop = new PropertyPop<>(filterBeans, 4);
                mPop.setGravity(Gravity.BOTTOM);
                mPop.setType(BasePop.MATCH_WRAP);

                mPop.setShopFilterListener(new PropertyPop.DoShopFilter() {
                    @Override
                    public void doShopFilter(String isBought, String shopType, String saleArea) {
                        fragment2.sendShopFilterData(1, isBought, shopType, saleArea);
                    }
                });

                mPop.show(getSupportFragmentManager(), "property");
            }
        });

        binding.tvSearchKey.setOnClickListener(this); // 点击搜索关键字
        binding.ivSearchCancel.setOnClickListener(this); // 点击关键字后面的叉叉
        binding.clSearchBar.setOnClickListener(this); // 点击搜索背景
        binding.tvTypeName.setOnClickListener(this); // 点击搜索类型文字

        // 点击综合排序
        binding.cbCommonSort.setOnClickListener(v -> {
            if (mSearchType == 0) {
                fragment1.sort(1, null, null, mSearchKey, mCid);
            } else {
                fragment2.sort(1, null, null, mSearchKey, null);
            }
            setSelected(true, false, false);
            priceSortFlag = true;
            salesSortFlag = false;
        });

        // 点击销量排序
        binding.cbSaleSort.setOnClickListener(v -> {
            if (mSearchType == 0) {
                fragment1.sort(1, "saleCount", salesSortFlag ? "asc" : "desc", mSearchKey, mCid);
            } else {
                fragment2.sort(1, "shopSaleCount", salesSortFlag ? "asc" : "desc", mSearchKey, null);
            }
            setSelected(false, true, false);
            binding.cbSaleSort.setChecked(salesSortFlag);
            salesSortFlag = !salesSortFlag;
            priceSortFlag = true;
        });

        // 点击价格排序
        binding.cbPriceSort.setOnClickListener(v -> {
            if (mSearchType == 0) {
                fragment1.sort(1, "sellPrice", priceSortFlag ? "asc" : "desc", mSearchKey, mCid);
            } else {
                // fragment2.sort("sellPrice", priceSortFlag ? "desc" : "asc", mSearchKey, null);
            }
            setSelected(false, false, true);
            binding.cbPriceSort.setChecked(priceSortFlag);
            priceSortFlag = !priceSortFlag;
            salesSortFlag = false;
        });

        // 点击搜索按钮 刷新当前搜索结果（重新搜索）
        binding.tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchType == 0) {
                    fragment1.search(1);
                } else {
                    fragment2.search(1);
                }
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
        mMode = extras.getString("mode");
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
            goSearchActivity(2047);
        }
        if (id == R.id.iv_searchCancel || id == R.id.cl_searchBar || id == R.id.tv_type_name) {
            goSearchActivity(2048);
        }
    }

    private void goSearchActivity(int resultCode) {
        if (mMode.equals("form_search")) {
            setResult(resultCode);
            finish();
        } else {
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
        void sort(int page, String orderColumn, String orderType, String keyword, String cid);

        void search(int page);

        /**
         * 筛选条件数据
         *
         * @return
         */
        ArrayList<SearchFilterBean> getFilterData();


    }

    public interface OnFilterClick {

        /**
         * 筛选条件组装发送的数据
         *
         * @return
         */
        void sendFilterData(String[] strings, int page);

    }

    public interface OnShopFilterClick {

        /**
         * 筛选条件
         *
         * @return
         */
        void sendShopFilterData(int page, String isBought, String shopType, String saleArea);

    }

    public static class LoadingHandlerInSearchActivity extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == COUNTING) {
                if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                    mLoadingDialog = null;
                }
            }
        }
    }
}

package com.rails.purchaseplatform.common.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.HotSearchBean;
import com.rails.lib_data.contract.HotSearchContract;
import com.rails.lib_data.contract.HotSearchPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.adapter.FlowLayoutManager;
import com.rails.purchaseplatform.common.adapter.HotSearchRecyclerAdapter;
import com.rails.purchaseplatform.common.adapter.SearchHistoryFlowAdapter;
import com.rails.purchaseplatform.common.adapter.SpaceItemDecoration;
import com.rails.purchaseplatform.common.databinding.ActivitySearchXBinding;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 搜索页面，点击首页搜索跳转到此页面
 * 1 - 是否需要自动完成
 * 2 - 展示搜索历史（最近10条，存储在本地）
 * 3 - 展示热门搜索（由服务器返回）
 * 4 - PopWindow，用来更改搜索商品或者搜索店铺
 * 5 -
 */
@Route(path = "/common/SearchActivityX")
public class SearchActivityX extends BaseErrorActivity<ActivitySearchXBinding> implements HotSearchContract.HotSearchView, View.OnKeyListener {
    final private String TAG = SearchActivityX.class.getName();

    private SharedPreferences mSp;
    private List<String> mHistorySearchList;

    private SearchHistoryFlowAdapter mSearchHistoryFlowAdapter;
    private FlowLayoutManager mFlowLayoutManager;

    private HotSearchContract.HotSearchPresenter mHotSearchPresenter;
    private HotSearchRecyclerAdapter mHotSearchRecyclerAdapter;


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

        mSp = this.getSharedPreferences("SEARCH_HISTORY", Context.MODE_PRIVATE);
        mHistorySearchList = new ArrayList<>();


        getSharedPreferenceData();


        mSearchHistoryFlowAdapter = new SearchHistoryFlowAdapter(this, mHistorySearchList);
        mFlowLayoutManager = new FlowLayoutManager();
        binding.recyclerSearchHistory.setLayoutManager(mFlowLayoutManager);
        binding.recyclerSearchHistory.addItemDecoration(new SpaceItemDecoration(28));
        binding.recyclerSearchHistory.setAdapter(mSearchHistoryFlowAdapter);

        mHotSearchRecyclerAdapter = new HotSearchRecyclerAdapter(this);
        mHotSearchPresenter = new HotSearchPresenterImpl(this, this);
        binding.recyclerHotSearch.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        binding.recyclerHotSearch.setAdapter(mHotSearchRecyclerAdapter);
        mHotSearchPresenter.getHotSearch(false, 1);
    }

    /**
     * 把搜索记录存入SP
     */
    private void putSearchKeyInSharedPreference() {
        SharedPreferences.Editor editor = mSp.edit();
        editor.clear();
        for (int i = 0; i < mHistorySearchList.size(); i++) {
            editor.putString("HISTORY" + i, mHistorySearchList.get(i));
        }
        editor.apply();
    }

    /**
     * 获取本地数据
     */
    private void getSharedPreferenceData() {
        if (mSp != null) {
            int size = mSp.getAll().size();
            for (int i = 0; i < size; i++) {
                mHistorySearchList.add(mSp.getString("HISTORY" + i, ""));
            }
        }
    }

    /**
     * 更新列表
     *
     * @param text
     */
    private void updateList(String text) {
        Log.d(TAG, String.valueOf(mHistorySearchList.contains(text)));
        mHistorySearchList.remove(text);
        mHistorySearchList.add(0, text);
        Log.d(TAG, String.valueOf(mHistorySearchList.size()));
        if (mHistorySearchList.size() > 10) mHistorySearchList.remove(10);
        mSearchHistoryFlowAdapter.notifyDataSetChanged();
    }

    /**
     * 判断输入框是否为空
     *
     * @param text
     * @return
     */
    private boolean isEmptyText(String text) {
        Log.d(TAG, String.valueOf(text.equals("")));
        if (Objects.equals(text, "")) {
            Toast.makeText(SearchActivityX.this, "搜索不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void getHotSearch(ArrayList<HotSearchBean> hotSearchBeans, boolean hasMore, boolean isClear) {
        mHotSearchRecyclerAdapter.update(hotSearchBeans, true);
    }

    @Override
    protected void onClick() {
        super.onClick();
        // 左上角的返回按钮
        binding.ibBack.setOnClickListener(v -> SearchActivityX.this.finish());

        // 右上角搜索按钮
        binding.tvSearch.setOnClickListener(v -> {

            String text = binding.searchText.getText().toString().trim();

            if (isEmptyText(text)) return;

            updateList(text);

            putSearchKeyInSharedPreference();

            ARouter.getInstance().build(ConRoute.MARKET.SEARCH_RESULT).navigation();
        });
    }

    // TODO: 2021/3/25 软键盘回车键跳转
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {

            String text = binding.searchText.getText().toString().trim();

            if (isEmptyText(text)) return false;

            updateList(text);

            putSearchKeyInSharedPreference();

            ARouter.getInstance().build(ConRoute.MARKET.SEARCH_RESULT).navigation();

            return true;
        }
        return false;
    }
}

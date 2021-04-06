package com.rails.purchaseplatform.common.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.HotSearchBean;
import com.rails.lib_data.contract.HotSearchContract;
import com.rails.lib_data.contract.HotSearchPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.R;
import com.rails.purchaseplatform.common.adapter.FlowLayoutManager;
import com.rails.purchaseplatform.common.adapter.HotSearchRecyclerAdapter;
import com.rails.purchaseplatform.common.adapter.SearchHistoryFlowAdapter;
import com.rails.purchaseplatform.common.adapter.SpaceItemDecoration;
import com.rails.purchaseplatform.common.databinding.ActivitySearchXBinding;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;

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
public class SearchActivityX extends BaseErrorActivity<ActivitySearchXBinding>
        implements HotSearchContract.HotSearchView, View.OnKeyListener,
        HotSearchRecyclerAdapter.OnClickCallBack, SearchHistoryFlowAdapter.OnClickCallBack {

    private SharedPreferences mSp;
    private List<String> mHistorySearchList;

    private SearchHistoryFlowAdapter mSearchHistoryFlowAdapter;
    private FlowLayoutManager mFlowLayoutManager;

    private HotSearchContract.HotSearchPresenter mHotSearchPresenter;
    private HotSearchRecyclerAdapter mHotSearchRecyclerAdapter;

    private PopupWindow mPopupWindow;

    private String mSearchKey;
    private int mSearchType = 0; // 0-商品, 1-店铺,

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

        if (bundle != null) binding.searchText.setText(bundle.getString("search_key"));

        getSharedPreferenceData();

        mSearchHistoryFlowAdapter = new SearchHistoryFlowAdapter(this, mHistorySearchList);
        mFlowLayoutManager = new FlowLayoutManager();
        mSearchHistoryFlowAdapter.setListener(this);
        binding.recyclerSearchHistory.setLayoutManager(mFlowLayoutManager);
        binding.recyclerSearchHistory.addItemDecoration(new SpaceItemDecoration(28));
        binding.recyclerSearchHistory.setAdapter(mSearchHistoryFlowAdapter);

        mHotSearchRecyclerAdapter = new HotSearchRecyclerAdapter(this);
        mHotSearchPresenter = new HotSearchPresenterImpl(this, this);
        mHotSearchRecyclerAdapter.setListener(this);
        binding.recyclerHotSearch.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        binding.recyclerHotSearch.setAdapter(mHotSearchRecyclerAdapter);
        mHotSearchPresenter.getHotSearch(false, 1);

        setSearchType(0);

        binding.searchText.setText(mSearchKey);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getBundleExtra("search_key");
        if (bundle != null) binding.searchText.setText(bundle.getString("search_key"));
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
        mHistorySearchList.remove(text);
        mHistorySearchList.add(0, text);
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
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        if (extras != null) mSearchKey = extras.getString("search_key", "");
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

            startActivityWithBundle(text);
        });

        // 切换搜索类型按钮
        binding.tvTypeName.setOnClickListener(v -> {
            View itemView = (View) binding.tvTypeName.getParent();

            View view = LayoutInflater.from(SearchActivityX.this).inflate(R.layout.pop_search_type, null);
            int width = ScreenSizeUtil.dp2px(SearchActivityX.this, 64);
            int height = ScreenSizeUtil.dp2px(SearchActivityX.this, 84);
            mPopupWindow = new PopupWindow(view, width, height, true);
            TextView popTypeSales = view.findViewById(R.id.tv_popTypeSales);
            TextView popTypeShops = view.findViewById(R.id.tv_popTypeShop);

            String showingText = binding.tvTypeName.getText().toString().trim();

            if (showingText.equals("商品"))
                popTypeSales.setTextColor(getResources().getColor(R.color.text_blue));
            if (showingText.equals("店铺"))
                popTypeShops.setTextColor(getResources().getColor(R.color.text_blue));


            popTypeSales.setOnClickListener(sale -> {
                binding.tvTypeName.setText(popTypeSales.getText().toString().trim());
                setSearchType(0);
                mPopupWindow.dismiss();
                mPopupWindow = null;
            });

            popTypeShops.setOnClickListener(shop -> {
                binding.tvTypeName.setText(popTypeShops.getText().toString().trim());
                setSearchType(1);
                mPopupWindow.dismiss();
                mPopupWindow = null;
            });

            // 点击popupwindow范围以外的地方时隐藏
            // mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopupWindow.setOutsideTouchable(true);

            // 控制位置
            // 显示popupwindow在itemView的下方，偏移量都为0
            if (isShowBottom(itemView)) {
                mPopupWindow.showAsDropDown(itemView, 0, 0);
            }
            // 显示popupwindow在itemView的上方，偏移量y都为-2*itemView.getHeight()
            else {
                mPopupWindow.showAsDropDown(itemView, 0, -2 * itemView.getHeight());
            }

        });
    }

    /**
     * 设置搜索类型
     *
     * @param type 搜索类型: 0-商品, 1-店铺.
     */
    private void setSearchType(int type) {
        mSearchType = type;
        mSearchHistoryFlowAdapter.setSearchType(type);
        mHotSearchRecyclerAdapter.setSearchType(type);
    }

    /**
     * 传入搜索文字
     *
     * @param text 搜索文字
     */
    private void startActivityWithBundle(String text) {
        Bundle bundle = new Bundle();
        bundle.putInt("search_type", mSearchType);
        bundle.putString("search_key", text);
        ARouter.getInstance().build(ConRoute.MARKET.SEARCH_RESULT).with(bundle).navigation();
    }

    private boolean isShowBottom(View itemView) {
        // 得到屏幕的高度
        // int heightPixels =
        // getResources().getDisplayMetrics().heightPixels;//方式1
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();// 方式2

        int[] location = new int[2];
        // location[0]-->x
        // location[1]-->y
        itemView.getLocationInWindow(location);
        // 得到itemView在屏幕中Y轴的值
        int itemViewY = location[1];

        // 得到itemView距离屏幕底部的距离
        int distance = screenHeight - itemViewY - itemView.getHeight();

        // 条目下方放不下popupWindow return false
        // 条目下方放得下popupWindow return true
        return distance >= itemView.getHeight();
    }

    // TODO: 2021/3/25 软键盘回车键跳转
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {

            String text = binding.searchText.getText().toString().trim();

            if (isEmptyText(text)) return false;

            updateList(text);

            putSearchKeyInSharedPreference();

            startActivityWithBundle(text);

            return true;
        }
        return false;
    }

    @Override
    public void onClickCallBack(String text) {
        updateList(text);
        putSearchKeyInSharedPreference();
    }
}

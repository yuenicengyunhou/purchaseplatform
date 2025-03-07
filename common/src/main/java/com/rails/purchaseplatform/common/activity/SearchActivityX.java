package com.rails.purchaseplatform.common.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.common.databinding.ActivitySearchXBinding;
import com.rails.purchaseplatform.common.pop.AlterDialog;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.framwork.utils.SystemUtil;

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
@Route(path = ConRoute.COMMON.SEARCH)
public class SearchActivityX extends BaseErrorActivity<ActivitySearchXBinding>
        implements
        HotSearchContract.HotSearchView,
        HotSearchRecyclerAdapter.OnClickCallBack,
        SearchHistoryFlowAdapter.OnClickCallBack {
    final private String TAG = SearchActivityX.class.getSimpleName();

    private SharedPreferences mSp;
    private List<String> mHistorySearchList;

    private SearchHistoryFlowAdapter mSearchHistoryFlowAdapter;
    private FlowLayoutManager mFlowLayoutManager;

    private HotSearchContract.HotSearchPresenter mHotSearchPresenter;
    private HotSearchRecyclerAdapter mHotSearchRecyclerAdapter;

    private PopupWindow mPopupWindow;

    private String mSearchKey;
    private int mSearchType = 0; // 0-商品, 1-店铺,

    private String mMaterialType;

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

        mSp = this.getSharedPreferences("SEARCH_HISTORY", Context.MODE_PRIVATE);
        mHistorySearchList = new ArrayList<>();

        getSharedPreferenceData();

        mSearchHistoryFlowAdapter = new SearchHistoryFlowAdapter(this, mHistorySearchList);
        mFlowLayoutManager = new FlowLayoutManager();
        mSearchHistoryFlowAdapter.setListener(this);
        binding.recyclerSearchHistory.setLayoutManager(mFlowLayoutManager);
        binding.recyclerSearchHistory.addItemDecoration(new SpaceItemDecoration(28));
        binding.recyclerSearchHistory.setAdapter(mSearchHistoryFlowAdapter);


        mHotSearchPresenter = new HotSearchPresenterImpl(this, this);
        mHotSearchPresenter.getHotSearch(false, 1);

        mHotSearchRecyclerAdapter = new HotSearchRecyclerAdapter(this);
        mHotSearchRecyclerAdapter.setListener(this);
        binding.recyclerHotSearch.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        binding.recyclerHotSearch.setAdapter(mHotSearchRecyclerAdapter);

        setSearchType(0);

        if (mSearchKey != null) binding.searchText.setText(mSearchKey);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        if (!text.equals("")) {
            mSearchKey = text;
            mHistorySearchList.remove(text);
            mHistorySearchList.add(0, text);
            if (mHistorySearchList.size() > 10) mHistorySearchList.remove(10);
            mSearchHistoryFlowAdapter.notifyDataSetChanged();
        }
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
        if (extras != null) {
            mSearchKey = extras.getString("search_key");
            mMaterialType = extras.getString("materialType", "0"); // TODO: 2021/8/25 通用物资/专用物资
        }
    }

    @Override
    protected void onClick() {
        super.onClick();
        // 左上角的返回按钮
        binding.ibBack.setOnClickListener(v -> SearchActivityX.this.finish());

        // 右上角搜索按钮
        binding.tvSearch.setOnClickListener(v -> {

            String text = binding.searchText.getText().toString().trim();

//            if (isEmptyText(text)) return;

            updateList(text);

            putSearchKeyInSharedPreference();

            startActivityWithBundle(text);
        });

        // Override ENTER key on soft keyboard.
        binding.searchText.setOnEditorActionListener((view, action, event) -> {
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                String text = binding.searchText.getText().toString().trim();
//                if (isEmptyText(text)) return false;
                updateList(text);
                putSearchKeyInSharedPreference();
                startActivityWithBundle(text);
                return true;
            }
            return false;
        });

        // 显示/隐藏清空输入按钮
        binding.searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.ibClearSearch.setVisibility(
                        String.valueOf(s).length() == 0
                                ? View.GONE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 清空搜索关键字按钮
        binding.ibClearSearch.setOnClickListener(v -> binding.searchText.setText(""));

        // 清空搜索记录按钮 1427992 1428147 1428148 1428149 1428150
        binding.ibClearHistory.setOnClickListener(v -> {
            new AlterDialog.Builder().context(this)
                    .title("提示")
                    .msg("是否清空搜索历史")
                    .leftBtn("确定")
                    .rightBtn("取消")
                    .setDialogListener(new AlterDialog.DialogListener() {
                        @Override
                        public void onLeft() {
                            mSp.edit().clear().apply();
                            mHistorySearchList.clear();
                            mSearchHistoryFlowAdapter.notifyDataSetChanged();
                            dismissDialog();
                        }

                        @Override
                        public void onRight() {
                            dismissDialog();
                        }
                    })
                    .builder().show();
        });

        // 切换搜索类型按钮
        binding.tvTypeName.setOnClickListener(v -> {


            View itemView = (View) binding.tvTypeName.getParent();

            View view = LayoutInflater.from(SearchActivityX.this).inflate(R.layout.pop_search_type, null);
            int width;
            int height;
            if (SystemUtil.isPad(this)) {
                width = ScreenSizeUtil.dp2px(SearchActivityX.this, 128);
                height = ScreenSizeUtil.dp2px(SearchActivityX.this, 168);
            } else {
                width = ScreenSizeUtil.dp2px(SearchActivityX.this, 64);
                height = ScreenSizeUtil.dp2px(SearchActivityX.this, 84);
            }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 2047:
                binding.searchText.setText(mSearchKey);
//                binding.searchText.addTextChangedListener();
                binding.searchText.setFocusable(true);
                binding.searchText.setFocusableInTouchMode(true);
                binding.searchText.requestFocus();
                binding.searchText.setSelection(binding.searchText.getText().length());
                InputMethodManager inputManager =
                        (InputMethodManager) binding.searchText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case 2048:
                binding.searchText.setText("");
                break;
            default:
                break;
        }
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
        bundle.putString("mode", "form_search");
        bundle.putString("materialType", mMaterialType);
        ARouter.getInstance().build(ConRoute.MARKET.SEARCH_RESULT).with(bundle).navigation(this, 2047);
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

    @Override
    public void onClickCallBack(String text, Bundle bundle) {
        updateList(text);
        putSearchKeyInSharedPreference();
        mSearchKey = text;
        bundle.putString("materialType", mMaterialType);
        ARouter.getInstance().build(ConRoute.MARKET.SEARCH_RESULT).with(bundle).navigation(this, 2047);
    }


}

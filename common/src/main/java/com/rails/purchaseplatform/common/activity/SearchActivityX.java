package com.rails.purchaseplatform.common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.purchaseplatform.common.R;
import com.rails.purchaseplatform.common.multiplecolumnslist.HotSearchGridViewAdapter;
import com.rails.purchaseplatform.common.multiplecolumnslist.MultipleColumnsListAdapter;
import com.rails.purchaseplatform.common.widget.LineBreakLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索页面，点击首页搜索跳转到此页面
 * 1 - 是否需要自动完成
 * 2 - 展示搜索历史（最近10条，存储在本地）
 * 3 - 展示热门搜索（由服务器返回）
 * 4 - PopWindow，用来更改搜索商品或者搜索店铺
 * 5 -
 */
@Route(path = "/common/SearchActivityX")
public class SearchActivityX extends Activity {
    final private String TAG = SearchActivityX.class.getName();

    private List<String> mHistorySearchList;
    private List<String> mSelectedLabel;

    private LineBreakLayout mLineBreakLayout;
    private ListView mHotSearchListView;
    private BaseAdapter mHotSearchAdapter;

    private GridView mHotSearchGridView;
    private List<String> mHotSearchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_x);
        ARouter.getInstance().inject(this);

        initData();
        initView();
        doTask();

//        binding.
    }

    /**
     * 初始化页面数据
     */
    private void initData() {
        // TODO: 2021/3/11 这里应该使用本地存储来保存搜索历史
        mHistorySearchList = new ArrayList<>();
        mHistorySearchList.add("经济");
        mHistorySearchList.add("八卦");
        mHistorySearchList.add("天文爱好者");
        mHistorySearchList.add("完美主义者阿了哈");
        mHistorySearchList.add("小道消息不对劲啊");
        mHistorySearchList.add("选择困难症");
        mHistorySearchList.add("娱乐");
        mHistorySearchList.add("政治中心");
        mHistorySearchList.add("彩票");
        mHistorySearchList.add("情感");
        mHistorySearchList.add("人傻钱多");
        mHistorySearchList.add("旅游爱好者");

        // TODO: 2021/3/11 这里应该发送网络请求以获取热门搜索
        mHotSearchList = new ArrayList<>();
        mHotSearchList = mHistorySearchList;
    }

    private void initView() {
        mLineBreakLayout = findViewById(R.id.lbl_search_history);
        mHotSearchListView = findViewById(R.id.lv_hot_search);
        mHotSearchAdapter = new MultipleColumnsListAdapter(this, mHistorySearchList, R.layout.item_hot_search, 2);

        mHotSearchGridView = findViewById(R.id.gv_hot_search);
        mHotSearchAdapter = new HotSearchGridViewAdapter<>(this, mHotSearchList);
    }

    private void doTask() {
        //设置标签
        mLineBreakLayout.setLabels(mHistorySearchList, true);
        //获取选中的标签
        mSelectedLabel = mLineBreakLayout.getSelectedLabels();

//        mHotSearchListView.setAdapter(mHotSearchAdapter);
//        mHotSearchAdapter.notifyDataSetChanged();

        mHotSearchGridView.setAdapter(mHotSearchAdapter);
        mHotSearchAdapter.notifyDataSetChanged();
    }
}

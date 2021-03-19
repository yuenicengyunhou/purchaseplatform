package com.rails.purchaseplatform.common.activity;

import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.GridView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.HotSearchBean;
import com.rails.purchaseplatform.common.R;
import com.rails.purchaseplatform.common.adapter.HotSearchRecyclerAdapter;
import com.rails.purchaseplatform.common.databinding.ActivitySearchXBinding;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.LineBreakLayout;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

import java.lang.reflect.Type;
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
public class SearchActivityX extends BaseErrorActivity<ActivitySearchXBinding> {
    final private String TAG = SearchActivityX.class.getName();

    private List<String> mHistorySearchList;

    private List<HotSearchBean> mHotSearchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_x);
        ARouter.getInstance().inject(this);
    }

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
        initData();
        // 左上角的返回按钮
        binding.ibBack.setOnClickListener(v -> SearchActivityX.this.finish());

        binding.recyclerHotSearch.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        binding.recyclerHotSearch.setAdapter(new HotSearchRecyclerAdapter(this));
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

        Type type = new TypeToken<ArrayList<HotSearchBean>>() {
        }.getType();
        mHotSearchList = JsonUtil.parseJson(this, "hotSearch.json", type);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

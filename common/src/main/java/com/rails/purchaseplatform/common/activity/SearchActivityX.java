package com.rails.purchaseplatform.common.activity;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.purchaseplatform.common.R;
import com.rails.purchaseplatform.common.widget.LineBreakLayout;
import com.rails.purchaseplatform.framwork.base.BaseActivity;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/common/SearchActivityX")
public class SearchActivityX extends BaseActivity {
    final private String TAG = SearchActivityX.class.getName();

    private List<String> mDataList = new ArrayList<>();
    private List<String> mSelectedLabel;
    private LineBreakLayout mLineBreakLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_x);
        ARouter.getInstance().inject(this);

        Log.d(TAG, "1234567");

        initData();
        initView();
        doTask();

//        binding.
    }

    private void doTask() {
        //设置标签
        mLineBreakLayout.setLabels(mDataList, true);
        //获取选中的标签
        mSelectedLabel = mLineBreakLayout.getSelectedLabels();
    }

    /**
     * 初始化页面数据
     */
    private void initData() {
        // TODO: 2021/3/11 这里应该使用本地存储来保存搜索历史
        // TODO: 2021/3/11 这里应该发送网络请求以获取热门搜索
        mDataList.add("经济");
        mDataList.add("八卦");
        mDataList.add("天文爱好者");
        mDataList.add("完美主义者阿了哈");
        mDataList.add("选择困难症");
        mDataList.add("小道消息");
        mDataList.add("娱乐");
        mDataList.add("政治中心");
        mDataList.add("彩票");
        mDataList.add("情感");
        mDataList.add("人傻钱多");
        mDataList.add("旅游爱好者");
    }

    private void initView() {
        mLineBreakLayout = findViewById(R.id.lbl_search_history);
    }

    @Override
    protected void initialize(Bundle bundle) {

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
    public void onError(ErrorBean errorBean) {

    }
}

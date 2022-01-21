package com.rails.purchaseplatform.market.ui.pop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.forAppShow.SearchFilterValue;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.loading.LoadingDialog;
import com.rails.purchaseplatform.market.adapter.SearchItemFilterAdapter;
import com.rails.purchaseplatform.market.databinding.PopSearchResultFilterBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchResultFilterPop extends BasePop<PopSearchResultFilterBinding> {
    final private String TAG = SearchResultFilterPop.class.getSimpleName();

    final private String
            BRAND = "品牌",
            CATEGORY = "类目";

    private ArrayList<SearchFilterBean> mBeans;
    private String mMinPrice, mMaxPrice;

    private SearchResultFilterPop.DoFilter mDoFilter;

    private LoadingDialog mLoadingDialog;

    public SearchResultFilterPop() {

    }

    public SearchResultFilterPop(ArrayList<SearchFilterBean> beans, int mode, String minPrice, String maxPrice) {
        super();
        mBeans = beans;
        mMinPrice = minPrice;
        mMaxPrice = maxPrice;
    }

    public SearchResultFilterPop(ArrayList<SearchFilterBean> beans, int mode, String minPrice, String maxPrice, LoadingDialog dialog) {
        super();
        Log.e(TAG, "================= I AM POP");
        mBeans = beans;
        mMinPrice = minPrice;
        mMaxPrice = maxPrice;
        mLoadingDialog = dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        initDialogParams();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initialize(Bundle bundle) {
        setFilterPopEvent(); // 商品筛选弹窗
    }

    private void initDialogParams() {
        Window window = getDialog().getWindow();
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        if (window != null && windowManager != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED);
            WindowManager.LayoutParams lp = window.getAttributes();
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Display display = windowManager.getDefaultDisplay();
            //设置dialog高度
            Point pSize = new Point();
            display.getSize(pSize);
            lp.height = (int) (pSize.y * 0.8);
            window.setAttributes(lp);
        }
        setCancelable(true);
    }

    /**
     * 商品搜索结果页 -> 过滤弹窗
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setFilterPopEvent() {
        if (!TextUtils.isEmpty(mMinPrice)) binding.etLowPrice.setText(mMinPrice);
        if (!TextUtils.isEmpty(mMaxPrice)) binding.etHighPrice.setText(mMaxPrice);
        SearchItemFilterAdapter adapter1 = new SearchItemFilterAdapter(getActivity(), mLoadingDialog);
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
        binding.recycler.setAdapter(adapter1);
        adapter1.update(mBeans, true);
        binding.ibClose.setOnClickListener(v -> this.dismiss());
        binding.btnReset.setOnClickListener(v -> {
            for (SearchFilterBean bean : (ArrayList<SearchFilterBean>) mBeans) {
                for (SearchFilterValue value : bean.getFilterValues()) {
                    value.setSelect(false);
                }
            }
            binding.etLowPrice.setText("");
            binding.etHighPrice.setText("");
            adapter1.update(mBeans, true);
        });
        binding.btnOk.setOnClickListener(v -> {
            String[] params = getParams(adapter1);
            mDoFilter.doFilter(params[0], // brands
                    params[1], // cid
                    params[2], // categoryAttr
                    params[3], // expandAttr
                    binding.etLowPrice.getText().toString().trim(),
                    binding.etHighPrice.getText().toString().trim());
            dismiss();
        });
    }


    /**
     * 组装请求参数
     *
     * @param adapter1 SearchItemFilterAdapter
     * @return 长度为4的参数的数组，依次是：品牌、cid、主属性、折叠属性
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NotNull
    private String[] getParams(SearchItemFilterAdapter adapter1) {
        String[] params = new String[4];
        ArrayList<String> categoryAttrs = new ArrayList<>();
        ArrayList<String> expandAttrs = new ArrayList<>();
        for (SearchFilterBean searchFilterBean : (ArrayList<SearchFilterBean>) adapter1.getData()) {
            String attrName = searchFilterBean.getFilterName();
            ArrayList<String> attrs = new ArrayList<>();
            int attrFlag = -1;
            for (SearchFilterValue searchFilterValue : searchFilterBean.getFilterValues()) {
                int attrFlag1 = -1;
                if (searchFilterValue.isSelect()) { // 是选中状态
                    if (attrFlag1 == -1) {
                        attrFlag1 = searchFilterValue.getAttrFlag();
                        attrFlag = attrFlag1;
                    }
                    if (searchFilterValue.getAttrFlag() == 0 && attrName == BRAND) // 是品牌
                        attrs.add(searchFilterValue.getValueName());
                    else if (searchFilterValue.getAttrFlag() == 1 && attrName == CATEGORY) // 是品类
                        params[1] = searchFilterValue.getValueId();
                    else { // 是主类型或折叠类型
                        attrs.add(searchFilterValue.getValueName());
                    }
                }
            }
            if (attrName == BRAND) {
                params[0] = String.join(",", attrs);
            } else if (attrName == CATEGORY) {
                // 不用管了
            } else if (attrFlag == 2) {
                String cateAttr = attrName + "_" + String.join("||", attrs);
                categoryAttrs.add(cateAttr);
            } else if (attrFlag == 3) {
                String exAttr = attrName + "_" + String.join("||", attrs);
                expandAttrs.add(exAttr);
            }
        }
        params[2] = categoryAttrs.size() != 0 ? String.join("@", categoryAttrs) + "@" : "";
        params[3] = expandAttrs.size() != 0 ? String.join("@", expandAttrs) + "@" : "";
        return params;
    }

    /**
     * 商品搜索结果页 过滤监听
     *
     * @param doFilter
     */
    public void setFilterListener(SearchResultFilterPop.DoFilter doFilter) {
        this.mDoFilter = doFilter;
    }

    /**
     * 商品搜索结果页 接口方法
     */
    public interface DoFilter {
        void doFilter(String brand, String cid,
                      String categoryAttr, String expandAttr,
                      String minPrice, String maxPrice);
    }
}

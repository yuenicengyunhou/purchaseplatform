package com.rails.purchaseplatform.market.ui.pop;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.forAppShow.SearchFilterValue;
import com.rails.lib_data.bean.forAppShow.SpecificationPopBean;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.market.adapter.PropertyAdapter;
import com.rails.purchaseplatform.market.adapter.SearchItemFilterAdapter;
import com.rails.purchaseplatform.market.databinding.PopMarketPropertyBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * 商品/购物车规格弹窗
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/29
 */
public class PropertyPop<T> extends BasePop<PopMarketPropertyBinding> {
    final private String TAG = PropertyPop.class.getSimpleName();
    final private int
            MODE_1 = 1, // 商品详情页选择规格
            MODE_2 = 2, // 商品详情页购物车弹窗
            MODE_3 = 3, // 商品搜索结果页筛选
            MODE_4 = 4;

    private PropertyAdapter mAdapter;

    private AddToCart mAddToCart;
    private DoFilter mDoFilter;
    private TypeSelect mTypeSelect;

    private String mMinPrice, mMaxPrice;

    private ArrayList<T> mBeans;
    private int mMode = 0;
//    private  adapter;

    public PropertyPop() {
        super();
    }


    public PropertyPop(ArrayList<T> beans, int mode, String minPrice, String maxPrice) {
        super();
        mBeans = beans;
        mMode = mode;
        mMinPrice = minPrice;
        mMaxPrice = maxPrice;
    }

    public PropertyPop(ArrayList<T> beans, int mode) {
        super();
        mBeans = beans;
        mMode = mode;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initialize(Bundle bundle) {
        switch (mMode) {
            case MODE_1:
            case MODE_2:
                setPopEvent();
                break;
            case MODE_3:
                setFilterPopEvent();
                break;

            default:
                break;
        }
    }

    /**
     * 搜索结果页 -> 过滤弹窗
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setFilterPopEvent() {
        binding.rlTitle.setVisibility(View.VISIBLE);
        binding.rlPriceRange.setVisibility(View.VISIBLE);
        binding.llBottom.setVisibility(View.VISIBLE);
        binding.tvTitle.setText("筛选");
        if (!TextUtils.isEmpty(mMinPrice)) binding.etLowPrice.setText(mMinPrice);
        if (!TextUtils.isEmpty(mMaxPrice)) binding.etHighPrice.setText(mMaxPrice);
        SearchItemFilterAdapter adapter1 = new SearchItemFilterAdapter(getActivity());
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
//            Log.d(TAG, " =========== " + Arrays.toString(params));
//            Log.d(TAG, " =========== " + binding.etLowPrice.getText().toString().trim());
//            Log.d(TAG, " =========== " + binding.etHighPrice.getText().toString().trim());
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
                    if (searchFilterValue.getAttrFlag() == 0 && attrName == "品牌") // 是品牌
                        attrs.add(searchFilterValue.getValueName());
                    else if (searchFilterValue.getAttrFlag() == 1 && attrName == "品类") // 是品类
                        params[1] = searchFilterValue.getValueName();
                    else { // 是主类型或折叠类型
                        attrs.add(searchFilterValue.getValueName());
                    }
                }
            }
            if (attrName == "品牌") {
                params[0] = String.join(",", attrs);
            } else if (attrName == "类目") {
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
     * 详情页 -> 加入购物车弹窗 || 选择规格弹窗
     */
    private void setPopEvent() {
        binding.llTop.setVisibility(View.VISIBLE);
        binding.rlBuyCount.setVisibility(View.VISIBLE);
        binding.addCart.setVisibility(View.VISIBLE);
        mAdapter = new PropertyAdapter(getActivity());
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
        binding.recycler.setAdapter(mAdapter);
        mAdapter.update(mBeans, true);
        binding.btnClose.setOnClickListener(v -> this.dismiss());
        binding.tvAdd.setOnClickListener(v -> changeNum(true));
        binding.tvReduce.setOnClickListener(v -> changeNum(false));
        binding.tvReduce.setTextColor(getActivity().getResources().getColor(com.rails.purchaseplatform.common.R.color.font_gray));
        binding.addCart.setOnClickListener(v -> {
            if (null != mTypeSelect) {
                mTypeSelect.onSelectComplete(mAdapter.getListData());
            }
            dismiss();
        });
        binding.etNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (String.valueOf(s).length() == 0) {
                    binding.tvReduce.setTextColor(getActivity().getResources().getColor(com.rails.purchaseplatform.common.R.color.font_gray));
                } else if (Integer.parseInt(String.valueOf(s)) <= 1) {
                    binding.tvReduce.setTextColor(getActivity().getResources().getColor(com.rails.purchaseplatform.common.R.color.font_gray));
                } else {
                    binding.tvReduce.setTextColor(getActivity().getResources().getColor(com.rails.purchaseplatform.common.R.color.font_black));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 变更购买数量
     *
     * @param isAdd 是否增加数量
     */
    private void changeNum(boolean isAdd) {
        int number = 1;
        String num = binding.etNum.getText().toString().trim();
        if (!TextUtils.isEmpty(num)) {
            number = Integer.parseInt(num);
        }
        if (isAdd) {
            binding.etNum.setText(String.valueOf(number + 1));
        } else {
            if (number <= 1) {
                return;
            }
            binding.etNum.setText(String.valueOf(number - 1));
        }
    }


    /**
     * 商品详情页 加入购物车弹窗按钮监听方法
     *
     * @param addToCart 接口实现
     */
    public void setAddToCartListener(AddToCart addToCart) {
        this.mAddToCart = addToCart;
    }


    /**
     * 商品详情页 加入购物车弹窗 却定按钮监听接口
     * 商详页 使用此接口的匿名内部类 并重写addToCart方法
     */
    public interface AddToCart {
        void addToCart();
    }


    /**
     * 搜索结果页 过滤监听
     *
     * @param doFilter
     */
    public void setFilterListener(DoFilter doFilter) {
        this.mDoFilter = doFilter;
    }

    /**
     * 搜索结果页 过滤接口方法
     */
    public interface DoFilter {
        void doFilter(String brand, String cid,
                      String categoryAttr, String expandAttr,
                      String minPrice, String maxPrice);
    }


    /**
     * 商品详情页 选择规格弹窗监听
     *
     * @param typeSelect
     */
    public void setTypeSelectListener(TypeSelect typeSelect) {
        this.mTypeSelect = typeSelect;
    }

    /**
     * 商品详情页 选规格
     */
    public interface TypeSelect {
        void onSelectComplete(ArrayList<SpecificationPopBean> data);
//        void onSelectComplete(ArrayList<SpecificationPopBean> beans);

        void onReset();
    }
}

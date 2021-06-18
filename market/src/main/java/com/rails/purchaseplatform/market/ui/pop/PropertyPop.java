package com.rails.purchaseplatform.market.ui.pop;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.forAppShow.SearchFilterValue;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.market.adapter.SearchItemFilterAdapter;
import com.rails.purchaseplatform.market.databinding.PopMarketPropertyBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * 商品、店铺搜索结果筛选弹窗
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

    final private String
            BRAND = "品牌",
            CATEGORY = "类目";


    private DoFilter mDoFilter;
    private DoShopFilter mDoShopFilter;

    private String mMinPrice, mMaxPrice;

    private ArrayList<T> mBeans;

    private int mMode = 0;

    public PropertyPop() {
        super();
    }

    /**
     * 商品筛选弹窗
     *
     * @param beans
     * @param mode
     * @param minPrice
     * @param maxPrice
     */
    public PropertyPop(ArrayList<T> beans, int mode, String minPrice, String maxPrice) {
        super();
        mBeans = beans;
        mMode = mode;
        mMinPrice = minPrice;
        mMaxPrice = maxPrice;
    }

    /**
     * 店铺筛选弹窗
     *
     * @param beans
     * @param mode
     */
    public PropertyPop(ArrayList<T> beans, int mode) {
        super();
        mBeans = beans;
        mMode = mode;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initialize(Bundle bundle) {
        switch (mMode) {
            case MODE_3:
                setFilterPopEvent(); // 商品筛选弹窗
                break;
            case MODE_4:
                setShopFilterPopEvent(); // 店铺筛选弹窗
                break;
            default:
                break;
        }
    }


    /**
     * 店铺搜索结果 -> 过滤弹窗
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setShopFilterPopEvent() {
        binding.rlTitle.setVisibility(View.VISIBLE);
        binding.llBottom.setVisibility(View.VISIBLE);
        binding.tvTitle.setText("筛选");
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
            ((SearchFilterBean) mBeans.get(0)).getFilterValues().get(1).setSelect(true); // 将 "是否购买过" 选项置为 "全部" (固定第1行第2个)
            adapter1.update(mBeans, true);
        });
        binding.btnOk.setOnClickListener(v -> {
            ArrayList<SearchFilterBean> beans = (ArrayList<SearchFilterBean>) adapter1.getData();
            String isBought = null;
            String shopType = "", saleArea = "";
            for (SearchFilterValue value : beans.get(0).getFilterValues()) {
                if (value.isSelect()) {
                    isBought = value.getValueId();
                }
            }
            for (SearchFilterValue value : beans.get(1).getFilterValues()) {
                if (value.isSelect()) {
                    shopType = value.getValueId();
                }
            }
            for (SearchFilterValue value : beans.get(2).getFilterValues()) {
                if (value.isSelect()) {
                    saleArea = value.getValueId();
                }
            }
            mDoShopFilter.doShopFilter(isBought, shopType, saleArea);
            dismiss();
        });
    }

    /**
     * 商品搜索结果页 -> 过滤弹窗
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
    public void setFilterListener(DoFilter doFilter) {
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


    /**
     * 店铺搜索结果页 过滤监听
     *
     * @param doShopFilter
     */
    public void setShopFilterListener(DoShopFilter doShopFilter) {
        this.mDoShopFilter = doShopFilter;
    }

    /**
     * 店铺搜索结果页 接口方法
     */
    public interface DoShopFilter {
        void doShopFilter(String isBought, String shopType, String saleArea);
    }

}

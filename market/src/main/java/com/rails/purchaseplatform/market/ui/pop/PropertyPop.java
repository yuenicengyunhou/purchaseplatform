package com.rails.purchaseplatform.market.ui.pop;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rails.lib_data.bean.DeliveryBean;
import com.rails.lib_data.bean.ProductServiceBean;
import com.rails.lib_data.bean.forAppShow.ProductDetailsPackingBean;
import com.rails.lib_data.bean.forAppShow.RecommendItemsBean;
import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.forAppShow.SearchFilterValue;
import com.rails.lib_data.bean.forAppShow.SpecificationPopBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemPicture;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemSkuInfo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductPriceBean;
import com.rails.lib_data.contract.ProductDetailsContract;
import com.rails.lib_data.contract.ProductDetailsPresenterImpl;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.market.adapter.PropertyAdapter;
import com.rails.purchaseplatform.market.adapter.SearchItemFilterAdapter;
import com.rails.purchaseplatform.market.databinding.PopMarketPropertyBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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

    final private String
            BRAND = "品牌",
            CATEGORY = "类目";


    private DoFilter mDoFilter;
    private DoShopFilter mDoShopFilter;
    private TypeSelect mTypeSelect;

    private String mMinPrice, mMaxPrice, mDelivery, mPrice;

    private ArrayList<T> mBeans;
    private ArrayList<ItemSkuInfo> mItemSkuInfos;
    private ItemSkuInfo mItemSkuInfo;

    private int mMode = 0;

    private ProductDetailsContract.ProductDetailsPresenter mProductDetailsPresenter;

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

    public PropertyPop(ArrayList<T> beans, List<ItemSkuInfo> skuInfos, String price, String delivery, int mode) {
        super();
        mBeans = beans;
        mMode = mode;
        mItemSkuInfos = (ArrayList<ItemSkuInfo>) skuInfos;
        mItemSkuInfo = (skuInfos != null && skuInfos.size() != 0) ? skuInfos.get(0) : null;
        mDelivery = delivery;
        mPrice = price;
        mProductDetailsPresenter = new ProductDetailsPresenterImpl(getActivity(), new ProductDetailsContract.ProductDetailsView() {
            @Override
            public void onGetProductDetailsSuccess(ProductDetailsBean bean, ArrayList<ProductServiceBean> serviceBeans, ArrayList<ProductServiceBean> recCompanys, ArrayList<SpecificationPopBean> specificationPopBeanList) {

            }

            @Override
            public void onGetProductPriceSuccess(ProductPriceBean bean, ArrayList<ItemPicture> pics, ArrayList<ProductDetailsPackingBean> billBeans) {
                binding.tvPrice.setText(String.valueOf(bean.getSellPrice()));
            }

            @Override
            public void onGetHotSaleSuccess(ArrayList<RecommendItemsBean> beans) {

            }

            @Override
            public void onGetUserCollectSuccess(boolean isCollect) {

            }

            @Override
            public void onGetCartCountSuccess(String count) {

            }

            @Override
            public void getDelivery(DeliveryBean deliveryBean) {

            }

            @Override
            public void onError(ErrorBean errorBean) {

            }

            @Override
            public void showDialog(String msg) {

            }

            @Override
            public void showResDialog(int res) {

            }

            @Override
            public void dismissDialog() {

            }
        });
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

            case MODE_4:
                setShopFilterPopEvent();
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
     * 详情页 -> 加入购物车弹窗 || 选择规格弹窗
     */
    private void setPopEvent() {
        binding.llTop.setVisibility(View.VISIBLE);
        binding.rlBuyCount.setVisibility(View.VISIBLE);
        binding.addCart.setVisibility(View.VISIBLE);
        Glide.with(getContext())
                .load("https:" + mItemSkuInfo.getPictureUrl())
                .placeholder(com.rails.purchaseplatform.common.R.drawable.ic_cart_null)
                .into(binding.imgProduct);
        binding.tvPrice.setText(mPrice);
        binding.tvSend.setText(mDelivery);
        PropertyAdapter mAdapter = new PropertyAdapter(getActivity());
        mAdapter.setItemSkuInfoList(mItemSkuInfos);
        mAdapter.setOnItemClicked(new PropertyAdapter.OnItemClicked() {
            @Override
            public void onItemClicked(ItemSkuInfo itemSkuInfo) {
                mItemSkuInfo = itemSkuInfo;
                if (mItemSkuInfo != null && mTypeSelect != null) {
                    Glide.with(getContext())
                            .load("https:" + mItemSkuInfo.getPictureUrl())
                            .placeholder(com.rails.purchaseplatform.common.R.drawable.ic_cart_null)
                            .into(binding.imgProduct);
                    mProductDetailsPresenter.getProductPrice("20", mItemSkuInfo.getId(), true);
                    mTypeSelect.getSkuInfo(mItemSkuInfo);
                    binding.tvAdd.setTextColor(getActivity().getResources().getColor(com.rails.purchaseplatform.common.R.color.font_black));
                    binding.tvCountState.setText("有货");
                } else {
                    ToastUtil.showCenter(getActivity(), "没有此型号商品或商品库存不足");
                    binding.tvAdd.setTextColor(getActivity().getResources().getColor(com.rails.purchaseplatform.common.R.color.font_gray));
                    binding.tvCountState.setText("无货");
                }
            }
        });
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
        binding.recycler.setAdapter(mAdapter);
        mAdapter.update(mBeans, true);
        binding.btnClose.setOnClickListener(v -> this.dismiss());
        binding.tvAdd.setOnClickListener(v -> changeNum(true));
        binding.tvReduce.setOnClickListener(v -> changeNum(false));
        binding.tvReduce.setTextColor(getActivity().getResources().getColor(com.rails.purchaseplatform.common.R.color.font_gray));
        binding.addCart.setOnClickListener(v -> {
            if (binding.tvCountState.getText().toString().equals("无货")) {
                ToastUtil.showCenter(getActivity(), "请选择其它型号的商品");
                return;
            }
            if (null != mTypeSelect) {
                mTypeSelect.onSelectComplete(binding.etNum.getText().toString().trim());
            }
        });
        binding.etNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeReduceBtnColor(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 改变减小数量按键的颜色
     *
     * @param s
     */
    private void changeReduceBtnColor(CharSequence s) {
        if (String.valueOf(s).length() == 0) {
            binding.tvReduce.setTextColor(getActivity().getResources().getColor(com.rails.purchaseplatform.common.R.color.font_gray));
        } else if (Integer.parseInt(String.valueOf(s)) <= 1) {
            binding.tvReduce.setTextColor(getActivity().getResources().getColor(com.rails.purchaseplatform.common.R.color.font_gray));
        } else {
            binding.tvReduce.setTextColor(getActivity().getResources().getColor(com.rails.purchaseplatform.common.R.color.font_black));
        }
    }

    /**
     * 变更购买数量
     *
     * @param isAdd 是否增加数量
     */
    private void changeNum(boolean isAdd) {
        if (binding.tvCountState.getText().toString().equals("无货")) {
            return;
        }
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

        /**
         * 点击确定按钮 回传购买数量
         *
         * @param count
         */
        void onSelectComplete(String count);

        /**
         * 点击规格型号 回传ItemSkuInfo
         *
         * @param info
         */
        void getSkuInfo(ItemSkuInfo info);
    }
}

package com.rails.purchaseplatform.market.ui.pop;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.rails.lib_data.bean.DeliveryBean;
import com.rails.lib_data.bean.ProductServiceBean;
import com.rails.lib_data.bean.SkuStockBean;
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
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.market.R;
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

    private int fontGray, fontBlack;
    private Drawable backgroundBlue, backgroundGray;


    private DoFilter mDoFilter;
    private DoShopFilter mDoShopFilter;
    private TypeSelect mTypeSelect;

    private String mMinPrice, mMaxPrice, mDelivery, mPrice;

    private String mShopId, mProvinceId, mCityId, mCountryId, mAddress, mSkuNum;

    private String mStockStateStr = "";
    private int mStockNum = 0; // 库存数量 默认0

    private ArrayList<T> mBeans;
    private ArrayList<ItemSkuInfo> mItemSkuInfos;
    private ItemSkuInfo mItemSkuInfo, mPreviousItemSkuInfo;
    private SkuStockBean mSkuStockBean;

    private int mMode = 0;

    private ProductDetailsContract.ProductDetailsPresenter mProductDetailsPresenter;

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

    /**
     * 选择型号/加入购物车弹窗
     *
     * @param beans
     * @param skuInfos
     * @param price
     * @param delivery
     * @param mode
     */
    public PropertyPop(ArrayList<T> beans, List<ItemSkuInfo> skuInfos, SkuStockBean skuStockBean, String price, String delivery,
                       String supplierId, String provinceId, String cityId, String countryId,
                       String address, String skuNum,
                       int mode) {
        super();
        mBeans = beans;
        mShopId = supplierId;
        mProvinceId = provinceId;
        mCityId = cityId;
        mCountryId = countryId;
        mAddress = address;
        mSkuNum = skuNum;
        mMode = mode;
        mItemSkuInfos = (ArrayList<ItemSkuInfo>) skuInfos;
        mItemSkuInfo = (skuInfos != null && skuInfos.size() != 0) ? skuInfos.get(0) : null;
        mPreviousItemSkuInfo = mItemSkuInfo;
        mDelivery = delivery;
        mPrice = price;
        mSkuStockBean = skuStockBean;
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
            public void getSkuSaleStocks(SkuStockBean bean) {
                mSkuStockBean = bean;
//                Log.d(TAG, "谁动了我的接口？？？？？？？");
                setAddCartButton();
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
//        Log.d(TAG, "初始化方法是在哪里调用的呢？");
        Resources res = getActivity().getResources();
        fontGray = res.getColor(com.rails.purchaseplatform.common.R.color.font_gray);
        fontBlack = res.getColor(com.rails.purchaseplatform.common.R.color.font_black);
        backgroundBlue = res.getDrawable(R.drawable.bg_corner_blue_20);
        backgroundGray = res.getDrawable(R.drawable.bg_corner_gray_20_8e8e8e);

        switch (mMode) {
            case MODE_1:
            case MODE_2:
                setPopEvent(); // 规格、加入购物车弹窗
                break;
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
        binding.llTop.setVisibility(View.VISIBLE); // 显示图片头
        binding.rlBuyCount.setVisibility(View.VISIBLE); // 显示数量步进器
        binding.addCart.setVisibility(View.VISIBLE); // 显示加入购物车按钮（长按钮 确定）
        String pic = "";
        if (mItemSkuInfo != null && mItemSkuInfo.getPictureUrl() != null) {
            pic = "https:" + mItemSkuInfo.getPictureUrl();
        }
        Glide.with(getContext())
                .load(pic)
                .placeholder(com.rails.purchaseplatform.common.R.drawable.ic_placeholder_rect)
                .into(binding.imgProduct); // 显示sku图片
        binding.tvPrice.setText(mPrice); // 显示价格
        binding.tvSend.setText(mDelivery); // 显示运费

        PropertyAdapter mAdapter = new PropertyAdapter(getActivity()); // 型号列表Adapter
        mAdapter.setItemSkuInfoList(mItemSkuInfos);
        mAdapter.setOnItemClicked(new PropertyAdapter.OnItemClicked() { // 点击按钮时请求价格和库存
            @Override
            public void onItemClicked(ItemSkuInfo itemSkuInfo) {
                mItemSkuInfo = itemSkuInfo;
                if (mItemSkuInfo != null) mPreviousItemSkuInfo = mItemSkuInfo;
                if (itemSkuInfo != null && mTypeSelect != null) {
                    Glide.with(getContext())
                            .load("https:" + mItemSkuInfo.getPictureUrl())
                            .placeholder(com.rails.purchaseplatform.common.R.drawable.ic_placeholder_rect)
                            .into(binding.imgProduct); // 切换sku时切换图片
//                    Bitmap bitmap =
                    mTypeSelect.getSkuInfo(mItemSkuInfo); // 把ItemSkuInfo传给activity
                    mProductDetailsPresenter.getProductPrice("20", mItemSkuInfo.getId(), true); // 请求价格接口
                    mProductDetailsPresenter.querySkuSaleStocks(mShopId, mProvinceId, mCityId, mCountryId,
                            mAddress, mSkuNum, mItemSkuInfo.getId(), false); // 请求库存接口
                } else {
                    mSkuStockBean = null;
                    mStockStateStr = "无货";
                    binding.tvCountState.setText(mStockStateStr); // 显示无货
                    binding.tvAdd.setTextColor(fontGray); // 增加数量按钮灰色
                    binding.addCart.setBackground(backgroundGray); // 确定（加入购物车）按钮灰色
                    mTypeSelect.getSkuInfo(mPreviousItemSkuInfo); // 把ItemSkuInfo传给activity
                }
            }
        });
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
        binding.recycler.setAdapter(mAdapter);
        mAdapter.update(mBeans, true);

//        binding.imgProduct.setOnClickListener(v -> {
//            String imageUrl = "";
//            if (mItemSkuInfo != null && mItemSkuInfo.getPictureUrl() != null) {
//                imageUrl = "https:" + mItemSkuInfo.getPictureUrl();
//            }
//            Bundle bundle = new Bundle();
//            bundle.putString("imageUrl", imageUrl);
//            ARouter.getInstance().build(ConRoute.MARKET.IMAGE_ZOOM).with(bundle).navigation();
//        });
        binding.btnClose.setOnClickListener(v -> this.dismiss());
        binding.tvAdd.setOnClickListener(v -> changeNum(true));
        binding.tvReduce.setOnClickListener(v -> changeNum(false));
        binding.tvReduce.setTextColor(fontGray);
//        Log.d(TAG, "为啥又走了初始化？？？？？");
        setAddCartButton();
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
     * 设置按钮颜色，pop初始化时调用，切换sku请求库存成功时调用。
     */
    private void setAddCartButton() {
//        Log.d(TAG, "设置按钮颜色：pop初始化时调用，切换sku请求库存成功时调用");
        if (mSkuStockBean == null) { // 如果库存Bean为null 视为无货
            mStockStateStr = "无货";
            binding.addCart.setBackground(backgroundGray);
            binding.tvCountState.setText(mStockStateStr);
            binding.tvAdd.setTextColor(fontGray);
            binding.tvReduce.setTextColor(fontGray);
            return;
        }
        String saleState = mSkuStockBean.getSaleState();
        String skuStock = mSkuStockBean.getSkuStock();
        // 如果 销售状态 或 库存数量 为null或0 表示不可销售状态
        if (saleState == null || saleState.equals("0") || skuStock == null || skuStock.equals("0")) {
            mStockStateStr = "无货";
            binding.addCart.setBackground(backgroundGray); // 确定（加入购物车）按钮灰色
            binding.tvCountState.setText(mStockStateStr); // 显示无货
            binding.tvAdd.setTextColor(fontGray); // 增加数量按钮灰色
            binding.tvReduce.setTextColor(fontGray); // 减少数量按钮灰色
        } else {
            mStockStateStr = "有货";
            binding.addCart.setBackground(backgroundBlue);
            binding.tvCountState.setText(mStockStateStr);
            binding.tvAdd.setTextColor(fontBlack); // 增加数量按钮灰色
            if (Integer.parseInt(binding.etNum.getText().toString()) <= 1)
                binding.tvReduce.setTextColor(fontGray); // 减少数量按钮灰色
            else
                binding.tvReduce.setTextColor(fontBlack); // 减少数量按钮黑色
        }
    }

//    private boolean isCheckedOneSku(ArrayList<SpecificationPopBean> beans) {
//        boolean ji
//        return false;
//    }

    /**
     * 改变减小数量按键的颜色
     *
     * @param s
     */
    private void changeReduceBtnColor(CharSequence s) {
        // 设置减少数量按钮颜色
        if (String.valueOf(s).length() == 0) { // 判断输入框内没有文字
            binding.tvReduce.setTextColor(fontGray); // 减少数量按钮灰色
        } else if (Integer.parseInt(String.valueOf(s)) <= 1) { // 如果值小于等于1
            binding.tvReduce.setTextColor(fontGray); // 减少数量按钮灰色
        } else {
            binding.tvReduce.setTextColor(fontBlack); // 减少数量按钮黑色
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

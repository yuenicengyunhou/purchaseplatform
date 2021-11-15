package com.rails.purchaseplatform.market.ui.pop;

import android.content.res.Resources;
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
import com.rails.lib_data.bean.forAppShow.ProductDetailsPageBean;
import com.rails.lib_data.bean.forAppShow.ProductDetailsPopBean;
import com.rails.lib_data.bean.forAppShow.RecommendItemsBean;
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
import com.rails.purchaseplatform.market.databinding.PopMarketPropertyBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品/购物车规格弹窗
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/29
 */
public class AddCartPop extends BasePop<PopMarketPropertyBinding> {

    final private String TAG = AddCartPop.class.getSimpleName();

    /**
     * 添加购物车Pop中需要展示或计算的所有数据
     */
    private ProductDetailsPopBean mPopBean;

    /**
     * 字颜色 灰色、黑色
     */
    private int fontGray, fontBlack;

    /**
     * 背景 蓝色、灰色
     */
    private Drawable backgroundBlue, backgroundGray;

    private ChooseSkuAndAddCart mChooseSkuAndAddCart;

    private String mMinPrice, mMaxPrice, mDelivery, mPrice, mMaterialType;

    private String mShopId, mProvinceId, mCityId, mCountryId, mAddress, mSkuNum;

    private String mStockStateStr = "";

    private ArrayList<SpecificationPopBean> mBeans;
    private ArrayList<ItemSkuInfo> mItemSkuInfos;
    private ItemSkuInfo mItemSkuInfo, mPreviousItemSkuInfo;
    private SkuStockBean mSkuStockBean;

    private ProductDetailsContract.ProductDetailsPresenter mProductDetailsPresenter;

    /**
     * 选择型号/加入购物车弹窗
     */
    public AddCartPop(ProductDetailsPageBean pageBean, List<ItemSkuInfo> skuInfos) {
        super();
        mBeans = pageBean.getSpecPopBeanList();
        mShopId = pageBean.getShopId();
        mProvinceId = pageBean.getProvinceCode();
        mCityId = pageBean.getCityCode();
        mCountryId = pageBean.getCountryCode();
        mAddress = "";
        mSkuNum = "1";
        mItemSkuInfos = (ArrayList<ItemSkuInfo>) skuInfos;
        mItemSkuInfo = pageBean.getCurrentItemSkuInfo();
        mPreviousItemSkuInfo = mItemSkuInfo;
        mDelivery = pageBean.getDelivery();
        mPrice = pageBean.getSellPrice();
        mSkuStockBean = pageBean.getSkuStockBean();
        mMaterialType = pageBean.getMaterialType();
        mProductDetailsPresenter = new ProductDetailsPresenterImpl(getActivity(), new ProductDetailsContract.ProductDetailsView() {
            @Override
            public void onGetProductDetailsSuccess(ProductDetailsBean bean, ArrayList<ProductServiceBean> serviceBeans, ArrayList<ProductServiceBean> recCompanys, ArrayList<SpecificationPopBean> specificationPopBeanList) {

            }

            @Override
            public void onGetProductPriceSuccess(ProductPriceBean bean, ArrayList<ItemPicture> pics, ArrayList<ProductDetailsPackingBean> billBeans) {
                binding.tvPrice.setText(String.format("%.2f", bean.getSellPrice()));
                // 显示单位
                String skuUnitStr = mItemSkuInfo.getSkuUnit();
                if (!TextUtils.isEmpty(skuUnitStr)) {
                    binding.tvSkuUnit.setVisibility(View.VISIBLE);
                    binding.tvSkuUnit.setText(String.format("/%s", mItemSkuInfo.getSkuUnit()));
                } else {
                    binding.tvSkuUnit.setVisibility(View.GONE);
                }
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

        Resources res = getActivity().getResources();
        fontGray = res.getColor(com.rails.purchaseplatform.common.R.color.font_gray);
        fontBlack = res.getColor(com.rails.purchaseplatform.common.R.color.font_black);
        backgroundBlue = res.getDrawable(R.drawable.bg_corner_blue_20);
        backgroundGray = res.getDrawable(R.drawable.bg_corner_gray_20_8e8e8e);

        setPopEvent(); // 规格、加入购物车弹窗

    }

    /**
     * 详情页 -> 加入购物车弹窗 || 选择规格弹窗
     */
    private void setPopEvent() {
        if (mMaterialType.equals("1")) {
            binding.llSkuPrice.setVisibility(View.INVISIBLE);
        }
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
        // 显示单位
        String skuUnitStr = mItemSkuInfo.getSkuUnit();
        if (!TextUtils.isEmpty(skuUnitStr)) {
            binding.tvSkuUnit.setVisibility(View.VISIBLE);
            binding.tvSkuUnit.setText(String.format("/%s", mItemSkuInfo.getSkuUnit()));
        } else {
            binding.tvSkuUnit.setVisibility(View.GONE);
        }
        binding.tvSend.setText(mDelivery); // 显示运费

        PropertyAdapter mAdapter = new PropertyAdapter(getActivity()); // 型号列表Adapter
        mAdapter.setItemSkuInfoList(mItemSkuInfos);
        mAdapter.setOnItemClicked(new PropertyAdapter.OnItemClicked() { // 点击按钮时请求价格和库存
            @Override
            public void onItemClicked(ItemSkuInfo itemSkuInfo) {
                mItemSkuInfo = itemSkuInfo;
                if (mItemSkuInfo != null) mPreviousItemSkuInfo = mItemSkuInfo;
                if (itemSkuInfo != null && mChooseSkuAndAddCart != null) {
                    Glide.with(getContext())
                            .load("https:" + mItemSkuInfo.getPictureUrl())
                            .placeholder(com.rails.purchaseplatform.common.R.drawable.ic_placeholder_rect)
                            .into(binding.imgProduct); // 切换sku时切换图片
//                    Bitmap bitmap =
                    mChooseSkuAndAddCart.onSkuChanged(mItemSkuInfo); // 把ItemSkuInfo传给activity
                    mProductDetailsPresenter.getProductPrice("20", mItemSkuInfo.getId(), true); // 请求价格接口
                    mProductDetailsPresenter.querySkuSaleStocks(mShopId, mProvinceId, mCityId, mCountryId,
                            mAddress, mSkuNum, mItemSkuInfo.getId(), false); // 请求库存接口
                } else {
                    mSkuStockBean = null;
                    mStockStateStr = "无货";
                    binding.tvCountState.setText(mStockStateStr); // 显示无货
                    binding.tvAdd.setTextColor(fontGray); // 增加数量按钮灰色
                    binding.addCart.setBackground(backgroundGray); // 确定（加入购物车）按钮灰色
                    mChooseSkuAndAddCart.onSkuChanged(mPreviousItemSkuInfo); // 把ItemSkuInfo传给activity
                }
            }
        });
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
        binding.recycler.setAdapter(mAdapter);
        mAdapter.update(mBeans, true);

        binding.imgProduct.setOnClickListener(v -> {
            String imageUrl = "";
            if (mItemSkuInfo != null && mItemSkuInfo.getPictureUrl() != null) {
                imageUrl = "https:" + mItemSkuInfo.getPictureUrl();
            }
            Bundle bundle = new Bundle();
            bundle.putString("imageUrl", imageUrl);
            ARouter.getInstance().build(ConRoute.MARKET.IMAGE_ZOOM).with(bundle).navigation();
        });
        binding.btnClose.setOnClickListener(v -> this.dismiss());
        binding.tvAdd.setOnClickListener(v -> changeNum(true));
        binding.tvReduce.setOnClickListener(v -> changeNum(false));
        binding.tvReduce.setTextColor(fontGray);
        setAddCartButton();
        binding.addCart.setOnClickListener(v -> {
            if (mMaterialType != null && mMaterialType.equals("1")) {
                ToastUtil.showCenter(this.getActivity(), "APP暂不支持");
                return;
            }
            if (binding.tvCountState.getText().toString().equals("无货")) {
                ToastUtil.showCenter(getActivity(), "请选择其它型号的商品");
                return;
            }
            if (null != mChooseSkuAndAddCart) {
                mChooseSkuAndAddCart.onAddCart(binding.etNum.getText().toString().trim());
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
        if (String.valueOf(s).length() >= 7) {
            binding.etNum.setText("999999");
            binding.tvAdd.setTextColor(fontGray);
        } else if (String.valueOf(s).length() != 0 && Integer.parseInt(String.valueOf(s)) == 999999) {
            binding.tvAdd.setTextColor(fontGray);
        } else {
            binding.tvAdd.setTextColor(fontBlack);
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
        } else {
            number = 0;
        }
        if (isAdd) {
            if (number >= 999999) {
                return;
            }
            binding.etNum.setText(String.valueOf(number + 1));
        } else {
            if (number <= 1) {
                binding.etNum.setText("1");
                return;
            }
            binding.etNum.setText(String.valueOf(number - 1));
        }
    }


    /**
     * 商品详情页 选择规格弹窗监听
     *
     * @param chooseSkuAndAddCart
     */
    public void setTypeSelectListener(ChooseSkuAndAddCart chooseSkuAndAddCart) {
        this.mChooseSkuAndAddCart = chooseSkuAndAddCart;
    }

    /**
     * 商品详情页 选规格
     */
    public interface ChooseSkuAndAddCart {

        /**
         * 点击确定按钮 回传购买数量
         *
         * @param count
         */
        void onAddCart(String count);

        /**
         * 点击规格型号 回传ItemSkuInfo
         *
         * @param info
         */
        void onSkuChanged(ItemSkuInfo info);
    }
}

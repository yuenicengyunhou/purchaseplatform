package com.rails.purchaseplatform.market.ui.activity;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayout;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.DeliveryBean;
import com.rails.lib_data.bean.ProductBillBean;
import com.rails.lib_data.bean.ProductServiceBean;
import com.rails.lib_data.bean.forAppShow.ItemParams;
import com.rails.lib_data.bean.forAppShow.RecommendItemsBean;
import com.rails.lib_data.bean.forAppShow.SpecificationPopBean;
import com.rails.lib_data.bean.forAppShow.SpecificationValue;
import com.rails.lib_data.bean.forNetRequest.productDetails.AttrNameValueReaultVo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemPicture;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemPictureVo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemSkuInfo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductPriceBean;
import com.rails.lib_data.contract.AddressToolContract;
import com.rails.lib_data.contract.AddressToolPresenterImpl;
import com.rails.lib_data.contract.CartContract;
import com.rails.lib_data.contract.CartToolPresenterImpl;
import com.rails.lib_data.contract.ProductDetailsContract;
import com.rails.lib_data.contract.ProductDetailsPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.PropertyAdapter;
import com.rails.purchaseplatform.market.adapter.RecommendItemsRecyclerAdapter;
import com.rails.purchaseplatform.market.adapter.pdetail.DetailImgAdapter;
import com.rails.purchaseplatform.market.adapter.pdetail.ProductBillAdapter;
import com.rails.purchaseplatform.market.adapter.pdetail.ProductRecCompanyAdapter;
import com.rails.purchaseplatform.market.adapter.pdetail.ProductServiceAdapter;
import com.rails.purchaseplatform.market.databinding.ActivityProductDetailsBinding;
import com.rails.purchaseplatform.market.ui.pop.ProductDetailsChooseAddressPop;
import com.rails.purchaseplatform.market.ui.pop.ProductDetailsParamsPop;
import com.rails.purchaseplatform.market.ui.pop.PropertyPop;
import com.rails.purchaseplatform.market.util.GlideImageLoader4ProductDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 商品详情页
 */
@Route(path = ConRoute.MARKET.PRODUCT_DETAIL)
public class ProductDetailsActivity extends BaseErrorActivity<ActivityProductDetailsBinding>
        implements
        CartContract.DetailsCartView,
        ProductDetailsContract.ProductDetailsView,
        AddressToolContract.AddressToolView, PropertyAdapter.OnItemClicked {


    private RecommendItemsRecyclerAdapter recommendItemsRecyclerAdapter;

    private ArrayList<String> pictureUrls = new ArrayList<>();
    private PropertyPop<SpecificationPopBean> mPop;

    private String mItemId;
    private String mShopId;
    private String mPlatformId = "20";
    private String mSkuId;
    private ArrayList<AddressBean> addresses;

    private ProductDetailsParamsPop mParamsPop;
    private ProductDetailsChooseAddressPop mChooseAddressPop;

    private CartContract.CartPresenter2 mPresenter;
    private ProductDetailsContract.ProductDetailsPresenter mGetProductDetailsPresenter;
    private AddressToolContract.AddressToolPresenter mAddressPresenter;

    private ProductDetailsBean productDetailsBean;

    private boolean isCollect = false;

    private ProductDetailsBean mProductDetailsBean;

    private ArrayList<SpecificationPopBean> SPECIFICATION_BEAN;
    private ArrayList<SpecificationPopBean> mSpecificationPopBean;

    private ItemSkuInfo mCheckedItemSkuInfo;


    //
    private DetailImgAdapter imgAdapter;
    private ProductBillAdapter billAdapter;
    private ProductRecCompanyAdapter companyAdapter;
    private ProductServiceAdapter serviceAdapter;


    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        mItemId = extras.getString("itemId", "");
    }


    @Override
    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected void initialize(Bundle bundle) {

        setTextStyleDeprecated();

        mGetProductDetailsPresenter = new ProductDetailsPresenterImpl(this, this);
        mAddressPresenter = new AddressToolPresenterImpl(this, this);
        mPresenter = new CartToolPresenterImpl(this, this);
        mGetProductDetailsPresenter.getProductDetails("20", mItemId, "20", true);
        mAddressPresenter.getAddress("", "1");
        mAddressPresenter.getDefAddress("", "1");


        //图片详情列表
        imgAdapter = new DetailImgAdapter(this);
        binding.webProductInfo.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.webProductInfo.setAdapter(imgAdapter);

        //包装清单
        billAdapter = new ProductBillAdapter(this);
        binding.webPackageList.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.webPackageList.addItemDecoration(new SpaceDecoration(this, 1, R.color.bg_gray));
        binding.webPackageList.setAdapter(billAdapter);

        //推荐企业
        companyAdapter = new ProductRecCompanyAdapter(this);
        binding.webRecommend.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.webRecommend.setAdapter(companyAdapter);


        //售后服务
        serviceAdapter = new ProductServiceAdapter(this);
        binding.webService.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.webService.setAdapter(serviceAdapter);


        // 设置banner宽高
        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) binding.productPictureHD.getLayoutParams();
        layoutParams.width = ScreenSizeUtil.getScreenWidth(this);
        layoutParams.height = layoutParams.width * 24 / 25;
        binding.productPictureHD.setLayoutParams(layoutParams);

//        binding.productPictureHD.setImages(pictureUrls).setImageLoader(new GlideImageLoader4ProductDetails()).start();

        recommendItemsRecyclerAdapter = new RecommendItemsRecyclerAdapter(this);
        binding.recyclerRecommendItems.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 3);
        binding.recyclerRecommendItems.setAdapter(recommendItemsRecyclerAdapter);

        int px = (int) ScreenSizeUtil.dp2px(this, 80);
        float px2 = ScreenSizeUtil.dp2px(this, 240);
        float px3 = ScreenSizeUtil.dp2px(this, 1100);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.rlHeadViewWithTabLayout.setTransitionAlpha(0);
        }
        binding.ibGoTop.setVisibility(View.GONE);

        // 监视TabLayout标签事件，使NestedScrollView滚动到相应的位置
        binding.tabDetails.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectTab(tab, px);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                selectTab(tab, px);
            }
        });

        // 监听NestedScrollView滚动事件设置TabLayout透明度和Tab标签
        binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    updateTabTitleStateOnScroll(px, px2);
                    updateBackButtonStateOnScroll(scrollY);
                }
                updateTabStateOnScroll(px);
                updateGoTopButtonStateOnScroll(scrollY, px3);

            }
        });

        // 监视返回顶部ImageButton点击事件，设置滚回顶部并设置标签选择
        binding.ibGoTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tabDetails.selectTab(binding.tabDetails.getTabAt(0));
                binding.nestedScrollView.scrollTo(0, 0);
            }
        });

        // 左上角返回按钮
        binding.ibBack.setOnClickListener(v -> finish());
        binding.ibBack1.setOnClickListener(v -> finish());
    }

    private void setTextStyleDeprecated() {
        binding.tvRmbGray.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        binding.tvPriceGray.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //获取购物车数量
        mGetProductDetailsPresenter.getCartCount(mPlatformId, "", "", false);
    }

    /**
     * 当页面滚动时更新返回按钮的显示状态
     *
     * @param scrollY
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void updateBackButtonStateOnScroll(int scrollY) {
        if (scrollY <= 200) {
            binding.ibBack1.setTransitionAlpha(1);
        } else {
            binding.ibBack1.setTransitionAlpha(0);
        }
    }

    /**
     * 当页面滚动时更新返回顶部按钮的显示状态
     *
     * @param scrollY
     * @param px3
     */
    private void updateGoTopButtonStateOnScroll(int scrollY, float px3) {
        if (scrollY >= px3) {
            binding.ibGoTop.setVisibility(View.VISIBLE);
        } else {
            binding.ibGoTop.setVisibility(View.GONE);
        }
    }

    /**
     * 当页面滚动时更新TabLayout标签选中的状态
     *
     * @param px
     */
    private void updateTabStateOnScroll(int px) {
        int productInfoPosition = getCurrentPositionY(binding.webProductInfo) - px;
        int packageListPosition = getCurrentPositionY(binding.webPackageList) - px;
        int servicePosition = getCurrentPositionY(binding.webService) - px;
        int recommendPosition = getCurrentPositionY(binding.webRecommend) - px;

        if (packageListPosition <= 0 && servicePosition > 0) {
            binding.tabDetails.selectTab(binding.tabDetails.getTabAt(1));
        } else if (servicePosition <= 0 && recommendPosition > 0) {
            binding.tabDetails.selectTab(binding.tabDetails.getTabAt(2));
        } else if (recommendPosition <= 0) {
            binding.tabDetails.selectTab(binding.tabDetails.getTabAt(3));
        }
    }

    /**
     * 当页面滚动时更新TabLayout透明度
     *
     * @param px
     * @param px2
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void updateTabTitleStateOnScroll(float px, float px2) {
        float llFlagPosition = getCurrentPositionY(binding.llFlag) - px;
        binding.rlHeadViewWithTabLayout.setTransitionAlpha(1 - (llFlagPosition / px2));
    }

    /**
     * 选中TabLayout标签，滚动NestedScrollView
     *
     * @param tab 点击的Tab
     * @param px  标题头的高度
     */
    private void selectTab(TabLayout.Tab tab, int px) {
        if (binding.tabDetails.getTabAt(0).equals(tab)) {
            binding.nestedScrollView.scrollBy(0, getCurrentPositionY(binding.webProductInfo) - px);
        } else if (binding.tabDetails.getTabAt(1).equals(tab)) {
            binding.nestedScrollView.scrollBy(0, getCurrentPositionY(binding.webPackageList) - px);
        } else if (binding.tabDetails.getTabAt(2).equals(tab)) {
            binding.nestedScrollView.scrollBy(0, getCurrentPositionY(binding.webService) - px);
        } else if (binding.tabDetails.getTabAt(3).equals(tab)) {
            binding.nestedScrollView.scrollBy(0, getCurrentPositionY(binding.webRecommend) - px);
        }
    }


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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onClick() {
        super.onClick();

        binding.tvShowAll.setOnClickListener(v -> productDetailGoShopDetail());
        binding.tvGoInShop.setOnClickListener(v -> productDetailGoShopDetail());

        binding.tvPutInCart.setOnClickListener(v -> {
            showPropertyPop(0, 2, mSkuId);
        });

        binding.rlTypeChosen.setOnClickListener(v -> {
            showPropertyPop(1, 1, mSkuId);
        });
        binding.rlAddressChosen.setOnClickListener(v -> {
            showChooseAddressPop();
        });
        binding.rlParamsCheck.setOnClickListener(v -> {
            showParamsCheckPop();
        });

        // 点击店铺按钮 跳转到店铺详情页
        binding.llShop.setOnClickListener(v -> {
            ARouter.getInstance().build(ConRoute.MARKET.SHOP_DETAILS).navigation();
        });

        // 点击收藏按钮 收藏商品
        binding.llCollection.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mSkuId))
                return;
            mPresenter.onCollect(String.valueOf(mSkuId), "20", this.isCollect, -1);
        });

        // 点击购物车按钮 跳转到购物车页面
        binding.llCart.setOnClickListener(v -> startIntent(CartActivity.class));
    }

    /**
     * 商品详情跳转到店铺详情
     */
    private void productDetailGoShopDetail() {
        Bundle bundle = new Bundle();
        bundle.putString("shopInfoId", mShopId);
        ARouter.getInstance().build(ConRoute.MARKET.SHOP_DETAILS).with(bundle).navigation();
    }


    public void getLocalVisibleRect(Context context, View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        view.setTag(location[1]);//存储y方向的位置
        Log.d("HELLO -> LOCATION-Y = ", "" + location[1]);
    }

    /**
     * 获取View与Window的距离
     *
     * @param view 需要测量的View
     * @return 返回View左上角与Window左上角的Y方向的距离
     */
    public int getCurrentPositionY(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[1];
    }

    /**
     * 选择型号规格、加入购物车弹窗
     *
     * @param flag 0-加入购物车 1-选择型号规格
     * @param mode 2-加入购物车 1-选择型号规格
     */
    private void showPropertyPop(int flag, int mode, String skuId) {
        if (TextUtils.isEmpty(skuId))
            return;
        if (mSpecificationPopBean == null || mSpecificationPopBean.size() == 0) {
            if (flag == 0) {
                String saleNum = "1"; // 固定1
                String skuIdSaleNumJson = String.format("[{\"saleNum\":\"%s\",\"skuId\":\"%s\"}]", saleNum, skuId);
                mPresenter.addCart(20L,
                        30L, 40L, 50, // 非必要属性
                        skuIdSaleNumJson, true);
            } else if (flag == 1) {
                ToastUtil.showCenter(this, "商品型号未上传");
            }
            return;
        }

        if (mPop == null) {
            mPop = new PropertyPop<>(mSpecificationPopBean, mProductDetailsBean.getItemSkuInfoList(), mode);
            mPop.setGravity(Gravity.BOTTOM);
            mPop.setType(BasePop.MATCH_WRAP);

//            mPop.setAddToCartListener(() -> mPresenter.addCart(20L,
//                    30L, 40L, 50, // 非必要属性
//                    skuIdSaleNumJson, true));
            //选择型号完成的监听
            mPop.setTypeSelectListener(new PropertyPop.TypeSelect() {
                @Override
                public void onSelectComplete(String count) {
                    // TODO: 2021/5/6 拿到返回的数据请求 所有sku相关的接口
                    if (mCheckedItemSkuInfo != null) {
                        mPresenter.addCart(20L,
                                30L, 40L, 50, // 非必要属性
                                String.format("[{\"saleNum\":\"%s\",\"skuId\":\"%s\"}]", count, mCheckedItemSkuInfo.getId()),
                                true);
                    }
                }
            });

        }
        mPop.show(getSupportFragmentManager(), "property");
    }

    /**
     * 点击规格、加入购物车弹窗中的item时，会切换itemSku
     *
     * @param itemSkuInfo
     */
    @Override
    public void onItemClicked(ItemSkuInfo itemSkuInfo) {
        mCheckedItemSkuInfo = itemSkuInfo;
    }

    /**
     * 弹出查看商品参数弹窗
     */
    void showParamsCheckPop() {
        if (productDetailsBean == null) {
            ToastUtil.showCenter(this, "产品参数未上传");
            return;
        }
        if (mParamsPop == null) {
            ItemParams params = new ItemParams();
            params.setBrand(productDetailsBean.getItemPublishVo().getBrandName());
            params.setName(productDetailsBean.getItemPublishVo().getItemName());
            params.setProductNum(String.valueOf(productDetailsBean.getItemPublishVo().getId()));
            params.setMadeIn(productDetailsBean.getItemPublishVo().getOrigin());
            params.setItemNum(String.valueOf(productDetailsBean.getItemSkuInfoList().get(0).getId()));
            params.setType(productDetailsBean.getItemSkuInfoList().get(0).getModelCode());
            params.setItemBarCode(productDetailsBean.getItemSkuInfoList().get(0).getBarCode());
            params.setWeight(String.valueOf(productDetailsBean.getItemSkuInfoList().get(0).getWeight()));
            params.setWeightUnit(productDetailsBean.getItemSkuInfoList().get(0).getWeightUnit());
            // TODO: 2021/4/22 包装尺寸从哪里取？
            params.setSize(productDetailsBean.getItemSkuInfoList().get(0).getWeightUnit());
            // TODO: 2021/4/22 商品单位从哪里取？
            params.setItemUnit(productDetailsBean.getItemSkuInfoList().get(0).getWeightUnit());

            mParamsPop = new ProductDetailsParamsPop(params);
            mParamsPop.setType(BasePop.MATCH_WRAP);
            mParamsPop.setGravity(Gravity.BOTTOM);
        }
        mParamsPop.show(getSupportFragmentManager(), "product_details_params");
    }

    /**
     * 弹出选择地址弹窗
     */
    void showChooseAddressPop() {
        if (mChooseAddressPop == null) {
            mChooseAddressPop = new ProductDetailsChooseAddressPop(this, addresses);
            mChooseAddressPop.setType(BasePop.MATCH_WRAP);
            mChooseAddressPop.setGravity(Gravity.BOTTOM);
        }
        mChooseAddressPop.show(getSupportFragmentManager(), "product_details_choose_address");
    }


    @Override
    public void addCartSuccess(boolean isComplete) {
        ToastUtil.showCenter(this, "添加成功");
        if (mPop != null) mPop.dismiss();

        //获取购物车数量
        mGetProductDetailsPresenter.getCartCount(mPlatformId, "", "", false);
    }

    @Override
    public void onGetUserCollectSuccess(boolean isCollect) {
        if (isCollect) {
            binding.ivCollect.setBackground(this.getResources().getDrawable(R.drawable.ic_collect_true));
        } else {
            binding.ivCollect.setBackground(this.getResources().getDrawable(R.drawable.ic_collection));
        }
        this.isCollect = isCollect;
    }

    @Override
    public void onCollect(boolean isCollect, int position) {
        if (this.isCollect) {
            binding.ivCollect.setBackground(this.getResources().getDrawable(R.drawable.ic_collection));
        } else {
            binding.ivCollect.setBackground(this.getResources().getDrawable(R.drawable.ic_collect_true));
        }
        this.isCollect = !this.isCollect;
    }


    @Override
    public void onGetProductDetailsSuccess(ProductDetailsBean bean, ArrayList<ProductServiceBean> serviceBeans, ArrayList<ProductServiceBean> recCompanys) {
        if (bean == null)
            return;
        this.productDetailsBean = bean;

        serviceAdapter.update(serviceBeans, true);
        companyAdapter.update(recCompanys, true);

        mGetProductDetailsPresenter.getProductDelivery(bean.getItemPublishVo().getShopId());


        if (bean.getItemPictureVoList() != null && bean.getItemPictureVoList().size() != 0)
            for (ItemPictureVo itemPictureVo : bean.getItemPictureVoList()) {
                pictureUrls.add(itemPictureVo.getPictureUrl());
            }
        binding.productPictureHD.setImages(pictureUrls).setImageLoader(new GlideImageLoader4ProductDetails()).start();
        binding.tvItemName.setText(bean.getItemPublishVo().getItemName());
        binding.textView.setText(bean.getItemPublishVo().getShopName());
        binding.itemSalesCounts.setText(String.valueOf(bean.getItemPublishVo().getItemSaleCount()));
        binding.tvSelectType.setText(bean.getItemSkuInfoList().get(0).getAttributesName());

        List<ItemSkuInfo> itemSkuInfo = bean.getItemSkuInfoList();
        if (itemSkuInfo == null)
            return;
        if (itemSkuInfo.isEmpty())
            return;
        mSkuId = itemSkuInfo.get(0).getId();
        mShopId = String.valueOf(bean.getItemPublishVo().getShopId());
        mGetProductDetailsPresenter.getProductPrice(mPlatformId, mSkuId, false);
        mGetProductDetailsPresenter.getUserCollect(mSkuId, false);
        mGetProductDetailsPresenter.getHotSale(mPlatformId, "", String.valueOf(bean.getItemPublishVo().getCid()), 1, false);

//        binding.ratioImage // TODO 设置图片

        String creditLv = bean.getItemPublishVo().getCreditLevel();
        switch (creditLv) {
            case "B":
                binding.tvCredit.setText("风险较低");
                break;
            case "C":
                binding.tvCredit.setText("风险较高");
                break;
            case "D":
                binding.tvCredit.setText("风险极高");
                break;
            default:
                break;
        }


//        Glide.with(this).load("https:" + bean.getItemPictureVoList().get(0).getPictureUrl()).into(binding.webProductInfo);
        getSpecificationPopBeans(bean);

    }

    /**
     * 组装购物车弹窗和规格弹窗的数据
     *
     * @param bean
     */
    private void getSpecificationPopBeans(ProductDetailsBean bean) {
        mProductDetailsBean = bean;

        HashMap<String, String> hashMap = new HashMap<>();
        if (bean.getItemSkuInfoList() != null && bean.getItemSkuInfoList().size() != 0) {
            ItemSkuInfo skuInfo = bean.getItemSkuInfoList().get(0);
            if (skuInfo.getAttributes() != null && !skuInfo.getAttributes().equals("")) {
                String stringAttr = bean.getItemSkuInfoList().get(0).getAttributes();
                String[] strings = stringAttr.split(";");
                for (String s : strings) {
                    String[] subStrings = s.split(":");
                    hashMap.put(subStrings[0], subStrings[1]);
                }
            }
        }

        ArrayList<SpecificationPopBean> specificationPopBeans = new ArrayList<>();
        if (bean.getItemPublishVo().getAttrNameArray() != null && bean.getItemPublishVo().getAttrNameArray().size() != 0) {

            for (String attrName : bean.getItemPublishVo().getAttrNameArray()) {  // 遍历属性名称
                SpecificationPopBean specificationPopBean = new SpecificationPopBean();  // 创建属性Bean 用于展示
                specificationPopBean.setAttrName(attrName);  // 属性Bean设置名称
                ArrayList<SpecificationValue> specificationValues = new ArrayList<>(); // 创建属性子集合

                for (AttrNameValueReaultVo nameValue : bean.getItemPublishVo().getAttrNameValueReaultVos()) {  // 遍历 从集合对象中取 四项属性
                    SpecificationValue specificationValue = new SpecificationValue();  // 创建子bean

                    if (nameValue.getAttrName().equals(attrName)) {  // 判断 名称相同
                        specificationPopBean.setAttrId(nameValue.getAttrId());  // 添加id
                        specificationValue.setAttrValueId(nameValue.getAttrValueId());  // 添加属性值 属性值Id
                        specificationValue.setAttrValueName(nameValue.getAttrValueName());

                        if (hashMap.containsKey(nameValue.getAttrId()) && hashMap.get(nameValue.getAttrId()).equals(nameValue.getAttrValueId()))
                            specificationValue.setSelect(true);
                        specificationValues.add(specificationValue);
                    }

                    specificationPopBean.setSpecificationValue(specificationValues);
                }
                specificationPopBeans.add(specificationPopBean);
            }
        }
        SPECIFICATION_BEAN = specificationPopBeans;
        mSpecificationPopBean = specificationPopBeans;
    }

    @Override
    public void onGetProductPriceSuccess(ProductPriceBean bean, ArrayList<ItemPicture> pics, ArrayList<ProductBillBean> billBeans) {
        binding.tvSellPrice.setText(String.valueOf(bean.getSellPrice()));
        binding.tvPriceGray.setText(String.valueOf(bean.getMarketPrice()));
        binding.fsvScore.setStar((int) bean.getScore());

        billAdapter.update(billBeans, true);
        imgAdapter.update(pics, true);

    }

    @Override
    public void onGetHotSaleSuccess(ArrayList<RecommendItemsBean> beans) {
        recommendItemsRecyclerAdapter.update(beans, false);
    }

    @Override
    public void onGetCartCountSuccess(String count) {
        binding.tvCartCount.setText(count);
//        try {
//            int num;
//            if (TextUtils.isEmpty(count))
//                num = 0;
//            else
//                num = Integer.parseInt(count);
//            binding.tvCartCount.setNumber(num);
//        } catch (Exception e) {
//            binding.tvCartCount.setNumber(0);
//        }
    }

    @Override
    public void getDelivery(DeliveryBean deliveryBean) {
        if (deliveryBean == null)
            return;
        binding.rlDelivery.setVisibility(View.VISIBLE);
        binding.tvDelivery.setText(String.format(getResources().getString(R.string.product_details_delivery),
                String.valueOf(deliveryBean.getFreightPrice())));
    }


    @Override
    public void getAddress(ArrayList<AddressBean> addressBeans) {
        this.addresses = addressBeans;
    }

    @Override
    public void getDefAddress(AddressBean bean) {
        binding.tvAddressDefault.setText(bean.getFullAddress());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
        } catch (Exception e) {

        }
    }
}

package com.rails.purchaseplatform.market.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayout;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.forAppShow.ItemParams;
import com.rails.lib_data.bean.forAppShow.RecommendItemsBean;
import com.rails.lib_data.bean.forAppShow.SpecificationPopBean;
import com.rails.lib_data.bean.forAppShow.SpecificationValue;
import com.rails.lib_data.bean.forNetRequest.productDetails.AttrNameValueReaultVo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemAfterSaleVo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemPictureVo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemSkuInfo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductPriceBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.SupplierInfoImportData;
import com.rails.lib_data.contract.AddressToolContract;
import com.rails.lib_data.contract.AddressToolPresenterImpl;
import com.rails.lib_data.contract.CartContract;
import com.rails.lib_data.contract.CartToolPresenterImpl;
import com.rails.lib_data.contract.ProductDetailsContract;
import com.rails.lib_data.contract.ProductDetailsPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.common.utils.JSBack;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.RecommendItemsRecyclerAdapter;
import com.rails.purchaseplatform.market.databinding.ActivityProductDetailsBinding;
import com.rails.purchaseplatform.market.ui.pop.ProductDetailsChooseAddressPop;
import com.rails.purchaseplatform.market.ui.pop.ProductDetailsParamsPop;
import com.rails.purchaseplatform.market.ui.pop.PropertyPop;
import com.rails.purchaseplatform.market.util.GlideImageLoader4ProductDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 商品详情页
 */
@Route(path = ConRoute.MARKET.PRODUCT_DETAIL)
public class ProductDetailsActivity extends BaseErrorActivity<ActivityProductDetailsBinding>
        implements
        JSBack,
        CartContract.DetailsCartView,
        ProductDetailsContract.ProductDetailsView,
        AddressToolContract.AddressToolView {


    final private String TAG = ProductDetailsActivity.class.getSimpleName();

    private RecommendItemsRecyclerAdapter recommendItemsRecyclerAdapter;

    private CartContract.CartPresenter2 mPresenter;

    final private ArrayList<String> TAB_URLS = new ArrayList<>();

    private ArrayList<String> pictureUrls = new ArrayList<>();
    private PropertyPop<SpecificationPopBean> mPop;

    private String mItemId;
    private String mShopId;
    private String mPlatformId = "20";
    private String skuId;
    private ArrayList<AddressBean> addresses;

    final private ArrayList<View> VIEWS = new ArrayList<>();

    private ProductDetailsParamsPop mParamsPop;
    private ProductDetailsChooseAddressPop mChooseAddressPop;

    private ProductDetailsContract.ProductDetailsPresenter mGetProductDetailsPresenter;
    private AddressToolContract.AddressToolPresenter mAddressPresenter;

    private ProductDetailsBean productDetailsBean;

    private String recommendOrg, bindOrgName, accountName;
    private String refundInfo, changeInfo, repairInfo, specialInfo;
    private String packagingList;

    private boolean isCollect = false;

    private ArrayList<SpecificationPopBean> mSpecificationPopBean;


    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        mItemId = extras.getString("itemId", "");
    }


    @Override
    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected void initialize(Bundle bundle) {
        TAB_URLS.add(ConRoute.WEB_URL.PRODUCT_INFO);
        TAB_URLS.add(ConRoute.WEB_URL.PACKAGE_LIST);
        TAB_URLS.add(ConRoute.WEB_URL.SERVICES);
        TAB_URLS.add(ConRoute.WEB_URL.RECOMMENDS);

        mGetProductDetailsPresenter = new ProductDetailsPresenterImpl(this, this);
        mGetProductDetailsPresenter.getProductDetails("20", mItemId, "20", true);

        mAddressPresenter = new AddressToolPresenterImpl(this, this);
        mAddressPresenter.getAddress("", "1");
        mAddressPresenter.getDefAddress("", "1");

        VIEWS.add(binding.viewSplit1);
        VIEWS.add(binding.viewSplit2);
        VIEWS.add(binding.viewSplit3);
        VIEWS.add(binding.viewSplit4);

        setTextStyleDeprecated();

        mPresenter = new CartToolPresenterImpl(this, this);

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

    /**
     * 加载4个WebView
     */
    @SuppressLint("JavascriptInterface")
    private void loadWebView() {
        WebView[] WEB_VIEWS = {
                binding.webProductInfo,
                binding.webPackageList,
                binding.webService,
                binding.webRecommend
        };

        for (int i = 0; i < WEB_VIEWS.length; i++) {
            setWeb(WEB_VIEWS[i], i);
            WEB_VIEWS[i].loadUrl(TAB_URLS.get(i));
            WEB_VIEWS[i].addJavascriptInterface(ProductDetailsActivity.this, "app");
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
            showPropertyPop(0, 2, skuId);
        });

        binding.rlTypeChosen.setOnClickListener(v -> {
            showPropertyPop(1, 1, skuId);
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
            if (TextUtils.isEmpty(skuId))
                return;
            mPresenter.onCollect(String.valueOf(skuId), "20", this.isCollect, -1);
        });

        // 点击购物车按钮 跳转到购物车页面
        binding.tvCartCount.setOnClickListener(v -> startIntent(CartActivity.class));
    }

    /**
     * 商品详情跳转到店铺详情
     */
    private void productDetailGoShopDetail() {
        Bundle bundle = new Bundle();
        bundle.putString("shopInfoId", mShopId);
        ARouter.getInstance().build(ConRoute.MARKET.SHOP_DETAILS).with(bundle).navigation();
    }

    /**
     * WebView属性设置
     *
     * @param view WebView
     */
    private void setWeb(WebView view, int index) {
        WebSettings webSettings = view.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        webSettings.setJavaScriptEnabled(true);//是否允许JavaScript脚本运行，默认为false。设置true时，会提醒可能造成XSS漏洞
        webSettings.setSupportZoom(true);//是否可以缩放，默认true
        webSettings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        webSettings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        webSettings.setAppCacheEnabled(true);//是否使用缓存
        webSettings.setDomStorageEnabled(true);//开启本地DOM存储
        webSettings.setLoadsImagesAutomatically(true); // 加载图片
        webSettings.setMediaPlaybackRequiresUserGesture(false);//播放音频，多媒体需要用户手动？设置为false为可自动播放

        // 设置WebViewClient
        view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "FINISHED_URL = " + url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.setVisibility(View.GONE);
                VIEWS.get(index).setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.d(TAG, "\nERROR_CODE = " + errorCode + "\nDESCRIPTION = " + description + "\nFAILING_URL = " + failingUrl);
            }
        });
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
                final String SALE_NUM = "1"; // 固定1
                String skuIdSaleNumJson = String.format("[{\"saleNum\":\"%s\",\"skuId\":\"%s\"}]", SALE_NUM, skuId);
                mPresenter.addCart(20L,
                        30L, 40L, 50, // 非必要属性
                        skuIdSaleNumJson, true);
            } else if (flag == 1) {
                ToastUtil.showCenter(this, "商品型号未上传");
            }
            return;
        }
        final String SALE_NUM = "1"; // 固定1
        String skuIdSaleNumJson = String.format("[{\"saleNum\":\"%s\",\"skuId\":\"%s\"}]", SALE_NUM, skuId);
        if (mPop == null) {
            mPop = new PropertyPop<>(mSpecificationPopBean, mode);
            mPop.setGravity(Gravity.BOTTOM);
            mPop.setType(BasePop.MATCH_WRAP);
            mPop.setAddToCartListener(() -> mPresenter.addCart(20L,
                    30L, 40L, 50, // 非必要属性
                    skuIdSaleNumJson, true));
        }
        mPop.show(getSupportFragmentManager(), "property");
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
        if (mPop != null) mPop.dismiss();
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
    public void onGetProductDetailsSuccess(ProductDetailsBean bean) {
        if (bean == null)
            return;
        this.productDetailsBean = bean;
        if (bean.getItemPictureVoList() != null && bean.getItemPictureVoList().size() != 0)
            for (ItemPictureVo itemPictureVo : bean.getItemPictureVoList()) {
                pictureUrls.add(itemPictureVo.getPictureUrl());
            }
        binding.productPictureHD.setImages(pictureUrls).setImageLoader(new GlideImageLoader4ProductDetails()).start();
        binding.tvItemName.setText(bean.getItemPublishVo().getItemName());
        binding.textView.setText(bean.getItemPublishVo().getShopName());
        binding.itemSalesCounts.setText(String.valueOf(bean.getItemPublishVo().getItemSaleCount()));

        List<ItemSkuInfo> itemSkuInfo = bean.getItemSkuInfoList();
        if (itemSkuInfo == null)
            return;
        if (itemSkuInfo.isEmpty())
            return;
        skuId = itemSkuInfo.get(0).getId();
        mShopId = String.valueOf(bean.getItemPublishVo().getShopId());
        mGetProductDetailsPresenter.getProductPrice(mPlatformId, skuId, false);
        mGetProductDetailsPresenter.getUserCollect(skuId, false);
        mGetProductDetailsPresenter.getHotSale(mPlatformId, "", String.valueOf(bean.getItemPublishVo().getCid()), 1, false);
        mGetProductDetailsPresenter.getCartCount(mPlatformId, "", "", false);


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

        ItemAfterSaleVo afterSale = bean.getItemPublishVo().getItemAfterSaleVo();
        if (afterSale.getRefundService() == 1) refundInfo = "特殊商品不允许退货。";
        else refundInfo = "确认收货后" + afterSale.getRefundDuration() + "日内出现质量问题可申请退货。";
        if (afterSale.getChangeService() == 1) changeInfo = "特殊商品，一经签收不予换货。";
        else refundInfo = "确认收货后" + afterSale.getChangeDuration() + "日内出现质量问题可申请换货。";
        repairInfo = "确认收货后" + afterSale.getRepaireDuration() + "月内出现质量问题可审请质保。";
        specialInfo = "特殊说明" + afterSale.getSpecialDesc();

        SupplierInfoImportData suppData = bean.getItemPublishVo().getSupplierInfoImportData();
        recommendOrg = suppData.getRecommendOrg();
        bindOrgName = suppData.getBindOrgName();
        accountName = suppData.getAccountName();


        getSpecificationPopBeans(bean);

    }

    /**
     * 组装购物车弹窗和规格弹窗的数据
     *
     * @param bean
     */
    private void getSpecificationPopBeans(ProductDetailsBean bean) {

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
            for (String attrName : bean.getItemPublishVo().getAttrNameArray()) {
                SpecificationPopBean specificationPopBean = new SpecificationPopBean();
                specificationPopBean.setAttrName(attrName);
                ArrayList<SpecificationValue> specificationValues = new ArrayList<>();
                for (AttrNameValueReaultVo nameValue : bean.getItemPublishVo().getAttrNameValueReaultVos()) {
                    SpecificationValue specificationValue = new SpecificationValue();
                    specificationPopBean.setAttrId(nameValue.getAttrId());
                    if (nameValue.getAttrName().equals(attrName)) {
                        specificationValue.setAttrValueId(nameValue.getAttrValueId());
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
        mSpecificationPopBean = specificationPopBeans;
    }

    @Override
    public void onGetProductPriceSuccess(ProductPriceBean bean) {
        binding.tvSellPrice.setText(String.valueOf(bean.getSellPrice()));
        binding.tvPriceGray.setText(String.valueOf(bean.getMarketPrice()));
        binding.fsvScore.setStar((int) bean.getScore());

        if (bean.getPackinglist().size() != 0)
            spliceList(bean.getPackinglist().get(0).getAnnexName());

        loadWebView();
    }

    public void spliceList(String annexName) {
        String result = "{\"data\":[";
        if (annexName.contains("``")) {
            String[] strings = annexName.split("``");
            for (int i = 0; i < strings.length; i++) {
                String[] key_values = strings[i].split("\\*");
                strings[i].split("\\*");
                if (i == strings.length - 1)
                    result += "{\"key\":\"" + key_values[0] + "\",\"value\":\"" + key_values[1] + "\"}]}";
                else
                    result += "{\"key\":\"" + key_values[0] + "\",\"value\":\"" + key_values[1] + "\"},";
            }
        } else {
            result += "{\"key\":\"" + annexName.split("\\*")[0] + "\",\"value\":\"" + annexName.split("\\*")[1] + "\"}]}";
        }
        packagingList = result;
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
    public void getAddress(ArrayList<AddressBean> addressBeans) {
        this.addresses = addressBeans;
    }

    @Override
    public void getDefAddress(AddressBean bean) {
        binding.tvAddressDefault.setText(bean.getFullAddress());
    }

    @JavascriptInterface
    @Override
    public void onBack() {

    }

    @JavascriptInterface
    @Override
    public String getToken() {
        return PrefrenceUtil.getInstance(this).getString(ConShare.TOKEN, "");
    }

    @JavascriptInterface
    @Override
    public void onLogin() {

    }


    /**
     * H5 商品信息
     */
    @JavascriptInterface
    public String getProductInfo() {
        return "//xsky.rails.cn/mall/4a1e89d7991613af647b3490a9378c0820200308021209740.jpg";
    }

    /**
     * H5 推荐单位
     */
    @JavascriptInterface
    public String getRecommend() {
        return "{\"recommendOrg\":\"" + recommendOrg + "\",\"bindOrgName\":\"" + bindOrgName + "\",\"accountName\":\"" + accountName + "\"}";
    }

    /**
     * H5 售后服务
     */
    @JavascriptInterface
    public String getAfterSale() {
        return "{\"refundInfo\":\"" + refundInfo + "\",\"changeInfo\":\"" + changeInfo + "\",\"repairInfo\":\"" + repairInfo + "\",\"specialInfo\":\"" + specialInfo + "\"}";
    }

    /**
     * H5 包装清单
     */
    @JavascriptInterface
    public String getPackagingList() {
        return packagingList;
    }

}

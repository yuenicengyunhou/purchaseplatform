package com.rails.purchaseplatform.market.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.DeliveryBean;
import com.rails.lib_data.bean.ProductServiceBean;
import com.rails.lib_data.bean.SkuStockBean;
import com.rails.lib_data.bean.forAppShow.ProductDetailsPackingBean;
import com.rails.lib_data.bean.forAppShow.ProductDetailsPageBean;
import com.rails.lib_data.bean.forAppShow.ProductDetailsPopBean;
import com.rails.lib_data.bean.forAppShow.ProductSpecificParameter;
import com.rails.lib_data.bean.forAppShow.RecommendItemsBean;
import com.rails.lib_data.bean.forAppShow.SpecificationPopBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemPicture;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemSkuInfo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductPriceBean;
import com.rails.lib_data.contract.AddressToolContract;
import com.rails.lib_data.contract.CartContract;
import com.rails.lib_data.contract.CartToolPresenterImpl;
import com.rails.lib_data.contract.ProductDetailsContract;
import com.rails.lib_data.contract.ProductDetailsContract2;
import com.rails.lib_data.contract.ProductDetailsDataUtils;
import com.rails.lib_data.contract.ProductDetailsPresenterImpl;
import com.rails.lib_data.contract.ProductDetailsPresenterImpl2;
import com.rails.lib_data.contract.StatisticContract;
import com.rails.lib_data.contract.StatisticPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.common.pop.AreaPop;
import com.rails.purchaseplatform.common.pop.QuickJumpPop;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.base.XiaoMiStatusBar;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.RecommendItemsRecyclerAdapter;
import com.rails.purchaseplatform.market.adapter.pdetail.DetailImgAdapter;
import com.rails.purchaseplatform.market.adapter.pdetail.ProductBillAdapter;
import com.rails.purchaseplatform.market.adapter.pdetail.ProductRecCompanyAdapter;
import com.rails.purchaseplatform.market.adapter.pdetail.ProductServiceAdapter;
import com.rails.purchaseplatform.market.databinding.ActivityProductDetailsBinding;
import com.rails.purchaseplatform.market.ui.pop.AddCartPop;
import com.rails.purchaseplatform.market.ui.pop.ProductDetailsChooseAddressPop;
import com.rails.purchaseplatform.market.ui.pop.ProductDetailsParamsPop;
import com.rails.purchaseplatform.market.util.GlideImageLoader;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * 商品详情页
 */
@Route(path = ConRoute.MARKET.PRODUCT_DETAIL)
public class ProductDetailsActivity extends BaseErrorActivity<ActivityProductDetailsBinding>
        implements
        CartContract.DetailsCartView,
        ProductDetailsContract.ProductDetailsView,
        ProductDetailsContract2.ProductDetailsView2,
        AddressToolContract.AddressToolView,
        StatisticContract.StatisticView {

    final private String TAG = ProductDetailsActivity.class.getSimpleName();

    final private int LOAD_BITMAP = 63;

    private final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == LOAD_BITMAP) {
                imgAdapter.update(mDescribePictureList, true);
                if (mThread != null) mThread.interrupt();
                mHandler.removeCallbacksAndMessages(null);
                return true;
            }
            return false;
        }
    });

    private Thread mThread;

    /**
     * 实现并行和串行网络请求
     */
    private ProductDetailsContract2.ProductDetailsPresenter2 mProductDetailsPresenterImpl2;

    /**
     * 商品详情页面所有需要展示的、可能需要运算的数据
     */
    private ProductDetailsPageBean mPageBean = new ProductDetailsPageBean();

    /**
     * 添加购物车Pop中需要展示或计算的所有数据
     */
    private ProductDetailsPopBean mPopBean;


    private ArrayList<String> mTabs = new ArrayList<String>() {{
        add("商品信息");
        add("包装清单");
        add("售后服务");
        add("推荐单位");
    }};


    //判断是点击还是滑动
    private boolean isScroll = true;


    /**
     * 三个点 快捷跳转的pop
     */
    private QuickJumpPop pop;

    private ArrayList<String> mDescribeUrlList = new ArrayList<>();
    private ArrayList<ItemPicture> mDescribePictureList = new ArrayList<>();

    private ProductDetailsBean productDetailsBean;

    private String mItemId;
    private String mSkuId;
    private String mPlatformId = "20";

    private AddCartPop mPop;
    private ProductDetailsChooseAddressPop mChooseAddressPop;
    private ProductDetailsParamsPop mParamsPop;
    private QuickJumpPop mQuickJumpPop;

    private CartContract.CartPresenter2 mPresenter;
    private ProductDetailsContract.ProductDetailsPresenter mGetProductDetailsPresenter;
    private StatisticContract.StatisticPresenter statisticPresenter;

    private RecommendItemsRecyclerAdapter recommendItemsRecyclerAdapter;
    private DetailImgAdapter imgAdapter;
    private ProductBillAdapter billAdapter;
    private ProductRecCompanyAdapter companyAdapter;
    private ProductServiceAdapter serviceAdapter;


    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        mItemId = extras.getString("itemId", "");
        mSkuId = extras.getString("skuId", "");
    }


    @Override
    protected void initialize(Bundle bundle) {
        // 设置市场价格文字删除线
        binding.tvRmbGray.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        binding.tvPriceGray.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        binding.tvSkuUnitGray.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        // 请求商品信息
        mProductDetailsPresenterImpl2 = new ProductDetailsPresenterImpl2(this, this);
        mProductDetailsPresenterImpl2.getAllProductInfo("20", mItemId, mSkuId, "1", true);

        mGetProductDetailsPresenter = new ProductDetailsPresenterImpl(this, this);
        mPresenter = new CartToolPresenterImpl(this, this);

        // 请求商品信息（弃用的）
//        mGetProductDetailsPresenter.getProductDetails("20", mItemId, "20", true);


        //图片详情列表
        imgAdapter = new DetailImgAdapter(this);
        imgAdapter.setOnItemClickListener(new DetailImgAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
//                bundle.putString("imageUrl", mPageBean.getTopPictureList().get(position));
                bundle.putStringArrayList("imageUrlList", mDescribeUrlList);
                bundle.putInt("position", position);
                ARouter.getInstance().build(ConRoute.MARKET.IMAGE_ZOOM).with(bundle).navigation();
            }
        });
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
        layoutParams.height = layoutParams.width * 25 / 25;
        binding.productPictureHD.setLayoutParams(layoutParams);

//        binding.productPictureHD.setImages(pictureUrls).setImageLoader(new GlideImageLoader4ProductDetails()).start();

        recommendItemsRecyclerAdapter = new RecommendItemsRecyclerAdapter(this);
        binding.recyclerRecommendItems.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 3);
        binding.recyclerRecommendItems.setAdapter(recommendItemsRecyclerAdapter);

        int px = (int) ScreenSizeUtil.dp2px(this, 80); // Tab底部距离屏幕顶部的高度
        float px2 = ScreenSizeUtil.dp2px(this, 240); // 内容上移到Tab完全透明状态的高度阈值
        float px3 = ScreenSizeUtil.dp2px(this, 1100); // 内容上移到返回顶部按钮完全透明状态的高度阈值

        binding.rlHeadViewWithTabLayout.setAlpha(0);
        binding.ibGoTop.setVisibility(View.GONE);


        /**
         *
         */
        for (int i = 0; i < mTabs.size(); i++) {
            TabLayout.Tab tab = binding.tabDetails.newTab();
            TextView lable = new TextView(this);
            tab.setCustomView(lable);
            lable.setText(mTabs.get(i));
            lable.setGravity(Gravity.CENTER);
            lable.setTextColor(getResources().getColor(i == 0 ? R.color.font_blue : R.color.font_black));
//            lable.setTypeface(Typeface.defaultFromStyle(i == 0 ? Typeface.BOLD : Typeface.NORMAL));
            lable.setTextSize(13F);
            tab.setTag(i);
            binding.tabDetails.addTab(tab);
            int finalI = i;
            lable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tab.select();
                    isScroll = false;
                    if (finalI == 0) {
                        binding.nestedScrollView.scrollBy(0, getCurrentPositionY(binding.webProductInfo) - px);
                    } else if (finalI == 1) {
                        binding.nestedScrollView.scrollBy(0, getCurrentPositionY(binding.webPackageList) - px);
                    } else if (finalI == 2) {
                        binding.nestedScrollView.scrollBy(0, getCurrentPositionY(binding.webService) - px);
                    } else if (finalI == 3) {
                        binding.nestedScrollView.scrollBy(0, getCurrentPositionY(binding.webRecommend) - px);
                    }
                }
            });
        }


        // 监视TabLayout标签事件，使NestedScrollView滚动到相应的位置
        binding.tabDetails.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView lable = (TextView) tab.getCustomView();
                lable.setTextColor(getResources().getColor(R.color.font_blue));
                lable.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView lable = (TextView) tab.getCustomView();
                lable.setTextColor(getResources().getColor(R.color.font_black));
                lable.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                selectTab(tab, px);
            }
        });


        // 监听NestedScrollView滚动事件设置TabLayout透明度和Tab标签
        binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                updateTabTitleStateOnScroll(px, px2);
                updateBackButtonStateOnScroll(scrollY);
                if (isScroll) {
                    updateTabStateOnScroll(px);
                }
                updateGoTopButtonStateOnScroll(scrollY, px3);
                isScroll = true;
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
    private void updateBackButtonStateOnScroll(int scrollY) {
        if (scrollY <= 200) {
            binding.ibBack1.setAlpha(1F);
            binding.ibQuickJump1.setAlpha(1F);
        } else {
            binding.ibBack1.setAlpha(0F);
            binding.ibQuickJump1.setAlpha(0F);
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
        int packageListPosition = getCurrentPositionY(binding.webPackageList) - getScreenHeight() + px;
        int servicePosition = getCurrentPositionY(binding.webService) - getScreenHeight() + px;
        int recommendPosition = getCurrentPositionY(binding.webRecommend) - getScreenHeight() + px;

        if (productInfoPosition >= 0) {
            binding.tabDetails.selectTab(binding.tabDetails.getTabAt(0));
        } else if (packageListPosition >= 0) {
            binding.tabDetails.selectTab(binding.tabDetails.getTabAt(1));
        } else if (servicePosition >= 0) {
            binding.tabDetails.selectTab(binding.tabDetails.getTabAt(2));
        } else if (recommendPosition >= 0) {
            binding.tabDetails.selectTab(binding.tabDetails.getTabAt(3));
        }
    }

    /**
     * 当页面滚动时更新TabLayout透明度
     *
     * @param px
     * @param px2
     */
    private void updateTabTitleStateOnScroll(float px, float px2) {
        float llFlagPosition = getCurrentPositionY(binding.llFlag) - px;
        binding.rlHeadViewWithTabLayout.setAlpha(1 - (llFlagPosition / px2));
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
        return XiaoMiStatusBar.isXiaomi() ?
                android.R.color.transparent :
                android.R.color.white;
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

        // 左上角返回按钮
        binding.ibBack.setOnClickListener(v -> finish());
        binding.ibBack1.setOnClickListener(v -> finish());

        // 右上角快捷跳转
        binding.ibQuickJump.setOnClickListener(v -> showJumpPop());
        binding.ibQuickJump1.setOnClickListener(v -> showJumpPop());

        // 设置轮播点击事件
        binding.productPictureHD.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Bundle bundle = new Bundle();
//                bundle.putString("imageUrl", mPageBean.getTopPictureList().get(position));
//                for (String str : mPageBean.getSkuMarkPicList()) {
//                    Log.d(TAG, "点击轮播展示水印图片 = " + str);
//                }

                Log.d(TAG, "   MARK PIC LENGTH = " + mPageBean.getSkuMarkPicList());
                Log.d(TAG, "UN MARK PIC LENGTH = " + mPageBean.getSkuPicList());
                if (mPageBean.getSkuMarkPicList() == null || mPageBean.getSkuMarkPicList().size() == 0 || mPageBean.getSkuMarkPicList().contains(null))
                    bundle.putStringArrayList("imageUrlList", mPageBean.getSkuPicList());
                else
                    bundle.putStringArrayList("imageUrlList", mPageBean.getSkuMarkPicList());
                bundle.putInt("position", position);
                ARouter.getInstance().build(ConRoute.MARKET.IMAGE_ZOOM).with(bundle).navigation();
            }
        });

        // 跳转到店铺详情
//        binding.tvShowAll.setOnClickListener(v -> productDetailGoShopDetail()); // 此按钮不在跳转到店铺页，隐藏此按钮
        binding.tvGoInShop.setOnClickListener(v -> productDetailGoShopDetail());
        binding.llShop.setOnClickListener(v -> productDetailGoShopDetail());

        // 弹出添加购物车Pop
        binding.tvPutInCart.setOnClickListener(v -> {
            if (mPageBean.getCurrentItemSkuInfo() != null)
                showPropertyPop(mPageBean.getCurrentItemSkuInfo().getId());
        });

        // 弹出选择型号Pop
        binding.rlTypeChosen.setOnClickListener(v -> {
            if (mPageBean.getCurrentItemSkuInfo() != null)
                showPropertyPop(mPageBean.getCurrentItemSkuInfo().getId());
        });

        // 弹出选择地址Pop
        binding.rlAddressChosen.setOnClickListener(v -> {
            showChooseAddressPop(mPageBean.getAddressId());
        });

        // 弹出详细参数Pop
        binding.rlParamsCheck.setOnClickListener(v -> {
            showParamsCheckPop();
        });

        // 点击收藏按钮 收藏商品
        binding.llCollection.setOnClickListener(v -> {
            if (mPageBean.getCurrentItemSkuInfo() == null || TextUtils.isEmpty(mPageBean.getCurrentItemSkuInfo().getId()))
                return;
            mPresenter.onCollect(String.valueOf(mPageBean.getCurrentItemSkuInfo().getId()), "20", mPageBean.isCollected(), -1);
        });

        // 点击购物车按钮 跳转到购物车页面
        binding.llCart.setOnClickListener(v -> startIntent(CartActivity.class));
    }


    /**
     * 商品详情跳转到店铺详情
     */
    private void productDetailGoShopDetail() {
        if (null == mPageBean) {
            ToastUtil.showCenter(this, "商品信息为空");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("shopInfoId", mPageBean.getShopId());
        bundle.putString("materialType", mPageBean.getMaterialType());
        ARouter.getInstance().build(ConRoute.MARKET.SHOP_DETAILS).with(bundle).navigation();
    }

    private void showJumpPop() {
        if (null == mQuickJumpPop) {
            mQuickJumpPop = new QuickJumpPop();
            mQuickJumpPop.setGravity(Gravity.BOTTOM);
            mQuickJumpPop.setType(BasePop.MATCH_WRAP);
        }
        mQuickJumpPop.show(getSupportFragmentManager(), "quick");
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

    public int getScreenHeight() {
        return this.getWindowManager().getDefaultDisplay().getHeight();
    }

    /**
     * 选择型号规格、加入购物车弹窗
     *
     * @param skuId
     */
    private void showPropertyPop(String skuId) {
        if (TextUtils.isEmpty(skuId))
            return;
        ArrayList<ItemSkuInfo> itemSkuInfoList = new ArrayList<>();
        if (productDetailsBean != null) {
            itemSkuInfoList = (ArrayList<ItemSkuInfo>) productDetailsBean.getItemSkuInfoList();
        }
        if (mPop == null) {
            mPop = new AddCartPop(mPageBean, itemSkuInfoList);
            mPop.setGravity(Gravity.BOTTOM);
            mPop.setType(BasePop.MATCH_WRAP);
            //选择型号完成的监听
            mPop.setTypeSelectListener(new AddCartPop.ChooseSkuAndAddCart() {
                @Override
                public void onAddCart(String count) {
                    if (mPageBean.getCurrentItemSkuInfo() != null) {
                        mPresenter.addCart(20L,
                                30L, 40L, 50, // 非必要属性
                                String.format("[{\"saleNum\":\"%s\",\"skuId\":\"%s\"}]", count, mPageBean.getCurrentItemSkuInfo().getId()),
                                true);
                        /*mGetProductDetailsPresenter.getProductPrice(mPlatformId, mPageBean.getCurrentItemSkuInfo().getId(), false);*/
                        mPop.dismiss();
                    } else {
                        ToastUtil.showCenter(ProductDetailsActivity.this, "没有此型号商品或商品库存不足");
                    }
                }

                // 点击规格、加入购物车弹窗中的item时，会切换itemSku
                @Override
                public void onSkuChanged(ItemSkuInfo itemSkuInfo) {
                    if (itemSkuInfo != null) {
                        mPageBean.setCurrentItemSkuInfo(itemSkuInfo);
                        binding.tvSelectType.setText(itemSkuInfo.getAttributesName());
                        binding.tvItemName.setText(itemSkuInfo.getSkuName());
                        mGetProductDetailsPresenter.getProductPrice(
                                "20",
                                mPageBean.getCurrentItemSkuInfo().getId(),
                                true);
                        mGetProductDetailsPresenter.querySkuSaleStocks(
                                mPageBean.getShopId(),
                                mPageBean.getProvinceCode(),
                                mPageBean.getCityCode(),
                                mPageBean.getCountryCode(),
                                mPageBean.getFullAddress(),
                                "1",
                                mPageBean.getCurrentItemSkuInfo().getId(),
                                false);

                        /*mProductDetailsPresenterImpl2.getProductSkuInfo("20", mPageBean.getCid(), mPageBean.getSkuId(),
                                mPageBean.getShopId(), mPageBean.getProvinceCode(), mPageBean.getCityCode(), mPageBean.getCountryCode(),
                                mPageBean.getFullAddress(), "1", true);*/
                    } else {

                    }
                }
            });
        }
        mPop.show(getSupportFragmentManager(), "property");
    }

    /**
     * 弹出查看商品参数弹窗
     */
    void showParamsCheckPop() {
        if (mPageBean.getCurrentItemSkuInfo() == null || productDetailsBean == null) {
            ToastUtil.showCenter(this, "产品参数未上传");
            return;
        }
        ProductDetailsDataUtils utils = ProductDetailsDataUtils.getInstance();
        ArrayList<ProductSpecificParameter> parameters = new ArrayList<>();
        utils.getCommonParams(parameters, productDetailsBean, mPageBean.getCurrentItemSkuInfo());
        ArrayList<ProductSpecificParameter> specParameters = new ArrayList<>();
        utils.getSpecParams(specParameters, productDetailsBean, mPageBean.getCurrentItemSkuInfo());
        if (mParamsPop == null) {
            mParamsPop = new ProductDetailsParamsPop(parameters, specParameters);
            mParamsPop.setType(BasePop.MATCH_WRAP);
            mParamsPop.setGravity(Gravity.BOTTOM);
        } else {
            mParamsPop.setParameters(parameters);
            mParamsPop.setSpecParameters(specParameters);
        }
        mParamsPop.show(getSupportFragmentManager(), "product_details_params");
    }

    /**
     * 弹出选择地址弹窗
     */
    void showChooseAddressPop(String addressId) {
        if (mChooseAddressPop == null) {
            mChooseAddressPop = new ProductDetailsChooseAddressPop(this, mPageBean.getAddressList());
            mChooseAddressPop.setCurrentAddressId(addressId);
            mChooseAddressPop.setType(BasePop.MATCH_WRAP);
            mChooseAddressPop.setGravity(Gravity.BOTTOM);
            mChooseAddressPop.setListener(new ProductDetailsChooseAddressPop.AddressListener() {
                @Override
                public void getAddrss(AddressBean bean) {
                    binding.tvAddressDefault.setText(bean.getFullAddress());
                    mPageBean.setFullAddress(bean.getFullAddress());
                    mPageBean.setAddressId(bean.getAddressId());
                }

                @Override
                public void getArea(String area) {
                    AreaPop pop = new AreaPop();
                    pop.setGravity(Gravity.BOTTOM);
                    pop.setType(BasePop.MATCH_WRAP);
                    pop.setListener((address, provinceCode, cityCode, countryCode) -> {
                        mPageBean.setProvinceCode(provinceCode);
                        mPageBean.setCityCode(cityCode);
                        mPageBean.setCountryCode(countryCode);
                        mPageBean.setFullAddress(address);
                        binding.tvAddressDefault.setText(address);
                    });
                    pop.show(getSupportFragmentManager(), "area");

                }
            });
        } else {
            mChooseAddressPop.setCurrentAddressId(addressId);
        }
        mChooseAddressPop.show(getSupportFragmentManager(), "product_details_choose_address");
    }

    /**
     * 快捷跳转的方法
     *
     * @param type 根据此参数决定跳转目标页面
     */
    public void onQuickClick(String type) {
        if (null == pop) {
            pop = new QuickJumpPop();
            pop.setGravity(Gravity.BOTTOM);
            pop.setType(BasePop.MATCH_WRAP);
        }
        pop.show(getSupportFragmentManager(), "quick");
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
    }

    @Override
    public void onCollect(boolean isCollect, int position) {
        // TODO: 2021/6/10 保留 点击收藏按钮时发送网络请求 成功后更新收藏状态
        if (mPageBean.isCollected()) {
            binding.ivCollect.setBackground(this.getResources().getDrawable(R.drawable.ic_collection));
        } else {
            binding.ivCollect.setBackground(this.getResources().getDrawable(R.drawable.ic_collect_true));
        }
        mPageBean.setCollected(!mPageBean.isCollected());
    }


    /**
     * 请求商品详情接口成功回调方法
     *
     * @param bean         商品详情信息
     * @param serviceBeans 售后服务bean
     * @param recCompanys  推荐企业列表
     */
    @Override
    public void onGetProductDetailsSuccess(ProductDetailsBean bean,
                                           ArrayList<ProductServiceBean> serviceBeans,
                                           ArrayList<ProductServiceBean> recCompanys,
                                           ArrayList<SpecificationPopBean> specificationPopBeanList) {
    }


    /**
     * 展示商品介绍图片
     */
    private void showDescribePictures() {
        String longUrl = mPageBean.getDetailsPictureUrl();
        if (!TextUtils.isEmpty(longUrl) && longUrl.contains(".jpg")) {
            mDescribeUrlList.clear();
            String[] urls = longUrl.split("\\.jpg");
            for (String string : urls) {
                if (string.contains("//")) {
                    String[] realUrls = string.split("//");
                    ItemPicture picture = new ItemPicture();
                    mDescribeUrlList.add("https://" + realUrls[1] + ".jpg");
                    picture.setPictureUrl("https://" + realUrls[1] + ".jpg");
                    mDescribePictureList.add(picture);
                }
            }

            mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < mDescribePictureList.size(); i++) {
                            Bitmap bitmap = Glide.with(ProductDetailsActivity.this)
                                    .asBitmap()
                                    .load(mDescribePictureList.get(i).getPictureUrl())
                                    .submit(960, 960)
                                    .get();
                            mDescribePictureList.get(i).setBitmap(bitmap);
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Message msg = Message.obtain();
                        msg.what = LOAD_BITMAP;
                        mHandler.sendMessage(msg);
                    }
                }
            });
            mThread.setDaemon(true);
            mThread.start();
        }
    }


    @Override
    public void onGetProductPriceSuccess(ProductPriceBean bean, ArrayList<ItemPicture> pics, ArrayList<ProductDetailsPackingBean> billBeans) {
        // TODO: 2021/6/9 需要保留 每次切换sku时走这里的逻辑
        binding.tvSellPrice.setText(String.format("%.2f", bean.getSellPrice()));
        if (bean.getMarketPrice() == 0 || bean.getMarketPrice() == bean.getSellPrice()) {
            binding.tvRmbGray.setVisibility(View.INVISIBLE);
            binding.tvPriceGray.setVisibility(View.INVISIBLE);
        } else {
            binding.tvPriceGray.setText(String.format("%.2f", bean.getMarketPrice()));
        }
        // 把sku图片取出来 带水印的和不带水印的 然后塞到mPageBean中
        if (bean.getPictureUrl() != null && bean.getPictureUrl().size() != 0) {
            ArrayList<String> skuPicList = new ArrayList<>();
            ArrayList<String> skuMarkPicList = new ArrayList<>();
            for (ItemPicture itemPicture : bean.getPictureUrl()) {
                skuPicList.add(itemPicture.getPictureUrl());
                skuMarkPicList.add(itemPicture.getWatermarkUrl());
            }
            mPageBean.setSkuPicList(skuPicList);
            mPageBean.setSkuMarkPicList(skuMarkPicList);
        }
        // 设置价格单位
        String skuUnitStr = mPageBean.getCurrentItemSkuInfo().getSkuUnit();
        if (!TextUtils.isEmpty(skuUnitStr)) {
            binding.tvSkuUnit.setText(String.format("/%s", skuUnitStr));
            binding.tvSkuUnitGray.setText(String.format("/%s", skuUnitStr));
        } else {
            binding.tvSkuUnit.setVisibility(View.GONE);
            binding.tvSkuUnitGray.setVisibility(View.GONE);
        }
        // 设置商品评分
        binding.fsvScore.setStar(bean.getScore());
        // 设置商品销量
        binding.itemSalesCounts.setText(String.valueOf(bean.getSaleNum()));
        // 设置包装清单
        billAdapter.update(billBeans, true);
        // 更新轮播图
//        for (String str : mPageBean.getSkuPicList()) {
//            Log.d(TAG, "切换sku更新普通图片 = " + str);
//        }
//        for (String str : mPageBean.getSkuMarkPicList()) {
//            Log.d(TAG, "切换sku更新水印图片 = " + str);
//        }
        binding.productPictureHD
                .setImages(mPageBean.getSkuPicList())
                .setImageLoader(GlideImageLoader.getInstance().setWidthHeight(1, 1)).start();
        // TODO: 2021/6/9 需要更新sku名称（应该是在点的时候通过查询本地ItemSkuInfoList更新了，不过推荐在请求成功后更新）
    }

    @Override
    public void onGetHotSaleSuccess(ArrayList<RecommendItemsBean> beans) {

    }

    @Override
    public void onGetCartCountSuccess(String count) {
        binding.tvCartCount.setText(count);
    }

    @Override
    public void getDelivery(DeliveryBean deliveryBean) {

    }



    @Override
    public void getAddress(ArrayList<AddressBean> addressBeansp, boolean showAddressPop) {

    }

    @Override
    public void getDefAddress(AddressBean bean) {
    }

    @Override
    public void getSkuSaleStocks(SkuStockBean bean) {
    }


    @Override
    public void onDestroy() {
        mThread = null;
        super.onDestroy();
    }

    @Override
    public void onProductInfoLoadCompleted(ProductDetailsPageBean finalProductBean) {
        mPageBean = finalProductBean;

        this.productDetailsBean = mPageBean.getProductDetailsBean();

//        ArrayList<String> l1 = mPageBean.getSkuPicList();
//        ArrayList<String> l2 = mPageBean.getSkuMarkPicList();
//        for (String str : l1) {
//            Log.d(TAG, "初始化的普通图片 = " + str);
//        }
//        for (String str : l2) {
//            Log.d(TAG, "初始化的水印图片 = " + str);
//        }


        // 设置轮播图
        binding.productPictureHD
                .setImages(mPageBean.getSkuPicList())
                .setImageLoader(GlideImageLoader.getInstance().setWidthHeight(1, 1)).start();

        // 设置价格
        binding.tvSellPrice.setText(mPageBean.getSellPrice());
        if (mPageBean.getPriceBean().getMarketPrice() == 0 || mPageBean.getPriceBean().getMarketPrice() == mPageBean.getPriceBean().getSellPrice()) {
            binding.tvRmbGray.setVisibility(View.INVISIBLE);
            binding.tvPriceGray.setVisibility(View.INVISIBLE);
            binding.tvSkuUnitGray.setVisibility(View.INVISIBLE);
        } else {
            binding.tvPriceGray.setText(mPageBean.getMarketPrice());
        }
        // 设置价格单位
        String skuUnitStr = mPageBean.getCurrentItemSkuInfo().getSkuUnit();
        if (!TextUtils.isEmpty(skuUnitStr)) {
            binding.tvSkuUnit.setText(String.format("/%s", skuUnitStr));
            binding.tvSkuUnitGray.setText(String.format("/%s", skuUnitStr));
        } else {
            binding.tvSkuUnit.setVisibility(View.GONE);
            binding.tvSkuUnitGray.setVisibility(View.GONE);
        }

        // 设置商品名称
        binding.tvItemName.setText(mPageBean.getProductName());

        // 设置商品评分
        binding.fsvScore.setStar(mPageBean.getProductScore());

        // 设置商品销量
        binding.itemSalesCounts.setText(mPageBean.getSaleCount());

        // 不同规格组合产生的名称
        if (TextUtils.isEmpty(mPageBean.getProductAttrName())) {
            binding.rlTypeChosen.setVisibility(View.GONE);
        } else {
            binding.tvSelectType.setText(mPageBean.getProductAttrName());
        }

        // 显示地址
        binding.tvAddressDefault.setText(mPageBean.getFullAddress());

        // 设置邮费
        if (!TextUtils.isEmpty(mPageBean.getDelivery())) {
            binding.rlDelivery.setVisibility(View.VISIBLE);
            binding.tvDelivery.setText(mPageBean.getDelivery());
        }

        // 设置店铺Logo
        Glide.with(this)
                .load("https:" + mPageBean.getShopLogo())
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.ratioImage);

        // 设置店铺名称
        binding.textView.setText(mPageBean.getShopName());

        // 设置店铺风险等级
        binding.tvCredit.setText(mPageBean.getShopSecurity());
        binding.ivCreditLevel.setBackground(mPageBean.getShopSecurityIcon());

        // 更新店铺推荐商品
        recommendItemsRecyclerAdapter.setMaterialType(mPageBean.getMaterialType());
        recommendItemsRecyclerAdapter.update(mPageBean.getRecommendItemList(), false);

        // 展示商品介绍图片
        showDescribePictures();

        // 更新包装清单
        billAdapter.update(mPageBean.getPackingList(), true);

        // 更新售后服务
        serviceAdapter.update(mPageBean.getServiceList(), true);

        // 更新推荐单位
        companyAdapter.update(mPageBean.getCompanyList(), true);

        // 设置收藏状态
        if (mPageBean.isCollected()) binding.ivCollect.setBackground(
                this.getResources().getDrawable(R.drawable.ic_collect_true));
        else binding.ivCollect.setBackground(
                this.getResources().getDrawable(R.drawable.ic_collection));

        // 设置购物车内商品数量
        binding.tvCartCount.setText(mPageBean.getCartCount());

        // 专用物资 不显示价格 只显示物资编码。后续的比价、查看历史价格、购物车能看到价格。
        if (mPageBean.getMaterialType().equals("1")) {
//            if (isNonePrice(mPageBean.getSellPrice())) {
            binding.llFlag.setVisibility(View.GONE);
            binding.llItemCode.setVisibility(View.VISIBLE);
            binding.tvItemCode.setText(mPageBean.getCurrentItemSkuInfo().getMaterialCode());
//            } else if (isNonePrice(mPageBean.getMarketPrice())) {
//                binding.tvRmbGray.setVisibility(View.GONE);
//                binding.tvPriceGray.setVisibility(View.GONE);
//                binding.tvSkuUnitGray.setVisibility(View.GONE);
//            }
        }

        // 流量统计
        statisticPresenter = new StatisticPresenterImpl(this, this);
        statisticPresenter.getVisitors("0", mPageBean.getShopId(), mPageBean.getSkuId());
    }

    private boolean isNonePrice(String price) {
        return price == null || price.equals("0.00") || price.equals("");
    }

    @Override
    public void onPopViewClick(ProductDetailsPopBean popBean) {

    }

    @Override
    public void onHaveNoSkuId() {
        ProductDetailsActivity.this.finish();
    }
}

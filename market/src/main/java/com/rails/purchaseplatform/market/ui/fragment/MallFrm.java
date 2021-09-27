package com.rails.purchaseplatform.market.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.appbar.AppBarLayout;
import com.orhanobut.logger.Logger;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.MarketIndexBean;
import com.rails.lib_data.bean.NavigationBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.contract.MarKetIndexPresenterImpl;
import com.rails.lib_data.contract.MarketIndexContract;
import com.rails.lib_data.contract.StatisticContract;
import com.rails.lib_data.contract.StatisticPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.BrandAdapter;
import com.rails.purchaseplatform.market.adapter.FloorTabAdapter;
import com.rails.purchaseplatform.market.adapter.NavigationAdapter;
import com.rails.purchaseplatform.market.adapter.ProductRecAdapter;
import com.rails.purchaseplatform.market.adapter.ProductRecSubAdapter;
import com.rails.purchaseplatform.market.databinding.FrmMallBinding;
import com.rails.purchaseplatform.market.ui.activity.RankActivity;
import com.rails.purchaseplatform.market.util.GlideImageLoader;
import com.rails.purchaseplatform.market.widget.CenterManger;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 商城首页
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/28
 */
public class MallFrm extends LazyFragment<FrmMallBinding>
        implements MarketIndexContract.MarketIndexView, PositionListener<ProductBean>, StatisticContract.StatisticView {

    //推荐商品
    private ProductRecAdapter recAdapter;
    //分类金刚区
    private NavigationAdapter categoryAdapter;


    //判断是点击还是滑动
    private boolean isScroll = true;
    private FloorTabAdapter tabAdapter;
    CenterManger tabManger;
    //品牌区
    private BrandAdapter brandAdapter;
    //热门商品
    private ProductRecSubAdapter hotAdapter;

    private MarketIndexBean indexBean;

    private MarketIndexContract.MarketIndexPresenter presenter;
    private StatisticContract.StatisticPresenter statisticPresenter;


    public MallFrm() {

    }

    @Override
    protected void loadData() {

        //设置banner的宽高
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) binding.banner.getLayoutParams();
        linearParams.width = ScreenSizeUtil.getScreenWidth(getActivity());
        linearParams.height = linearParams.width * 227 / 375;
        binding.banner.setLayoutParams(linearParams);

        //推荐分类列表
        categoryAdapter = new NavigationAdapter(getActivity());
        binding.categoryRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.HORIZONTAL, false, 0);
        binding.categoryRecycler.setAdapter(categoryAdapter);
        categoryAdapter.setListener(new PositionListener<NavigationBean>() {
            @Override
            public void onPosition(NavigationBean bean, int position) {
                String linkUrl = bean.getLinkUrl();
                Bundle bundle = new Bundle();
                if (TextUtils.isEmpty(linkUrl))
                    return;
                if (linkUrl.contains("cid")) {
                    try {
                        String cid = linkUrl.substring(linkUrl.lastIndexOf("=") + 1);
                        bundle.putString("cid", cid);
                        bundle.putString("mode", "form_main");
                        goLogin(null, ConRoute.MARKET.SEARCH_RESULT, bundle);
                    } catch (Exception e) {

                    }
                } else if (linkUrl.contains("specialMaterials")) {
                    try {
                        //materialType  0:通用  1：专用
                        bundle.putString("mode", "form_main");
                        bundle.putString("materialType", "1");
                        goLogin(null, ConRoute.MARKET.SEARCH_RESULT, bundle);
                    } catch (Exception e) {

                    }
                } else {
                    bundle.putString("url", linkUrl);
                    bundle.putString("title", bean.getNavigationBarName());
                    goLogin(null, ConRoute.WEB.WEB_COMMON, bundle);
                }

            }
        });


        //推荐品牌
        brandAdapter = new BrandAdapter(getActivity());
        binding.brandRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.HORIZONTAL, false, 0);
        binding.brandRecycler.setAdapter(brandAdapter);
        brandAdapter.setListener(new PositionListener<BrandBean>() {
            @Override
            public void onPosition(BrandBean bean, int position) {

                String shopId = bean.getShopid();
                Bundle bundle = new Bundle();
                if (TextUtils.isEmpty(shopId))
                    return;
                try {
                    bundle.putString("shopInfoId", shopId);
                    goLogin(null, ConRoute.MARKET.SHOP_DETAILS, bundle);
                } catch (Exception e) {

                }
            }
        });


        hotAdapter = new ProductRecSubAdapter(getActivity());
        hotAdapter.setListener(this);
        binding.hotRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.HORIZONTAL, false, 0);
        binding.hotRecycler.setAdapter(hotAdapter);


        //分类标签
        tabAdapter = new FloorTabAdapter(getActivity());
        tabManger = new CenterManger(getActivity(), RecyclerView.HORIZONTAL, false);
        binding.magic.setLayoutManager(tabManger);
        binding.magic.setAdapter(tabAdapter);


        tabAdapter.setListener(new PositionListener<ProductRecBean>() {

            @Override
            public void onPosition(ProductRecBean bean, int position) {
                tabManger.smoothScrollToPosition(binding.magic, new RecyclerView.State(), position);
                isScroll = false;
                ((LinearLayoutManager) binding.recycler.getLayoutManager()).scrollToPositionWithOffset(position, 0);


            }
        });
        binding.recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScroll = true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isScroll) {
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int position = manager.findFirstVisibleItemPosition();
                    tabAdapter.setLastBean(position);
                    binding.magic.smoothScrollToPosition(position);
                }
            }
        });


        //推荐商品列表
        recAdapter = new ProductRecAdapter(getActivity());
        recAdapter.setListener(this);
        recAdapter.setMulPositionListener((bean, position, params) -> {
            if (indexBean != null) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", ++position);
                    startIntent(RankActivity.class, bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.recycler.addItemDecoration(new SpaceDecoration(getActivity(), 10, R.color.bg));
        binding.recycler.setAdapter(recAdapter);


        int tabHeight = -ScreenSizeUtil.dp2px(getActivity(), 100);
        binding.bar.addOnOffsetChangedListener((appBarLayout, i) -> {
            binding.layoutHeader.setBackgroundColor(getResources().getColor(i < tabHeight ? R.color.bg_blue : R.color.trans));


            if (i < -800) {
                binding.imgTop.setVisibility(View.VISIBLE);
            } else
                binding.imgTop.setVisibility(View.GONE);

        });

        presenter = new MarKetIndexPresenterImpl(getActivity(), this);
        statisticPresenter = new StatisticPresenterImpl(getActivity(), this);
        //通用 首页请求此接口时 itemShopId skuId 为null
        statisticPresenter.getVisitors("0", null, null);

        onRefresh();
    }


    /**
     * 设置Banner
     *
     * @param banners
     */
    void setBanners(ArrayList<BannerBean> banners) {
        if (banners == null)
            return;
        if (banners.isEmpty())
            return;

        ArrayList<String> imgs = new ArrayList<>();
        for (BannerBean bean : banners)
            imgs.add(bean.getPictureUrl());

        binding.banner.setImages(imgs).setDelayTime(3000).setImageLoader(new GlideImageLoader()).start();

    }


    /**
     * 数据刷新操作
     */
    void onRefresh() {
        binding.rlRecycler.setEnableLoadMore(false);
        binding.rlRecycler.setOnRefreshListener(refreshLayout -> {
            binding.rlRecycler.finishRefresh();
            presenter.getMarketIndexInfo(false, false);
        });
        presenter.getMarketIndexInfo(true, true);
    }

    @Override
    protected void loadPreVisitData() {
        StatusBarUtil.StatusBarMode(getActivity(), android.R.color.transparent);
    }


    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    @Override
    public void onError(ErrorBean errorBean) {

    }


    @Override
    public void getHotProducts(ArrayList<ProductBean> beans) {

    }

    @Override
    public void getIndexInfo(MarketIndexBean bean) {
        indexBean = bean;
        brandAdapter.update(bean.getBrandBeans(), true);
        hotAdapter.update(bean.getHotBeans(), true);
        tabAdapter.updateData(bean.getRecBeans(), true);
        recAdapter.update(bean.getRecBeans(), true);
        categoryAdapter.update(bean.getCategorySubBeans(), true);
        setBanners(bean.getBannerBeans());

    }

    @Override
    public void getBrands(ArrayList<BrandBean> brandBeans, boolean hasMore, boolean isClear) {

    }

    @Override
    public void getFloorProducts(ArrayList<ProductBean> productBeans, boolean hasMore, boolean isClear) {

    }

    @Override
    protected void onClick() {
        super.onClick();

        // 搜索页面跳转
        binding.etSearch.setOnClickListener(v -> goLogin(null, ConRoute.COMMON.SEARCH, null));

        binding.imgTop.setOnClickListener(v -> {
            CoordinatorLayout.Behavior behavior =
                    ((CoordinatorLayout.LayoutParams) binding.bar.getLayoutParams()).getBehavior();
            if (behavior instanceof AppBarLayout.Behavior) {
                AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
                int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
                if (topAndBottomOffset != 0) {
                    appBarLayoutBehavior.setTopAndBottomOffset(0);
                    binding.recycler.scrollToPosition(0);
//                    imgTop.setVisibility(View.GONE);
                }
            }

        });

        binding.imgMsg.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("url", ConRoute.WEB_URL.MSG);
            goLogin(null, ConRoute.WEB.WEB_MSG, bundle);
        });

        binding.lrTitle.setOnClickListener(v -> {
            if (indexBean != null) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", 0);
                    startIntent(RankActivity.class, bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onPosition(ProductBean bean, int position) {
        if (TextUtils.isEmpty(bean.getItemId())) {
            ToastUtil.showCenter(this.getActivity(), "商品不存在或已下架");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("itemId", bean.getItemId());
        goLogin(null, ConRoute.MARKET.PRODUCT_DETAIL, bundle);
    }


    /**
     * token是否存在
     *
     * @return
     */
    private boolean hasToken() {
        String token = PrefrenceUtil.getInstance(getActivity()).getString(ConShare.TOKEN, "");
        return !TextUtils.isEmpty(token);
    }


    /**
     * 跳转页面
     *
     * @param cls
     * @param aPath
     */
    private void goLogin(Class cls, String aPath, Bundle bundle) {
        if (!hasToken()) {
            ARouter.getInstance()
                    .build(ConRoute.USER.LOGIN)
                    .navigation();
        } else {
            if (cls == null) {
                ARouter.getInstance().build(aPath).with(bundle).navigation();
            } else
                startIntent(cls);
        }
    }


    /**
     * 获取v的高度
     *
     * @param v
     */
    private int getViewHeight(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int y = location[1]; // view距离window 顶边的距离（即y轴方向）
        return y;
    }


}

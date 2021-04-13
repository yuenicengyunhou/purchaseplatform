package com.rails.purchaseplatform.market.ui.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.CategorySubBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.contract.MarKetIndexPresenterImpl;
import com.rails.lib_data.contract.MarketIndexContract;
import com.rails.lib_data.contract.ProductContract;
import com.rails.lib_data.contract.ProductPresenterImpl;
import com.rails.lib_data.h5.ConstantH5;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.ConShare;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.AlphaScrollView;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.common.widget.SpaceGirdWeightDecoration;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.BrandAdapter;
import com.rails.purchaseplatform.market.adapter.CategorySubAdapter;
import com.rails.purchaseplatform.market.adapter.ProductHotAdapter;
import com.rails.purchaseplatform.market.adapter.ProductRecAdapter;
import com.rails.purchaseplatform.market.databinding.FrmMallBinding;
import com.rails.purchaseplatform.market.ui.activity.ProductDetailsActivity;
import com.rails.purchaseplatform.market.ui.activity.SearchResultActivity;
import com.rails.purchaseplatform.market.ui.activity.ShopDetailActivity;
import com.rails.purchaseplatform.market.util.GlideImageLoader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 商城首页
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/28
 */
public class MallFrm extends LazyFragment<FrmMallBinding>
        implements MarketIndexContract.MarketIndexView, PositionListener<ProductBean>,
        ProductContract.ProductView, ConstantH5.Messages {


    private ProductRecAdapter recAdapter;
    private CategorySubAdapter categoryAdapter;
    private BrandAdapter brandAdapter;
    private ProductHotAdapter hotAdapter;

    private MarketIndexContract.MarketIndexPresenter presenter;
    private ProductContract.ProductPresenter productPresenter;


    @Override
    protected void loadData() {

        //设置banner的宽高
        CardView.LayoutParams linearParams = (CardView.LayoutParams) binding.banner.getLayoutParams();
        linearParams.width = ScreenSizeUtil.getScreenWidth(getActivity()) - ScreenSizeUtil.dp2px(getActivity(), 32);
        linearParams.height = linearParams.width * 13 / 35;
        binding.banner.setLayoutParams(linearParams);

        //推荐分类列表
        categoryAdapter = new CategorySubAdapter(getActivity());
        binding.categoryRecycler.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 5);
        binding.categoryRecycler.setAdapter(categoryAdapter);
        categoryAdapter.setListener(new PositionListener<CategorySubBean>() {
            @Override
            public void onPosition(CategorySubBean bean, int position) {
                goLogin(SearchResultActivity.class, "", null);
            }
        });


        //推荐品牌
        brandAdapter = new BrandAdapter(getActivity());
        binding.brandRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.HORIZONTAL, false, 0);
        binding.brandRecycler.setAdapter(brandAdapter);
        brandAdapter.setListener(new PositionListener<BrandBean>() {
            @Override
            public void onPosition(BrandBean bean, int position) {
                goLogin(ShopDetailActivity.class, "", null);
            }
        });


        //推荐商品列表
        recAdapter = new ProductRecAdapter(getActivity());
        recAdapter.setListener(this);
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.recycler.addItemDecoration(new SpaceDecoration(getActivity(), 10, R.color.bg));
        binding.recycler.setAdapter(recAdapter);


        hotAdapter = new ProductHotAdapter(getActivity(), 1);
        hotAdapter.setListener(new PositionListener<ProductBean>() {
            @Override
            public void onPosition(ProductBean bean, int position) {
                goLogin(ProductDetailsActivity.class, "", null);
            }
        });
        binding.hotRecycler.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 2);
        binding.hotRecycler.setAdapter(hotAdapter);


        binding.scroll.setScrollViewListener(new AlphaScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(AlphaScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (y > 800) {
                    binding.imgTop.setVisibility(View.VISIBLE);
                } else
                    binding.imgTop.setVisibility(View.GONE);
            }
        });

        presenter = new MarKetIndexPresenterImpl(getActivity(), this);
        productPresenter = new ProductPresenterImpl(getActivity(), this);


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
        binding.rlRecycler.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                binding.rlRecycler.finishRefresh();
                presenter.getRectProducts(false);
            }
        });

        presenter.getRectProducts(true);
        presenter.getBanners();
        presenter.getRecCategorys();
        presenter.getBrands();
        productPresenter.getHotProducts(false, 1);
    }

    @Override
    protected void loadPreVisitData() {
        StatusBarUtil.StatusBarMode(getActivity(), R.color.bg_blue);
    }


    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    @Override
    public void onError(ErrorBean errorBean) {

    }


    public boolean getLocalVisibleRect(Context context, View view) {
        int screenWidth = ScreenSizeUtil.getScreenWidth(context);
        int screenHeight = ScreenSizeUtil.getScreenHeight(context);
        Rect rect = new Rect(0, 0, screenWidth, screenHeight);
        int[] location = new int[2];
        view.getLocationInWindow(location);
        view.setTag(location[1]);//存储y方向的位置
        if (view.getLocalVisibleRect(rect)) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void getRecProducts(ArrayList<ProductRecBean> beans) {
        recAdapter.update(beans, true);
    }

    @Override
    public void getBanners(ArrayList<BannerBean> bannerBeans) {
        setBanners(bannerBeans);
    }

    @Override
    public void getBrands(ArrayList<BrandBean> brandBeans) {
        brandAdapter.update(brandBeans, true);
    }

    @Override
    public void getRecCategorys(ArrayList<CategorySubBean> beans) {
        categoryAdapter.update(beans, true);
    }

    @Override
    protected void onClick() {
        super.onClick();

        // 搜索页面跳转
        binding.etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin(null, ConRoute.COMMON.SEARCH, null);
            }
        });

        binding.imgTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.scroll.smoothScrollTo(0, 0);
            }
        });

        binding.imgMsg.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("url", ConRoute.WEB_URL.MSG);
            goLogin(null, ConRoute.WEB.WEB_MSG, bundle);
        });
    }

    @Override
    public void onPosition(ProductBean bean, int position) {
        // TODO: 2021/3/23 跳转商品详情
        goLogin(ProductDetailsActivity.class, "", null);
    }

    @Override
    public void getHotProducts(ArrayList<ProductBean> productBeans, boolean hasMore, boolean isClear) {
        hotAdapter.update(productBeans, true);
    }


    /**
     * token是否存在
     *
     * @return
     */
    private boolean hasToken() {
        String token = PrefrenceUtil.getInstance(getActivity()).getString(ConShare.TOKEN, "");
        if (TextUtils.isEmpty(token))
            return false;
        else
            return true;
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


}

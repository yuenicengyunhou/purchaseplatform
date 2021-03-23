package com.rails.purchaseplatform.market.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.orhanobut.logger.Logger;
import com.rails.lib_data.bean.RecommendItemsBean;
import com.rails.lib_data.contract.RecommendItemsContract;
import com.rails.lib_data.contract.RecommendItemsPresenterImpl;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.RatioImage;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.RecommendItemsRecyclerAdapter;
import com.rails.purchaseplatform.market.adapter.SectionsPagerAdapter;
import com.rails.purchaseplatform.market.databinding.ActivityProductDetailsBinding;
import com.rails.purchaseplatform.market.ui.fragment.PlaceholderFragment;
import com.rails.purchaseplatform.market.web.PackageListPage4Js;
import com.rails.purchaseplatform.market.web.ProductInfoPage4Js;
import com.rails.purchaseplatform.market.web.RecommendPage4Js;
import com.rails.purchaseplatform.market.web.ServicePage4Js;

import java.util.ArrayList;

public class ProductDetailsActivity extends BaseErrorActivity<ActivityProductDetailsBinding> implements RecommendItemsContract.RecommendItemsView, NestedScrollView.OnScrollChangeListener {
    final private String TAG = ProductDetailsActivity.class.getSimpleName();

    private RecommendItemsRecyclerAdapter recommendItemsRecyclerAdapter;
    private RecommendItemsContract.RecommendItemsPresenter recommendItemsPresenter;

    final private String BASE_URL = "http://172.28.20.109:3000/";
    //    final private String BASE_URL = "https://crmall.rails.cn/purchase-android-web/";
    final private String DETAILS = "productInfo";
    final private String LIST = "packingList";
    final private String SERVICE = "serviceOrPartner?service=0";
    final private String RECOMMEND = "serviceOrPartner?service=1";
    final private String[] TAB_URLS = {
            BASE_URL + DETAILS,
            BASE_URL + LIST,
            BASE_URL + SERVICE,
            BASE_URL + RECOMMEND
    };


    @SuppressLint("JavascriptInterface")
    @Override
    protected void initialize(Bundle bundle) {
        recommendItemsRecyclerAdapter = new RecommendItemsRecyclerAdapter(this);
        recommendItemsPresenter = new RecommendItemsPresenterImpl(this, this);
        binding.recyclerRecommendItems.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 3);
        binding.recyclerRecommendItems.setAdapter(recommendItemsRecyclerAdapter);
        recommendItemsPresenter.getRecommendItems(false, 1);


        WebView[] WEB_VIEWS = {
                binding.webProductInfo,
                binding.webPackageList,
                binding.webService,
                binding.webRecommend
        };

        Object[] OBJECTS = {
                new ProductInfoPage4Js(this),
                new PackageListPage4Js(this),
                new ServicePage4Js(this),
                new RecommendPage4Js(this)
        };

        String[] FLAGS = {
                "PRODUCT_INFO",
                "PACKAGE_LIST",
                "SERVICE",
                "RECOMMEND"
        };

        for (int i = 0; i < WEB_VIEWS.length; i++) {
            setWeb(WEB_VIEWS[i]);
            WEB_VIEWS[i].loadUrl(TAB_URLS[i]);
            WEB_VIEWS[i].addJavascriptInterface(OBJECTS[i], FLAGS[i]);
        }


        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        binding.riItemPhoto.measure(width, height);
        Log.d(TAG, "getMeasuredWidth = " + binding.riItemPhoto.getMeasuredWidth());
        Log.d(TAG, "getMeasuredHeight = " + binding.riItemPhoto.getMeasuredHeight());

//        binding.tabDetails.selectTab(binding.tabDetails.getTabAt(3));

        binding.tabDetails.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (binding.tabDetails.getTabAt(0).equals(tab)) {
                    binding.nestedScrollView.scrollTo(0, 200);
                } else if (binding.tabDetails.getTabAt(1).equals(tab)) {
                    binding.nestedScrollView.scrollTo(0, 400);
                } else if (binding.tabDetails.getTabAt(2).equals(tab)) {
                    binding.nestedScrollView.scrollTo(0, 600);
                } else if (binding.tabDetails.getTabAt(3).equals(tab)) {
                    binding.nestedScrollView.scrollTo(0, 800);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.d(TAG, "scrollX = " + scrollX + ", scrollY = " + scrollY + ", oldScrollX = " + oldScrollX + ", oldScrollY = " + oldScrollY);
                binding.tabDetails.selectTab(binding.tabDetails.getTabAt(2));
                getLocalVisibleRect(ProductDetailsActivity.this, binding.webProductInfo);
            }
        });
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
    public void getRecommendItems(ArrayList<RecommendItemsBean> recommendItemsBeans, boolean hasMore, boolean isClear) {
        recommendItemsRecyclerAdapter.update(recommendItemsBeans, false);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        Log.d("HELLO = scrollX", String.valueOf(scrollX));
        Log.d("HELLO = scrollY", String.valueOf(scrollY));
        Log.d("HELLO = oldScrollX", String.valueOf(oldScrollX));
        Log.d("HELLO = oldScrollY", String.valueOf(oldScrollY));


    }

    private void setWeb(WebView view) {
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
    }


    public void getLocalVisibleRect(Context context, View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        view.setTag(location[1]);//存储y方向的位置
    }


}

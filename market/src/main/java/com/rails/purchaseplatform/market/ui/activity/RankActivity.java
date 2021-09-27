package com.rails.purchaseplatform.market.ui.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.CategorySubBean;
import com.rails.lib_data.bean.MarketIndexBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.contract.MarKetIndexPresenterImpl;
import com.rails.lib_data.contract.MarketIndexContract;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.adapter.ViewPageAdapter;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.utils.BASE64;
import com.rails.purchaseplatform.framwork.utils.file.FileCacheUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.ActivityMarketRankBinding;
import com.rails.purchaseplatform.market.ui.fragment.RankFragment;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * 商品排行榜列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/6/7
 */
public class RankActivity extends BaseErrorActivity<ActivityMarketRankBinding> {

    private ViewPageAdapter viewPageAdapter;
    private MarketIndexBean indexBean;
    private int position = 0;


    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        position = extras.getInt("position");
    }

    @Override
    protected void initialize(Bundle bundle) {
        indexBean = (MarketIndexBean) FileCacheUtil.getInstance(this).readObject("mallInfo");
        if (indexBean != null) {
            ProductRecBean recBean = new ProductRecBean();
            recBean.setFirstCategoryName("热销品牌");
            recBean.setFirstCategoryId("");
            indexBean.getRecBeans().add(0, recBean);

            ProductRecBean recBean1 = new ProductRecBean();
            recBean1.setFirstCategoryName("热销商品");
            recBean1.setFirstCategoryId("1");
            indexBean.getRecBeans().add(1, recBean1);


            initPager(indexBean.getRecBeans());
        }
    }

    @Override
    protected int getColor() {
        return android.R.color.transparent;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    /**
     * 初始化pageradapter
     */
    private void initPager(ArrayList<ProductRecBean> beans) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        Fragment fragment;
        for (ProductRecBean bean : beans) {
            fragment = RankFragment.getInstance(bean.getFirstCategoryId());
            fragments.add(fragment);
        }


        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewPager.setAdapter(viewPageAdapter);
        binding.viewPager.setOffscreenPageLimit(beans.size());
        viewPageAdapter.update(fragments, true);


        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(false);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return beans.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {

                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(getResources().getColor(R.color.white));
                colorTransitionPagerTitleView.setSelectedColor(getResources().getColor(R.color.font_yellow));
                colorTransitionPagerTitleView.setTextSize(15f);
                colorTransitionPagerTitleView.setText(beans.get(index).getFirstCategoryName());
                colorTransitionPagerTitleView.setOnClickListener(view -> binding.viewPager.setCurrentItem(index));
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(getResources().getColor(R.color.font_yellow));
                return indicator;
            }
        });
        binding.indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(binding.indicator, binding.viewPager);
        binding.viewPager.setCurrentItem(position);
    }


    @Override
    protected void onClick() {
        super.onClick();

        binding.imgLeft.setOnClickListener(v -> {
            finish();
        });

        binding.imgRight.setOnClickListener(v -> {
            ARouter.getInstance().build(ConRoute.WEB.WEB_RANK_QUESTION).navigation();
        });
    }
}

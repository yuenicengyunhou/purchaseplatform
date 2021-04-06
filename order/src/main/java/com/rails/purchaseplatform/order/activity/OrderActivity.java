package com.rails.purchaseplatform.order.activity;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.adapter.ViewPageAdapter;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ActivityOrderBinding;
import com.rails.purchaseplatform.order.fragment.OrderFragment;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;

/**
 * 采购单列表页面
 * <p>
 * -1- 列表(采购单订单)和子列表(订单内商品)
 * -2- 搜索
 * -3- 筛选
 * -4- PopupWindow切换搜索类型
 * -5- Tab切换我的采购单和全部采购单
 */
//@Route(path = ConRoute.ORDER.ORDER_MAIN)
public class OrderActivity extends BaseErrorActivity<ActivityOrderBinding> {

    private ArrayList<Fragment> fragments;
    private ViewPageAdapter viewPageAdapter;


    @Override
    protected void initialize(Bundle bundle) {
        String[] tabs = getResources().getStringArray(R.array.order_list_tab);
        initPager(tabs);
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
        binding.ibBack.setOnClickListener(v -> finish());
        binding.ibFilter.setOnClickListener(v -> Toast.makeText(this, "暂时没有内容哦", Toast.LENGTH_SHORT).show());
    }


    /**
     * 初始化pageradapter
     */
    private void initPager(String... tabs) {
        fragments = new ArrayList<>();
        Fragment fragment;
        for (int i = 0; i < tabs.length; i++) {
            fragment = OrderFragment.getInstance(i);
            fragments.add(fragment);
        }

        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewpager.setAdapter(viewPageAdapter);
        binding.viewpager.setOffscreenPageLimit(tabs.length);
        viewPageAdapter.update(fragments, true);


        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabs == null ? 0 : tabs.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {

                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(getResources().getColor(R.color.font_black_light));
                colorTransitionPagerTitleView.setSelectedColor(getResources().getColor(R.color.font_blue));
                colorTransitionPagerTitleView.setTextSize(14f);
                colorTransitionPagerTitleView.setText(tabs[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.viewpager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(getResources().getColor(R.color.bg_blue));
                return indicator;
            }
        });
        binding.indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(binding.indicator, binding.viewpager);
        binding.viewpager.setCurrentItem(0);
    }


}
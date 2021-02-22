package com.rails.purchaseplatform.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.rails.purchaseplatform.R;
import com.rails.purchaseplatform.common.adapter.ViewPageAdapter;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.databinding.FrmIndexBinding;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * 首页
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/28
 */
public class IndexFrm extends LazyFragment<FrmIndexBinding> {



    private ArrayList<Fragment> fragments;
    private ViewPageAdapter viewPageAdapter;


    @Override
    protected void loadData() {


        binding.bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (i > 0) {
                   binding.rlRecycler.setEnabled(true);
                } else {
                    binding.rlRecycler.setEnabled(false);

                }

                boolean isVisit = getLocalVisibleRect(getActivity(), binding.rvStep);
                if (Math.abs(i) > 500 && !isVisit) {
                    binding.lineBar.setVisibility(View.VISIBLE);
                    StatusBarUtil.StatusBarLightMode(getActivity());
                } else {
                    binding.lineBar.setVisibility(View.GONE);
                    StatusBarUtil.StatusBarMode(getActivity(), R.color.bg_blue);
                }
            }
        });


        onRefresh();

        String[] tabs = getResources().getStringArray(R.array.aution_tab);
        initPager(tabs);
    }


    /**
     * 数据刷新操作
     */
    void onRefresh() {
        binding.rlRecycler.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新数据
                binding.rlRecycler.setRefreshing(false);
            }
        });
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

    @Override
    public void onFailure(String errorMsg) {

    }


    /**
     * 初始化pageradapter
     */
    private void initPager(String[] tabs) {

        fragments = new ArrayList<>();
        Fragment fragment;
        for (String tab : tabs) {
            fragment = new AutionFragment();
            Bundle bundle = new Bundle();
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        viewPageAdapter = new ViewPageAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPageAdapter.update(fragments, true);
        binding.viewPager.setAdapter(viewPageAdapter);
        binding.viewPager.setOffscreenPageLimit(tabs.length);

        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(false);
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
                        binding.viewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(getResources().getColor(android.R.color.transparent));
                return indicator;
            }
        });
        binding.magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(binding.magicIndicator, binding.viewPager);
        binding.viewPager.setCurrentItem(0);
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
}

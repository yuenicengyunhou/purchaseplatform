package com.rails.purchaseplatform.address.ui.pop;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.SearchResultBean;
import com.rails.purchaseplatform.address.R;
import com.rails.purchaseplatform.address.adapter.CityAdapter;
import com.rails.purchaseplatform.address.adapter.NavigatorAdapter;
import com.rails.purchaseplatform.address.databinding.PopAddressAreaBinding;
import com.rails.purchaseplatform.address.ui.AreaFragment;
import com.rails.purchaseplatform.common.adapter.ViewPageAdapter;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 筛选
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/29
 */
public class AreaPop extends BasePop<PopAddressAreaBinding> {

    private ArrayList<Fragment> fragments;
    private ViewPageAdapter viewPageAdapter;
    private NavigatorAdapter navigatorAdapter;

    @Override
    protected void initialize(Bundle bundle) {

        initPager();
        onClick();
    }


    void onClick() {
        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dismiss();
                Fragment fragment = new AreaFragment();
                viewPageAdapter.updateAdd(fragment);
            }
        });
    }


    /**
     * 初始化pageradapter
     */
    private void initPager() {
        fragments = new ArrayList<>();
        AreaFragment fragment = new AreaFragment();
        fragment.setListener(new AreaFragment.AreaListener() {
            @Override
            public void onPosition(String string) {
                navigatorAdapter.modify(string,0);

            }
        });
        fragments.add(fragment);

        viewPageAdapter = new ViewPageAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewpager.setAdapter(viewPageAdapter);

        navigatorAdapter = new NavigatorAdapter(getActivity(), binding.viewpager);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(false);
        commonNavigator.setAdapter(navigatorAdapter);
        binding.indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(binding.indicator, binding.viewpager);
        binding.viewpager.setCurrentItem(0);


        viewPageAdapter.update(fragments, true);
        ArrayList<String> strs = new ArrayList<>();
        strs.add("请选择");
        navigatorAdapter.update(strs);
    }


}

package com.rails.purchaseplatform.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rails.purchaseplatform.R;
import com.rails.purchaseplatform.common.adapter.ViewPageAdapter;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.databinding.FrmMsgBinding;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * 消息fragment
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/28
 */
public class MsgFrm extends LazyFragment<FrmMsgBinding> {


    private ArrayList<Fragment> fragments;
    private ViewPageAdapter viewPageAdapter;

    @Override
    protected void loadData() {


        String[] tabs = getResources().getStringArray(R.array.msgs_tab);
        int[] res = {R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
        initPager(tabs, res);
    }

    @Override
    protected void loadPreVisitData() {
        StatusBarUtil.StatusBarLightMode(getActivity());
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
    private void initPager(String[] tabs, int[] res) {
        fragments = new ArrayList<>();
        Fragment fragment;
        for (int i = 0; i < tabs.length; i++) {
            fragment = new MsgsubFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("status", i);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }

        viewPageAdapter = new ViewPageAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewPager.setAdapter(viewPageAdapter);
        binding.viewPager.setOffscreenPageLimit(tabs.length);
        viewPageAdapter.update(fragments, true);


        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabs == null ? 0 : tabs.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {

                CommonPagerTitleView titleView = new CommonPagerTitleView(getActivity());
                titleView.setContentView(R.layout.item_tab);
                ImageView img = titleView.findViewById(R.id.tv_res);
                TextView name = titleView.findViewById(R.id.tv_name);
                img.setImageResource(res[index]);
                name.setText(tabs[index]);
                titleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        name.setTextColor(getResources().getColor(R.color.font_blue));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        name.setTextColor(getResources().getColor(R.color.font_black_light));
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                    }
                });

                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.viewPager.setCurrentItem(index);
                    }
                });

                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
//                LinePagerIndicator indicator = new LinePagerIndicator(context);
//                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
//                indicator.setColors(getResources().getColor(R.color.bg_blue));
//                return indicator;
                return null;
            }
        });
        binding.indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(binding.indicator, binding.viewPager);
        binding.viewPager.setCurrentItem(0);
    }

}

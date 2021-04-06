package com.rails.purchaseplatform.address.adapter;

import android.content.Context;
import android.view.View;

import com.rails.purchaseplatform.address.R;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;

import androidx.viewpager.widget.ViewPager;

/**
 * @author： sk_comic@163.com
 * @date: 2021/4/6
 */
public class NavigatorAdapter extends CommonNavigatorAdapter {

    private Context mContext;
    private ViewPager viewPager;
    private ArrayList<String> tabs;

    public NavigatorAdapter(Context mContext, ViewPager viewPager) {
        this.mContext = mContext;
        this.viewPager = viewPager;
        tabs = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, int index) {
        ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
        colorTransitionPagerTitleView.setNormalColor(mContext.getResources().getColor(R.color.font_black_light));
        colorTransitionPagerTitleView.setSelectedColor(mContext.getResources().getColor(R.color.font_blue));
        colorTransitionPagerTitleView.setTextSize(14f);
        colorTransitionPagerTitleView.setText(tabs.get(index));
        colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(index);
            }
        });
        return colorTransitionPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        return null;
    }

    public void updateAdd(String tab) {
        this.tabs.add(tab);
        notifyDataSetChanged();
    }


    public void modify(String tab,int index){
        tabs.set(index,tab);
        notifyDataSetChanged();
    }


    public void update(ArrayList<String> fragments) {
        this.tabs.addAll(fragments);
        notifyDataSetChanged();
    }
}

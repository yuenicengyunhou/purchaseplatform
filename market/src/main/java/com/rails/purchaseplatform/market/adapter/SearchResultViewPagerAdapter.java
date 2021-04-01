package com.rails.purchaseplatform.market.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SearchResultViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;


    public SearchResultViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public SearchResultViewPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<Fragment> fragments) {
        super(fm, behavior);
        mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    /**
     * 设置适配器数据
     *
     * @param dataList data
     *                 是否需要清空list然后在加载数据
     */
    public void update(ArrayList<Fragment> dataList, Boolean isClear) {
        if (isClear) {
            mFragments.clear();
        }
        if (dataList != null) {
            mFragments.addAll(dataList);
        }
        notifyDataSetChanged();
    }
}

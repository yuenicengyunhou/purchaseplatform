package com.rails.purchaseplatform.common.adapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * 项目主页adapter
 * author wangchao
 * email  wangchao@chengantech.com
 * date   2017/12/20
 */

public class ViewPageAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments = new ArrayList<>();
    String[] titles = new String[]{};


    /**
     * 设置适配器数据
     *
     * @param dataList data
     *                 是否需要清空list然后在加载数据
     */
    public void update(ArrayList<Fragment> dataList, Boolean isClear) {
        if (isClear) {
            fragments.clear();
        }
        if (dataList != null) {
            fragments.addAll(dataList);
        }
        notifyDataSetChanged();
    }


    public void updateAdd(Fragment frm) {
        fragments.add(frm);
        notifyDataSetChanged();
    }


    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }


    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior, String[] titles) {
        super(fm, behavior);
        if (titles != null)
            this.titles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    public ArrayList<Fragment> getFragments() {
        return fragments;
    }


    /**
     * 移除超越标记的fragment
     * @param position
     */
    public void delAbovePosition(int position) {
        for (int i = fragments.size()-1; i >position; i--) {
                fragments.remove(i);
        }
        notifyDataSetChanged();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (titles.length > 0)
            return titles[position];
        else
            return super.getPageTitle(position);
    }


}

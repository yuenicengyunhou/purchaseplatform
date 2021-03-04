package com.rails.purchaseplatform.market.ui.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.rails.lib_data.bean.CategoryBean;
import com.rails.lib_data.bean.CategoryRootBean;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.bean.BusEvent;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.CategoryAdapter;
import com.rails.purchaseplatform.market.databinding.FragmentCategorySubBinding;
import com.rails.purchaseplatform.market.widget.MoreLinearLayout;
import com.rails.purchaseplatform.market.widget.MoreScrollView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 分类子列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/26
 */
public class CategorySubFragment extends LazyFragment<FragmentCategorySubBinding> {

    private static final String ARG_PARAM = "bean";

    //判断是点击还是滑动
    private boolean isScroll = true;

    private CategoryRootBean bean;
    private CategoryAdapter adapter;


    public static CategorySubFragment newInstance(CategoryRootBean bean) {
        CategorySubFragment fragment = new CategorySubFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, bean);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        bean = (CategoryRootBean) getArguments().getSerializable(ARG_PARAM);
    }


    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected void loadData() {
        adapter = new CategoryAdapter(getActivity());
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.recycler.addItemDecoration(new SpaceDecoration(getActivity(), 10, R.color.white));
        binding.recycler.setAdapter(adapter);

        if (bean != null) {
            initPager((ArrayList<CategoryBean>) bean.getSecondPlatformCategoryList());
            adapter.update((ArrayList<CategoryBean>) bean.getSecondPlatformCategoryList(), true);
        }

    }

    @Override
    protected void loadPreVisitData() {

    }


    /**
     * 初始化pageradapter
     */
    private void initPager(ArrayList<CategoryBean> tabs) {
        binding.magic.setTabTextColors(Color.GREEN, Color.RED);
        TextView lable;
        for (CategoryBean bean : tabs) {
            TabLayout.Tab tab = binding.magic.newTab();
            lable = new TextView(getActivity());
            tab.setCustomView(lable);
            lable.setText(bean.getName());
            int position = tabs.indexOf(bean);
            lable.setTextColor(getResources().getColor(position == 0 ? R.color.font_blue : R.color.font_black));
            lable.setTypeface(Typeface.defaultFromStyle(position == 0 ? Typeface.BOLD : Typeface.NORMAL));
            tab.setTag(position);
            binding.magic.addTab(tab);
            lable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tab.select();
                    isScroll = false;
                    ((LinearLayoutManager) binding.recycler.getLayoutManager()).scrollToPositionWithOffset((Integer) tab.getTag(), 0);
                }
            });
        }

        binding.magic.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView lable = (TextView) tab.getCustomView();
                lable.setTextColor(getResources().getColor(R.color.font_blue));
                lable.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView lable = (TextView) tab.getCustomView();
                lable.setTextColor(getResources().getColor(R.color.font_black));
                lable.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


//        binding.more.setMoreListener(new MoreLinearLayout.OnMoreListener() {
//            @Override
//            public void onMore() {
//                EventBus.getDefault().post(new BusEvent("onMore", "CategoryFragment"));
//            }
//
//            @Override
//            public void onChange(int l, int t, int oldl, int oldt) {
//
//            }
//        });

        binding.recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isScroll) {
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int position = manager.findFirstVisibleItemPosition();
                    binding.magic.getTabAt(position).select();
                }
                isScroll = true;

            }
        });

    }


    private void setClick(TabLayout.Tab tab) {

    }


}
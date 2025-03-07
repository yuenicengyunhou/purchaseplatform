package com.rails.purchaseplatform.market.ui.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayout;
import com.rails.lib_data.bean.CategoryBean;
import com.rails.lib_data.bean.CategoryRootBean;
import com.rails.lib_data.bean.CategorySubBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.CategoryAdapter;
import com.rails.purchaseplatform.market.databinding.FragmentCategorySubBinding;

import java.util.ArrayList;


/**
 * 分类子列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/26
 */
public class CategorySubFragment extends LazyFragment<FragmentCategorySubBinding> implements PositionListener<CategorySubBean> {

    private static final String ARG_PARAM = "bean";

    //判断是点击还是滑动
    private boolean isScroll = true;

    private CategoryRootBean bean;
    private CategoryAdapter adapter;
    Drawable blueDraw, whiteDraw;


    public CategorySubFragment(){

    }


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
        blueDraw = getResources().getDrawable(R.drawable.svg_line_blue);
        whiteDraw = getResources().getDrawable(R.drawable.svg_line_white);

        adapter = new CategoryAdapter(getActivity());
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.recycler.addItemDecoration(new SpaceDecoration(getActivity(), 10, R.color.white));
        adapter.setListener(this);
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
            lable.setGravity(Gravity.CENTER);
            int position = tabs.indexOf(bean);
            lable.setCompoundDrawablesWithIntrinsicBounds(null, null, null, position == 0 ? blueDraw : whiteDraw);
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
                lable.setCompoundDrawablesWithIntrinsicBounds(null, null, null, blueDraw);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView lable = (TextView) tab.getCustomView();
                lable.setTextColor(getResources().getColor(R.color.font_black));
                lable.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                lable.setCompoundDrawablesWithIntrinsicBounds(null, null, null, whiteDraw);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


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

    @Override
    public void onPosition(CategorySubBean bean, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("cid", bean.getFcid());
        bundle.putString("mode", "from_category");
        ARouter.getInstance()
                .build(ConRoute.MARKET.SEARCH_RESULT)
                .with(bundle)
                .navigation();
    }
}
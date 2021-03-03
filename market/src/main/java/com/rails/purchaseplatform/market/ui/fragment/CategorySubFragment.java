package com.rails.purchaseplatform.market.ui.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.orhanobut.logger.Logger;
import com.rails.lib_data.bean.CategoryBean;
import com.rails.lib_data.bean.CategorySubBean;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.CategoryAdapter;
import com.rails.purchaseplatform.market.databinding.FragmentCategorySubBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 分类子列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/26
 */
public class CategorySubFragment extends LazyFragment<FragmentCategorySubBinding> {

    private static final String ARG_PARAM1 = "param1";

    private String name;
    private CategoryAdapter adapter;


    public static CategorySubFragment newInstance(String param1) {
        CategorySubFragment fragment = new CategorySubFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        name = getArguments().getString(ARG_PARAM1);
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


        ArrayList<CategoryBean> categoryBeans = new ArrayList<>();
        ArrayList<CategorySubBean> subBeans = new ArrayList<>();
        CategoryBean bean = new CategoryBean();
        bean.setName("篮球鞋");
        subBeans.add(new CategorySubBean("乔丹", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans.add(new CategorySubBean("亚瑟士", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans.add(new CategorySubBean("特步", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans.add(new CategorySubBean("安踏", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans.add(new CategorySubBean("李宁", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans.add(new CategorySubBean("耐克", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans.add(new CategorySubBean("阿迪达斯", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans.add(new CategorySubBean("361度", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        bean.setSubs(subBeans);
        categoryBeans.add(bean);


        CategoryBean bean2 = new CategoryBean();
        ArrayList<CategorySubBean> subBeans2 = new ArrayList<>();
        bean2.setName("花治百病");
        subBeans2.add(new CategorySubBean("蓝色妖姬", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans2.add(new CategorySubBean("紫罗兰", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans2.add(new CategorySubBean("秋菊", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans2.add(new CategorySubBean("康乃馨", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans2.add(new CategorySubBean("百合", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans2.add(new CategorySubBean("玫瑰", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans2.add(new CategorySubBean("牡丹", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans2.add(new CategorySubBean("花开富贵", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans2.add(new CategorySubBean("蓝色妖姬", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans2.add(new CategorySubBean("紫罗兰", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans2.add(new CategorySubBean("秋菊", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans2.add(new CategorySubBean("康乃馨", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans2.add(new CategorySubBean("百合", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans2.add(new CategorySubBean("玫瑰", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans2.add(new CategorySubBean("牡丹", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans2.add(new CategorySubBean("花开富贵", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        bean2.setSubs(subBeans2);
        categoryBeans.add(bean2);


        CategoryBean bean3 = new CategoryBean();
        ArrayList<CategorySubBean> subBeans3 = new ArrayList<>();
        bean3.setName("化妆品");
        subBeans3.add(new CategorySubBean("蓝色妖姬", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans3.add(new CategorySubBean("紫罗兰", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans3.add(new CategorySubBean("秋菊", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans3.add(new CategorySubBean("康乃馨", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans3.add(new CategorySubBean("百合", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans3.add(new CategorySubBean("玫瑰", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans3.add(new CategorySubBean("牡丹", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans3.add(new CategorySubBean("花开富贵", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans3.add(new CategorySubBean("蓝色妖姬", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans3.add(new CategorySubBean("紫罗兰", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans3.add(new CategorySubBean("秋菊", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans3.add(new CategorySubBean("康乃馨", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans3.add(new CategorySubBean("百合", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans3.add(new CategorySubBean("玫瑰", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans3.add(new CategorySubBean("牡丹", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        subBeans3.add(new CategorySubBean("花开富贵", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1983115038,1914523613&fm=26&gp=0.jpg"));
        bean3.setSubs(subBeans3);
        categoryBeans.add(bean3);

        initPager(categoryBeans);
        adapter.update(categoryBeans, true);
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
                    v.setSelected(true);
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


        binding.recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int position = manager.findFirstVisibleItemPosition();
                binding.magic.getTabAt(position).select();
            }
        });

    }


    private void setClick(TabLayout.Tab tab) {

    }


}
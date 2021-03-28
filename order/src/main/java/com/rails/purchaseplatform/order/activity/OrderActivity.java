package com.rails.purchaseplatform.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.adapter.MyFragmentAdapter;
import com.rails.purchaseplatform.order.fragment.AllFragment;
import com.rails.purchaseplatform.order.fragment.MyFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/*
*
采购单列表
 */
@Route(path = ConRoute.ORDER.ORDER_MAIN)
public class OrderActivity extends AppCompatActivity {
    private ImageView left;
    private EditText search;
    private ImageView right_add;
    private SlidingTabLayout slide_layout;
    private ViewPager vp;
    private List<String> mTitles=new ArrayList<>();
    private List<Fragment> mFragment=new ArrayList<>();
    private MyFragmentAdapter myFragmentAdapter;
    private final int mine = 1,all = 2;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        initTal();
        initData();
    }
    private void initTal() {
        slide_layout.setTabSpaceEqual(true);
        myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(),mFragment,mTitles);
        vp.setAdapter(myFragmentAdapter);
        slide_layout.setViewPager(vp);
    }
    private void initData() {
        Collections.addAll(mTitles,"我的采购单","全部采购单");
        Collections.addAll(mFragment, MyFragment.getInstance(mine), AllFragment.getInstance(all));
        myFragmentAdapter.notifyDataSetChanged();

        slide_layout.notifyDataSetChanged();

    }
    private void initView() {
        left = (ImageView) findViewById(R.id.left);
        search = (EditText) findViewById(R.id.search);
        right_add = (ImageView) findViewById(R.id.right_add);
        slide_layout =  findViewById(R.id.slide_layout);
        vp = (ViewPager) findViewById(R.id.vp);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });


    }

}
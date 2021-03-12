package com.rails.purchaseplatform.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.order.adapter.MyFragmentAdapter;
import com.rails.purchaseplatform.order.fragment.AllFragment;
import com.rails.purchaseplatform.order.fragment.MyFragment;
import com.rails.purchaseplatform.order.utils.MyTabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Route(path = ConRoute.ORDER.ORDER_MAIN)
public class OrderActivity extends AppCompatActivity {
    private ImageView left;
    private EditText search;
    private ImageView right_add;
    private MyTabLayout slide_layout;
    private ViewPager vp;
    private List<String> mTitles=new ArrayList<>();
    private List<Fragment> mFragment=new ArrayList<>();
    private MyFragmentAdapter myFragmentAdapter;
    private final int mine = 1,all = 2;

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
        slide_layout = (MyTabLayout) findViewById(R.id.slide_layout);
        vp = (ViewPager) findViewById(R.id.vp);

    }

}
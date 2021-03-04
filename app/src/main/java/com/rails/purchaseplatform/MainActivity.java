package com.rails.purchaseplatform;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.adapter.ViewPageAdapter;
import com.rails.purchaseplatform.databinding.ActivityMainBinding;
import com.rails.purchaseplatform.fragment.IndexFrm;
import com.rails.purchaseplatform.fragment.MallFrm;
import com.rails.purchaseplatform.fragment.MineFrm;
import com.rails.purchaseplatform.fragment.MsgFrm;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.market.ui.fragment.CategoryFragment;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;


/**
 * tabs 页面
 */
@Route(path = ConRoute.RAILS.MAIN)
public class MainActivity extends BaseErrorActivity<ActivityMainBinding> {


    private ViewPageAdapter viewPageAdapter;
    private ArrayList<Fragment> frms;

    @Override
    protected int getColor() {
        return android.R.color.transparent;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    @Override
    protected void initialize(Bundle bundle) {
        initPager();
    }


    @Override
    protected void onClick() {
        super.onClick();
        binding.rbMall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewpager.setCurrentItem(0);
            }
        });
        binding.rbIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewpager.setCurrentItem(1);
            }
        });

        binding.rbZc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewpager.setCurrentItem(2);
            }
        });
        binding.rbMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewpager.setCurrentItem(3);
            }
        });
    }

    /**
     * 初始化pageradapter
     */
    private void initPager() {
        frms = new ArrayList<>();
        frms.add(new IndexFrm());
        frms.add(new CategoryFragment());
        frms.add(new MsgFrm());
        frms.add(new MineFrm());
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPageAdapter.update(frms, true);
        binding.viewpager.setAdapter(viewPageAdapter);
        binding.viewpager.setOffscreenPageLimit(4);
    }


}
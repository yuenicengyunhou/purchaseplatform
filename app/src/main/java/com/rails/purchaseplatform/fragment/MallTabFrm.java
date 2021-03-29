package com.rails.purchaseplatform.fragment;

import android.os.Bundle;
import android.view.View;

import com.rails.purchaseplatform.R;
import com.rails.purchaseplatform.common.adapter.ViewPageAdapter;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.databinding.FrmTabMallBinding;
import com.rails.purchaseplatform.market.ui.fragment.CartFrm;
import com.rails.purchaseplatform.market.ui.fragment.CategoryFrm;
import com.rails.purchaseplatform.market.ui.fragment.MallFrm;
import com.rails.purchaseplatform.user.ui.fragment.MineMallFrm;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.fragment.NavHostFragment;

/**
 * 商城切换首页
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/4
 */
public class MallTabFrm extends LazyFragment<FrmTabMallBinding> {
    @Override
    protected void loadData() {

    }

    @Override
    protected void loadPreVisitData() {

    }

    private ViewPageAdapter viewPageAdapter;
    private ArrayList<Fragment> frms;

    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    @Override
    protected void initialize(Bundle bundle) {
        initPager();
        onClick();
    }


    protected void onClick() {
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

        binding.goPlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MallTabFrm.this)
                        .navigate(R.id.action_mall_to_plat);
            }
        });
    }

    /**
     * 初始化pageradapter
     */
    private void initPager() {
        frms = new ArrayList<>();
        frms.add(new MallFrm());
        frms.add(new CategoryFrm());
        frms.add(CartFrm.newInstance(0));
        frms.add(new MineMallFrm());
        viewPageAdapter = new ViewPageAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPageAdapter.update(frms, true);
        binding.viewpager.setAdapter(viewPageAdapter);
        binding.viewpager.setOffscreenPageLimit(4);
    }

}

package com.rails.purchaseplatform.fragment;

import android.os.Bundle;
import android.view.View;

import com.rails.purchaseplatform.R;
import com.rails.purchaseplatform.common.adapter.ViewPageAdapter;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.databinding.FrmTabPlatBinding;
import com.rails.purchaseplatform.msg.fragment.MsgFrm;
import com.rails.purchaseplatform.notice.fragment.PlatFrm;
import com.rails.purchaseplatform.user.ui.fragment.MineFrm;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.fragment.NavHostFragment;

/**
 * 平台切换首页
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/4
 */
public class PlatTabFrm extends LazyFragment<FrmTabPlatBinding> {
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
        onClick();
        initPager();
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

        binding.rbMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewpager.setCurrentItem(3);
            }
        });


        binding.goMall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(PlatTabFrm.this)
                        .navigate(R.id.action_plat_to_mall);
            }
        });
    }

    /**
     * 初始化pageradapter
     */
    private void initPager() {
        frms = new ArrayList<>();
        frms.add(new PlatFrm());
        frms.add(new MsgFrm());
        frms.add(new MineFrm());
        viewPageAdapter = new ViewPageAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPageAdapter.update(frms, true);
        binding.viewpager.setAdapter(viewPageAdapter);
        binding.viewpager.setOffscreenPageLimit(4);
    }

}

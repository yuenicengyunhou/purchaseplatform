package com.rails.purchaseplatform.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.rails.lib_data.bean.ResultWebBean;
import com.rails.purchaseplatform.R;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.adapter.ViewPageAdapter;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.databinding.FrmTabMallBinding;
import com.rails.purchaseplatform.framwork.bean.BusEvent;
import com.rails.purchaseplatform.market.ui.fragment.CartFrm;
import com.rails.purchaseplatform.market.ui.fragment.CategoryFrm;
import com.rails.purchaseplatform.market.ui.fragment.MallFrm;
import com.rails.purchaseplatform.market.ui.fragment.RankFragment;
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

    private int position;

    public MallTabFrm(){}


    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        position = extras.getInt("position");
    }

    public static MallTabFrm newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        MallTabFrm mallTabFrm = new MallTabFrm();
        mallTabFrm.setArguments(bundle);
        return mallTabFrm;
    }


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
        return true;
    }

    @Override
    public void onMessageEvent(BusEvent event) {
        super.onMessageEvent(event);
        String code = event.getEventCode();
        ResultWebBean bean = (ResultWebBean) event.getBean();
        if (code.equals("cartToHome")) {//当用户的购物车没有商品时，点击去采购，切换到首页
            binding.rbGroup.check(binding.rbMall.getId());
            binding.viewpager.setCurrentItem(0);
        }else {
            if (ConRoute.EVENTCODE.MAIN_CODE.equals(code)) {
                position = bean.getCode();
            }
        }

    }

    @Override
    protected void initialize(Bundle bundle) {
        initPager();
        onClick();
    }


    protected void onClick() {
        binding.rbMall.setOnClickListener(v -> binding.viewpager.setCurrentItem(0));
        binding.rbIndex.setOnClickListener(v -> binding.viewpager.setCurrentItem(1));

        binding.rbZc.setOnClickListener(v -> goLogin(2));
        binding.rbMine.setOnClickListener(v -> goLogin(3));

        binding.goPlat.setOnClickListener(v -> NavHostFragment.findNavController(MallTabFrm.this)
                .navigate(R.id.action_mall_to_plat));
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
        binding.viewpager.setCurrentItem(position);
        ((RadioButton) binding.rbGroup.getChildAt(position)).setChecked(true);
    }


    /**
     * 跳转页面
     *
     * @param page
     */
    private void goLogin(int page) {
        binding.viewpager.setCurrentItem(page);
    }

}

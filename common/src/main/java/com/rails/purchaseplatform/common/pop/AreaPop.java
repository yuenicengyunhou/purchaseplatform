package com.rails.purchaseplatform.common.pop;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import androidx.fragment.app.FragmentPagerAdapter;

import com.rails.purchaseplatform.common.adapter.NavigatorAdapter;
import com.rails.purchaseplatform.common.adapter.ViewPageAdapter;
import com.rails.purchaseplatform.common.databinding.PopAddressAreaBinding;
import com.rails.purchaseplatform.common.fragment.AreaFragment;
import com.rails.purchaseplatform.framwork.base.BasePop;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

/**
 * 筛选
 * <p>
 * author： sk_comic@163.com
 * date: 2021/3/29
 */
public class AreaPop extends BasePop<PopAddressAreaBinding> {

    private final static int CITY = 0;
    private final static int AREA = 1;
    private final static int TOWN = 2;

    private SparseArray<String> areas;
    private SparseArray<String> areaCodes;


    private ViewPageAdapter viewPageAdapter;
    private NavigatorAdapter navigatorAdapter;
    private PareaListener listener;
//    private CompleteAddressListener completeAddressListener;

    public void setListener(PareaListener listener) {
        this.listener = listener;
    }

    @Override
    protected void initialize(Bundle bundle) {

        areas = new SparseArray<>();
        areaCodes = new SparseArray<>();
        initPager();
        onClick();
    }


    void onClick() {
        binding.btnClose.setOnClickListener(v -> dismiss());
    }


    /**
     * 初始化pageradapter
     */
    private void initPager() {
        viewPageAdapter = new ViewPageAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewpager.setAdapter(viewPageAdapter);

        navigatorAdapter = new NavigatorAdapter(getActivity(), binding.viewpager);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(false);
        commonNavigator.setAdapter(navigatorAdapter);
        binding.indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(binding.indicator, binding.viewpager);
        binding.viewpager.setCurrentItem(0);

        addTab("0");
    }


    /**
     * 添加tab
     */
    private void addTab(String code) {
        int len = viewPageAdapter.getCount();
        if (len > TOWN) {
            return;
        }
        AreaFragment fragment = AreaFragment.getInstance(len, code);
        fragment.setListener((bean, type) -> {
            areas.put(type, bean.getName());
            String mCode = bean.getCode();
            areaCodes.put(type, mCode);
            Log.e("WQ", "code====" + mCode + "    =" + type);
            StringBuilder buffer = new StringBuilder();
            if (type == TOWN) {
                if (listener != null) {
                    for (int i = 0; i < areas.size(); i++) {
                        buffer.append(areas.get(i));
                    }
                    listener.getResult(buffer.toString(),areaCodes.get(CITY),areaCodes.get(AREA),areaCodes.get(TOWN));
                }
                dismiss();
                return;
            } else {
                viewPageAdapter.delAbovePosition(type);
                navigatorAdapter.delAbovePosition(type);
            }
            navigatorAdapter.modify(bean.getName(), type);
            addTab(mCode);
        });
        viewPageAdapter.updateAdd(fragment);
        navigatorAdapter.updateAdd("请选择");
        binding.viewpager.setCurrentItem(viewPageAdapter.getCount(), true);
    }


    public interface PareaListener {
        void getResult(String area,String provinceCode, String cityCode, String countryCode);
    }

}

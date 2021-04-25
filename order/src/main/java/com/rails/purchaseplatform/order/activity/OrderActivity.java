package com.rails.purchaseplatform.order.activity;


import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.adapter.ViewPageAdapter;
import com.rails.purchaseplatform.common.pop.OrderSearchFilterPop;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ActivityOrderBinding;
import com.rails.purchaseplatform.order.fragment.OrderFragment;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;

/**
 * 采购单列表页面
 * <p>
 * -1- 列表(采购单订单)和子列表(订单内商品)
 * -2- 搜索
 * -3- 筛选
 * -4- PopupWindow切换搜索类型
 * -5- Tab切换我的采购单和全部采购单
 */
@Route(path = ConRoute.ORDER.ORDER_MAIN)
public class OrderActivity extends BaseErrorActivity<ActivityOrderBinding> {

    private ArrayList<Fragment> fragments;
    private ViewPageAdapter viewPageAdapter;

    private PopupWindow mTypePopup;
    private OrderSearchFilterPop mFilterPopup;

    /**
     * Search type, default 0.
     * 0 - 采购单号
     * 1 - 采购人用户名
     * 2 - 供应商名称
     */
    private int mType = 0;
    private SearchListener searchListener;

    @Override
    protected void initialize(Bundle bundle) {
        String[] tabs = getResources().getStringArray(R.array.order_list_tab);
        initPager(tabs);
        binding.noneScrollViewPager.setPagingEnabled(false);

        //监听输入框按下搜索键
        binding.etSearchKey.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String content = binding.etSearchKey.getText().toString();
                int currentItem = binding.noneScrollViewPager.getCurrentItem();
                OrderFragment fragment = (OrderFragment) viewPageAdapter.getItem(currentItem);
                fragment.notifyData(mType,content);
//                    String text = binding.searchText.getText().toString().trim();
//                    if (isEmptyText(text)) return false;
//                    updateList(text);
//                    putSearchKeyInSharedPreference();
//                    startActivityWithBundle(text);
                return true;
            }
            return false;
        });
    }

    @Override
    protected int getColor() {
        return android.R.color.white;
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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onClick() {
        super.onClick();
        binding.ibBack.setOnClickListener(v -> finish());
        binding.ibFilter.setOnClickListener(v -> {
            showFilterPopup();
        });

        binding.tvSelectType.setOnClickListener(v -> {
            showTypePopup();
        });
    }


    /**
     * 显示筛选PopupWindow
     */
    private void showFilterPopup() {
        if (mFilterPopup == null) {
            String[] text = {"选择品牌", "价格区间", "上架时间"};
            mFilterPopup = new OrderSearchFilterPop(text);
            mFilterPopup.setType(BasePop.MATCH_WRAP);
            mFilterPopup.setGravity(Gravity.BOTTOM);
        }
        mFilterPopup.show(getSupportFragmentManager(), "orderStatus");
    }


    /**
     * 显示选择搜索类型的PopupWindow.
     */
    private void showTypePopup() {
        View itemView = (View) binding.tvSelectType.getParent();

        View view = LayoutInflater.from(OrderActivity.this).inflate(R.layout.popup_search_type, null);
        int width = ScreenSizeUtil.dp2px(this, 120);
        int height = ScreenSizeUtil.dp2px(this, 120);
        mTypePopup = new PopupWindow(view, width, height, true);
        TextView orderNum = view.findViewById(R.id.tv_orderNum),
                orderUser = view.findViewById(R.id.tv_orderUser),
                orderProvider = view.findViewById(R.id.tv_orderProvider);

        String showingText = binding.tvSelectType.getText().toString().trim();

        int colorBlue = getResources().getColor(com.rails.purchaseplatform.common.R.color.font_blue);
        if (showingText.equals("采购单号")) {
            orderNum.setTextColor(colorBlue);
        } else if (showingText.equals("采购人用户名")) {
            orderUser.setTextColor(colorBlue);
        } else if (showingText.equals("供应商名称")) {
            orderProvider.setTextColor(colorBlue);
        }

        orderNum.setOnClickListener(num -> {
            binding.etSearchKey.setHint("搜索单号");
            binding.tvSelectType.setText("采购单号");
            mType = 0;
            mTypePopup.dismiss();
            mTypePopup = null;
        });

        orderUser.setOnClickListener(user -> {
            binding.etSearchKey.setHint("搜索用户名");
            binding.tvSelectType.setText("采购人用户名");
            mType = 1;
            mTypePopup.dismiss();
            mTypePopup = null;
        });

        orderProvider.setOnClickListener(provider -> {
            binding.etSearchKey.setHint("搜索供应商");
            binding.tvSelectType.setText("供应商名称");
            mType = 2;
            mTypePopup.dismiss();
            mTypePopup = null;
        });

        mTypePopup.setOutsideTouchable(true);

        if (isShowBottom(itemView)) {
            mTypePopup.showAsDropDown(itemView, 150, -20);
        } else {
            mTypePopup.showAsDropDown(itemView, 0, -2 * itemView.getHeight());
        }
    }


    private boolean isShowBottom(View itemView) {
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        int[] location = new int[2];
        itemView.getLocationInWindow(location);
        int itemViewY = location[1];
        int distance = screenHeight - itemViewY - itemView.getHeight();
        return distance >= itemView.getHeight();
    }


    /**
     * 初始化pageradapter
     */
    private void initPager(String... tabs) {
        fragments = new ArrayList<>();
        Fragment fragment;
        for (int i = 0; i < tabs.length; i++) {
            fragment = OrderFragment.getInstance(i);
            fragments.add(fragment);
        }



        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.noneScrollViewPager.setAdapter(viewPageAdapter);
        binding.noneScrollViewPager.setOffscreenPageLimit(tabs.length);
        viewPageAdapter.update(fragments, true);


        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabs == null ? 0 : tabs.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {

                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(getResources().getColor(R.color.font_black_light));
                colorTransitionPagerTitleView.setSelectedColor(getResources().getColor(R.color.font_blue));
                colorTransitionPagerTitleView.setTextSize(14f);
                colorTransitionPagerTitleView.setText(tabs[index]);
                colorTransitionPagerTitleView.setOnClickListener(view -> binding.noneScrollViewPager.setCurrentItem(index));
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(getResources().getColor(R.color.bg_blue));
                return indicator;
            }
        });
        binding.indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(binding.indicator, binding.noneScrollViewPager);
        binding.noneScrollViewPager.setCurrentItem(0);
    }


    interface SearchListener{
        void onSearch(int searchType, String searchContent,int currentFragmentPosition);
    }


}
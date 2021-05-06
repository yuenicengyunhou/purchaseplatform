package com.rails.purchaseplatform.order.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.bean.BuyerBean;
import com.rails.lib_data.bean.OrderFilterBean;
import com.rails.lib_data.bean.OrderInfoBean;
import com.rails.lib_data.contract.OrderContract;
import com.rails.lib_data.contract.OrderPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.adapter.ViewPageAdapter;
import com.rails.purchaseplatform.common.pop.OrderFilterPop;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.adapter.ConditionAdapter;
import com.rails.purchaseplatform.order.databinding.ActivityOrderBinding;
import com.rails.purchaseplatform.order.databinding.PopConditionBinding;
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
public class OrderActivity extends BaseErrorActivity<ActivityOrderBinding> implements OrderContract.OrderView {

    private ViewPageAdapter viewPageAdapter;

    private PopupWindow mTypePopup;

    /**
     * Search type, default 0.
     * 0 - 采购单号
     * 1 - 采购人用户名
     * 2 - 供应商名称
     */
    private int mType = 0;
    private PopupWindow listPop;
    private OrderPresenterImpl presenter;
    private ConditionAdapter adapter;
    private String conditionId = "";
    private OrderFilterBean filterBean;
    private String statusCode;

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        statusCode = extras.getString("statusCode");
        Log.e("WQ", "getExtraEvent: ");
    }

    @Override
    protected void initialize(Bundle bundle) {
        Log.e("WQ", "init");
        String[] tabs = getResources().getStringArray(R.array.order_list_tab);
        initPager(tabs);
        binding.noneScrollViewPager.setPagingEnabled(false);

        //监听输入框按下搜索键
        binding.etSearchKey.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (null != listPop && listPop.isShowing()) {//如果当下有弹窗，dismiss掉
                    listPop.dismiss();
                }
                callFragmentToSearch(filterBean);
                return true;
            }
            return false;
        });

        presenter = new OrderPresenterImpl(this, this);

        binding.etSearchKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = binding.etSearchKey.getText().toString();
                showConditionListPopWindow(string);
            }
        });

        this.filterBean = new OrderFilterBean();


        //获取organizeId


    }

    /**
     * 传递搜索条件给fragment，筛选列表
     */
    private void callFragmentToSearch(OrderFilterBean filterBean) {
        String content = binding.etSearchKey.getText().toString();
        int currentItem = binding.noneScrollViewPager.getCurrentItem();
        OrderFragment fragment = (OrderFragment) viewPageAdapter.getItem(currentItem);
        if (mType != 0) {
            content = conditionId;
        }
        fragment.notifyData(mType, content, filterBean);
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
        binding.ibFilter.setOnClickListener(v -> showFilterPopup());

        binding.tvSelectType.setOnClickListener(v ->
                        showTypePopup()
        );
    }


    /**
     * 显示筛选PopupWindow
     */
    private void showFilterPopup() {
        String[] text = {"采购单状态", "采购单金额", "下单时间"};
        OrderFilterPop mFilterPopup = new OrderFilterPop(text, filterBean);
        mFilterPopup.setCompleteListener(data -> {
            this.filterBean = data;
            callFragmentToSearch(data);
        });
        mFilterPopup.setType(BasePop.MATCH_WRAP);
        mFilterPopup.setGravity(Gravity.BOTTOM);
        mFilterPopup.show(getSupportFragmentManager(), "orderStatus");
    }


    /**
     * 显示选择搜索类型的PopupWindow.
     */
    private void showTypePopup() {
        View itemView = (View) binding.tvSelectType.getParent();

        @SuppressLint("InflateParams") View view = LayoutInflater.from(OrderActivity.this).inflate(R.layout.popup_search_type, null);
        int width = ScreenSizeUtil.dp2px(this, 120);
        int height = ScreenSizeUtil.dp2px(this, 120);
        mTypePopup = new PopupWindow(view, width, height, true);
        TextView orderNum = view.findViewById(R.id.tv_orderNum),
                orderUser = view.findViewById(R.id.tv_orderUser),
                orderProvider = view.findViewById(R.id.tv_orderProvider);

        String showingText = binding.tvSelectType.getText().toString().trim();

        int colorBlue = getResources().getColor(com.rails.purchaseplatform.common.R.color.font_blue);
        switch (showingText) {
            case "采购单号":
                orderNum.setTextColor(colorBlue);
                break;
            case "采购人用户名":
                orderUser.setTextColor(colorBlue);
                break;
            case "供应商名称":
                orderProvider.setTextColor(colorBlue);
                break;
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
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int screenHeight = point.y;
//        int screenHeight = defaultDisplay.getHeight();
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
        ArrayList<Fragment> fragments = new ArrayList<>();
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
                return tabs.length;
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

    /**
     * 点击采购人用户名、供应商名称，弹框给用户选择搜索
     */
    private void showConditionListPopWindow(String condition) {
        this.conditionId = condition;
        if (mType == 0) {
            return;
        }
        if (null != listPop && listPop.isShowing()) {
            getConditons(condition);
        } else {
            if (null == listPop) {
                PopConditionBinding listBinding = PopConditionBinding.inflate(getLayoutInflater());
                int width = ScreenSizeUtil.dp2px(this, 250);
                int height = ScreenSizeUtil.dp2px(this, 250);
                listPop = new PopupWindow(listBinding.getRoot(), width, height, false);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                listBinding.recycler.setLayoutManager(layoutManager);
                adapter = new ConditionAdapter(this);
                listBinding.recycler.setAdapter(adapter);
                adapter.setListener((PositionListener<BuyerBean>) (bean, position) -> {
                    String accountName = bean.getAccountName();
                    String supplierName = bean.getSupplierName();
                    String name = null == supplierName ? accountName : supplierName;
                    binding.etSearchKey.setText(name);
                    conditionId = bean.getId();
                    callFragmentToSearch(filterBean);
                    listPop.dismiss();
                });
                getConditons(condition);
//                listPop.setFocusable(true);
                listPop.setOutsideTouchable(true);
                listPop.showAsDropDown(binding.etSearchKey, 150, -20);
                listPop.setOnDismissListener(() -> {
                    listPop = null;
                    adapter = null;
                });
                return;
            }
            if (!listPop.isShowing()) {
                listPop.showAsDropDown(binding.etSearchKey, 150, -20);
            }
        }

    }

    /**
     * 获取采购人或供应商列表
     */
    private void getConditons(String condition) {
        if (mType == 1) {
            presenter.getBuyerNameList(condition, "1");
        } else {
            presenter.getSupplierNameList(condition);
        }
    }

    @Override
    public void getOrder(ArrayList<OrderInfoBean> orderBeans, boolean hasMore, boolean isClear, int totalPageCount) {

    }

    /**
     * 加载采购人用户列表/供应商名称列表
     */
    @Override
    public void loadConditionNameList(ArrayList<BuyerBean> list) {
        if (null != adapter) {
            adapter.update(list, true);
        }
    }

}
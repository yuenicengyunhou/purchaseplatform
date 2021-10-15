package com.rails.purchaseplatform.order.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.BuyerBean;
import com.rails.lib_data.bean.OrderFilterBean;
import com.rails.lib_data.bean.OrderInfoBean;
import com.rails.lib_data.bean.OrderStatusBean;
import com.rails.lib_data.bean.orderdetails.DeliveredFile;
import com.rails.lib_data.contract.OrderContract;
import com.rails.lib_data.contract.OrderPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.adapter.ViewPageAdapter;
import com.rails.purchaseplatform.common.pop.OrderFilterPop;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
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


import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

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
    private int mCurrentFragmentPosition;


    /**
     * Search type, default 0.
     * 0 - 采购单号
     * 1 - 采购人用户名
     * 2 - 供应商名称
     */
    private PopupWindow mTypePopup;//采购单切换检索条件的pop
    private int mType = 0;//采购单号0  采购人用户名1  供应商名称2  订单号3   物资类型4   品牌8  需求编号9
    private PopupWindow listPop;//采购人姓名和供应商检索列表
    private OrderPresenterImpl presenter;
    private ConditionAdapter adapter;//采购人姓名和供应商检索列表的adapter
    private String conditionId = "";
    /**
     * 筛选采购单状态弹出框
     */
//    private OrderFilterPop mFilterPopup;//筛选弹窗
//    private OrderFilterBean filterBean;
    private String statusCode = null;
    private HashMap<Integer, OrderFilterBean> mFilterCache;


    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        statusCode = extras.getString("statusCode");
    }

    @Override
    protected void initialize(Bundle bundle) {
        String[] tabs = getResources().getStringArray(R.array.order_list_tab);
        initFilterBean(tabs);//初始化filter数据和cache，放在initPager之前
        initPager(tabs);
        binding.noneScrollViewPager.setPagingEnabled(false);

        binding.etSearchKey.setInputType(InputType.TYPE_CLASS_NUMBER);//默认是采购单号搜索，限定只能输入数字

        //监听输入框按下搜索键
        binding.etSearchKey.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (null != listPop && listPop.isShowing()) {//如果当下有弹窗，dismiss掉
                    listPop.dismiss();
                }
                if (mType == 0 || mType == 3 || mType == 4 || mType == 6 || mType == 7 || mType == 9) {
                    callFragmentToSearch(mFilterCache.get(mCurrentFragmentPosition));
                } else if (mType == 1) {
                    searchIfPop("请先检索采购人用户名");
                } else if (mType == 2) {
                    searchIfPop("请先检索供应商名称");
                } else if (mType == 5) {
                    searchIfPop("请先检索商品名称");
                } else if (mType == 8) {
                    searchIfPop("请先检索品牌");
                }
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
                if (string.length() > 0) {
                    binding.ivClear.setVisibility(View.VISIBLE);
                } else {
                    binding.ivClear.setVisibility(View.GONE);
                }
                showConditionListPopWindow(string);
            }
        });

        binding.ivClear.setOnClickListener(v -> binding.etSearchKey.setText(""));

    }

    /**
     * aaaaa
     */
    private void searchIfPop(String toast) {
        String trim = binding.etSearchKey.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            conditionId = "";//重置搜索条件
            callFragmentToSearch(mFilterCache.get(mCurrentFragmentPosition));
        } else {
            ToastUtil.show(this, toast);
        }
    }

    private void initFilterBean(@NotNull String[] tabs) {
        mFilterCache = new HashMap<>();//初始化cache，分开存储两个filterBean
        for (int i = 0; i < tabs.length; i++) {
            OrderFilterBean filterBean = new OrderFilterBean();
            Type type = new TypeToken<ArrayList<OrderStatusBean>>() {
            }.getType();
            ArrayList<OrderStatusBean> statusBeans = JsonUtil.parseJson(this, "orderStatus.json", type);
            if (null != statusCode) {
                for (OrderStatusBean status : statusBeans) {
                    status.setChecked(status.getStatusCode().equals(statusCode));
                }
            }
            filterBean.setStatusBeans(statusBeans);
            mFilterCache.put(i, filterBean);
        }


    }

    /**
     * 传递搜索条件给fragment，筛选列表
     */
    private void callFragmentToSearch(OrderFilterBean filterBean) {
        String content = binding.etSearchKey.getText().toString();
        int currentItem = binding.noneScrollViewPager.getCurrentItem();
        mFilterCache.put(currentItem, filterBean);
        OrderFragment fragment = (OrderFragment) viewPageAdapter.getItem(currentItem);
        if (mType == 1 || mType == 2 || mType == 5 || mType == 8) {
            content = conditionId;
        }
        fragment.notifyFragment(mType, content, filterBean);
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
        OrderFilterPop mFilterPopup = new OrderFilterPop(text, mFilterCache.get(mCurrentFragmentPosition));
        mFilterPopup.setCompleteListener(this::callFragmentToSearch);
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
        int width;
        if (isPad(this)) {
            width = ScreenSizeUtil.dp2px(this, 200);
        } else {
            width = ScreenSizeUtil.dp2px(this, 150);
        }
        int height = ScreenSizeUtil.dp2px(this, 150);
        mTypePopup = new PopupWindow(view, width, height, true);
        TextView orderNum = view.findViewById(R.id.tv_orderNum);
        TextView orderUser = view.findViewById(R.id.tv_orderUser);
        TextView orderProvider = view.findViewById(R.id.tv_orderProvider);
        TextView subOrderNum = view.findViewById(R.id.tv_subOrderNum);
        TextView tvBrand = view.findViewById(R.id.tv_brand);
        TextView tvNeedNum = view.findViewById(R.id.tv_needNum);

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
            case "订单号":
                subOrderNum.setTextColor(colorBlue);
                break;
            case "品牌":
                tvBrand.setTextColor(colorBlue);
                break;
            case "需求编号":
                tvNeedNum.setTextColor(colorBlue);
                break;
        }

        orderNum.setOnClickListener(num -> onConditionChoose("搜索单号", "采购单号", InputType.TYPE_CLASS_NUMBER, 0));
        orderUser.setOnClickListener(user -> onConditionChoose("搜索用户名", "采购人用户名", InputType.TYPE_CLASS_TEXT, 1));
        orderProvider.setOnClickListener(provider -> onConditionChoose("搜索供应商", "供应商名称", InputType.TYPE_CLASS_TEXT, 2));//
        subOrderNum.setOnClickListener(subOrder -> onConditionChoose("搜索订单号", "订单号", InputType.TYPE_CLASS_NUMBER, 3));
        tvBrand.setOnClickListener(brand -> onConditionChoose("搜索品牌", "品牌", InputType.TYPE_CLASS_TEXT, 8));
        tvNeedNum.setOnClickListener(needNum -> onConditionChoose("搜索需求编号", "需求编号", InputType.TYPE_CLASS_NUMBER, 9));


        mTypePopup.setOutsideTouchable(true);

        if (isShowBottom(itemView)) {
            mTypePopup.showAsDropDown(itemView, 150, -20);
        } else {
            mTypePopup.showAsDropDown(itemView, 0, -2 * itemView.getHeight());
        }
    }


    private void onConditionChoose(String hint, String text, int inputType, int type) {
        binding.etSearchKey.setText("");
        binding.etSearchKey.setInputType(inputType);
        binding.etSearchKey.setHint(hint);
        binding.tvSelectType.setText(text);
        mType = type;//搜索类型
        if (null != listPop && listPop.isShowing()) {
            listPop.dismiss();
        }
        mTypePopup.dismiss();
        mTypePopup = null;
    }

    private boolean isShowBottom(View itemView) {
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int screenHeight = point.y;
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
            fragment = OrderFragment.getInstance(i, statusCode, statusCode == null ? null : mFilterCache.get(mCurrentFragmentPosition));
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
        binding.noneScrollViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentFragmentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
        if (mType == 0 || mType == 3 || mType == 4 || mType == 6 || mType == 7 || mType == 9) {
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
                adapter.setChooseListener((name, id) -> {
                    binding.etSearchKey.setText(name);
                    conditionId = id;
                    callFragmentToSearch(mFilterCache.get(mCurrentFragmentPosition));
                    listPop.dismiss();
                });
                getConditons(condition);
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
        } else if (mType == 2) {
            presenter.getSupplierNameList(condition);
        } else if (mType == 5) {
            presenter.getSkuNameList(condition);
        } else if (mType == 8) {
            presenter.getBrandList(condition);
        }
    }

    @Override
    public void getOrder(ArrayList<OrderInfoBean> orderBeans, boolean firstPage, int totalCount) {

    }

    /**
     * 加载采购人用户列表/供应商名称列表
     */
    @Override
    public void loadConditionNameList(ArrayList<BuyerBean> list) {
        if (null != adapter) {
            adapter.setType(mType);
            adapter.update(list, true);
        }

    }

    @Override
    public void loadDeliveredFileList(ArrayList<DeliveredFile> list) {

    }

    private boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
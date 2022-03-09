package com.rails.purchaseplatform.address.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.PopupWindow;
import android.widget.ScrollView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.bean.address.AddressArea;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.AuthorBean;
import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.lib_data.contract.AddressContract;
import com.rails.lib_data.contract.AddressPresenterImpl;
import com.rails.lib_data.contract.UserToolContract;
import com.rails.lib_data.contract.UserToolPresenterImpl;
import com.rails.purchaseplatform.address.R;
import com.rails.purchaseplatform.address.adapter.AddressAdapter;
import com.rails.purchaseplatform.address.databinding.ActivityAddressBinding;
import com.rails.purchaseplatform.address.databinding.PopAddressSearchBinding;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.common.utils.StatusBarCompat;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.adapter.listener.MulPositionListener;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 地址管理列表页面
 */
@Route(path = ConRoute.ADDRESS.ADDRESS_MAIN)
public class AddressActivity extends BaseErrorActivity<ActivityAddressBinding> implements AddressContract.AddressView,
        PositionListener<AddressBean>, MulPositionListener<AddressBean>, UserToolContract.UserToolView {
    private final int PAGE_DEF = 1;
    private int mPage = PAGE_DEF;
    private AddressAdapter addressAdapter;
    private AddressContract.AddressPresenter presenter;

    // 是否有新增地址权限
    private boolean isAdd = false;

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
    protected void initialize(Bundle bundle) {
        setSupportActionBar(binding.toolbar);
//判断有没有添加地址的权限
        if (!isAdd) {
            binding.btnAdd.setVisibility(View.GONE);
        }
        //初始化地址列表
        addressAdapter = new AddressAdapter(this);
        addressAdapter.setListener(this);
        addressAdapter.setMulPositionListener(this);
        binding.recycler.addItemDecoration(new SpaceDecoration(this, 1, R.color.line_gray));
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recycler.setAdapter(addressAdapter);
//请求权限接口
        UserToolPresenterImpl toolPresenter = new UserToolPresenterImpl(this, this);
        toolPresenter.queryAuthor();

//请求地址接口
        presenter = new AddressPresenterImpl(this, this);
        onRefresh(true);
//控件交互时间
        onViewClick();

    }

    /**
     * 控件交互事件
     */
    private void onViewClick() {
        binding.ibBack.setOnClickListener(v -> finish());
//切换搜索条件按钮
        binding.tvPop.setOnClickListener(v -> showSearchTypePop(binding.tvPop.getText().toString()));

        binding.smart.setOnRefreshListener(refreshLayout -> {
            mPage = PAGE_DEF;
            onRefresh(true);
        });

        binding.smart.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            onRefresh((false));
        });

        //搜索
        binding.tvSearch.setOnClickListener(v -> {
            mPage = PAGE_DEF;
            onRefresh(true);
        });

        //搜索确认按键监听
        binding.editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mPage = PAGE_DEF;
                onRefresh(true);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDarkStatusBar(R.color.white);
    }
    protected void setDarkStatusBar(int color) {
        int statusBarColor;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            statusBarColor = ContextCompat.getColor(this, color);
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            statusBarColor = ContextCompat.getColor(this, color);
        }
        StatusBarCompat.compat(this, statusBarColor);
    }

    /**
     * 刷新请求
     */
    private void onRefresh(boolean isDialog) {
        presenter.getAddresses(isDialog, mPage, binding.tvPop.getText().toString(), binding.editText.getText().toString().trim());
    }


    @Override
    protected void onClick() {
        super.onClick();
        binding.btnAdd.setOnClickListener(v -> startIntent(AddressAddActivity.class));
    }

    /**
     * 选择搜索类型的弹窗
     */
    private void showSearchTypePop(String text) {
        PopAddressSearchBinding popBinding = PopAddressSearchBinding.inflate(getLayoutInflater());
        ScrollView root = popBinding.getRoot();
        int width;
        int height;
        if (isPad(this)) {
            width = ScreenSizeUtil.dp2px(this, 150);
            height = ScreenSizeUtil.dp2px(this, 160);
        } else {
            width = ScreenSizeUtil.dp2px(this, 100);
            height = ScreenSizeUtil.dp2px(this, 130);
        }
        popBinding.tvReceiver.setChecked(text.equals("收货人"));
        popBinding.tvMobile.setChecked(text.equals("手机号码"));
        popBinding.tvFullAddress.setChecked(text.equals("详细地址"));

        PopupWindow popupWindow = new PopupWindow(root, width, height, true);
        popBinding.tvReceiver.setOnClickListener(v -> onToolbarChange("收货人", "搜索收货人", popupWindow));
        popBinding.tvMobile.setOnClickListener(v -> onToolbarChange("手机号码", "搜索手机号码", popupWindow));
        popBinding.tvFullAddress.setOnClickListener(v -> onToolbarChange("详细地址", "搜索详细地址", popupWindow));
        popupWindow.showAsDropDown((View) binding.tvPop.getParent());
    }

    /**
     * 点击弹窗条件之后，搜索栏信息切换
     */
    private void onToolbarChange(String text, String hint, PopupWindow popupWindow) {
        switch (text) {
            case "收货人":
            case "详细地址":
                binding.editText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "手机号码":
                binding.editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
        }
        binding.tvPop.setText(text);
        binding.editText.setText("");
        binding.editText.setHint(hint);
        popupWindow.dismiss();
    }


    /**
     * 刷新列表
     */
    @Override
    public void getResult(int type, int position, String msg) {
//        if (type == 0) {
//            addressAdapter.updateRemove(position);
//        } else if (type == 1) {
//            addressAdapter.modifyDef(position);
//            addressAdapter.swapData(position, 0);
//        }
        onRefresh(false);
        if (null != msg) {
            ToastUtil.show(this, msg);
        }
    }

    @Override
    public void getAddresses(ArrayList<AddressBean> addressBeans, boolean isLastPage, int totalCount) {
        binding.smart.finishLoadMore();
        binding.smart.finishRefresh();
        if (mPage != PAGE_DEF && isLastPage && addressAdapter.getItemCount() >= totalCount) {
            return;
        }
        addressAdapter.update(addressBeans, mPage == PAGE_DEF);
    }

    @Override
    public void getArea(ArrayList<AddressArea> list) {

    }

    @Override
    public void onPosition(AddressBean bean, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        startIntent(AddressAddActivity.class, bundle);
    }

    @Override
    public void onPosition(AddressBean bean, int position, int... params) {
        presenter.delAddress(bean.getAddressId(), position);
    }

    @Override
    public void loadAddressInfo(AddressBean addressInfo) {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPage=PAGE_DEF;
        onRefresh(true);
    }

    @Override
    public void getUserStatictics(UserStatisticsBean bean) {

    }

    @Override
    public void getUserInfoStatictics(UserStatisticsBean bean) {

    }

    @Override
    public void checkPermissions(UserStatisticsBean bean) {

    }

    @Override
    public void getAuthor(AuthorBean authorBean) {
        isAdd = PrefrenceUtil.getInstance(this).getBoolean(ConShare.BUTTON_ADDRESS_ADD, false);
        if (!isAdd) {
            binding.btnAdd.setVisibility(View.GONE);
        } else {
            binding.btnAdd.setVisibility(View.VISIBLE);
        }
    }

    private boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }



}
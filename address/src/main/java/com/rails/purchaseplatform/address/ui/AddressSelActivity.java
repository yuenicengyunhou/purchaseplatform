package com.rails.purchaseplatform.address.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.PopupWindow;
import android.widget.ScrollView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.AuthorBean;
import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.lib_data.contract.AddressToolContract;
import com.rails.lib_data.contract.AddressToolPresenterImpl;
import com.rails.lib_data.contract.UserToolContract;
import com.rails.lib_data.contract.UserToolPresenterImpl;
import com.rails.purchaseplatform.address.adapter.AddressSelAdapter;
import com.rails.purchaseplatform.address.databinding.ActivityAddressSelBinding;
import com.rails.purchaseplatform.address.databinding.PopAddressSearchBinding;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.adapter.listener.MulPositionListener;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 选择地址
 * <p>
 * author： sk_comic@163.com
 * date: 2021/3/28
 */
@Route(path = ConRoute.ADDRESS.ADDRESS_SEL)
public class AddressSelActivity extends BaseErrorActivity<ActivityAddressSelBinding> implements UserToolContract.UserToolView,
        AddressToolContract.AddressToolView, MulPositionListener<AddressBean>, PositionListener<AddressBean> {

    private AddressSelAdapter addressAdapter;
    private AddressToolContract.AddressToolPresenter presenter;
    private String type;
    private AddressBean addressBean;

    private UserToolContract.UserToolPresenter toolPresenter;

    // 是否有新增地址权限
    private boolean isAdd = false;

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        type = extras.getString("type", "1");
        addressBean = (AddressBean) extras.getSerializable("bean");
    }

    @Override
    protected void initialize(Bundle bundle) {

//        binding.titleBar
//                .setTitleBar(R.string.address_main)
//                .setShowLine(true)
//                .setImgLeftRes(R.drawable.svg_back_black);

        if (!isAdd) {
            binding.btnAdd.setVisibility(View.GONE);
        }


        binding.smart.setEnableLoadMore(false);
        addressAdapter = new AddressSelAdapter(this);
        addressAdapter.setMulPositionListener(this);
        addressAdapter.setListener(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        binding.empty.setDescEmpty(R.string.empty_data).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        binding.recycler.setAdapter(addressAdapter);
//        binding.recycler.

        toolPresenter = new UserToolPresenterImpl(this, this);
        toolPresenter.queryAuthor();

        presenter = new AddressToolPresenterImpl(this, this);

        binding.ibBack.setOnClickListener(v -> finish());
        binding.tvPop.setOnClickListener(v -> showSearchTypePop(binding.tvPop.getText().toString()));
        binding.tvSearch.setOnClickListener(v -> onRefresh());

        //搜索确认按键监听
        binding.editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onRefresh();
                return true;
            }
            return false;
        });

        onRefresh();

    }


    /**
     * 刷新请求
     */
    private void onRefresh() {
        binding.smart.setOnRefreshListener(refreshLayout -> {
            binding.smart.finishRefresh();
            presenter.getAddress("", type, binding.tvPop.getText().toString(), binding.editText.getText().toString().trim(),false);
        });
        presenter.getAddress("", type, binding.tvPop.getText().toString(), binding.editText.getText().toString().trim(),false);
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
        binding.editText.setHint(hint);
        popupWindow.dismiss();
    }


    @Override
    public void onPosition(AddressBean bean, int position, int... params) {
        Intent intent = new Intent();
        intent.putExtra("bean", bean);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onPosition(AddressBean bean, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        startIntent(AddressAddActivity.class, bundle);
    }


    @Override
    protected void onClick() {
        super.onClick();

        binding.btnAdd.setOnClickListener(v -> startIntent(AddressAddActivity.class));
    }

    @Override
    public void getAddress(ArrayList<AddressBean> addressBeans, boolean showAddressPop) {
        addressAdapter.update(addressBeans, true);
        if (addressBean != null) {
            addressAdapter.setSelPosition(addressBean);
        } else {
            if (addressBeans.isEmpty())
                return;
            addressAdapter.setSelPosition(addressBeans.get(0));
        }
    }

    @Override
    public void getDefAddress(AddressBean bean) {

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


    @Override
    protected void reNetLoad() {
        super.reNetLoad();
        toolPresenter.queryAuthor();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onRefresh();
    }

    private boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}

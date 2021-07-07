package com.rails.purchaseplatform.address.ui;

import android.os.Bundle;
import android.security.keystore.KeyInfo;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

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
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.adapter.listener.MulPositionListener;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import java.util.ArrayList;

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
    private UserToolPresenterImpl toolPresenter;

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

        if (!isAdd) {
            binding.btnAdd.setVisibility(View.GONE);
        }
        addressAdapter = new AddressAdapter(this);
        addressAdapter.setListener(this);
        addressAdapter.setMulPositionListener(this);


        binding.recycler.addItemDecoration(new SpaceDecoration(this, 1, R.color.line_gray));
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        binding.empty.setDescEmpty(R.string.empty_data).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        binding.recycler.setAdapter(addressAdapter);

        toolPresenter = new UserToolPresenterImpl(this, this);
        toolPresenter.queryAuthor();


        presenter = new AddressPresenterImpl(this, this);
        onRefresh(true);

        binding.smart.setOnRefreshListener(refreshLayout -> {
            mPage = PAGE_DEF;
            onRefresh(true);
        });

        binding.smart.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            onRefresh((false));
        });

        binding.ibBack.setOnClickListener(v -> finish());

        binding.tvPop.setOnClickListener(v -> showSearchTypePop(binding.tvPop.getText().toString()));
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
        int width = ScreenSizeUtil.dp2px(this, 150);
        int height = ScreenSizeUtil.dp2px(this, 130);
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
        if (type == 0) {
            addressAdapter.updateRemove(position);
        } else if (type == 1) {
            addressAdapter.modifyDef(position);
            addressAdapter.swapData(position, 0);
        }
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


}
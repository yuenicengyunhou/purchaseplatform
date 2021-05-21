package com.rails.purchaseplatform.address.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.AuthorBean;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.lib_data.contract.AddressToolContract;
import com.rails.lib_data.contract.AddressToolPresenterImpl;
import com.rails.lib_data.contract.UserToolContract;
import com.rails.lib_data.contract.UserToolPresenterImpl;
import com.rails.purchaseplatform.address.R;
import com.rails.purchaseplatform.address.adapter.AddressSelAdapter;
import com.rails.purchaseplatform.address.databinding.ActivityAddressSelBinding;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.framwork.adapter.listener.MulPositionListener;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 选择地址
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/28
 */
@Route(path = ConRoute.ADDRESS.ADDRESS_SEL)
public class AddressSelActivity extends ToolbarActivity<ActivityAddressSelBinding> implements UserToolContract.UserToolView,
        AddressToolContract.AddressToolView, MulPositionListener<AddressBean>, PositionListener<AddressBean> {

    private AddressSelAdapter addressAdapter;
    private AddressToolContract.AddressToolPresenter presenter;
    private String type;
    private AddressBean addressBean;

    private UserInfoBean userInfoBean;
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

        binding.titleBar
                .setTitleBar(R.string.address_main)
                .setShowLine(true)
                .setImgLeftRes(R.drawable.svg_back_black);

        if (!isAdd) {
            barBinding.btnAdd.setVisibility(View.GONE);
        }


        barBinding.smart.setEnableLoadMore(false);
        addressAdapter = new AddressSelAdapter(this);
        addressAdapter.setMulPositionListener(this);
        addressAdapter.setListener(this);
        barBinding.recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        barBinding.recycler.setAdapter(addressAdapter);

        userInfoBean = PrefrenceUtil.getInstance(this).getBean(ConShare.USERINFO, UserInfoBean.class);
        toolPresenter = new UserToolPresenterImpl(this, this);
        if (userInfoBean != null)
            toolPresenter.queryAuthor(userInfoBean.getId(), userInfoBean.getAccountType());

        presenter = new AddressToolPresenterImpl(this, this);
        onRefresh();

    }


    /**
     * 刷新请求
     */
    private void onRefresh() {
        barBinding.smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                barBinding.smart.finishRefresh();
                presenter.getAddress("", type);
            }
        });
        presenter.getAddress("", type);
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

        barBinding.btnAdd.setOnClickListener(v -> {
            startIntent(AddressAddActivity.class);
        });
    }

    @Override
    public void getAddress(ArrayList<AddressBean> addressBeans) {
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
            barBinding.btnAdd.setVisibility(View.GONE);
        }
    }


    @Override
    protected void reNetLoad() {
        super.reNetLoad();
        if (userInfoBean != null)
            toolPresenter.queryAuthor(userInfoBean.getId(), userInfoBean.getAccountType());
    }
}

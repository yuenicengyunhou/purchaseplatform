package com.rails.purchaseplatform.address.ui;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.AddressArea;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.AuthorBean;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.lib_data.contract.AddressContract;
import com.rails.lib_data.contract.AddressPresenterImpl;
import com.rails.lib_data.contract.UserToolContract;
import com.rails.lib_data.contract.UserToolPresenterImpl;
import com.rails.purchaseplatform.address.R;
import com.rails.purchaseplatform.address.adapter.AddressAdapter;
import com.rails.purchaseplatform.address.databinding.ActivityAddressBinding;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.adapter.listener.MulPositionListener;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 地址管理列表页面
 */
@Route(path = ConRoute.ADDRESS.ADDRESS_MAIN)
public class AddressActivity extends ToolbarActivity<ActivityAddressBinding> implements AddressContract.AddressView,
        PositionListener<AddressBean>, MulPositionListener<AddressBean>, UserToolContract.UserToolView {
    private final int PAGE_DEF = 1;
    private int mPage = PAGE_DEF;
    private AddressAdapter addressAdapter;
    private AddressContract.AddressPresenter presenter;
    private UserToolContract.UserToolPresenter toolPresenter;

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
        binding.titleBar
                .setTitleBar(R.string.address_main)
                .setShowLine(true)
                .setImgLeftRes(R.drawable.svg_back_black);

        if (!isAdd) {
            barBinding.btnAdd.setVisibility(View.GONE);
        }
        addressAdapter = new AddressAdapter(this);
        addressAdapter.setListener(this);
        addressAdapter.setMulPositionListener(this);


        barBinding.recycler.addItemDecoration(new SpaceDecoration(this, 1, R.color.line_gray));
        barBinding.recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        barBinding.recycler.setAdapter(addressAdapter);

        toolPresenter = new UserToolPresenterImpl(this, this);
            toolPresenter.queryAuthor();


        presenter = new AddressPresenterImpl(this, this);
        onRefresh(true);


        //下拉刷新和上拉加载
//        barBinding.smart.setOnLoadMoreListener(refreshLayout -> {
//            mPage++;
//            onRefresh(true);
//        });
//        barBinding.recycler.useDefaultLoadMore();
//        barBinding.recycler.setLoadMoreListener(new SwipeRecyclerView.LoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                mPage++;
//                onRefresh(true);
//            }
//        });

        barBinding.smart.setOnRefreshListener(refreshLayout -> {
            mPage = PAGE_DEF;
            onRefresh(true);
        });

        barBinding.smart.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            onRefresh((false));
        });

    }


    /**
     * 刷新请求
     */
    private void onRefresh(boolean isDialog) {
        presenter.getAddresses(isDialog, mPage);
    }


    @Override
    protected void onClick() {
        super.onClick();
        barBinding.btnAdd.setOnClickListener(v -> startIntent(AddressAddActivity.class));
    }


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
        barBinding.smart.finishLoadMore();
        barBinding.smart.finishRefresh();
        if (mPage != PAGE_DEF && isLastPage && addressAdapter.getItemCount() >= totalCount) {
            return;
        }
        addressAdapter.update(addressBeans, mPage == PAGE_DEF);
//        int defPosition = 0;
//        boolean defExist = false;
//        for (int i = 0; i < addressBeans.size(); i++) {
//            if (addressBeans.get(i).getHasDefault() == 1) {
//                defPosition = i;
//                defExist = true;
//                break;
//            }
//        }
//        if (defExist) {
//            getResult(1, defPosition, null);
//        }
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
//        presenter.setDefAddress(bean.getAddressId(), position, bean.getReceivingAddress() == 1, bean.getInvoiceAddress() == 1);
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
            barBinding.btnAdd.setVisibility(View.GONE);
        } else {
            barBinding.btnAdd.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void reNetLoad() {
        super.reNetLoad();
            toolPresenter.queryAuthor();
    }
}
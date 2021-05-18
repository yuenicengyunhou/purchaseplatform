package com.rails.purchaseplatform.address.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.AddressArea;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.contract.AddressContract;
import com.rails.lib_data.contract.AddressPresenterImpl;
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
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 地址管理列表页面
 */
@Route(path = ConRoute.ADDRESS.ADDRESS_MAIN)
public class AddressActivity extends ToolbarActivity<ActivityAddressBinding> implements AddressContract.AddressView,
        PositionListener<AddressBean>, MulPositionListener<AddressBean> {
    private final int PAGE_DEF = 1;
    private int mPage = PAGE_DEF;
    private AddressAdapter addressAdapter;
    private AddressContract.AddressPresenter presenter;

    //是否有删除权限
    private boolean isDel = false;
    // 是否有新增地址权限
    private boolean isAdd = false;


    //测滑删除按钮
    private final SwipeMenuCreator swipeMenuCreator = (swipeLeftMenu, swipeRightMenu, position) -> {
        int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            SwipeMenuItem addItem = new SwipeMenuItem(AddressActivity.this)
//                    .setText(getResources().getString(R.string.address_main_def))
//                    .setTextColor(getResources().getColor(R.color.font_black_light))
//                    .setTextSize(12)
//                    .setBackgroundColor(getResources().getColor(R.color.bg_gray))
//                    .setWidth(width)
//                    .setHeight(height);
//            swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。

        {
            SwipeMenuItem deleteItem = new SwipeMenuItem(AddressActivity.this)
                    .setText(getResources().getString(R.string.address_main_del))
                    .setTextColor(Color.WHITE)
                    .setBackgroundColor(getResources().getColor(R.color.bg_red))
                    .setTextSize(12)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
        }
    };


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


        isAdd = PrefrenceUtil.getInstance(this).getBoolean(ConShare.BUTTON_ADDRESS_ADD, false);
        isDel = PrefrenceUtil.getInstance(this).getBoolean(ConShare.BUTTON_ADDRESS_DEL, false);

        if (!isAdd) {
            barBinding.btnAdd.setVisibility(View.GONE);
        }


        addressAdapter = new AddressAdapter(this);
        addressAdapter.setListener(this);
        addressAdapter.setMulPositionListener(this);

        if (isDel)
            barBinding.recycler.setSwipeMenuCreator(swipeMenuCreator);

        barBinding.recycler.addItemDecoration(new SpaceDecoration(this, 1, R.color.line_gray));
        barBinding.recycler.setOnItemMenuClickListener((menuBridge, adapterPosition) -> {
            menuBridge.closeMenu();
            int position = menuBridge.getPosition();
            AddressBean bean = addressAdapter.getBean(adapterPosition);
            long addressId = bean.getAddressId();
            if (position == 0) {
                presenter.delAddress(addressId, adapterPosition);
            }
//            else {
//                //设为默认
//                int receivingAddress = bean.getReceivingAddress();
//                int invoiceAddress = bean.getInvoiceAddress();
//                presenter.setDefAddress(addressId, adapterPosition, receivingAddress == 1, invoiceAddress == 1);
//
//            }
        });
        barBinding.recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        barBinding.recycler.setAdapter(addressAdapter);


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
}
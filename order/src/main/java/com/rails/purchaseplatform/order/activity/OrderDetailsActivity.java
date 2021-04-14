package com.rails.purchaseplatform.order.activity;


import android.os.Bundle;
import android.view.View;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.bean.OrderAuditBean;
import com.rails.lib_data.bean.OrderCreatedBean;
import com.rails.lib_data.bean.OrderDetailsBean;
import com.rails.lib_data.bean.OrderProcessBean;
import com.rails.lib_data.contract.OrderDetailsContract;
import com.rails.lib_data.contract.OrderDetailsImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.adapter.order_details.OrderAuditAdapter;
import com.rails.purchaseplatform.order.adapter.order_details.OrderCreatedAdapter;
import com.rails.purchaseplatform.order.adapter.order_details.OrderProductAdapter;
import com.rails.purchaseplatform.order.adapter.order_details.OrderProcessAdapter;
import com.rails.purchaseplatform.order.databinding.ActivityOrderDetailsBinding;

import java.text.MessageFormat;
import java.util.ArrayList;

@Route(path = ConRoute.ORDER.ORDER_DETAILS)
public class OrderDetailsActivity extends ToolbarActivity<ActivityOrderDetailsBinding> implements OrderDetailsContract.DetailView {


    private OrderProcessAdapter processAdapter;
    private OrderCreatedAdapter createdAdapter;
    private OrderAuditAdapter auditAdapter;

    @Override
    protected void initialize(Bundle bundle) {
        binding.titleBar.setTitleBar(R.string.order_detail_title)
                .setImgLeftRes(R.drawable.svg_back_black);
        barBinding.ivBackTop.setOnClickListener(v -> {
            barBinding.detailScorllView.smoothScrollTo(0, 0);
            showOrHideBackButton(false);
        });
        barBinding.detailScorllView.setSmoothScrollingEnabled(true);
        float px3 = ScreenSizeUtil.dp2px(this, 80);
        barBinding.detailScorllView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            showOrHideBackButton(scrollY > (int) px3);
        });

        binding.titleBar.setBackgroundColor(getResources().getColor(R.color.bg_blue));

        initProcessRecycler();//订单状态列表

        initProductsRecycler();//商品列表

        initOrderCreatedRecycler();//已生成订单列表

        initAuditRecycler();//审核列表

        OrderDetailsImpl presenter = new OrderDetailsImpl(this, this);
        presenter.getOrderDetailsInfo();
    }

    /**
     * 订单状态列表
     */
    private void initProcessRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        barBinding.orderProcessRecycler.setLayoutManager(layoutManager);
        processAdapter = new OrderProcessAdapter(this);
        barBinding.orderProcessRecycler.setAdapter(processAdapter);
    }

    /**
     * 商品列表
     */
    private void initProductsRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        barBinding.orderGoodsRecycler.setLayoutManager(layoutManager);
        OrderProductAdapter productAdapter = new OrderProductAdapter(this);
        barBinding.orderGoodsRecycler.setAdapter(productAdapter);
    }

    /**
     * 已生成订单列表
     */
    private void initOrderCreatedRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        barBinding.orderCreatedRecycler.setLayoutManager(layoutManager);
        createdAdapter = new OrderCreatedAdapter(this);
        barBinding.orderCreatedRecycler.setAdapter(createdAdapter);
    }

    /**
     * 审核列表
     */
    private void initAuditRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        barBinding.orderAuditRecycler.setLayoutManager(layoutManager);
        auditAdapter = new OrderAuditAdapter(this);
        barBinding.orderAuditRecycler.setAdapter(auditAdapter);
    }


    @Override
    protected int getColor() {
        return R.color.bg_blue;
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
    public void showOrHideBackButton(boolean show) {
        int visibility = show ? View.VISIBLE : View.GONE;
        barBinding.ivBackTop.setVisibility(visibility);
    }

    /**
     * 采购单信息
     */
    @Override
    public void loadOrderDetailsInfo(OrderDetailsBean bean) {
        barBinding.basicLayout.tvDetailState.setText(bean.getStateString());
        barBinding.basicLayout.tvDetailNumber.setText(MessageFormat.format("采购单号：{0}", bean.getPurchaseNumber()));
        barBinding.basicLayout.tvDetailSupplier.setText(MessageFormat.format("供应商：{0}", bean.getSupplier()));
        barBinding.basicLayout.tvDetailPurchaser.setText(MessageFormat.format("采购人：{0}", bean.getBuyer()));
        barBinding.basicLayout.tvDetailDelay.setText(MessageFormat.format("延迟收货：{0}", bean.getReceiveDelay()));

        barBinding.userLayout.tvDetailName.setText(bean.getBuyerInfo().getName());
        barBinding.userLayout.tvDetaliPhone.setText(bean.getBuyerInfo().getPhoneNumber());
        barBinding.userLayout.tvDetailAddress.setText(bean.getBuyerInfo().getAddress());

        barBinding.layoutSettle.apartment.tvHead.setText("结算单位");
        barBinding.layoutSettle.tvDetailSettleApartment.setText(bean.getSettleApartment());
        barBinding.layoutSettle.invoice.tvHead.setText("发票信息");
        barBinding.layoutSettle.tvDetailInvoiceApartment.setText(bean.getInvoiceApartment());

        barBinding.commentLayout.tvComment.setText(bean.getComment());

    }

    /**
     * 审核信息
     */
    @Override
    public void loadOrderAuditInfo(ArrayList<OrderAuditBean> list) {

    }

    @Override
    public void loadProcessInfo(ArrayList<OrderProcessBean> list) {
        processAdapter.update(list,true);
    }

    @Override
    public void loadCreatedInfo(ArrayList<OrderCreatedBean> list) {
        createdAdapter.update(list,true);
    }

    @Override
    public void loadAuditInfo(ArrayList<OrderAuditBean> list) {
        auditAdapter.update(list, true);
    }
}
package com.rails.purchaseplatform.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.OrderVerifyBean;
import com.rails.lib_data.bean.ResultWebBean;
import com.rails.lib_data.contract.OrderVerifyContract;
import com.rails.lib_data.contract.OrderVerifyPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.DecimalUtil;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.adapter.OrderVerifyAdapter;
import com.rails.purchaseplatform.order.databinding.ActivityOrderVerityBinding;
import com.rails.purchaseplatform.order.pop.CompanyPop;
import com.rails.purchaseplatform.order.pop.GoodsPop;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 核实订单
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/27
 */
@Route(path = ConRoute.ORDER.ORDER_VERITY)
public class OrderVerityActivity extends ToolbarActivity<ActivityOrderVerityBinding> implements OrderVerifyContract.OrderVerifyView {

    private OrderVerifyAdapter adapter;
    private OrderVerifyContract.OrderVerifyPresenter presenter;

    @Override
    protected void initialize(Bundle bundle) {
        binding.titleBar.setTitleBar(R.string.order_verify)
                .setImgLeftRes(R.drawable.svg_back_black)
                .setShowLine(true);


        adapter = new OrderVerifyAdapter(this);
        barBinding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        barBinding.recycler.setAdapter(adapter);

        presenter = new OrderVerifyPresenterImpl(this, this);
        presenter.getVerifyOrder();
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
    public void getVerifyOrder(OrderVerifyBean bean) {
        adapter.update((ArrayList) bean.getCart().getShopList(), true);

        setAddress(bean.getAddress());
        setOrderInfo(bean);
    }


    /**
     * 设置收货地址
     *
     * @param bean
     */
    private void setAddress(AddressBean bean) {
        if (bean == null) {
            barBinding.btnAddress.setVisibility(View.VISIBLE);
            barBinding.llAddress.setVisibility(View.GONE);
        } else {
            barBinding.btnAddress.setVisibility(View.GONE);
            barBinding.llAddress.setVisibility(View.VISIBLE);

            barBinding.tvArea.setText(bean.getAddress());
            barBinding.tvAddress.setText(bean.getAddress());
            barBinding.tvPhone.setText(String.format(getResources().getString(R.string.order_verify_np), bean.getName(), bean.getPhone()));
        }
    }

    /**
     * 设置确认单其他信息
     */
    private void setOrderInfo(OrderVerifyBean bean) {
        if (bean == null)
            return;
        barBinding.rlGoods.setKey("延迟收货");
        barBinding.rlGoods.setContent(bean.getTime());


        barBinding.rlCompay.setKey(bean.getCompany());

        barBinding.rlBill.setContent("专票");
        barBinding.rlBill.setKey(bean.getCompany());

        barBinding.rlPay.setKey("账期支付");

        barBinding.rlTotal.setContent(String.valueOf(DecimalUtil.formatDouble(bean.getYearTotal())));
        barBinding.rlExtra.setContent(String.valueOf(DecimalUtil.formatDouble(bean.getYearExtra())));


        barBinding.tvTotal.setText(DecimalUtil.formatStrSize("¥ ", bean.getTotalPay(), "", 18));
        barBinding.tvTotalNum.setText(String.format(getResources().getString(R.string.order_verify_number), bean.getTotalNum()));
    }


    @Override
    protected void onClick() {
        super.onClick();
        barBinding.llAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ConRoute.ADDRESS.ADDRESS_SEL).navigation(OrderVerityActivity.this, 0);
            }
        });

        barBinding.btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ConRoute.ADDRESS.ADDRESS_SEL).navigation(OrderVerityActivity.this, 0);
            }
        });

        barBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = "{\"type\":1,\"msg\":\"评价成功\",\"btnleft\":\"查看采购单\",\"btnright\":\"立即评价\",\"urlleft\":\"/web/purchase/detail\",\"urlright\":\"/web/evalute\"}";
                ResultWebBean bean = JsonUtil.parseJson(json, ResultWebBean.class);
                ARouter.getInstance().build(ConRoute.MARKET.COMMIT_RESULT).withParcelable("bean", bean).navigation();
            }
        });

        barBinding.rlGoods.setOnClickListener(v -> {
            GoodsPop pop = new GoodsPop();
            pop.setGravity(Gravity.BOTTOM);
            pop.setType(BasePop.MATCH_WRAP);
            pop.show(getSupportFragmentManager(), "goods");
        });

        barBinding.rlCompay.setOnClickListener(v -> {
            CompanyPop pop = new CompanyPop();
            pop.setGravity(Gravity.BOTTOM);
            pop.setType(BasePop.MATCH_WRAP);
            pop.show(getSupportFragmentManager(), "company");
        });

        barBinding.rlBill.setOnClickListener(v -> {
            startIntent(this, InvoiceActivity.class, null, 1);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            if (data == null)
                return;
            AddressBean bean = (AddressBean) data.getExtras().getSerializable("bean");
            setAddress(bean);
        }
    }

}

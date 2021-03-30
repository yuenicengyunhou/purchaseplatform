package com.rails.purchaseplatform.order.activity;


import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.OrderBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.utils.JsonUtil;
import com.rails.purchaseplatform.order.databinding.ActivityOrderBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;

@Route(path = ConRoute.ORDER.ORDER_MAIN)
public class OrderActivity extends BaseErrorActivity<ActivityOrderBinding> {


    @Override
    protected void initialize(Bundle bundle) {
        Type type = new TypeToken<ArrayList<OrderBean>>() {
        }.getType();
        ArrayList<OrderBean> beans = JsonUtil.parseJson(this, "orderList.json", type);

    }

    @Override
    protected int getColor() {
        return 0;
    }

    @Override
    protected boolean isSetSystemBar() {
        return false;
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
        binding.ibFilter.setOnClickListener(v -> Toast.makeText(this, "暂时没有内容哦", Toast.LENGTH_SHORT).show());
    }
}
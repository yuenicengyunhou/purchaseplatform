package com.rails.purchaseplatform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.rails.purchaseplatform.Bean.AddressBean;
import com.rails.purchaseplatform.R;
import com.rails.purchaseplatform.adapter.AdderssAdapter;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.databinding.ActivityAddressBinding;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends ToolbarActivity<ActivityAddressBinding> {

    private List<AddressBean> list = new ArrayList();
    private AdderssAdapter adderssAdapter;


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

        binding.titleBar.setImgLeftRes(R.drawable.jiantou)
                .setTitle("收货地址管理").setShowLine(true);

        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            list.add(new AddressBean("张三" + i, "12345678909", "北京市海淀区怡和八号院"));
        }
        adderssAdapter.notifyDataSetChanged();
    }

    private void initView() {

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        barBinding.recyclerAddress.setLayoutManager(linearLayoutManager);
        adderssAdapter = new AdderssAdapter(list, this);
        barBinding.recyclerAddress.setAdapter(adderssAdapter);
        barBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddressActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddressActivity.this, RaddressActivity.class);
                startActivity(intent);
            }
        });


    }
}
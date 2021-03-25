package com.rails.purchaseplatform.address.ui;

import android.os.Bundle;
import android.widget.Button;

import com.rails.purchaseplatform.address.databinding.ActivityRaddressBinding;
import com.rails.purchaseplatform.common.base.ToolbarActivity;


public class AddressAddActivity extends ToolbarActivity<ActivityRaddressBinding> {

    private Button btnOk;


    @Override
    protected int getColor() {
        return  android.R.color.white;
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
//        binding.titleBar.setImgLeftRes(R.drawable.jiantou)
//                .setTitle("+添加收货地址").setShowLine(true);

    }

  /*  private void initView() {
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RaddressActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RaddressActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }*/
}
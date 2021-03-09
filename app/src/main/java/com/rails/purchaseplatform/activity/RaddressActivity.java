package com.rails.purchaseplatform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rails.purchaseplatform.R;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.databinding.ActivityRaddressBinding;

public class RaddressActivity extends ToolbarActivity<ActivityRaddressBinding> {

    private Button btnOk;

  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.);
      //  initView();
    }
*/
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
        binding.titleBar.setImgLeftRes(R.drawable.jiantou)
                .setTitle("+添加收货地址").setShowLine(true);

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
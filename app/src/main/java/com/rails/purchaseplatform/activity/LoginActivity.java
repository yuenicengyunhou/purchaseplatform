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

public class LoginActivity extends AppCompatActivity {

    private ImageView ivImg;
    private TextView tvTitle;
    private LinearLayout lay1;
    private TextView tvId;
    private EditText etId;
    private LinearLayout lay2;
    private TextView tvPwd;
    private EditText etPwd;
    private TextView btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        ivImg = (ImageView) findViewById(R.id.iv_img);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        lay1 = (LinearLayout) findViewById(R.id.lay1);
        tvId = (TextView) findViewById(R.id.tv_id);
        etId = (EditText) findViewById(R.id.et_id);
        lay2 = (LinearLayout) findViewById(R.id.lay2);
        tvPwd = (TextView) findViewById(R.id.tv_pwd);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        btnLogin= findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });
    }
}
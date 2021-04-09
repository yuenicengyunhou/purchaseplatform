package com.rails.purchaseplatform.user.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.rails.purchaseplatform.framwork.base.BaseErrorActivity;
import com.rails.purchaseplatform.user.databinding.ActivityRetrievePasswordBinding;

import java.util.regex.Pattern;

/**
 * @author ZhangXiaofu
 * @version 1
 * <p>
 * 找回密码页面
 * 输入用户名和邮箱进行验证
 * @since 2021.03.28
 */
public class RetrievePasswordActivity extends BaseErrorActivity<ActivityRetrievePasswordBinding> {

    @Override
    protected void initialize(Bundle bundle) {

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
    protected void onClick() {
        super.onClick();
        binding.ibBack.setOnClickListener(v -> finish());

        binding.btnConfirm.setOnClickListener(v -> {
            String username = binding.etUserNameInput.getText().toString().trim(),
                    email = binding.etEmailInput.getText().toString().trim();

            if (!matchUsername(username)) {
                Toast.makeText(this, "用户名格式错误", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!matchEmail(email)) {
                Toast.makeText(this, "邮箱格式错误", Toast.LENGTH_SHORT).show();
                return;
            }

            Bundle bundle = new Bundle();
            Intent intent = new Intent(this, RetrievePasswordConfirmActivity.class);
            startActivity(intent);
            finish();
        });
    }

    /**
     * 验证邮箱格式
     *
     * @param email 邮箱
     * @return 不为空并且匹配格式返回true，否则返回false
     */
    private boolean matchEmail(String email) {
        // TODO: 2021/3/28 邮箱正则
        Pattern pattern = Pattern.compile("^\\d{6}$");
//        return !email.equals("") && pattern.matcher(email).matches();
        return true;
    }

    /**
     * 验证用户名格式
     *
     * @param username 用户名
     * @return 不为空并且匹配格式返回true，否则返回false
     */
    private boolean matchUsername(String username) {
        // TODO: 2021/3/28 用户名正则
        Pattern pattern = Pattern.compile("^\\d{6}$");
//        return !username.equals("") && pattern.matcher(username).matches();
        return true;
    }
}
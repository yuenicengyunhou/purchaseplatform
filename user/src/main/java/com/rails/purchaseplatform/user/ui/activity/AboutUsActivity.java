package com.rails.purchaseplatform.user.ui.activity;


import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.user.SharePop;
import com.rails.purchaseplatform.user.databinding.ActivityAboutUsBinding;

@Route(path = ConRoute.USER.USER_ABOUT_US)
public class AboutUsActivity extends BaseErrorActivity<ActivityAboutUsBinding> {

    private SharePop pop;

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
        setSupportActionBar(binding.toolbar);
//        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)binding.areaQrCode.getLayoutParams();

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int screenWidth = ScreenSizeUtil.getScreenWidth(this);
        params.width = screenWidth;
        params.height=(screenWidth*310)/375;
        params.topToBottom=binding.toolbar.getId();
        binding.areaQrCode.setLayoutParams(params);
        binding.ibBack.setOnClickListener(v -> finish());
        binding.ivShare.setOnClickListener(v -> showPop());
        binding.tvContentPhone.setOnClickListener(v -> toPhonePage());
        binding.tvContentWebsite.setOnClickListener(v -> toWebPage());
        binding.tvContentMail.setOnClickListener(v -> copyToClipboard());
    }

    private void copyToClipboard() {
        ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", "wzcg@cric-china.com.cn");
        manager.setPrimaryClip(clipData);
        ToastUtil.showCenter(this, "已复制邮箱地址");
    }

    private void toWebPage() {
        try {
        Uri uri = Uri.parse("https://mall.95306.cn/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            ToastUtil.showCenter(this,"该设备不支持浏览器功能");
        }
    }

    private void toPhonePage() {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "010-95306-8"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            ToastUtil.showCenter(this,"该设备不支持拨号功能");
        }

    }

    private void showPop() {
        if (null == pop) {
            pop = new SharePop();
            pop.setGravity(Gravity.BOTTOM);
            pop.setType(BasePop.MATCH_WRAP);
        }
        pop.show(getSupportFragmentManager(), "share");
    }
}
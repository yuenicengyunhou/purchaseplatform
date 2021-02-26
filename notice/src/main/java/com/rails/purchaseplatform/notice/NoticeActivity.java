package com.rails.purchaseplatform.notice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.notice.databinding.ActivityNoticeBinding;

public class NoticeActivity extends ToolbarActivity<ActivityNoticeBinding> {


    @Override
    protected void initialize(Bundle bundle) {
        //显示头部信息
        binding.titleBar
                .setTitleBar(R.string.app_name)
                .setImgLeftRes(R.drawable.svg_arrow_white)
                .setShowLine(true);

        //页面内容信息
        barBinding.tvDesc.setText("hello world");
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
    public void onFailure(String errorMsg) {

    }
}
package com.rails.purchaseplatform;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.databinding.ActivitySplashBinding;

import androidx.annotation.NonNull;

/**
 * 启动页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/5/12
 */
public class SplashActivity extends BaseErrorActivity<ActivitySplashBinding> {

    private final static int NEXT_IN = 10;

    Handler mHandler = new Handler(msg -> {
        switch (msg.what) {
            case NEXT_IN:
                startNext();
                break;
            default:
                break;
        }
        return false;
    });


    private void startNext() {
        startIntent(MainActivity.class);
        finish();
    }

    @Override
    protected void initialize(Bundle bundle) {
        mHandler.sendEmptyMessageDelayed(NEXT_IN, 3 * 1000);
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
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }
}

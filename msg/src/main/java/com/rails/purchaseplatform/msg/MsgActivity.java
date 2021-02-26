package com.rails.purchaseplatform.msg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.msg.databinding.ActivityMsgBinding;

/**
 * 消息
 */
public class MsgActivity extends ToolbarActivity<ActivityMsgBinding> {


    @Override
    protected int getColor() {
        // TODO: 2021/2/25 该方法是控制顶部状态栏沉浸式颜色的 ，回传颜色
        return 0;
    }

    @Override
    protected boolean isSetSystemBar() {
        // TODO: 2021/2/25 该方法控制是否能操作状态栏颜色 
        return false;
    }

    @Override
    protected boolean isBindEventBus() {
        // TODO: 2021/2/25 页面传递数据，如果使用EventBus，要开启此方法 
        return false;
    }

    @Override
    protected void initialize(Bundle bundle) {
        // TODO: 2021/2/25 页面操作的处理 
    }

    @Override
    public void onFailure(String errorMsg) {

    }
}
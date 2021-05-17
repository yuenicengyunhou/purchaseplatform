package com.rails.purchaseplatform;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.ResultWebBean;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.lib_data.contract.UserToolContract;
import com.rails.lib_data.contract.UserToolPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.databinding.ActivityMainBinding;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.framwork.base.BaseActManager;
import com.rails.purchaseplatform.framwork.bean.BusEvent;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;


/**
 * tabs 页面
 */
@Route(path = ConRoute.RAILS.MAIN)
public class MainActivity extends BaseErrorActivity<ActivityMainBinding>  implements UserToolContract.UserToolView {

    private UserToolContract.UserToolPresenter toolPresenter;

    private ResultWebBean webBean;
    // 定义一个变量，来标识是否退出
    private boolean isExit = false;

    Handler mHandler = new Handler(msg -> {
        isExit = false;
        return false;
    });


    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        webBean = extras.getParcelable("webBean");
    }

    @Override
    protected void initialize(Bundle bundle) {
        if (webBean != null) {
            EventBus.getDefault().post(new BusEvent<ResultWebBean>(webBean, ConRoute.EVENTCODE.MAIN_CODE));
        }

        toolPresenter = new UserToolPresenterImpl(this, this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        UserInfoBean bean = PrefrenceUtil.getInstance(this).getBean(ConShare.USERINFO, UserInfoBean.class);
        if (bean != null) {
            toolPresenter.queryResourceButton(bean.getId(), bean.getAccountType());
            toolPresenter.queryResource(bean.getId(), bean.getAccountType());
        }
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
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtil.show(this, "再按一次退出程序");
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            BaseActManager.getInstance().clear();
            finish();
            System.exit(0);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    @Override
    public void getUserStatictics(UserStatisticsBean bean) {

    }

    @Override
    public void getUserInfoStatictics(UserStatisticsBean bean) {

    }

    @Override
    public void checkPermissions(UserStatisticsBean bean) {

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
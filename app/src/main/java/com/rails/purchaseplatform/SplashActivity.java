package com.rails.purchaseplatform;

import android.os.Bundle;
import android.os.Handler;
import android.text.Html;

import com.rails.lib_data.bean.VersionBean;
import com.rails.lib_data.contract.VersionContract;
import com.rails.lib_data.contract.VersionPresenterImpl;
import com.rails.purchaseplatform.common.base.BaseErrorActivity;
import com.rails.purchaseplatform.databinding.ActivitySplashBinding;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.utils.StringUtil;
import com.rails.purchaseplatform.framwork.utils.SystemUtil;

import constant.UiType;
import listener.OnBtnClickListener;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

/**
 * 启动页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/5/12
 */
public class SplashActivity extends BaseErrorActivity<ActivitySplashBinding> implements VersionContract.VersionView {

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

    private VersionContract.VersionPresenter presenter;
    private String versionCode;


    private void startNext() {
        startIntent(MainActivity.class);
        finish();
    }

    @Override
    protected void initialize(Bundle bundle) {
        versionCode = SystemUtil.getVersionName(this);
        presenter = new VersionPresenterImpl(this, this);
        presenter.getVersion();
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
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        super.onDestroy();

    }

    @Override
    public void getVersion(VersionBean versionBean) {
        int isUpdate = StringUtil.compareVersion(versionBean.getLastVersionCode(), versionCode);
        if (isUpdate < 1) {
            //todo 倒计时，跳转页面
            mHandler.sendEmptyMessageDelayed(NEXT_IN, 3 * 1000);
        } else {
            // TODO: 2019/5/6 版本更新
            versionDialog(versionBean);
        }
    }

    /**
     * @param bean
     */
    private void versionDialog(VersionBean bean) {
        //显示二维码
        try {
            UpdateConfig updateConfig = new UpdateConfig();
            updateConfig.setCheckWifi(false);
            updateConfig.setNeedCheckMd5(true);
            updateConfig.setForce(bean.getIsForce());

            UiConfig uiConfig = new UiConfig();
            uiConfig.setUiType(UiType.SIMPLE);
            uiConfig.setCancelBtnTextColor(getResources().getColor(R.color.font_gray));
            uiConfig.setUpdateBtnTextColor(getResources().getColor(R.color.font_blue));
            UpdateAppUtils
                    .getInstance()
                    .apkUrl(bean.getUrl())
                    .updateTitle("版本更新")
                    .updateContent(Html.fromHtml(bean.getRemork()))
                    .setCancelBtnClickListener(new OnBtnClickListener() {
                        @Override
                        public boolean onClick() {
                            mHandler.sendEmptyMessageDelayed(NEXT_IN, 1000);
                            return false;
                        }
                    })
                    .uiConfig(uiConfig)
                    .updateConfig(updateConfig)
                    .update();

        } catch (Exception e) {

        }
    }


    @Override
    public void onError(ErrorBean errorBean) {
        super.onError(errorBean);
        mHandler.sendEmptyMessageDelayed(NEXT_IN, 3 * 1000);
    }
}

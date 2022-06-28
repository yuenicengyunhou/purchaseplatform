package com.rails.purchaseplatform.user.ui.activity;


import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rails.lib_data.bean.VersionBean;
import com.rails.lib_data.contract.VersionContract;
import com.rails.lib_data.contract.VersionPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.framwork.utils.StringUtil;
import com.rails.purchaseplatform.framwork.utils.SystemUtil;
import com.rails.purchaseplatform.user.R;
import com.rails.purchaseplatform.user.databinding.ActivityCerErrorBinding;

import java.text.MessageFormat;

import constant.UiType;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

@Route(path = ConRoute.USER.SSL_EXCEPTION)
public class CerErrorActivity extends ToolbarActivity<ActivityCerErrorBinding> implements VersionContract.VersionView {

    private String versionName;
    private VersionBean mVersionBean;

    @Override
    protected int getColor() {
        return R.color.white;
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
        versionName = SystemUtil.getVersionName(this);
        VersionContract.VersionPresenter presenter = new VersionPresenterImpl(this, this);
        barBinding.tvDownload.setOnClickListener(view -> versionDialog(mVersionBean));
        presenter.getVersion();
    }

    @Override
    public void getVersion(VersionBean versionBean) {
//        versionBean.
        this.mVersionBean = versionBean;
        String lastVersionCode = versionBean.getLastVersionCode();
        int i = StringUtil.compareVersion(lastVersionCode, "1.1.4");
        barBinding.tvVersion.setText(MessageFormat.format("当前版本：V{0}  最新版本：V{1}", versionName, lastVersionCode));
        if (i < 1) {
            barBinding.tvDownload.setVisibility(View.INVISIBLE);
            barBinding.tvQr.setVisibility(View.INVISIBLE);
            barBinding.ivQr.setVisibility(View.INVISIBLE);
            barBinding.relative.setBackgroundColor(getResources().getColor(R.color.white));
            barBinding.tvInstrument.setText("您已是最新版本，请拨打客服电话或在官方沟通群中联系客服人员");
        }
    }

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
                    .setCancelBtnClickListener(() -> false)
                    .uiConfig(uiConfig)
                    .updateConfig(updateConfig)
                    .update();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
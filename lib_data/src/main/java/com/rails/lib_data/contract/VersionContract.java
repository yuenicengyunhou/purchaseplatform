package com.rails.lib_data.contract;

import com.rails.lib_data.bean.VersionBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

/**
 * 版本更新
 * @author： sk_comic@163.com
 * @date: 2021/5/21
 */
public interface VersionContract {

    interface VersionView extends BaseView{

        /**
         * 获取版本是否有更新
         */
        void getVersion(VersionBean versionBean);
    }



    interface VersionPresenter{

        /**
         * 获取版本是否有更新
         */
        void getVersion();
    }
}

package com.rails.lib_data.contract;

import com.rails.purchaseplatform.framwork.base.BaseView;

/**
 * 商城首页 数据请求contract
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public interface MarketIndexContract {

    interface MarketIndexView extends BaseView {

    }


    interface MarketIndexPresenter {

        /**
         * 获取商城首页所有信息
         */
        void getIndexInfo();
    }
}

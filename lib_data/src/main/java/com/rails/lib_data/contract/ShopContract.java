package com.rails.lib_data.contract;

import com.rails.purchaseplatform.framwork.base.BaseView;

/**
 * 商城--店铺
 * @author： sk_comic@163.com
 * @date: 2021/3/26
 */
public interface ShopContract {

    interface ShopView extends BaseView{

    }



    interface ShopPresenter{


        /**
         *
         * @param id
         */
        void getShopDetails(String id);

    }
}

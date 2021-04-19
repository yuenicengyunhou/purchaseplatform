package com.rails.lib_data.contract;

import com.rails.lib_data.bean.SearchResultBean;
import com.rails.lib_data.bean.ShopVO;
import com.rails.lib_data.bean.showOnApp.BaseItemAttribute;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

/**
 * 商城--店铺
 * author： sk_comic@163.com
 * date: 2021/3/26
 */
public interface ShopContract {

    interface ShopView extends BaseView{

        /**
         * 加载店铺商品列表
         */
        void loadShopProductList(ArrayList<BaseItemAttribute> list);


        /**
         * 加载店铺信息
         */
        void loadShopInfo(ShopVO shop);

    }



    interface ShopPresenter{


        /**
         *
         * param 店铺信息详情
         */
        void getShopDetails(long id);

    }
}

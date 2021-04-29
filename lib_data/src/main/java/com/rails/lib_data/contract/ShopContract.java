package com.rails.lib_data.contract;

import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.shop.ResultListBean;
import com.rails.lib_data.bean.shop.ShopInfoBean;
import com.rails.lib_data.bean.shop.ShopRecommendBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * 商城--店铺
 * author： sk_comic@163.com
 * date: 2021/3/26
 */
public interface ShopContract {

    interface ShopView extends BaseView {

        /**
         * 加载店铺商品列表
         */
        void loadShopProductList(ArrayList<ResultListBean> list);


        /**
         * 加载店铺信息
         */
        void loadShopInfo(ShopInfoBean shop);

        /**
         * 筛选弹窗
         */
        void loadFilter(ArrayList<SearchFilterBean> filterBeans);

    }


    interface ShopPresenter {


        /**
         * param 店铺信息详情
         */
        void getShopDetails(String id);

        /**
         * 店铺推荐商品列表
         */
        void getShopItemList(long platformId, String shopInfoId, int page, int pageSize,String orderColumn,String orderType,ArrayList<SearchFilterBean> filterBeans);

    }
}

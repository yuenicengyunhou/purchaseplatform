package com.rails.lib_data.contract;

import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.CategorySubBean;
import com.rails.lib_data.bean.MarketIndexBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

/**
 * 商城首页 数据请求contract
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/9
 */
public interface MarketIndexContract {


    interface MarketIndexView extends BaseView {

        /**
         * 获取首页推荐产品列表
         *
         * @param beans
         */
        void getRecProducts(ArrayList<ProductRecBean> beans);


        /**
         * 获取banner列表
         *
         * @param bannerBeans
         */
        void getBanners(ArrayList<BannerBean> bannerBeans);


        /**
         * 获取推荐品牌
         *
         * @param brandBeans
         */
        void getBrands(ArrayList<BrandBean> brandBeans);


        /**
         * 获取推荐分类
         *
         * @param beans
         */
        void getRecCategorys(ArrayList<CategorySubBean> beans);


        /**
         * 获取首页数据
         *
         * @param bean
         */
        void getIndexInfo(MarketIndexBean bean);
    }


    interface MarketIndexPresenter {


        /**
         * 获取首页推荐产品列表
         *
         * @param isDialog 是否显示加载窗口
         * @param isHot    是否显示热门产品
         */
        void getRectProducts(boolean isDialog, boolean isHot);


        /**
         * 获取banner列表
         */
        void getBanners();

        /**
         * 获取推荐品牌
         */
        void getBrands();


        /**
         * 获取推荐分类
         */
        void getRecCategorys();

        /**
         * 首页列表聚合
         */
        void getMarketIndexInfo(boolean isDialog);

    }


}

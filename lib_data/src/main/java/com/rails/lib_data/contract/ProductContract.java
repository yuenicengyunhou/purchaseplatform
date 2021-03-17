package com.rails.lib_data.contract;

import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.CategoryBean;
import com.rails.lib_data.bean.CategorySubBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

/**
 * 商城产品契约类
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/8
 */
public interface ProductContract {


    interface ProductView extends BaseView {

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
    }


    interface ProductPresenter {


        /**
         * 获取首页推荐产品列表
         *
         * @param isDialog 是否显示加载窗口
         */
        void getRectProducts(boolean isDialog);


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

    }
}

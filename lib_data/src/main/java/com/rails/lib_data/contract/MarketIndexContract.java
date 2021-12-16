package com.rails.lib_data.contract;

import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.CategorySubBean;
import com.rails.lib_data.bean.MarketIndexBean;
import com.rails.lib_data.bean.ProductBean;
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
        void getHotProducts(ArrayList<ProductBean> beans);


        /**
         * 获取首页数据
         *
         * @param bean
         */
        void getIndexInfo(MarketIndexBean bean);


        /**
         * 获取品牌列表排行
         * @param brandBeans
         * @param hasMore
         * @param isClear
         */
        void getBrands(ArrayList<BrandBean> brandBeans,boolean hasMore, boolean isClear);


        /**
         * 获取单个楼层列表排行
         * @param productBeans
         * @param hasMore
         * @param isClear
         */
        void getFloorProducts(ArrayList<ProductBean> productBeans,boolean hasMore, boolean isClear);


        /**
         * 获取楼层数据
         * @param productBeans
         */
        void getFloors(ArrayList<ProductRecBean> productBeans);

    }


    interface MarketIndexPresenter {


        /**
         * 获取首页推荐产品列表
         *
         * @param isDialog 是否显示加载窗口
         */
        void getHotProducts(boolean isDialog, int page, String pageSize);


        /**
         * 首页列表聚合
         */
        void getMarketIndexInfo(boolean isCache, boolean isDialog);


        /**
         * 获取楼层数据
         */
        void getFloors(boolean isCache);


        /**
         * 获取排行列表
         *
         * @param isDialog
         * @param page
         * @param pageSize
         * @param paramType 1代表七日品牌排名，0代表七日品牌所对应店铺排名 (必填项)
         * @param brandId 品牌ID
         * @param categoryId 分类id   热销商品为 "1"， 热销品牌为 ""
         */
        void getRanks(boolean isDialog, int page, String pageSize,String paramType, String brandId, String categoryId);

    }


}

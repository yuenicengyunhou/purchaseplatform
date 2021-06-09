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
    }


    interface MarketIndexPresenter {


        /**
         * 获取热门推荐产品
         *
         * @param isDialog 是否显示加载窗口
         */
        void getHotProducts(boolean isDialog, int page,String pageSize);



        /**
         * 首页列表聚合
         */
        void getMarketIndexInfo(boolean isCache, boolean isDialog);

    }


}

package com.rails.lib_data.contract;

import com.rails.lib_data.bean.ProductBean;
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
         * 获取热门商品列表
         *
         * @param productBeans
         * @param hasMore      是否有更多
         * @param isClear      是否清除
         */
        void getHotProducts(ArrayList<ProductBean> productBeans, boolean hasMore, boolean isClear);

    }


    interface ProductPresenter {


        /**
         * 获取热门推荐产品
         *
         * @param isDialog 是否显示加载窗口
         */
        void getHotProducts(boolean isDialog, int page);


    }
}

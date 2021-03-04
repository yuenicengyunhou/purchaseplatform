package com.rails.lib_data.contract;

import com.rails.lib_data.bean.CategoryRootBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

/**
 * 商城 分类contract
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/3
 */
public interface CategoryContract {

    interface CategoryView extends BaseView {

        /**
         * 获取列表
         *
         * @param beans
         */
        void getCategorys(ArrayList<CategoryRootBean> beans);
    }


    interface CategoryPresenter {

        /**
         * @param isReadCache 是否读取缓存
         *                    获取商城分类
         */
        void getCategorys(boolean isReadCache);
    }
}

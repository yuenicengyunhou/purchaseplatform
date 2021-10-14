package com.rails.lib_data.contract;

import com.rails.purchaseplatform.framwork.base.BaseView;

/**
 * 流量统计接口
 *
 * @author： sk_comic@163.com
 * @date: 2021/8/13
 */
public interface StatisticContract {

    interface StatisticView extends BaseView {

    }

    interface StatisticPresenter {

        /**
         * 获取首页访问量
         * <p>
         * platformId    平台id 默认20
         * visitorType   参观者类型      0：匿名用户   1：登陆用户
         * cookieFinger  cookie指纹，就是当前时间戳
         *
         * @param materialType 物资类型  0：通用   1：专用（商品详情用）
         *                     sourceType    来源信息 0：pc    1：app
         * @param itemShopId
         * @param skuId
         */
        void getVisitors(String materialType, String itemShopId, String skuId);
    }

}

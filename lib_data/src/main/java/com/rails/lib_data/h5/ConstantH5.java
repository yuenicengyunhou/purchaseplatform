package com.rails.lib_data.h5;

public interface ConstantH5 {
    /**
     * BaseUrl
     */
    String BASE_H5_URL = "http://172.28.22.92:3000/purchase-android-web/#/";
//    String BASE_H5_URL = "http://172.28.20.109:3000/";

    /**
     * 评价
     */
    interface EvaluateEg {
        String EVALUATE_EG = BASE_H5_URL + "evaluate_eg";
    }

    /**
     * 修改密码
     */
    String MODIFYPAW = BASE_H5_URL + "passwordEdit";

    /**
     * 我的收藏
     */
    String COLLECT = BASE_H5_URL + "collection";

    /**
     * 浏览记录
     */
    String BROWSE = BASE_H5_URL + "history";

    /**
     * 消息列表
     */
    interface Messages {
        String MSG_URL = BASE_H5_URL + "messageList";
    }

    interface ProductDetails {
        /**
         * 商品信息
         */
        String PRODUCT_INFO = BASE_H5_URL + "productInfo";

        /**
         * 包装列表
         */
        String PACKAGE_LIST = BASE_H5_URL + "packingList";

        /**
         * 售后服务
         */
        String SERVICES = BASE_H5_URL + "serviceOrPartner?service=1";

        /**
         * 推荐单位
         */
        String RECOMMENDS = BASE_H5_URL + "serviceOrPartner?service=0";
    }
}

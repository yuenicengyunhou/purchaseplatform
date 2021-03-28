package com.rails.purchaseplatform.common;

/**
 * 路由常数
 *
 * @author： sk_comic@163.com
 * @date: 2020/12/22
 */
public class ConRoute {


    public static final class RAILS {

        public final static String MAIN = "/rails/main";
    }

    //用户
    public static final class USER {

        //登录
        public final static String LOGIN = "/user/login";

        //注册
        public final static String REGISTER = "/user/register";
    }

    // 通用COMMON
    public static final class COMMON {

        // 搜索
        public final static String SEARCH = "/common/SearchActivityX";
    }

    // 商品包 market
    public static final class MARKET {
        // 搜索结果页面
        public final static String SEARCH_RESULT = "/market/SearchResultActivity";
        //提交结果页面
        public final static String COMMIT_RESULT = "/market/result";
    }

    //采购单
    public static final class ORDER {
        //列表
        public final static String ORDER_MAIN = "/order/mian";
        //详情页
        public final static String ALL = "/order/all";
        //核对订单
        public final static String ORDER_VERITY = "/order/verity";
    }


    /**
     * 消息相关页面
     */
    public final static class MSG {
        //消息首页
        public final static String MSG_MAIN = "/msg/main";
    }


    /**
     * 地址管理页面
     */
    public final static class ADDRESS {

        public final static String ADDRESS_MAIN = "/address/main";

        public final static String ADDRESS_SEL = "/address/select";

    }


    public final static class WEB {

        public final static String BASEURL = "http://172.28.22.92:3000/purchase-android-web/#/";

        //评价
        public final static String EVALUTE = BASEURL + "evaluate_eg";
        //修改密码
        public final static String MODIFYPAW = BASEURL + "passwordEdit";
        //我的收藏
        public final static String COLLECT = BASEURL + "collection";
        //浏览记录
        public final static String BROWSE = BASEURL + "history";

        public final static String MSG = BASEURL + "messageList";

//        public final static String EVALUTE = BASEURL + "evaluate";
//        public final static String EVALUTE = BASEURL + "evaluate";
//        public final static String EVALUTE = BASEURL + "evaluate";

    }


}

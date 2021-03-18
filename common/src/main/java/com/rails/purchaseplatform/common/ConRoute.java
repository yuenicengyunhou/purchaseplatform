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
    }

    //采购单
    public static final class ORDER {
    //列表
        public final static String ORDER_MAIN = "/order/mian";
        //详情页
        public final static String ALL = "/order/all";
    }


}

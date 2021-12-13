package com.rails.purchaseplatform.common;

import com.rails.purchaseplatform.framwork.http.observer.BaseRetrofit;

/**
 * 路由常数
 *
 * @author： sk_comic@163.com
 * @date: 2020/12/22
 */
public class ConRoute {


    public static final class EVENTCODE {

        //首页切换
        public static final String MAIN_CODE = "0x001";

        //新建地址，地区切换
        public static final String AREA_CODE = "0x002";

    }


    public static final class RAILS {

        public final static String MAIN = "/rails/main";
    }

    //用户
    public static final class USER {

        //登录
        public final static String LOGIN = "/user/login";

        //注册
        public final static String REGISTER = "/user/register";

        // 找回密码
        public final static String RETRIEVE_PASSWORD = "/user/password";
        //更改密码
        public final static String USER_MODIFY_PAW = "/user/modify_paw";
        public final static String USER_ABOUT_US = "/user/about_us";
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

        // 商品详情页
        public final static String PRODUCT_DETAIL = "/market/detail";

        // 店铺详情页
        public final static String SHOP_DETAILS = "/market/shopDetails";

        // 商品图片放大
        public final static String IMAGE_ZOOM = "/market/image_zoom";
    }

    //采购单
    public static final class ORDER {
        //列表
        public final static String ORDER_MAIN = "/order/mian";
        //详情页
        public final static String ALL = "/order/all";
        //核对订单
        public final static String ORDER_VERITY = "/order/verity";
        //采购单详情
        public final static String ORDER_DETAILS = "/order/details";
        //妥投文件
        public final static String ORDER_DELIVER = "/order/deliver";
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
        //地址管理页面列表
        public final static String ADDRESS_MAIN = "/address/main";
        //选择地址
        public final static String ADDRESS_SEL = "/address/select";
        //地图
        public final static String ADDRESS_MAP = "/address/map";

    }


    public final static class WEB {

        //通用
        public final static String WEB_COMMON = "/web/common";
        //我的收藏
        public final static String WEB_COLLECT = "/web/collect";
        //浏览记录
        public final static String WEB_BROWSE = "/web/browse";

        //我的消息
        public final static String WEB_MSG = "/web/msg";
        //评价
        public final static String WEB_EVALUTE = "/web/evalute";
        //订单详情
        public final static String WEB_ORDER_DETAIL = "/web/order/detail";
        //审核单
        public final static String WEB_APPROVAL = "/web/approval";
        //采购单详情
        public final static String WEB_PURCHASE_DETAIL = "/web/purchase/detail";
        //排行榜说明
        public final static String WEB_RANK_QUESTION = "/web/rankquestion";
    }


    public final static class WEB_URL {

//        public final static String BASEURL = "http://172.28.22.92:3000/purchase-android-web/#/";

        // WangYuhang - local
//        public final static String BASEURL = "http://172.28.22.140:3000/purchase-android-web/#/";
//        public final static String BASEURL = "http://192.168.69.214/";
//        public final static String BASEURL = "http://192.168.69.214/purchase-android-web";

        // H5地址
//        public final static String BASEURL = "https://shop.rails.cn/purchase-android-web/#/";
//        public final static String BASEURL = "https://mall.95306.cn/purchase-android-web/#/";
        public final static String BASEURL = BaseRetrofit.isDebug
                ? "https://shop.rails.cn/purchase-android-web/#/"   // DEBUG
                : "https://mall.95306.cn/purchase-android-web/#/";  // RELEASE


        public final static String HOME = BASEURL + "home";

        // 评价
        public final static String EVALUTE = BASEURL + "evaluate";

        // 修改密码
        public final static String MODIFYPAW = BASEURL + "passwordEdit";

        // 我的收藏
        public final static String COLLECT = BASEURL + "goodsCollection";

        // 浏览记录
        public final static String BROWSE = BASEURL + "history";

        // 消息列表
        public final static String MSG = BASEURL + "messageList";


        //订单详情
        public final static String ORDER_DETAIL = BASEURL + "orderDetails";

        //采购单详情
        public final static String PURCHASE_DETAIL = BASEURL + "purOrderDetails";


        // 订单详情
        public final static String ORDER_SUB_DETAIL = BASEURL + "orderDetails";

        // 待审核
        public final static String APPROVAL = BASEURL + "approvalList";

        // 已驳回   // 已通过
        public final static String TURN_DOWN_LIST = BASEURL + "kindsApproval";

        //排行榜说明
        public final static String RANK_QUESTION = BASEURL + "listDescription";

    }


    public final static class CODE {
        //登录编号
        public final static int LOGIN_CODE = 0x99;
    }


}

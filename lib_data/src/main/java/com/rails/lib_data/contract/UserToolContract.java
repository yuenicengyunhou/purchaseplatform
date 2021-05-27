package com.rails.lib_data.contract;

import com.rails.lib_data.bean.AuthorBean;
import com.rails.lib_data.bean.AuthorButtonBean;
import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

/**
 * 用户相关工具
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/20
 */
public interface UserToolContract {

    interface UserToolView extends BaseView {

        /**
         * 获取我的页面统计的数据
         *
         * @param bean
         */
        void getUserStatictics(UserStatisticsBean bean);


        void getUserInfoStatictics(UserStatisticsBean bean);

        /**
         * 权限设置
         *
         * @param bean
         */
        void checkPermissions(UserStatisticsBean bean);

        /**
         * 获取菜单和按钮权限
         *
         * @param authorBean
         */
        void getAuthor(AuthorBean authorBean);
    }


    interface UserToolPresenter {

        /**
         * 获取我的页面统计的数据（下半部分）
         *
         */
        void getUserStatictics();


        /**
         * 获取用户我的页面上半部分数据
         *
         */
        void getUserInfoStatictics();


        /**
         * 检查是否有权限
         */
        void checkPermissions();


        /**
         * 检查个人菜单是否可用
         * 检查个人中心按钮是否可用
         */
        void queryAuthor();

    }

}


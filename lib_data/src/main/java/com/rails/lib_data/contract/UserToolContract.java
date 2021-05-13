package com.rails.lib_data.contract;

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
         * 权限
         *
         * @param isPurchase
         * @param isApprove
         * @param isCollect
         * @param isTrack
         */
        void getAuthor(boolean isPurchase, boolean isApprove, boolean isCollect, boolean isTrack);
    }


    interface UserToolPresenter {

        /**
         * 获取我的页面统计的数据（下半部分）
         *
         * @param userId
         * @param userType
         */
        void getUserStatictics(String userId, String userType);


        /**
         * 获取用户我的页面上半部分数据
         *
         * @param userId
         * @param userType
         */
        void getUserInfoStatictics(String userId, String userType);


        /**
         * 检查是否有权限
         */
        void checkPermissions(String userId, String userType);


        /**
         * 检查个人菜单是否可用
         *
         * @param userId
         * @param userType
         */
        void queryResource(String userId, String userType);


        /**
         * 检查个人中心按钮是否可用
         *
         * @param userId
         * @param userType
         */
        void queryResourceButton(String userId, String userType);
    }

}


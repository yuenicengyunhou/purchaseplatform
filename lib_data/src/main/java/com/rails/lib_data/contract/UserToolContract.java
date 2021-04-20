package com.rails.lib_data.contract;

import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

/**
 *用户相关工具
 * @author： sk_comic@163.com
 * @date: 2021/4/20
 */
public interface UserToolContract {

    interface UserToolView extends BaseView{

        /**
         * 获取我的页面统计的数据
         * @param bean
         */
        void getUserStatictics(UserStatisticsBean bean);
    }



    interface UserToolPresenter{

        /**
         *获取我的页面统计的数据
         * @param userId
         * @param userType
         */
        void getUserStatictics(String userId,String userType);
    }

}


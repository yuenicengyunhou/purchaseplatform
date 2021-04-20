package com.rails.lib_data.contract;

import com.rails.purchaseplatform.framwork.base.BaseView;

/**
 *用户相关工具
 * @author： sk_comic@163.com
 * @date: 2021/4/20
 */
public interface UserToolContract {

    interface UserToolView extends BaseView{

    }



    interface UserToolPresenter{

        /**
         *
         * @param userId
         * @param userType
         */
        void getUserStatictics(String userId,String userType);
    }

}


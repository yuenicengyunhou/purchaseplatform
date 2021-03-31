package com.rails.lib_data.contract;

import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

/**
 * 测试用
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/23
 */
public interface LoginContract {

    interface LoginView extends BaseView {


        /**
         * 登录结果反馈
         *
         * @param type
         * @param msg
         */
        void onResult(int type, String msg);

    }


    interface LoginPresenter {


        /**
         * 登录页面
         *
         * @param phone 手机号
         * @param paw   密码
         * @param code  验证码
         */
        void onLogin(String phone, String paw, String code);
    }
}

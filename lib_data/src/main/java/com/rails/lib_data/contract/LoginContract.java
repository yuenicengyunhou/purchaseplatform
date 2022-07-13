package com.rails.lib_data.contract;

import com.rails.lib_data.bean.UserInfoBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

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
        void onResult(int type, String msg, String token);


        /**
         * 用户信息
         *
         * @param bean
         */
        void getUserInfo(UserInfoBean bean);


        /**
         * 填写验证码
         */
        void setVerifyCode(String response,String userPhone);

        /**
         * 获取标记成功时的回调
         *
         * @param randInit
         */
        void onRandomInitSuccess(String randInit);

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


        /**
         * 获取验证吗
         *
         * @param phone
         */
        void getCode(String phone);


        /**
         * 获取用户信息
         *
         * @param isDialog 是否显示loading窗口
         */
        void getUserInfo(boolean isDialog, String token);


        /**
         * 获取坐标 step1
         *
         * @param code
         * @param isDialog
         */
        void randomInit(String code, boolean isDialog);


        /**
         * 随机码登录
         *
         * @param account
         * @param password
         * @param randInit
         * @param randomCode1
         * @param randomCode2
         * @param randomCode3
         * @param isDialog
         */
        void randomCodeLogin(String account, String password, String randInit, String randomCode1, String randomCode2, String randomCode3, boolean isDialog);

        void refreshTicket();
    }
}

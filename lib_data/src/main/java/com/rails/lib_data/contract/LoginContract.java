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


        void getTests(ArrayList<String> testBeans);

    }


    interface LoginPresenter {


        void getTests();
    }
}

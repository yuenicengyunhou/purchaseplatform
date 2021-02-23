package com.rails.lib_data.contract;

import com.rails.lib_data.bean.TestBean;
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


        void getTests(ArrayList<TestBean> testBeans);

    }


    interface LoginPresenter {


        void getTests();
    }
}

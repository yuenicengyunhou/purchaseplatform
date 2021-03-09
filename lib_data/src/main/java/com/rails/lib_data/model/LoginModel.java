package com.rails.lib_data.model;

import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.LoginService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

/**
 * @author： sk_comic@163.com
 * @date: 2021/2/23
 */
public class LoginModel {


    public void getTests( HttpRxObserver httpRxObserver) {

            HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                    .create(LoginService.class).getTests())
                    .subscribe(httpRxObserver);
    }
}

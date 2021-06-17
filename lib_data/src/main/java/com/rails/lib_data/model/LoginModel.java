package com.rails.lib_data.model;

import com.rails.lib_data.bean.LoginCoordinateBean;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.LoginService;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.MD5Util;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

/**
 * @author： sk_comic@163.com
 * @date: 2021/2/23
 */
public class LoginModel {


    /**
     * 登录
     *
     * @param phone          手机号
     * @param paw            密码
     * @param code           验证码
     * @param httpRxObserver
     */
    public void onLogin(String phone, String paw, String code, HttpRxObserver httpRxObserver) {

        HashMap<String, String> params = new HashMap<>();
        params.put("loginType", "1");
        params.put("mobilePhone", phone);
        params.put("userPassword", MD5Util.MD5(MD5Util.MD5(paw)));
        params.put("mobilePhoneCode", code);
        params.put("isWeakPwd", "0");
        params.put("returnUrl", "aaa");

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(LoginService.class, 1).onLogin(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 获取验证码
     *
     * @param phone
     * @param httpRxObserver
     */
    public void getCode(String phone, HttpRxObserver httpRxObserver) {

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(LoginService.class, 1).getCode(phone))
                .subscribe(httpRxObserver);
    }


    /**
     * 获取用户信息
     *
     * @param httpRxObserver
     */
    public void getUserInfo(String token, HttpRxObserver httpRxObserver) {

        HashMap<String, String> params = new HashMap<>();
        params.put("serviceTicket", token);
        params.put("platformId", "30");


        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(LoginService.class).getUserInfo(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 找回密码
     *
     * @param userName
     * @param email
     * @param httpRxObserver
     */
    public void retrievePassword(String userName, String email, HttpRxObserver<String> httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        params.put("userEmail", email);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(LoginService.class, 1)
                .retrievePassword(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 获取坐标 step1
     *
     * @param code
     * @param httpRxObserver
     */
    public void getRandInit(String code, HttpRxObserver httpRxObserver) {
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(LoginService.class, 1).getRandInit(code))
                .subscribe(httpRxObserver);
    }


    public Observable<HttpResult<String>> getCoordinate1(String randInit) {
        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(LoginService.class, 1).getCoordinate1(randInit));
    }

    public Observable<HttpResult<String>> getCoordinate2(String randInit) {
        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(LoginService.class, 1).getCoordinate2(randInit));
    }

    public Observable<HttpResult<String>> getCoordinate3(String randInit) {
        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(LoginService.class, 1).getCoordinate3(randInit));
    }

    /**
     * 获取所有坐标
     *
     * @param randInit
     * @param httpRxObserver
     */
    public void getAllCoordinates(String randInit, HttpRxObserver httpRxObserver) {
        Observable coordinate1
                = getCoordinate1(randInit).subscribeOn(Schedulers.io());
        Observable coordinate2
                = getCoordinate2(randInit).subscribeOn(Schedulers.io());
        Observable coordinate3
                = getCoordinate3(randInit).subscribeOn(Schedulers.io());

        Observable.zip(
                coordinate1, coordinate2, coordinate3,
                new Function3<String, String, String, LoginCoordinateBean>() {
                    @NotNull
                    @Override
                    public LoginCoordinateBean apply(@NotNull String s1, @NotNull String s2, @NotNull String s3) throws Exception {
                        LoginCoordinateBean coordinateBean = new LoginCoordinateBean();
                        coordinateBean.setCoordinate1(s1);
                        coordinateBean.setCoordinate2(s2);
                        coordinateBean.setCoordinate3(s3);
                        return coordinateBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpRxObserver);
    }
}

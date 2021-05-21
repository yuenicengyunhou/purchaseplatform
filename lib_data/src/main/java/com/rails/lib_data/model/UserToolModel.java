package com.rails.lib_data.model;

import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.AuthorButtonBean;
import com.rails.lib_data.bean.AuthorMenuBean;
import com.rails.lib_data.bean.AuthorBean;
import com.rails.lib_data.bean.CartBean;
import com.rails.lib_data.bean.OrderPurchaseBean;
import com.rails.lib_data.bean.OrderVerifyBean;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.UserService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import kotlin.jvm.functions.Function2;

/**
 * 获取用户信息
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/20
 */
public class UserToolModel {


    /**
     * 获取用户统计信息
     *
     * @param userId
     * @param userType
     * @param httpRxObserver
     */
    public void getUserStatictics(String userId, String userType, HttpRxObserver httpRxObserver) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("platformId", "20");
        params.put("accountType", userType);
        params.put("accountId", userId);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(UserService.class).getUserStatictics(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 获取用户统计信息
     *
     * @param userId
     * @param userType
     * @param httpRxObserver
     */
    public void getUserInfoStatictics(String userId, String userType, HttpRxObserver httpRxObserver) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("platformId", "20");
        params.put("accountType", userType);
        params.put("accountId", userId);

        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(UserService.class).getUserInfoStatictics(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 检查用户下单权限（信息核对页面）
     *
     * @param httpRxObserver
     */
    public void checkPermissions(String userId, String userType, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("accountType", userType);
        params.put("accountId", userId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(UserService.class).checkPermissions(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 检查权限
     *
     * @param httpRxObserver
     */
    public void queryResource(String userId, String userType, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("accountType", userType);
        params.put("accountId", userId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(UserService.class).queryResource(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 检查权限
     *
     * @param httpRxObserver
     */
    public void queryResourceButton(String userId, String userType, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("accountType", userType);
        params.put("accountId", userId);
        params.put("owner", "buyer");
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(UserService.class).queryResourceButton(params))
                .subscribe(httpRxObserver);
    }


    /**
     * 检查权限
     */
    private Observable queryResourceButton(String userId, String userType) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("accountType", userType);
        params.put("accountId", userId);
        params.put("owner", "buyer");
        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(UserService.class).queryResourceButton(params))
                .subscribeOn(Schedulers.io());
    }


    /**
     * 检查权限
     */
    private Observable queryResource(String userId, String userType) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("accountType", userType);
        params.put("accountId", userId);
        return HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(UserService.class).queryResource(params)).subscribeOn(Schedulers.io());
    }


    /**
     *
     * @param httpRxObserver
     */
    public void getQueryResource(String userId, String userType, HttpRxObserver httpRxObserver) {

        Observable menuResource = queryResource(userId, userType).subscribeOn(Schedulers.io());
        Observable btnResource = queryResourceButton(userId, userType).subscribeOn(Schedulers.io());

        Observable.zip(menuResource, btnResource, (BiFunction<ArrayList<AuthorMenuBean>, ArrayList<AuthorButtonBean>, AuthorBean>)
                (menuBeans, buttonBeans) -> {
                    AuthorBean authorBean = new AuthorBean();
                    authorBean.setMenuBeans(menuBeans);
                    authorBean.setButtonBeans(buttonBeans);
                    return authorBean;
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpRxObserver);

    }


}

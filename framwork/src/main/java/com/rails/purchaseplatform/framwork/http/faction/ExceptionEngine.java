package com.rails.purchaseplatform.framwork.http.faction;


import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import com.orhanobut.logger.Logger;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Objects;

import retrofit2.HttpException;


/**
 * 错误/异常处理工具
 *
 * @author ZhongDaFeng
 */
public class ExceptionEngine {

    //登录
    public static final String ERROR_PASTDUE = "99999-4";
    public static final String ERROR_UNLOAD = "0-0004";
    public static final String ERROR_TIMEOUT = "0-0202";
    public static final String ERROR_UNLOAD_2 = "0000010200006";
    //502,跳转维护页面
    public static final String ERROE_502 = "502";

    //网络请求异常
    public static final String HTTP_ERROR = "9999";
    //解析(服务器)数据错误
    public static final String DATA_ERROR = "9998";
    //网络超时
    public static final String TIMEOUT_ERROR = "9997";
    //连接网络错误
    public static final String CONNECT_ERROR = "9996";
    //未知错误
    public static final String UN_KNOWN_ERROR = "9995";


    public static ErrorBean handleException(Throwable e) {
        ErrorBean errorBean;
        Logger.e(Objects.requireNonNull(e.getMessage()));
        if (e instanceof HttpException) {//HTTP错误
            if (((HttpException) e).code() == 502) {
                errorBean = new ErrorBean(e, ERROE_502);
                errorBean.setMsg("");
            } else {
                errorBean = new ErrorBean(e, HTTP_ERROR);
                errorBean.setMsg("网络请求异常");  //均视为网络错误
            }
        } else if (e instanceof HttpError) {    //服务器返回的错误
            HttpError httpError = (HttpError) e;
            errorBean = new ErrorBean(httpError, httpError.getCode());
            if (TextUtils.isEmpty(httpError.getMsg()) || "null".equals(httpError.getMsg()))
                errorBean.setMsg(httpError.getMessage());
            else
                errorBean.setMsg(httpError.getMsg());
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException
                || e instanceof MalformedJsonException) {  //解析数据错误
            errorBean = new ErrorBean(e, DATA_ERROR);
            errorBean.setMsg("JSON解析异常");
            if (e.getMessage().contains("but was STRING")) {
                errorBean.setMsg("but was STRING");
            }
            // 搜索skuId，如果是一个不存在的skuId,会报Json解析异常。
            // 设置ErrorBean.msg，在请求失败的回调方法中依据msg内容进行处理。
            // Expected a com.google.gson.JsonObject but was com.google.gson.JsonNull
            else if (e.getMessage().contains("but was com.google.gson.JsonNull")) {
                errorBean.setMsg("but was com.google.gson.JsonNull");
            }
        } else if (e instanceof ConnectException||e instanceof NoRouteToHostException|| e instanceof UnknownHostException) {//连接网络错误
            errorBean = new ErrorBean(e, CONNECT_ERROR);
            errorBean.setMsg("连接失败，请检查网络状况");
        } else if (e instanceof SocketTimeoutException) {//网络超时
            errorBean = new ErrorBean(e, TIMEOUT_ERROR);
            errorBean.setMsg("请求超时，请检查网络状况");
        } else {  //未知错误
            errorBean = new ErrorBean(e, UN_KNOWN_ERROR);
            errorBean.setMsg("系统异常");
        }
        return errorBean;
    }

}

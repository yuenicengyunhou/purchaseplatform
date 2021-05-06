package com.rails.purchaseplatform.framwork.http.faction;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import retrofit2.HttpException;


/**
 * 错误/异常处理工具
 *
 * @author ZhongDaFeng
 */
public class ExceptionEngine {

    //登录
    public static final String ERROR_UNLOAD = "0-0004";

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
        if (e instanceof HttpException) {             //HTTP错误
            errorBean = new ErrorBean(e, HTTP_ERROR);
            errorBean.setMsg("网络请求异常");  //均视为网络错误
        } else if (e instanceof HttpError) {    //服务器返回的错误
            HttpError httpError = (HttpError) e;
            errorBean = new ErrorBean(httpError, httpError.getCode());
            errorBean.setMsg(httpError.getMsg());
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException || e instanceof MalformedJsonException) {  //解析数据错误
            errorBean = new ErrorBean(e, DATA_ERROR);
            errorBean.setMsg("JSON解析异常");
        } else if (e instanceof ConnectException) {//连接网络错误
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

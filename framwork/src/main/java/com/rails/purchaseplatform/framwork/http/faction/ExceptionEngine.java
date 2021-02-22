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

    public static final int LOGIN = 1; //重新登录

    public static final int UN_KNOWN_ERROR = 1000;//未知错误
    public static final int ANALYTIC_SERVER_DATA_ERROR = 1001;//解析(服务器)数据错误
    public static final int CONNECT_ERROR = 9999;//网络连接错误

    public static ErrorBean handleException(Throwable e) {
        ErrorBean errorBean;
        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpExc = (HttpException) e;
            errorBean = new ErrorBean(e, CONNECT_ERROR);
            errorBean.setMsg("网络错误");  //均视为网络错误
            return errorBean;
        } else if (e instanceof HttpError) {    //服务器返回的错误
            HttpError httpError = (HttpError) e;
            errorBean = new ErrorBean(httpError, httpError.getCode());
            errorBean.setMsg(httpError.getMsg());
            return errorBean;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException || e instanceof MalformedJsonException) {  //解析数据错误
            errorBean = new ErrorBean(e, ANALYTIC_SERVER_DATA_ERROR);
            errorBean.setMsg("解析错误");
            return errorBean;
        } else if (e instanceof ConnectException) {//连接网络错误
            errorBean = new ErrorBean(e, CONNECT_ERROR);
            errorBean.setMsg("连接失败");
            return errorBean;
        } else if (e instanceof SocketTimeoutException) {//网络超时
            errorBean = new ErrorBean(e, CONNECT_ERROR);
            errorBean.setMsg("网络超时");
            return errorBean;
        } else {  //未知错误
            errorBean = new ErrorBean(e, CONNECT_ERROR);
            errorBean.setMsg("未知错误");
            return errorBean;
        }
    }

}

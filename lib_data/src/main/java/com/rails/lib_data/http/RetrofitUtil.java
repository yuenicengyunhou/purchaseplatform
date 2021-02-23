package com.rails.lib_data.http;


import com.rails.lib_data.http.interceptor.CommonInterceptor;
import com.rails.purchaseplatform.framwork.http.observer.BaseRetrofit;

import okhttp3.Interceptor;


/**
 * 网络请求基本配置
 */
public class RetrofitUtil extends BaseRetrofit {

    private static RetrofitUtil retrofitUtil;


    /**
     * 双检索单例模式
     *
     * @return
     */
    public static RetrofitUtil getInstance() {
        if (retrofitUtil == null) {
            synchronized (RetrofitUtil.class) {
                if (retrofitUtil == null)
                    retrofitUtil = new RetrofitUtil();
            }
        }
        return retrofitUtil;
    }


    @Override
    public String getBaseUrl() {
        return HttpConstants.COMMON_URL;
    }

    @Override
    public String getBaseUrl(int type) {
        return HttpConstants.COMMON_URL_2;
    }

    @Override
    public Interceptor getInterceptor() {
        return new CommonInterceptor();
    }
}

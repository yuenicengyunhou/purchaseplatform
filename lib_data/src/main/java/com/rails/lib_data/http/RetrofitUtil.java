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
        if (!isDebug)
            return HttpConstants.SHOP_URL;
        else
            return HttpConstants.DEBUG_SHOP_URL;
    }

    @Override
    public String getBaseUrl(int type) {
        if (!isDebug)
            return HttpConstants.PLATFORM_URL;
        else
            return HttpConstants.DEBUG_PLATFORM_URL;
    }

    @Override
    public Interceptor getInterceptor() {
        return new CommonInterceptor();
    }
}

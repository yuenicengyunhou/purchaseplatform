package com.rails.lib_data.http.interceptor;


import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.purchaseplatform.framwork.BaseApp;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;


/**
 * 请求的拦截器(get和post 添加公共参数)
 */

public class CommonInterceptor implements Interceptor {

    private static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private String token;


    public CommonInterceptor() {
        token = PrefrenceUtil.getInstance(BaseApp.getContext()).getString(ConShare.TOKEN, "");
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        if (!TextUtils.isEmpty(token)) {
            builder.addHeader("Authorization", token);
        }

        if ("GET".equals(request.method())) {
            request = methodGet(request, builder);
        } else if ("POST".equals(request.method())) {
            request = methodPost(request, builder);
        } else if ("PUT".equals(request.method())) {
            request = methodPut(request, builder);
        } else {
            request = methodDel(request, builder);
        }
        Response response = chain.proceed(request);
        return response;
    }


    /**
     * post网络请求
     *
     * @param request
     * @param builder
     * @return
     */
    private Request methodPost(Request request, Request.Builder builder) {
        RequestBody requestBody = request.body();
        return builder.post(requestBody).build();
    }


    /**
     * put网络请求
     *
     * @param request
     * @param builder
     * @return
     */
    private Request methodPut(Request request, Request.Builder builder) {
        JSONObject jsonObject = new JSONObject();
        RequestBody requestBody = request.body();
        if (!(requestBody instanceof FormBody) && !(requestBody instanceof MultipartBody)) {
            Buffer buffer = new Buffer();
            try {
                requestBody.writeTo(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String paramsStr = buffer.readString(charset);
            jsonObject = JSONObject.parseObject(paramsStr);
            requestBody = RequestBody.Companion.create(JSONObject.toJSONString(jsonObject), TYPE_JSON);

        } else if (requestBody instanceof FormBody) {
            FormBody oldBody = (FormBody) request.body();
            for (int i = 0; i < oldBody.size(); i++) {
                jsonObject.put(oldBody.encodedName(i), oldBody.encodedValue(i));
            }
            requestBody = RequestBody.Companion.create(JSONObject.toJSONString(jsonObject), TYPE_JSON);
        }
        return builder.put(requestBody).build();
    }


    /**
     * get 网络请求
     *
     * @param request
     * @param builder
     * @return
     */
    private Request methodGet(Request request, Request.Builder builder) {
        HttpUrl httpUrl = request.url().newBuilder()
                .build();
        return builder.url(httpUrl).build();
    }

    /**
     * get 网络请求
     *
     * @param request
     * @param builder
     * @return
     */
    private Request methodDel(Request request, Request.Builder builder) {
        HttpUrl httpUrl = request.url().newBuilder()
                .build();
        return builder.url(httpUrl).build();
    }


}

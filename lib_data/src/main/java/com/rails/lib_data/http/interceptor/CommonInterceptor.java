package com.rails.lib_data.http.interceptor;


import com.alibaba.fastjson.JSONObject;

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


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
//        String token = "";
//        if (!TextUtils.isEmpty(token))
//            builder.addHeader("token", token);
        //添加网络默认头
//        builder.addHeader("version", version);
//        builder.addHeader("appSystem", "android");
//        builder.addHeader("appVersion", "1.0");

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
            requestBody = RequestBody.create(TYPE_JSON, JSONObject.toJSONString(jsonObject));
        }
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
            requestBody = RequestBody.create(TYPE_JSON, JSONObject.toJSONString(jsonObject));

        } else if (requestBody instanceof FormBody) {
            FormBody oldBody = (FormBody) request.body();
            for (int i = 0; i < oldBody.size(); i++) {
                jsonObject.put(oldBody.encodedName(i), oldBody.encodedValue(i));
            }
            requestBody = RequestBody.create(TYPE_JSON, JSONObject.toJSONString(jsonObject));
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

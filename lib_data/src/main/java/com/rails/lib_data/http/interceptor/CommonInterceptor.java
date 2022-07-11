package com.rails.lib_data.http.interceptor;


import static com.alibaba.fastjson.util.IOUtils.UTF8;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_PASTDUE;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_TIMEOUT;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_UNLOAD;
import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.ERROR_UNLOAD_2;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.LoginService;
import com.rails.purchaseplatform.framwork.BaseApp;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.DeviceUtils;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;


/**
 * 请求的拦截器(get和post 添加公共参数)
 */

public class CommonInterceptor implements Interceptor {

    private static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private String token;
    private Request request;
    private Response response;


    public CommonInterceptor() {
        token = PrefrenceUtil.getInstance(BaseApp.getContext()).getString(ConShare.TOKEN, "");
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        String code = null;
        request = chain.request();
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
        response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            try {
                String result = getResponseBody(responseBody);
                if (TextUtils.isEmpty(result))
                    code = "0";
                else {
                    JSONObject obj = (JSONObject) JSON.parse(result);
                    code = String.valueOf(obj.get("code"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                code = "0";
            }
        }


        if ((response.code() == 401) || ERROR_PASTDUE.equals(code) || ERROR_UNLOAD.equals(code) ||
                ERROR_UNLOAD_2.equals(code) || ERROR_TIMEOUT.equals(code)) {
            // TODO: 2022/6/23 重新获取token

            if (!TextUtils.isEmpty(token)) {
                String deviceId = DeviceUtils.getDeviceId(BaseApp.getContext());
                HashMap<String, String> params = new HashMap<>();
                params.put("deviceCode", deviceId);
                HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                                .create(LoginService.class, 1).refreshAppTicket(params))
                        .subscribe(new HttpRxObserver<String>() {
                            @Override
                            protected void onError(ErrorBean e) {
                                if ("1".equals(e.getCode())) {
                                    ToastUtil.showCenter(BaseApp.getContext(), "登录已过期，请重新登录");
                                    ARouter.getInstance().build("/user/login").navigation();
                                    PrefrenceUtil.getInstance(BaseApp.getContext()).clear();
                                }
                            }

                            @Override
                            protected void onSuccess(String newToken) {
                                if (null != newToken && !"null".equals(newToken)) {
                                    token = newToken;
                                    PrefrenceUtil.getInstance(BaseApp.getContext()).setString(ConShare.TOKEN, token);
                                    request.newBuilder().addHeader("Authorization", token);
                                    if ("GET".equals(request.method())) {
                                        request = methodGet(request, builder);
                                    } else if ("POST".equals(request.method())) {
                                        request = methodPost(request, builder);
                                    }
                                    try {
                                        //intercept(chain)
                                        response = chain.proceed(request);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
            }
        }
        return response;
    }

    private String getResponseBody(ResponseBody responseBody) throws Exception {
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.getBuffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
            }
        }

        if (!isPlaintext(buffer)) {
            return null;
        }

        if (responseBody.contentLength() != 0) {
            String result = buffer.clone().readString(charset);
            return result;
        }
        return null;
    }

    private boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false;
        }
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

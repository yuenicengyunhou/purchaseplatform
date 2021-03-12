package com.rails.purchaseplatform.framwork.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 解析assets文件json
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/12
 */
public class JsonUtil {


    public static String getJson(Context context, String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    /**
     * 解析json
     *
     * @param <T>
     * @param context
     * @param fileName
     * @return
     */
    public static <T> T parseJson(Context context, String fileName,Type type) {
        String json = getJson(context, fileName);
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, type);

    }


}

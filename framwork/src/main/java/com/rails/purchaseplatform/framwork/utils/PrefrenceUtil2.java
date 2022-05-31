package com.rails.purchaseplatform.framwork.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * sharedprefence 存储
 * Created by sk on 2016/9/24.
 */

public class PrefrenceUtil2 {

    private final String DB_NAME = "config2";
    private static PrefrenceUtil2 prefrenceUtil = null;

    private Context context;
    private SharedPreferences preferences;

    private PrefrenceUtil2(Context context) {
        this.context = context.getApplicationContext();
        preferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
    }


    /**
     * 实例化当前类
     *
     * @param context
     * @return
     */
    public static PrefrenceUtil2 getInstance(Context context) {
        if (prefrenceUtil == null)
            synchronized (PrefrenceUtil2.class) {
                if (prefrenceUtil == null)
                    prefrenceUtil = new PrefrenceUtil2(context);
            }
        return prefrenceUtil;
    }

    /**
     * 设置字符串
     *
     * @param key   存储key
     * @param value 存储的value
     */
    public void setString(String key, String value) {
        SharedPreferences.Editor sharedata = preferences.edit();
        sharedata.putString(key, value);
        sharedata.commit();
    }


    /**
     * 获取存储的字符串
     *
     * @param key 存储的索引
     * @param def 获取的为空时，默认value
     * @return
     */
    public String getString(String key, String def) {
        return preferences.getString(key, def);
    }


    /**
     * 将枚举存放
     *
     * @param obj
     * @param value
     */
    public void setString(Object obj, String value) {
        SharedPreferences.Editor sharedata = preferences.edit();
        sharedata.putString(String.valueOf(obj), value);
        sharedata.commit();
    }


    /**
     * 获取存储的字符串,将枚举存放
     *
     * @param obj 存储的索引
     * @param def 获取的为空时，默认value
     * @return
     */
    public String getString(Object obj, String def) {
        return preferences.getString(String.valueOf(obj), def);
    }


    /**
     * 设置Int
     *
     * @param key
     * @param value
     */
    public void setInt(String key, int value) {
        SharedPreferences.Editor sharedata = preferences.edit();
        sharedata.putInt(key, value);
        sharedata.commit();
    }


    /**
     * 获取int
     *
     * @param key
     * @param def
     * @return
     */
    public int getInt(String key, int def) {
        return preferences.getInt(key, def);
    }

    /**
     * 设置Long
     *
     * @param key   存储key
     * @param value 存储的value
     */
    public void setLong(String key, long value) {
        SharedPreferences.Editor sharedata = preferences.edit();
        sharedata.putLong(key, value);
        sharedata.commit();
    }

    /**
     * 获取存储的Long
     *
     * @param key 存储的索引
     * @param def 获取的为空时，默认value
     * @return
     */
    public Long getLong(String key, long def) {
        return preferences.getLong(key, def);
    }


    /**
     * 存储Boolean
     *
     * @param key   存储索引
     * @param value 存储的value
     */
    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor sharedata = preferences.edit();
        sharedata.putBoolean(key, value);
        sharedata.commit();
    }


    /**
     * 获取存储布尔值
     *
     * @param key 索引
     * @param def @null 设置成默认值
     * @return
     */
    public boolean getBoolean(String key, boolean def) {
        return preferences.getBoolean(key, def);
    }


    /**
     * 存储List<String>数组
     *
     * @param key
     * @param values
     */
    public void setListString(String key, List<String> values) {
        SharedPreferences.Editor sharedata = preferences.edit();
        String str = "";
        if (values != null && !values.isEmpty()) {
            for (String value : values) {
                str += value;
                str += "#";
            }
        }
        sharedata.putString(key, str);
        sharedata.commit();
    }


    /**
     * 获取数组列表
     *
     * @param key
     * @return
     */
    public List<String> getListString(String key) {
        List<String> values = new ArrayList<>();
        String str = preferences.getString(key, "");
        if (!TextUtils.isEmpty(str)) {
            String[] strs = str.split("#");
            for (String value : strs)
                values.add(value);
        }
        return values;
    }


    /**
     * 设置存储对象到本地
     *
     * @param key
     * @param <T>
     */
    public <T> void setListBean(String key, ArrayList<T> values) {
        SharedPreferences.Editor sharedata = preferences.edit();
        String str = "";
        if (values != null && !values.isEmpty()) {
            Gson gson = new Gson();
            //转换成json数据，再保存
            str = gson.toJson(values);
        }
        sharedata.putString(key, str);
        sharedata.apply();
    }


    /**
     * 获取存储到本地的对象
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> List<T> getListBean(String key, Class<T> cls) {
        List<T> values = new ArrayList<T>();
        String str = preferences.getString(key, null);
        if (TextUtils.isEmpty(str)) {
            return values;
        }
        try {
            JsonArray array = new JsonParser().parse(str).getAsJsonArray();
            for (final JsonElement elem : array) {
                values.add(new Gson().fromJson(elem, cls));
            }
        } catch (Exception e) {
        }
        return values;
    }


    /**
     * 存储对象
     *
     * @param key
     * @param bean
     * @param <T>
     */
    public <T> void setBean(String key, T bean) {
        SharedPreferences.Editor sharedata = preferences.edit();
        String str = "";
        if (bean != null) {
            Gson gson = new Gson();
            //转换成json数据，再保存
            str = gson.toJson(bean);
        }
        sharedata.putString(key, str);
        sharedata.commit();
    }


    /**
     * 获取存储的对象
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getBean(String key,Class<T> cls) {
        String str = preferences.getString(key, null);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Gson gson = new GsonBuilder().create();
        return  gson.fromJson(str, cls);

    }


    /**
     * 清除所有记录
     */
    public void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}

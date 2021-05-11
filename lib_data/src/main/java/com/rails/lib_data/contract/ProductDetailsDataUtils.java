package com.rails.lib_data.contract;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.bean.forAppShow.ProductSpecificParameter;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemPublishVo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemSkuInfo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.SkuSpecInfo;

import java.util.ArrayList;
import java.util.Set;

public class ProductDetailsDataUtils {

    final private String TAG = ProductDetailsDataUtils.class.getSimpleName();

    public ArrayList<ProductSpecificParameter> getCommonParams(
            ArrayList<ProductSpecificParameter> parameters,
            ProductDetailsBean detailsBean,
            ItemSkuInfo currentItemSkuInfo) {

        ItemPublishVo itemPublishVo;
        try {
            itemPublishVo = detailsBean.getItemPublishVo();
            addParam(parameters, "品牌：", itemPublishVo.getBrandName());
            addParam(parameters, "商品名称：", itemPublishVo.getItemName());
            addParam(parameters, "规格型号：", currentItemSkuInfo.getModelCode());
            addParam(parameters, "商品毛重：", String.valueOf(currentItemSkuInfo.getWeight()), currentItemSkuInfo.getWeightUnit());
            addParam(parameters, "包装尺寸：", currentItemSkuInfo.getPackageDis(), "毫米");
            addParam(parameters, "商品单位：", currentItemSkuInfo.getSkuUnit());
            addParam(parameters, "商品产地：", itemPublishVo.getOrigin());
            addParam(parameters, "商品编码：", String.valueOf(itemPublishVo.getId()));
            addParam(parameters, "单品编码：", currentItemSkuInfo.getId());
            addParam(parameters, "单品条码：", currentItemSkuInfo.getBarCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parameters;
    }

    private void addParam(ArrayList<ProductSpecificParameter> parameters, String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            ProductSpecificParameter parameter = new ProductSpecificParameter();
            parameter.setParamKey(key);
            parameter.setParamValue(value);
            parameters.add(parameter);
        }
    }

    private void addParam(ArrayList<ProductSpecificParameter> parameters, String key, String value, String value2) {
        if (TextUtils.equals(value, "**")) // 包装尺寸为 "**" 不显示
            return;
        if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(value2)) {
            ProductSpecificParameter parameter = new ProductSpecificParameter();
            parameter.setParamKey(key);
            parameter.setParamValue(value + " " + value2);
            parameters.add(parameter);
        }
    }

    public ArrayList<ProductSpecificParameter> getSpecParams(
            ArrayList<ProductSpecificParameter> parameters,
            ProductDetailsBean detailsBean,
            ItemSkuInfo currentItemSkuInfo) {

        try {
            JSONObject data = detailsBean.getItemPublishVo().getSkuSpecMap();
            Set<String> keys = data.keySet();
            ArrayList<SkuSpecInfo> infoList;
            Gson gson = new GsonBuilder().create();
            for (String key : keys) {
                infoList = gson.fromJson(String.valueOf(data.getJSONArray(key)), new TypeToken<ArrayList<SkuSpecInfo>>() {
                }.getType());
                if (currentItemSkuInfo != null && currentItemSkuInfo.getId().equals(key)) {
                    for (SkuSpecInfo info : infoList) {
                        if (TextUtils.equals(info.getAttrValue(), "-")) // 生产许可证号为 "-" 不显示
                            continue;
                        if (!TextUtils.isEmpty(info.getAttrValue())) {
                            ProductSpecificParameter parameter = new ProductSpecificParameter();
                            parameter.setParamKey(info.getAttrName() + "：");
                            parameter.setParamValue(info.getAttrValue());
                            parameters.add(parameter);
                        }
                    }
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return parameters;
    }
}

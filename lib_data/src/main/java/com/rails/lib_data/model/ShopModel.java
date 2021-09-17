package com.rails.lib_data.model;

import android.text.TextUtils;
import android.util.Log;

import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.forAppShow.SearchFilterValue;
import com.rails.lib_data.bean.shop.AllCidsBean;
import com.rails.lib_data.bean.shop.AttrValuesBean;
import com.rails.lib_data.bean.shop.CategoryAttrsBean;
import com.rails.lib_data.bean.shop.ExpandAttrsBean;
import com.rails.lib_data.bean.shop.ShopRecommendBean;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.ShopService;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

public class ShopModel {

    final private String
            BRAND = "品牌",
            CATEGORY = "类目";

    private boolean cateFirst = true;
    private boolean expanFirst = true;

    public void getShopInfo(String shopInfoId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
//        map.put("platformId", platformId);
        map.put("shopInfoId", shopInfoId);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ShopService.class).getShopInfo(map))
                .subscribe(httpRxObserver);
    }


    /**
     * 排序：
     * <p>
     * 销量向下
     * orderColumn=saleCount&orderType=desc
     * 销量向上
     * orderColumn=saleCount&orderType=asc
     * <p>
     * 价格向上
     * orderColumn=sellPrice&orderType=asc
     * 价格向下
     * orderColumn=sellPrice&orderType=desc
     */
    public void getShopItemList( String shopInfoId, int page, int pageSize, String orderColumn, String orderType, ArrayList<SearchFilterBean> filterBeans,String keyword,int materialType, HttpRxObserver httpRxObserver) {
//        String temp = platformId == null ? "20" : platformId;
        HashMap<String, Object> map = new HashMap<>();
//        map.put("platformId", temp);
//        map.put("shopId", shopInfoId);
        map.put("pageNum", page);//页码
        map.put("pageSize", pageSize);//每页条目数
        map.put("shopInfoId", shopInfoId);//店铺ID
        map.put("businessType", "1");//
        map.put("keyword", keyword);//搜索关键字
        map.put("materialType", materialType);//是否是通用物资 0通用  1专用，由getShopInfo返回
        if (!TextUtils.isEmpty(orderColumn)) {
            map.put("orderColumn", orderColumn);//排序字段  销量 saleCount   ，  价格  sellPrice
            map.put("orderType", orderType);//排序顺序    向下  desc    ,向上  asc
        }
        boolean hasCid = analyzeFilterData(map, filterBeans);//处理筛选条件数据，并返回，有没有类目筛选条件，有则调用queryItemListByCid接口，没有则调用queryItemListByKeyword接口
        ShopService shopService = RetrofitUtil.getInstance()
                .create(ShopService.class);
        Observable<HttpResult<ShopRecommendBean>> observable;
        if (hasCid) {
            observable = shopService.getShopItemListByCid(map);
        } else {
            observable = shopService.getShopItemList(map);
        }
        HttpRxObservable.getObservable(observable)
                .subscribe(httpRxObserver);
    }

    /**
     * 提取筛选条件中选中项
     */
    private boolean analyzeFilterData(HashMap<String, Object> map, ArrayList<SearchFilterBean> filterBeans) {
        boolean hasCid = false;
        if (null != filterBeans) {
            StringBuilder brands = new StringBuilder();
            List<String> cidIdList = new ArrayList<>();
            List<String> categoryAttrValueIds = new ArrayList<>();
            List<String> expandAttrValueIds = new ArrayList<>();
            for (SearchFilterBean filterBean : filterBeans) {
                ArrayList<SearchFilterValue> filterValues = filterBean.getFilterValues();
                String filterName = filterBean.getFilterName();
                for (int i = 0; i < filterValues.size(); i++) {

                    SearchFilterValue childValue = filterValues.get(i);
                    int attrFlag = childValue.getAttrFlag();
                    String valueId = childValue.getValueId();
                    String valueName = childValue.getValueName();
                    if (childValue.isSelect()) {

                        switch (attrFlag) {
                            case 0: //0-brand
                                brands.append(valueName).append(",");
                                break;
                            case 1://1-cid
                                hasCid = true;
//                                cidIdList.add(valueId);
                                cidIdList.add(valueId);
//                                cidIdList.add("1000682");
                                break;
                            case 2:// 2-category
                                if (cateFirst) {
                                    categoryAttrValueIds.add(filterName + "_");
                                    cateFirst = false;
                                }
                                categoryAttrValueIds.add(valueName);
                                break;
                            case 3:// 3-expandAttrValue
                                if (expanFirst) {
                                    expandAttrValueIds.add(filterName + "_");
                                    expanFirst = false;
                                }
                                expandAttrValueIds.add(valueName);
                                break;
                        }
                    }
                }

            }
            cateFirst = true;
            expanFirst = true;
            String bransJson = brands.toString();
            if (!TextUtils.isEmpty(bransJson)) {
                map.put("brands", bransJson);
                map.put("brandsString", bransJson);
            }
            if (!cidIdList.isEmpty()) {
//                String cidString = cidIdList.get(0);
//                cidIdList.remove(0);
//               String cidJson= cidString + StringUtil.getJointString("||", cidIdList)+"@";
//                Gson gson = new Gson();
//                String cidsJson = gson.toJson(cidIdList);
                String s = cidIdList.get(0);
//                String cidsJson = "";
//                map.put("cidList", cidJson);
                map.put("cid", s);
            }
            if (!categoryAttrValueIds.isEmpty()) {
                String headString = categoryAttrValueIds.get(0);
                categoryAttrValueIds.remove(0);
                String cateJson = headString + StringUtil.getJointString("||", categoryAttrValueIds) + "@";
                map.put("categoryAttrValueIds", cateJson);
            }
            if (!expandAttrValueIds.isEmpty()) {
                String expanHead = expandAttrValueIds.get(0);
                expandAttrValueIds.remove(0);
                String expanJson = expanHead + StringUtil.getJointString("||", expandAttrValueIds) + "@";
                map.put("expandAttrValueIds", expanJson);
            }


        }
        return hasCid;
    }

    /**
     * 遍历shopRecommendBean，按二级菜单处理数据
     */

    public ArrayList<SearchFilterBean> getFilterBeans(ShopRecommendBean shopRecommendBean) {
        List<String> brands = shopRecommendBean.getBrands();
        List<CategoryAttrsBean> categoryAttrs = shopRecommendBean.getCategoryAttrs();
        List<ExpandAttrsBean> expandAttrs = shopRecommendBean.getExpandAttrs();
        List<AllCidsBean> allCids = shopRecommendBean.getAllCids();
        ArrayList<SearchFilterBean> mList = new ArrayList<>();

        if (null != brands && !brands.isEmpty()) {
            SearchFilterBean bean = new SearchFilterBean();
            bean.setFilterName(BRAND);
            ArrayList<SearchFilterValue> values = new ArrayList<>();
            for (String s : brands) {
                SearchFilterValue value = new SearchFilterValue();
                value.setValueName(s);
                value.setAttrFlag(0);
                values.add(value);
            }
            bean.setFilterValues(values);
            mList.add(bean);//添加品牌
        }

        if (null != categoryAttrs && !categoryAttrs.isEmpty()) {
            for (CategoryAttrsBean bean : categoryAttrs) {
                SearchFilterBean cateBean = new SearchFilterBean();
                String attrName = bean.getAttrName();
                String attrId = bean.getAttrId();
                List<AttrValuesBean> attrValues = bean.getAttrValues();
                cateBean.setFilterName(attrName);
                cateBean.setFilterId(attrId);
                ArrayList<SearchFilterValue> values = new ArrayList<>();
                for (AttrValuesBean valuesBean : attrValues) {
                    String id = valuesBean.getId();
                    String name = valuesBean.getName();
                    SearchFilterValue cateValue = new SearchFilterValue();
                    cateValue.setValueId(id);
                    cateValue.setValueName(name);
                    cateValue.setAttrFlag(2);
                    values.add(cateValue);
                }

                cateBean.setFilterValues(values);//添加catego...
                mList.add(cateBean);
            }
        }


        if (null != expandAttrs && !expandAttrs.isEmpty()) {
            for (ExpandAttrsBean bean : expandAttrs) {
                SearchFilterBean expandBean = new SearchFilterBean();
                String attrName = bean.getAttrName();
                String attrId = bean.getAttrId();
                List<AttrValuesBean> attrValues = bean.getAttrValues();
                expandBean.setFilterName(attrName);
                expandBean.setFilterId(attrId);
                ArrayList<SearchFilterValue> values = new ArrayList<>();
                for (AttrValuesBean valuesBean : attrValues) {
                    String id = valuesBean.getId();
                    String name = valuesBean.getName();
                    SearchFilterValue expandValue = new SearchFilterValue();
                    expandValue.setValueId(id);
                    expandValue.setValueName(name);
                    expandValue.setAttrFlag(3);
                    values.add(expandValue);
                }

                expandBean.setFilterValues(values);//添加expand...
                mList.add(expandBean);
            }
        }

        if (null != allCids && !allCids.isEmpty()) {
            SearchFilterBean bean = new SearchFilterBean();
            bean.setMultiSelect(false);
            bean.setFilterName(CATEGORY);
            ArrayList<SearchFilterValue> values = new ArrayList<>();
            for (AllCidsBean allCidsBean : allCids) {
                SearchFilterValue value = new SearchFilterValue();
                String id = allCidsBean.getId();
                String name = allCidsBean.getName();
                value.setValueName(name);
                value.setSingle(true);
                value.setValueId(id);
                value.setAttrFlag(1);
                values.add(value);
            }
            bean.setFilterValues(values);
            mList.add(bean);//添加类目
        }

        return mList;
    }

}


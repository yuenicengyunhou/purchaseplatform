package com.rails.lib_data.model;

import android.text.TextUtils;
import com.rails.lib_data.bean.forAppShow.SearchFilterBean;
import com.rails.lib_data.bean.forAppShow.SearchFilterValue;
import com.rails.lib_data.bean.shop.AllCidsBean;
import com.rails.lib_data.bean.shop.AttrValuesBean;
import com.rails.lib_data.bean.shop.CategoryAttrsBean;
import com.rails.lib_data.bean.shop.ExpandAttrsBean;
import com.rails.lib_data.bean.shop.ShopRecommendBean;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.ShopService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopModel {

    private boolean cateFirst,expanFirst=true;

    public void getShopInfo(long platformId, String shopInfoId, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", platformId);
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
    public void getShopItemList(long platformId, String shopInfoId, int page, int pageSize, String orderColumn, String orderType, ArrayList<SearchFilterBean> filterBeans, HttpRxObserver httpRxObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("platformId", "20");
//        map.put("shopId", shopInfoId);
        map.put("pageNum", page);
        map.put("pageSize", pageSize);
        map.put("shopInfoId", shopInfoId);
        if (!TextUtils.isEmpty(orderColumn)) {
            map.put("orderColumn", orderColumn);//排序字段
            map.put("orderType", orderType);//排序顺序
        }
        analyzeFilterData(map, filterBeans);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(ShopService.class).getShopItemList(map))
                .subscribe(httpRxObserver);
    }

    /**
     * 提取筛选条件中选中项
     */
    private void analyzeFilterData(HashMap<String, Object> map, ArrayList<SearchFilterBean> filterBeans) {
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
//                                cidIdList.add(valueId);
                                cidIdList.add(valueId);
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
//                String cidsJson = gson.toJson(cidIdList);
                String cidsJson = "";
                map.put("cidList", cidsJson);
            }
            if (!categoryAttrValueIds.isEmpty()) {
                String cateJson = StringUtil.getJointString("||", categoryAttrValueIds) + "@";
                map.put("categoryAttrValueIds", cateJson);
            }
            if (!expandAttrValueIds.isEmpty()) {
                String expanJson = StringUtil.getJointString("||", expandAttrValueIds) + "@";
                map.put("expandAttrValueIds", expanJson);
            }


        }
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
            bean.setFilterName("品牌");
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
            bean.setFilterName("类目");
            ArrayList<SearchFilterValue> values = new ArrayList<>();
            for (AllCidsBean allCidsBean : allCids) {
                SearchFilterValue value = new SearchFilterValue();
                String id = allCidsBean.getId();
                String name = allCidsBean.getName();
                value.setValueName(name);
                value.setValueId(id);
                value.setAttrFlag(1);
                values.add(value);
            }
            bean.setFilterValues(values);
            mList.add(bean);//添加品牌
        }

        return mList;
    }


//    /**
//     * 遍历shopRecommendBean，按二级菜单处理数据
//     */
    /*public List<SearchFilterValue> getFilterBeans2(ShopRecommendBean shopRecommendBean) {
        List<String> brands = shopRecommendBean.getBrands();
        List<CategoryAttrsBean> categoryAttrs = shopRecommendBean.getCategoryAttrs();
        List<ExpandAttrsBean> expandAttrs = shopRecommendBean.getExpandAttrs();
        List<AllCidsBean> allCids = shopRecommendBean.getAllCids();
        List<SearchFilterValue> mList = new ArrayList<>();

        if (null != brands && !brands.isEmpty()) {
            SearchFilterValue bean = new SearchFilterValue();
            bean.setValueName("品牌");
            bean.setParent(1);
            mList.add(bean);
//            ArrayList<SearchFilterValue> values = new ArrayList<>();
            for (String s : brands) {
                SearchFilterValue value = new SearchFilterValue();
                value.setValueName(s);
                value.setAttrFlag(0);
                mList.add(bean);
//                values.add(value);
            }
//            bean.setFilterValues(values);
            //添加品牌
        }

        if (null != categoryAttrs && !categoryAttrs.isEmpty()) {
            for (CategoryAttrsBean bean : categoryAttrs) {
                SearchFilterValue cateBean = new SearchFilterValue();
                String attrName = bean.getAttrName();
                String attrId = bean.getAttrId();
                List<AttrValuesBean> attrValues = bean.getAttrValues();
                cateBean.setValueName(attrName);
                cateBean.setValueId(attrId);
                cateBean.setParent(1);
                mList.add(cateBean);
//                ArrayList<SearchFilterValue> values = new ArrayList<>();
                for (AttrValuesBean valuesBean: attrValues) {
                    String id = valuesBean.getId();
                    String name = valuesBean.getName();
                    SearchFilterValue cateValue = new SearchFilterValue();
                    cateValue.setValueId(id);
                    cateValue.setValueName(name);
                    cateValue.setAttrFlag(2);
                    mList.add(cateValue);
                }

//                cateBean.setFilterValues(values);//添加catego...
//                mList.add(cateBean);
            }
        }


        if (null != expandAttrs && !expandAttrs.isEmpty()) {
            for (ExpandAttrsBean bean : expandAttrs) {
                SearchFilterValue expandBean = new SearchFilterValue();
                String attrName = bean.getAttrName();
                String attrId = bean.getAttrId();
                List<AttrValuesBean> attrValues = bean.getAttrValues();
                expandBean.setValueName(attrName);
                expandBean.setValueId(attrId);
                expandBean.setParent(1);
//                ArrayList<SearchFilterValue> values = new ArrayList<>();
                for (AttrValuesBean valuesBean: attrValues) {
                    String id = valuesBean.getId();
                    String name = valuesBean.getName();
                    SearchFilterValue expandValue = new SearchFilterValue();
                    expandValue.setValueId(id);
                    expandValue.setValueName(name);
                    expandValue.setAttrFlag(3);
                    mList.add(expandValue);
                }

//                expandBean.setFilterValues(values);//添加expand...
//                mList.add(expandBean);
            }
        }

        if (null != allCids && !allCids.isEmpty()) {
            SearchFilterValue bean = new SearchFilterValue();
            bean.setValueName("类目");
            bean.setParent(1);
            mList.add(bean);
//            ArrayList<SearchFilterValue> values = new ArrayList<>();
            for (AllCidsBean allCidsBean : allCids) {
                SearchFilterValue value = new SearchFilterValue();
                String id = allCidsBean.getId();
                String name = allCidsBean.getName();
                value.setValueName(name);
                value.setValueId(id);
                value.setAttrFlag(1);
                mList.add(value);
            }
//            bean.setFilterValues(values);
//            mList.add(bean);//添加品牌
        }

        return mList;
    }*/

}


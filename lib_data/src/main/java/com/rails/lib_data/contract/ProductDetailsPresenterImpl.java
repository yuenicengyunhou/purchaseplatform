package com.rails.lib_data.contract;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.DeliveryBean;
import com.rails.lib_data.bean.ProductServiceBean;
import com.rails.lib_data.bean.SkuStockBean;
import com.rails.lib_data.bean.forAppShow.ProductDetailsPackingBean;
import com.rails.lib_data.bean.forAppShow.RecommendItemsBean;
import com.rails.lib_data.bean.forAppShow.SpecificationPopBean;
import com.rails.lib_data.bean.forAppShow.SpecificationValue;
import com.rails.lib_data.bean.forNetRequest.productDetails.AttrNameValueReaultVo;
import com.rails.lib_data.bean.forNetRequest.productDetails.HotSaleBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemAfterSaleVo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemPicture;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemResult;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemSku;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemSkuInfo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductPriceBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.SupplierInfoImportData;
import com.rails.lib_data.model.ProductDetailsModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 商品详情 presenter实现类
 */
public class ProductDetailsPresenterImpl
        extends BasePresenter<ProductDetailsContract.ProductDetailsView>
        implements ProductDetailsContract.ProductDetailsPresenter {

    private final String TAG = ProductDetailsPresenterImpl.class.getSimpleName();

    private ProductDetailsModel mModel;

    /**
     * Constructor
     *
     * @param mContext
     * @param productDetailsView
     */
    public ProductDetailsPresenterImpl(
            Activity mContext,
            ProductDetailsContract.ProductDetailsView productDetailsView) {
        super(mContext, productDetailsView);
        mModel = new ProductDetailsModel();
    }

    /**
     * 获取商品详情
     *
     * @param platformId
     * @param itemId
     * @param companyId
     * @param isDialog
     */
    @Override
    public void getProductDetails(String platformId, String itemId, String companyId, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);

        mModel.getProductDetails(platformId, itemId, companyId, new HttpRxObserver<ProductDetailsBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }


            @Override
            protected void onSuccess(ProductDetailsBean detailsBean) {

                ArrayList<ProductServiceBean> serviceBeans = new ArrayList<>();
                try {
                    ItemAfterSaleVo afterSale = detailsBean.getItemPublishVo().getItemAfterSaleVo();
                    serviceBeans.add(new ProductServiceBean(R.drawable.ic_market_exits, "售后退货说明",
                            afterSale.getRefundService() == 1 ? "特殊商品不允许退货。" : "确认收货后" + afterSale.getRefundDuration() + "日内出现质量问题可申请退货。"));
                    serviceBeans.add(new ProductServiceBean(R.drawable.ic_market_updates, "售后换货说明",
                            afterSale.getChangeService() == 1 ? "特殊商品，一经签收不予换货。。" : "确认收货后" + afterSale.getRefundDuration() + "日内出现质量问题可申请换货。"));
                    serviceBeans.add(new ProductServiceBean(R.drawable.ic_market_saves, "售后质保说明",
                            "确认收货后" + afterSale.getRepaireDuration() + "个月内出现质量问题可申请质保。"));
                } catch (Exception e) {
                }


                ArrayList<ProductServiceBean> companyBeans = new ArrayList<>();
                try {
                    SupplierInfoImportData suppData = detailsBean.getItemPublishVo().getSupplierInfoImportData();
                    String recommendOrg = TextUtils.isEmpty(suppData.getRecommendOrg()) ? "暂无数据" : suppData.getRecommendOrg();
                    String bindOrgName = TextUtils.isEmpty(suppData.getBindOrgName()) ? "暂无数据" : suppData.getBindOrgName();
                    String accountName = TextUtils.isEmpty(suppData.getAccountName()) ? "暂无数据" : suppData.getAccountName();
                    companyBeans.add(new ProductServiceBean(R.drawable.ic_market_recc, "推荐单位", recommendOrg));
                    companyBeans.add(new ProductServiceBean(R.drawable.ic_market_bindc, "绑定货运单位", bindOrgName));
                    companyBeans.add(new ProductServiceBean(R.drawable.ic_market_sendc, "货运客户经理", accountName));
                } catch (Exception e) {

                }


                if (isCallBack()) {
                    baseView.onGetProductDetailsSuccess(detailsBean, serviceBeans, companyBeans, getSpecificationPopBeans3(detailsBean));
                }

                baseView.dismissDialog();
            }
        });

    }


    /**
     * 组装购物车弹窗和规格弹窗的数据
     *
     * @param bean
     */
    private ArrayList<SpecificationPopBean> getSpecificationPopBeans(ProductDetailsBean bean) {

        // 组装ItemSkuInfoList中第1个sku的规格名称ID和规格值ID  eg: HashMap{<28999,134066>,<29000,134073>}
        HashMap<String, String> firstSkuParamsIdMap = new HashMap<>();
        if (bean.getItemSkuInfoList() != null && bean.getItemSkuInfoList().size() != 0) {
            ItemSkuInfo skuInfo = bean.getItemSkuInfoList().get(0);
            if (skuInfo.getAttributes() != null && !skuInfo.getAttributes().equals("")) {
                String stringAttr = bean.getItemSkuInfoList().get(0).getAttributes();
                String[] strings = stringAttr.split(";");
                for (String s : strings) {
                    String[] subStrings = s.split(":");
                    firstSkuParamsIdMap.put(subStrings[0], subStrings[1]);
                }
            }
        }

        ArrayList<SpecificationPopBean> specificationPopBeans = new ArrayList<>();
        if (bean.getItemPublishVo().getAttrNameArray() != null && bean.getItemPublishVo().getAttrNameArray().size() != 0) {
            // 遍历ItemPublishVo.attrNameArray, 取得可显示的属性名称
            for (String attrName : bean.getItemPublishVo().getAttrNameArray()) {  // 遍历属性名称
                SpecificationPopBean specificationPopBean = new SpecificationPopBean();  // 创建属性Bean 用于展示
                specificationPopBean.setAttrName(attrName);  // 属性Bean设置名称
                ArrayList<SpecificationValue> specificationValues = new ArrayList<>(); // 创建属性子集合
                // 遍历ItemPublishVo.attrNameValueReaultVos, 取得可显示的属性值
                for (AttrNameValueReaultVo nameValue : bean.getItemPublishVo().getAttrNameValueReaultVos()) {  // 遍历 从集合对象中取 四项属性
                    SpecificationValue specificationValue = new SpecificationValue();  // 创建子bean

                    if (nameValue.getAttrName().equals(attrName)) {  // 判断 名称相同
                        specificationPopBean.setAttrId(nameValue.getAttrId());  // 添加id
                        specificationValue.setAttrValueId(nameValue.getAttrValueId());  // 添加属性值 属性值Id
                        specificationValue.setAttrValueName(nameValue.getAttrValueName());

                        if (firstSkuParamsIdMap.containsKey(nameValue.getAttrId()) && firstSkuParamsIdMap.get(nameValue.getAttrId()).equals(nameValue.getAttrValueId()))
                            specificationValue.setSelect(true);
                        specificationValues.add(specificationValue);
                    }

                    specificationPopBean.setSpecificationValue(specificationValues);
                }
                specificationPopBeans.add(specificationPopBean);
            }
        }
        return specificationPopBeans;
    }

    /**
     * -01- 使用第一个ItemSkuInfo获取所有的 型号名称 单存一个集合 记录集合长度
     * -02- 切割每个ItemSkuInfo的attributeName 存入集合 有重复的不要存
     * -03- 呵呵
     *
     * @param bean
     * @return
     */

    private ArrayList<SpecificationPopBean> getSpecificationPopBeans3(ProductDetailsBean bean) {
        // 最后需要返回的集合
        ArrayList<SpecificationPopBean> specificationPopBeans = new ArrayList<>();

        // 什么垃圾数据? itemSkuInfoList能是null?
        if (bean == null || bean.getItemSkuInfoList() == null || bean.getItemSkuInfoList().size() == 0) {
            return specificationPopBeans;
        }

        // 全都是空的
        ItemSkuInfo defaultSkuInfo = bean.getItemSkuInfoList().get(0);
        if (TextUtils.isEmpty(defaultSkuInfo.getAttributes()) || TextUtils.isEmpty(defaultSkuInfo.getAttributesName())) {
            return specificationPopBeans;
        }

        // 属性数量 也是默认选中的个数
        int typeCount = bean.getItemSkuInfoList().get(0).getAttributesName().split(";").length;

        // 属性名称集合
        ArrayList<String> typeNames = new ArrayList<>();

        // 预组装的集合
        ArrayList<String> nameList = new ArrayList<>(); // 每个元素的格式：【CPU型号:inter-i7】
        ArrayList<String> nameIdList = new ArrayList<>(); // 每个元素的格式：【12345:67890】

        // 遍历所有sku
        for (int i = 0; i < bean.getItemSkuInfoList().size(); i++) {
            defaultSkuInfo = bean.getItemSkuInfoList().get(i);
            String[] names = defaultSkuInfo.getAttributesName().split(";"); // 每个元素的格式：【CPU型号:inter-i7】
            String[] nameIds = defaultSkuInfo.getAttributes().split(";"); // 每个元素的格式：【12345:67890】
            // 遍历每个sku中包含的属性
            for (int j = 0; j < typeCount; j++) { //
                // 先拿到所有属性名 使用默认的skuInfo （一个sku应该含有所有的型号属性）
                if (i == 0) {
                    typeNames.add(names[j].split(":")[0]);
                }
                // 把所有型号都添加到预组装集合中，过滤重复的型号
                if (!nameList.contains(names[j]) && !nameIdList.contains(nameIds[j])) {
                    nameList.add(names[j]);
                    nameIdList.add(nameIds[j]);
                }
            }
        }

        int allAttrCount = nameList.size();
        // 遍历属性名称集合
        for (String typeName : typeNames) {
            SpecificationPopBean popBean = new SpecificationPopBean();
            popBean.setAttrName(typeName);
            ArrayList<SpecificationValue> valueList = new ArrayList<>();
            // 遍历预组装集合
            for (int i = 0; i < allAttrCount; i++) {
                SpecificationValue valueBean = new SpecificationValue();
                String[] names = nameList.get(i).split(":");
                String[] ids = nameIdList.get(i).split(":");
                String typeNameKey = names[0];
                String typeNameValue = names[1];
                String typeNameKeyId = ids[0];
                String typeNameValueId = ids[1];
                if (typeName.equals(typeNameKey)) {
                    popBean.setAttrId(typeNameKeyId);
                    valueBean.setAttrValueName(typeNameValue);
                    valueBean.setAttrValueId(typeNameValueId);
                    if (i < typeCount) valueBean.setSelect(true);
                    valueList.add(valueBean);
                }
            }
            popBean.setSpecificationValue(valueList);
            specificationPopBeans.add(popBean);
        }

        return specificationPopBeans;
    }

    /**
     * 获取商品价格、评分
     *
     * @param platformId
     * @param skuId
     * @param isDialog
     */
    @Override
    public void getProductPrice(String platformId, String skuId, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);

        mModel.getProductPrice(platformId, skuId, new HttpRxObserver<ArrayList<ProductPriceBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(ArrayList<ProductPriceBean> response) {
                // 需要返回的三个参数
                ProductPriceBean bean = new ProductPriceBean();
                ArrayList<ItemPicture> pics = new ArrayList<>(); // 这个集合是做什么的？
                ArrayList<ProductDetailsPackingBean> packingBeans = new ArrayList<>();

                if (response != null && response.size() != 0) {
                    bean = response.get(0);

                    if (bean.getPackinglist() != null && bean.getPackinglist().size() != 0) {
                        ProductDetailsPackingBean bean1 = new ProductDetailsPackingBean();
                        bean1.setAttrKey("附件名称");
                        bean1.setAttrValue("附件数量");
                        packingBeans.add(bean1);
                        String annexName = bean.getPackinglist().get(0).getAnnexName();
                        spliceList(packingBeans, annexName);
                    }
                }

                baseView.onGetProductPriceSuccess(bean, pics, packingBeans);
                baseView.dismissDialog();
            }
        });
    }


    public void spliceList(ArrayList<ProductDetailsPackingBean> packingBeans, String annexName) {
        if (annexName == null) return;
        if (annexName.contains("``")) {
            String[] strings = annexName.split("``");
            for (String keyValue : strings) {
                buildPackingBean(packingBeans, keyValue);
            }
        } else {
            buildPackingBean(packingBeans, annexName);
        }
    }

    private void buildPackingBean(ArrayList<ProductDetailsPackingBean> packingBeans, String keyValue) {
        ProductDetailsPackingBean packingBean = new ProductDetailsPackingBean();
        String[] key_value = keyValue.split("\\*");
        packingBean.setAttrKey(key_value[0]);
        packingBean.setAttrValue(key_value[1]);
        packingBeans.add(packingBean);
    }

    /**
     * 获取店铺推荐
     *
     * @param platformId
     * @param keyword
     * @param cid
     * @param pageNum
     * @param isDialog
     */
    @Override
    public void getHotSale(String platformId, String keyword, String cid, int pageNum, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);

        mModel.getHotSale(platformId, keyword, cid, pageNum, new HttpRxObserver<HotSaleBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(HotSaleBean response) {
                ArrayList<RecommendItemsBean> beans = new ArrayList<>();
                for (ItemResult element : response.getItemList().getResultList()) {
                    for (ItemSku itemSku : element.getItem_sku()) {
                        RecommendItemsBean bean = new RecommendItemsBean();
                        bean.setName(itemSku.getSkuName());
                        bean.setImageUrl(itemSku.getPictureUrl());
                        bean.setPrice(String.valueOf(itemSku.getSellPrice()));
                        bean.setCid(itemSku.getCid());
                        bean.setShopId(itemSku.getShopId());
                        bean.setItemId(itemSku.getItemId());
                        bean.setSkuId(itemSku.getSkuId());
                        beans.add(bean);
                        if (beans.size() == 6) break;
                    }
                    if (beans.size() == 6) break;
                }

                baseView.onGetHotSaleSuccess(beans);
                baseView.dismissDialog();
            }
        });
    }

    @Override
    public void getUserCollect(String skuId, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);
        mModel.getUserCollect(skuId, new HttpRxObserver<JSONObject>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(JSONObject response) {
                String string = response.toString();
                boolean isCollect = false;
                if (string.contains(String.valueOf(skuId)) && string.contains("true"))
                    isCollect = true;
                baseView.onGetUserCollectSuccess(isCollect);
                baseView.dismissDialog();
            }
        });
    }

    @Override
    public void getCartCount(String platformId, String organizeId, String accountId, boolean isDialog) {
//        if (isDialog) baseView.showResDialog(R.string.loading);
        mModel.getCartCount(platformId, organizeId, accountId, new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
//                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(String response) {
                baseView.onGetCartCountSuccess(response);
//                baseView.dismissDialog();
            }
        });
    }


    /**
     * 获取邮费
     *
     * @param shopId
     */
    @Override
    public void getProductDelivery(String shopId) {
        mModel.getProductDelivery(shopId, new HttpRxObserver<DeliveryBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);

            }

            @Override
            protected void onSuccess(DeliveryBean bean) {
                baseView.dismissDialog();
                if (isCallBack())
                    baseView.getDelivery(bean);
            }
        });
    }


    /**
     * 添加浏览记录
     *
     * @param categoryId
     * @param skuId
     * @param isDialog
     */
    @Override
    public void addSkuVisitTrack(String categoryId, String skuId, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);
        mModel.addSkuVisitTrack(categoryId, skuId, new HttpRxObserver<Boolean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(Boolean response) {
                baseView.dismissDialog();
            }
        });
    }


    /**
     * 请求sku库存
     *
     * @param supplierId
     * @param provinceId
     * @param cityId
     * @param countryId
     * @param address
     * @param skuNum
     * @param skuId
     * @param isDialog
     */
    public void querySkuSaleStocks(String supplierId, String provinceId, String cityId, String countryId,
                                   String address, String skuNum, String skuId, boolean isDialog) {
        if (isDialog) baseView.showResDialog(R.string.loading);
        mModel.querySkuSaleStocks(supplierId, provinceId, cityId, countryId, address, skuNum, skuId,
                new HttpRxObserver<ArrayList<SkuStockBean>>() {
                    @Override
                    protected void onError(ErrorBean e) {
                        baseView.onError(e);
                        baseView.dismissDialog();
                    }

                    @Override
                    protected void onSuccess(ArrayList<SkuStockBean> response) {
                        baseView.getSkuSaleStocks(response.get(0));
                        baseView.dismissDialog();
                    }
                });
    }
}

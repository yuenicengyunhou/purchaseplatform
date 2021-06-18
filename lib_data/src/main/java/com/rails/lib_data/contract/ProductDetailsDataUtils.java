package com.rails.lib_data.contract;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.DeliveryBean;
import com.rails.lib_data.bean.ProductServiceBean;
import com.rails.lib_data.bean.SkuStockBean;
import com.rails.lib_data.bean.forAppShow.ProductDetailsPackingBean;
import com.rails.lib_data.bean.forAppShow.ProductDetailsPageBean;
import com.rails.lib_data.bean.forAppShow.ProductDetailsPopBean;
import com.rails.lib_data.bean.forAppShow.ProductSpecificParameter;
import com.rails.lib_data.bean.forAppShow.RecommendItemsBean;
import com.rails.lib_data.bean.forAppShow.SpecificationPopBean;
import com.rails.lib_data.bean.forAppShow.SpecificationValue;
import com.rails.lib_data.bean.forNetRequest.productDetails.HotSaleBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemAfterSaleVo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemPictureVo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemPublishVo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemResult;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemSku;
import com.rails.lib_data.bean.forNetRequest.productDetails.ItemSkuInfo;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsStep1Bean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsStep2Bean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductDetailsStep3Bean;
import com.rails.lib_data.bean.forNetRequest.productDetails.ProductPriceBean;
import com.rails.lib_data.bean.forNetRequest.productDetails.SkuSpecInfo;
import com.rails.lib_data.bean.forNetRequest.productDetails.SupplierInfoImportData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Set;

public class ProductDetailsDataUtils {

    final private String TAG = ProductDetailsDataUtils.class.getSimpleName();

    final private String CREDIT_LEVEL_1 = "A";
    final private String CREDIT_LEVEL_2 = "B";
    final private String CREDIT_LEVEL_3 = "C";
    final private String CREDIT_LEVEL_4 = "D";
    final private String CREDIT_NAME_1 = " ";
    final private String CREDIT_NAME_2 = "风险较低";
    final private String CREDIT_NAME_3 = "风险较高";
    final private String CREDIT_NAME_4 = "风险极高";

    private ProductDetailsDataUtils() {

    }

    public static ProductDetailsDataUtils getInstance() {
        return ProductDetailsDataUtilsHolder.INSTANCE;
    }

    private static class ProductDetailsDataUtilsHolder {
        private static final ProductDetailsDataUtils INSTANCE = new ProductDetailsDataUtils();
    }

    private ProductDetailsPageBean productDetailsPageBean;

    private ProductDetailsPopBean productDetailsPopBean;


    public ProductDetailsPageBean getProductDetailsPageBean() {
        return productDetailsPageBean;
    }

    public ProductDetailsPopBean getProductDetailsPopBean() {
        return productDetailsPopBean;
    }

    /**
     * 解析数据获取 {@link #productDetailsPageBean} 和 {@link #productDetailsPopBean}
     *
     * @param context Context
     * @param bean1   Step1 result
     * @param bean2   Step2 result
     */
    public void analysisAllData(Context context, ProductDetailsStep1Bean bean1, ProductDetailsStep2Bean bean2) {
        ProductDetailsPageBean pageBean = new ProductDetailsPageBean();
        ProductDetailsPopBean popBean = new ProductDetailsPopBean();

        ProductDetailsBean detailsBean = bean1.getProductDetailsBean();

        // productBean todo 后期优化 不再使用此项属性
        ProductDetailsBean productDetailsBean = bean1.getProductDetailsBean();
        pageBean.setProductDetailsBean(productDetailsBean);

        // cid
        String cid = String.valueOf(productDetailsBean.getItemPublishVo().getCid());
        pageBean.setCid(cid);

        // skuStockBean
        SkuStockBean stockBean = bean2.getSkuStockBeans().get(0);
        pageBean.setSkuStockBean(stockBean);

        // 当前ItemSkuInfo
        ItemSkuInfo currentItemSkuInfo = bean1.getProductDetailsBean().getItemSkuInfoList().get(0);
        pageBean.setCurrentItemSkuInfo(currentItemSkuInfo);

        // 当前的skuId
        String skuId = currentItemSkuInfo.getId();
        pageBean.setSkuId(skuId);

        // sku价格（在售价格、市场价格、sku名称、sku图片、评分、销量）
        ProductPriceBean priceBean = bean2.getProductPriceBeans().get(0);
        pageBean.setPriceBean(priceBean);

        // 在售价格
        String sellPrice = String.format("%.2f", priceBean.getSellPrice());
        pageBean.setSellPrice(sellPrice);

        // 市场价格
        String marketPrice = String.format("%.2f", priceBean.getMarketPrice());
        pageBean.setMarketPrice(marketPrice);

        // sku名称 具体某个Sku的名称
        String productSkuName = currentItemSkuInfo.getSkuName();
        pageBean.setSkuName(productSkuName);

        // sku型号名称
        String productSkuAttrName = currentItemSkuInfo.getAttributesName();
        pageBean.setProductAttrName(productSkuAttrName);

        // 商品评分
        double productScore = priceBean.getScore();
        pageBean.setProductScore(productScore);

        // 商品销量
        String saleCount = String.valueOf(priceBean.getSaleNum());
        pageBean.setSaleCount(saleCount);

        // 包装清单
        ArrayList<ProductDetailsPackingBean> packingBeans = getPackingBeans(priceBean);
        pageBean.setPackingList(packingBeans);

        // 页面顶部轮播图
        ArrayList<String> topPictures = getTopPictureList(detailsBean);
        pageBean.setTopPictureList(topPictures);

        // 售后服务说明
        ArrayList<ProductServiceBean> serviceBeans = getServiceBeans(detailsBean);
        pageBean.setServiceList(serviceBeans);

        // 推荐单位说明
        ArrayList<ProductServiceBean> companyBeans = getCompanyBeans(detailsBean);
        pageBean.setCompanyList(companyBeans);

        // 选择型号弹窗Bean
        ArrayList<SpecificationPopBean> specPopBean = getSpecificationPopBeans(detailsBean);
        pageBean.setSpecPopBeanList(specPopBean);

        // 店铺推荐商品Bean
        ArrayList<RecommendItemsBean> shopRecommendItemList = getRecommends(bean2.getHotSaleBean());
        pageBean.setRecommendItemList(shopRecommendItemList);

        // 获取购物车内商品数量
        String cartCount = bean1.getCartCount();
        pageBean.setCartCount(cartCount);

        // 获取邮费
        String delivery = getDelivery(bean2.getDeliveryBean());
        pageBean.setDelivery(delivery);

        // 获取库存
        boolean isInStock = isInStock(bean2.getSkuStockBeans());
        pageBean.setInStock(isInStock);

        // 获取商品收藏状态
        boolean isCollected = getItemCollected(currentItemSkuInfo.getId(), bean2.getCollect());
        pageBean.setCollected(isCollected);

        // 获取商品名称
        String productName = detailsBean.getItemPublishVo().getItemName();
        pageBean.setProductName(productName);

        // 获取店铺名称
        String shopName = detailsBean.getItemPublishVo().getShopName();
        pageBean.setShopName(shopName);

        // 获取店铺ID
        String shopId = detailsBean.getItemPublishVo().getShopId();
        pageBean.setShopId(shopId);

        // 获取店铺Logo
        String logoUrl = detailsBean.getItemPublishVo().getLogoUrl();
        pageBean.setShopLogo(logoUrl);

        // 获取店铺风险等级
        String shopSecurity = getSecurityText(detailsBean);
        pageBean.setShopSecurity(shopSecurity);

        // 获取风险等级图标
        Drawable shopSecurityIcon = getSecurityIcon(context, shopSecurity);
        pageBean.setShopSecurityIcon(shopSecurityIcon);

        // 获取用户地址
        ArrayList<AddressBean> addressList = bean1.getAddressBeanList();
        pageBean.setAddressList(addressList);

        // 省
        String provinceCode = getProvinceCode(addressList);
        pageBean.setProvinceCode(provinceCode);

        // 市
        String cityCode = getCityCode(addressList);
        pageBean.setCityCode(cityCode);

        // 区/县
        String countryCode = getCountryCode(addressList);
        pageBean.setCountryCode(countryCode);

        // 地址
        String fullAddress = getFullAddress(addressList);
        pageBean.setFullAddress(fullAddress);

        // 获取商品介绍
        String longDescribeUrl = bean1.getProductDetailsBean().getItemPublishVo().getDescribeUrl();
        pageBean.setDetailsPictureUrl(longDescribeUrl);


        productDetailsPageBean = pageBean;
        productDetailsPopBean = popBean;
    }


    public void analysisPopData(Context context, ProductDetailsStep3Bean bean3) {

    }


    /**
     * 商品详情 - 参数弹窗 - 参数
     *
     * @param parameters
     * @param detailsBean
     * @param currentItemSkuInfo
     * @return
     */
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

    /**
     * 商品详情 - 参数弹窗 - 详细参数
     *
     * @param parameters
     * @param detailsBean
     * @param currentItemSkuInfo
     * @return
     */
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

    /**
     * 获取售后服务说明
     *
     * @param detailsBean
     * @return
     */
    @NotNull
    public ArrayList<ProductServiceBean> getServiceBeans(ProductDetailsBean detailsBean) {
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
            e.printStackTrace();
        }
        return serviceBeans;
    }

    /**
     * 获取推荐单位说明
     *
     * @param detailsBean
     * @return
     */
    @NotNull
    public ArrayList<ProductServiceBean> getCompanyBeans(ProductDetailsBean detailsBean) {
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
            e.printStackTrace();
        }
        return companyBeans;
    }


    /**
     * 获取包装清单
     *
     * @param priceBean
     * @return
     */
    @NotNull
    public ArrayList<ProductDetailsPackingBean> getPackingBeans(ProductPriceBean priceBean) {
        ArrayList<ProductDetailsPackingBean> packingBeans = new ArrayList<>();
        if (priceBean.getPackinglist() != null && priceBean.getPackinglist().size() != 0) {
            ProductDetailsPackingBean bean1 = new ProductDetailsPackingBean();
            bean1.setAttrKey("附件名称");
            bean1.setAttrValue("附件数量");
            packingBeans.add(bean1);
            String annexName = priceBean.getPackinglist().get(0).getAnnexName();
            spliceList(packingBeans, annexName);
        }
        return packingBeans;
    }

    public void spliceList(ArrayList<ProductDetailsPackingBean> packingBeans, String annexName) {
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
     * 获取省码
     *
     * @param addressBeans
     * @return
     */
    public String getProvinceCode(ArrayList<AddressBean> addressBeans) {
        if (addressBeans == null
                || addressBeans.size() == 0
                || TextUtils.isEmpty(addressBeans.get(0).getProvinceCode()))
            return "11";
        return addressBeans.get(0).getProvinceCode();
    }

    /**
     * 获取地区码
     *
     * @param addressBeans
     * @return
     */
    public String getCityCode(ArrayList<AddressBean> addressBeans) {
        if (addressBeans == null
                || addressBeans.size() == 0
                || TextUtils.isEmpty(addressBeans.get(0).getCityCode()))
            return "01";
        return addressBeans.get(0).getCityCode();
    }

    /**
     * 获取市/县码
     *
     * @param addressBeans
     * @return
     */
    public String getCountryCode(ArrayList<AddressBean> addressBeans) {
        if (addressBeans == null
                || addressBeans.size() == 0
                || TextUtils.isEmpty(addressBeans.get(0).getCountryCode()))
            return "01";
        return addressBeans.get(0).getCountryCode();
    }

    /**
     * 获取地址
     *
     * @param addressBeans
     * @return
     */
    public String getFullAddress(ArrayList<AddressBean> addressBeans) {
        if (addressBeans == null
                || addressBeans.size() == 0
                || TextUtils.isEmpty(addressBeans.get(0).getFullAddress()))
            return "北京市市辖区东城区";
        return addressBeans.get(0).getFullAddress();
    }

    /**
     * 组装购物车弹窗和规格弹窗的数据
     * -01- 使用第一个ItemSkuInfo获取所有的 型号名称 单存一个集合 记录集合长度
     * -02- 切割每个ItemSkuInfo的attributeName 存入集合 有重复的不要存
     * -03- 呵呵
     *
     * @param bean
     * @return
     */
    public ArrayList<SpecificationPopBean> getSpecificationPopBeans(ProductDetailsBean bean) {
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
     * 获取顶部轮播图
     *
     * @param bean
     * @return
     */
    public ArrayList<String> getTopPictureList(ProductDetailsBean bean) {
        ArrayList<String> pictures = new ArrayList<>();
        for (ItemPictureVo pictureVo : bean.getItemPictureVoList()) {
            String url = pictureVo.getPictureUrl();
            pictures.add("https:" + url);
        }
        return pictures;
    }

    /**
     * 获取店铺推荐商品Bean
     *
     * @param hotSaleBean
     * @return
     */
    public ArrayList<RecommendItemsBean> getRecommends(HotSaleBean hotSaleBean) {
        ArrayList<RecommendItemsBean> recommendList = new ArrayList<>();
        for (ItemResult element : hotSaleBean.getItemList().getResultList()) {
            for (ItemSku itemSku : element.getItem_sku()) {
                RecommendItemsBean bean = new RecommendItemsBean();
                bean.setName(itemSku.getSkuName());
                bean.setImageUrl(itemSku.getPictureUrl());
                bean.setPrice(String.valueOf(itemSku.getSellPrice()));
                bean.setItemId(itemSku.getItemId());
                recommendList.add(bean);
                if (recommendList.size() == 6) break;
            }
            if (recommendList.size() == 6) break;
        }
        return recommendList;
    }

    /**
     * 获取商品收藏状态
     *
     * @param skuId
     * @param jsonObject
     * @return
     */
    public boolean getItemCollected(String skuId, JSONObject jsonObject) {
        String string = jsonObject.toString();
        boolean isCollect = false;
        if (string.contains(String.valueOf(skuId)) && string.contains("true"))
            isCollect = true;
        return isCollect;
    }

    /**
     * 获取邮费
     *
     * @param deliveryBean
     * @return
     */
    public String getDelivery(DeliveryBean deliveryBean) {
        String delivery = "";
        if (deliveryBean == null)
            return delivery;
        delivery = String.format("店铺满%s元包邮", deliveryBean.getFreightPrice());
        return delivery;
    }

    /**
     * 获取库存状态
     *
     * @param skuStockBeans
     * @return
     */
    public boolean isInStock(ArrayList<SkuStockBean> skuStockBeans) {
        boolean haveNoStock = skuStockBeans == null
                || skuStockBeans.size() == 0
                || skuStockBeans.get(0) == null
                || skuStockBeans.get(0).getSaleState() == null
                || skuStockBeans.get(0).getSaleState().equals("0")
                || skuStockBeans.get(0).getSkuStock() == null
                || skuStockBeans.get(0).getSkuStock().equals("0");
        return !haveNoStock;
    }

    /**
     * 获取商品介绍
     *
     * @param detailsBean
     * @return
     */
    public ArrayList<Bitmap> getDescribePictures(Context context, ProductDetailsBean detailsBean) {
        ArrayList<Bitmap> describePics = new ArrayList<>();
        return describePics;
    }


    /**
     * 风险等级文字
     *
     * @param bean
     * @return
     */
    private String getSecurityText(ProductDetailsBean bean) {
        String creditLv = bean.getItemPublishVo().getCreditLevel();
        String text = CREDIT_NAME_1;
        if (creditLv == null) {
            return text;
        }
        switch (creditLv) {
            case CREDIT_LEVEL_2:
                text = CREDIT_NAME_2;
                break;
            case CREDIT_LEVEL_3:
                text = CREDIT_NAME_3;
                break;
            case CREDIT_LEVEL_4:
                text = CREDIT_NAME_4;
                break;
            default:
                break;
        }
        return text;
    }

    /**
     * 风险等级图标
     *
     * @param context
     * @param level
     * @return
     */
    private Drawable getSecurityIcon(Context context, String level) {
        Drawable drawable = null;
        switch (level) {
            case CREDIT_NAME_2:
                drawable = context.getResources().getDrawable(R.drawable.ic_security_b);
                break;
            case CREDIT_NAME_3:
                drawable = context.getResources().getDrawable(R.drawable.ic_security_c);
                break;
            case CREDIT_NAME_4:
                drawable = context.getResources().getDrawable(R.drawable.ic_security_d);
                break;
            default:
                break;
        }
        return drawable;
    }


}

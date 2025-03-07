package com.rails.lib_data.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.databinding.ObservableField;

/**
 * 商城首页推荐类别产品bean
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/8
 */
public class ProductRecBean implements Serializable {


    /**
     * id : 3370
     * key : 3370
     * platformId : 20
     * sortNumber : 1
     * floorTitle : 热销产品
     * iconUrl : //oss.mall.95306.cn/mall/d1457b72c3fb323a2671125aef3eab5d20200904165554646.png
     * floorPictureUrl : //oss.mall.95306.cn/mall/e0d8c4048cf94686afb662eb1f29bbf920200505193207340.jpg
     * templateType : 1
     * templateTypeView : 单层4图
     * operatorId : 1000091792
     * operatorName : gtscyy03
     * created : 2021-03-08 15:00:53
     * modified : 2021-03-08 15:00:53
     * yn : 1
     * linkUrl : null
     */




    private Integer id;
    private Integer key;
    private Integer platformId;
    private Integer sortNumber;
    private String floorTitle;
    private String iconUrl;
    private String floorPictureUrl;
    private Integer templateType;
    private String templateTypeView;
    private Integer operatorId;
    private String operatorName;
    private String created;
    private String modified;
    private Integer yn;
    private String linkUrl;
    private ArrayList<ProductBean> floorList;
    private String color;
    private int res;


    private String firstCategoryId;
    private String firstCategoryName;
    private String statisticsDate;
    private String createTime;
    private Integer orderNum;
    private Integer rank;
    private String englishName;
    private String lowerRgb;
    private String chineseenglishRgb;
    private String topPhoto;
    private String topPhotoUrl;
    public final ObservableField<Boolean> isSel = new ObservableField<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getFloorTitle() {
        return floorTitle;
    }

    public void setFloorTitle(String floorTitle) {
        this.floorTitle = floorTitle;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getFloorPictureUrl() {
        return floorPictureUrl;
//        return "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1696385818,1925333725&fm=26&gp=0.jpg";
    }

    public void setFloorPictureUrl(String floorPictureUrl) {
        this.floorPictureUrl = floorPictureUrl;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public String getTemplateTypeView() {
        return templateTypeView;
    }

    public void setTemplateTypeView(String templateTypeView) {
        this.templateTypeView = templateTypeView;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public Object getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public ArrayList<ProductBean> getFloorList() {
        return floorList;
    }

    public void setFloorList(ArrayList<ProductBean> floorList) {
        this.floorList = floorList;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }


    public String getFirstCategoryId() {
        return firstCategoryId;
    }

    public void setFirstCategoryId(String firstCategoryId) {
        this.firstCategoryId = firstCategoryId;
    }

    public String getFirstCategoryName() {
        return firstCategoryName;
    }

    public void setFirstCategoryName(String firstCategoryName) {
        this.firstCategoryName = firstCategoryName;
    }

    public String getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(String statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getLowerRgb() {
        if (TextUtils.isEmpty(lowerRgb)){
            return "#9F4343";
        }
        return lowerRgb;
    }

    public void setLowerRgb(String lowerRgb) {
        this.lowerRgb = lowerRgb;
    }

    public String getChineseenglishRgb() {
        if (TextUtils.isEmpty(chineseenglishRgb)){
            return "#FFFFFFFF";
        }
        return chineseenglishRgb;
    }

    public void setChineseenglishRgb(String chineseenglishRgb) {
        this.chineseenglishRgb = chineseenglishRgb;
    }

    public String getTopPhoto() {
        return topPhoto;
    }

    public void setTopPhoto(String topPhoto) {
        this.topPhoto = topPhoto;
    }


    public String getTopPhotoUrl() {
        return topPhotoUrl;
    }

    public void setTopPhotoUrl(String topPhotoUrl) {
        this.topPhotoUrl = topPhotoUrl;
    }
}

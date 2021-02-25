package com.rails.lib_data.bean;

/**
 * 公告信息（拍卖/招标）
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/25
 */
public class NoticeBean {


    /**
     * checkTime : 2020-12-22 11:40:04
     * projType : 03
     * projTypeName : 服务类
     * id : e98dabda-2691-4af9-8a5b-cca50699decb
     * bidTypeName : 招标
     * notTitle : 兰州局集团公司兰州车站2021年度站台保洁业务外包招标补遗公告
     */

    private String checkTime;
    private String projType;
    private String projTypeName;
    private String id;
    private String bidTypeName;
    private String notTitle;

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getProjType() {
        return projType;
    }

    public void setProjType(String projType) {
        this.projType = projType;
    }

    public String getProjTypeName() {
        return projTypeName;
    }

    public void setProjTypeName(String projTypeName) {
        this.projTypeName = projTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBidTypeName() {
        return bidTypeName;
    }

    public void setBidTypeName(String bidTypeName) {
        this.bidTypeName = bidTypeName;
    }

    public String getNotTitle() {
        return notTitle;
    }

    public void setNotTitle(String notTitle) {
        this.notTitle = notTitle;
    }
}

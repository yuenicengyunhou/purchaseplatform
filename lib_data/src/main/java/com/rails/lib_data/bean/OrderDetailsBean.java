package com.rails.lib_data.bean;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsBean {


    /**
     * stateString : 已收货
     * purchaseNumber : 12012132748204
     * supplier : 得力文具
     * buyer : 中国铁路某某局集团有限公司-admin
     * receiveDelay : 2021-03-23
     * settleApartment : 中国铁路某某局集团有限公司
     * invoiceApartment : 中国铁路某某局集团有限公司
     * comment : 集中结算专票
     * buyerInfo : {"name":"王小帅","phoneNumber":"13158810201","address":"北京市海淀区大柳树路2号"}
     * process : [{"state":"提交订单","time":"2021-01-01 17:29:29"},{"state":"已确定","time":"2021-01-01 17:29:29"},{"state":"已收货","time":"2021-01-01 17:29:29"},{"state":"测试","time":"2021-01-01 17:29:29"}]
     * audit : [{"state":"一级审批","auditMan":"admin","time":"2021-01-01 17:29:29"},{"state":"二级审批","auditMan":"admin","time":"2021-01-01 17:29:29"},{"state":"三级审批","auditMan":"admin","time":"2021-01-01 17:29:29"}]
     * createdOrder : [{"orderNum":"1349485893038450272773","generateTime":"2021-01-01 17:29:29"},{"orderNum":"1349485893038450272773","generateTime":"2021-01-01 17:29:29"}]
     * orderList : [{"code":"20210308125400","time":"2021.03.08 12:54:00","men":"得力文具","purchars":"中国铁路局某集团有限公司","totalPrice":"1308.80","order":[{"orderNumber":"20210308125400","generateTime":"20210308125400","provider":"得力文具","buyer":"中国铁路局某集团有限公司","delayTime":"2021-04-02","orderItemBeans":[{"orderNumber":"20210308125400","generateTime":"20210308125400","orderState":"准备出库","pictureUrl":"https://oss.mall.95306.cn/mall/a39ac26ef82abd36f1825b068fb3b56b20210129135909663.jpg","itemName":"得力正品2B铅笔小学生专用考试专用","itemPrice":"59.90","itemType":"100支/盒","itemCount":"10","itemNum":"11115649","totalPrice":"599.00"},{"orderNumber":"20210308125400","generateTime":"20210308125400","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/a39ac26ef82abd36f1825b068fb3b56b20210129135909663.jpg","itemName":"酒店会议专用幼儿园宝宝用HB铅笔","itemPrice":"33.99","itemType":"100支/盒","itemCount":"10","itemNum":"11115650","totalPrice":"339.90"},{"orderNumber":"20210308125400","generateTime":"20210308125400","orderState":"未发货","pictureUrl":"https://oss.mall.95306.cn/mall/a39ac26ef82abd36f1825b068fb3b56b20210129135909663.jpg","itemName":"正品中华牌HB铅笔素描美术专用","itemPrice":"36.99","itemType":"100支/盒","itemCount":"10","itemNum":"11115651","totalPrice":"369.90"}],"totalPrice":"1308.80"},{"orderNumber":"20210312160750","generateTime":"20210312160750","provider":"联想电脑","buyer":"中国铁路科学研究院","delayTime":"","orderItemBeans":[{"orderNumber":"20210312160750","generateTime":"20210312160750","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/a39ac26ef82abd36f1825b068fb3b56b20210129135909663.jpg","itemName":"联想ThinkVision P27i-10（27英寸低蓝光护眼窄边框显示器，分辨率1920*1080，VGA+HDMI+DP接口，支持俯仰、升降、旋转）","itemPrice":"2288.00","itemType":"27英寸低蓝光护眼窄边框显示器","itemCount":"2","itemNum":"44568953","totalPrice":"4576.00"}],"totalPrice":"52434.00"},{"orderNumber":"20210319095133","generateTime":"20210319095133","provider":"鲁花香","buyer":"中铁十三局","delayTime":null,"orderItemBeans":[{"orderNumber":"20210319095133","generateTime":"20210319095133","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/c0497521d35d66866e3af408094864c320210115192011241.jpg","itemName":"（整箱4桶）鲁花 食用油 5S 压榨一级 花生油 5L（3.1-5L 5000）","itemPrice":"208.88","itemType":"4桶/箱","itemCount":"8","itemNum":"0097498725","totalPrice":"1671.04"},{"orderNumber":"20210319095133","generateTime":"20210319095133","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/6512bd43d9caa6e02c990b0a82652dca20210118102726839.jpg","itemName":"晋思源 稻花香大米10KG（10kg）","itemPrice":"98.00","itemType":"10KG/袋","itemCount":"100","itemNum":"0097498729","totalPrice":"9800.00"},{"orderNumber":"20210319095133","generateTime":"20210319095133","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/a4a1ba17583cfe90320350fb820ca54120210118102831477.jpg","itemName":"晋思源 雪花粉（25kg）","itemPrice":"102.24","itemType":"25KG/袋","itemCount":"100","itemNum":"0097498737","totalPrice":"10224.00"},{"orderNumber":"20210319095133","generateTime":"20210319095133","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/396659e52bb13faf746d147f3edb3d4f20210118102922266.jpg","itemName":"晋思源 亚麻籽油（2500ml）","itemPrice":"59.88","itemType":"2.5L/桶","itemCount":"50","itemNum":"0097498744","totalPrice":"2994.00"},{"orderNumber":"20210319095133","generateTime":"20210319095133","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/c0497521d35d66866e3af408094864c320210120141819574.jpg","itemName":"胡老七蟹田米25kg（25kg）","itemPrice":"114.00","itemType":"25KG/袋","itemCount":"50","itemNum":"0097498762","totalPrice":"5700.00"}],"totalPrice":"30,389.04"},{"orderNumber":"20210327042953","generateTime":"20210327042953","provider":"得力工具","buyer":"中铁七局","delayTime":"2021-03-31","orderItemBeans":[{"orderNumber":"20210327042953","generateTime":"20210327042953","orderState":"","pictureUrl":"https://oss.mall.95306.cn/mall/d24bade136bc8cd77e37395ea94226eb20210115192423691.jpg","itemName":"得力工具DL-P6_帆布工具包320x110x310mm(新VI)（DL-P6）","itemPrice":"299.00","itemType":"DL-P6 320*110*310","itemCount":"2","itemNum":"0851487348","totalPrice":"598.00"}],"totalPrice":"598.00"}]},{"code":"20210308125400","time":"2021.03.08 12:54:00","men":"得力文具","purchars":"中国铁路局某集团有限公司","totalPrice":"1308.80","order":[{"orderNumber":"20210308125400","generateTime":"20210308125400","provider":"得力文具","buyer":"中国铁路局某集团有限公司","delayTime":"2021-04-02","orderItemBeans":[{"orderNumber":"20210308125400","generateTime":"20210308125400","orderState":"准备出库","pictureUrl":"https://oss.mall.95306.cn/mall/a39ac26ef82abd36f1825b068fb3b56b20210129135909663.jpg","itemName":"得力正品2B铅笔小学生专用考试专用","itemPrice":"59.90","itemType":"100支/盒","itemCount":"10","itemNum":"11115649","totalPrice":"599.00"},{"orderNumber":"20210308125400","generateTime":"20210308125400","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/a39ac26ef82abd36f1825b068fb3b56b20210129135909663.jpg","itemName":"酒店会议专用幼儿园宝宝用HB铅笔","itemPrice":"33.99","itemType":"100支/盒","itemCount":"10","itemNum":"11115650","totalPrice":"339.90"},{"orderNumber":"20210308125400","generateTime":"20210308125400","orderState":"未发货","pictureUrl":"https://oss.mall.95306.cn/mall/a39ac26ef82abd36f1825b068fb3b56b20210129135909663.jpg","itemName":"正品中华牌HB铅笔素描美术专用","itemPrice":"36.99","itemType":"100支/盒","itemCount":"10","itemNum":"11115651","totalPrice":"369.90"}],"totalPrice":"1308.80"},{"orderNumber":"20210312160750","generateTime":"20210312160750","provider":"联想电脑","buyer":"中国铁路科学研究院","delayTime":"","orderItemBeans":[{"orderNumber":"20210312160750","generateTime":"20210312160750","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/a39ac26ef82abd36f1825b068fb3b56b20210129135909663.jpg","itemName":"联想ThinkVision P27i-10（27英寸低蓝光护眼窄边框显示器，分辨率1920*1080，VGA+HDMI+DP接口，支持俯仰、升降、旋转）","itemPrice":"2288.00","itemType":"27英寸低蓝光护眼窄边框显示器","itemCount":"2","itemNum":"44568953","totalPrice":"4576.00"}],"totalPrice":"52434.00"},{"orderNumber":"20210319095133","generateTime":"20210319095133","provider":"鲁花香","buyer":"中铁十三局","delayTime":null,"orderItemBeans":[{"orderNumber":"20210319095133","generateTime":"20210319095133","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/c0497521d35d66866e3af408094864c320210115192011241.jpg","itemName":"（整箱4桶）鲁花 食用油 5S 压榨一级 花生油 5L（3.1-5L 5000）","itemPrice":"208.88","itemType":"4桶/箱","itemCount":"8","itemNum":"0097498725","totalPrice":"1671.04"},{"orderNumber":"20210319095133","generateTime":"20210319095133","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/6512bd43d9caa6e02c990b0a82652dca20210118102726839.jpg","itemName":"晋思源 稻花香大米10KG（10kg）","itemPrice":"98.00","itemType":"10KG/袋","itemCount":"100","itemNum":"0097498729","totalPrice":"9800.00"},{"orderNumber":"20210319095133","generateTime":"20210319095133","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/a4a1ba17583cfe90320350fb820ca54120210118102831477.jpg","itemName":"晋思源 雪花粉（25kg）","itemPrice":"102.24","itemType":"25KG/袋","itemCount":"100","itemNum":"0097498737","totalPrice":"10224.00"},{"orderNumber":"20210319095133","generateTime":"20210319095133","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/396659e52bb13faf746d147f3edb3d4f20210118102922266.jpg","itemName":"晋思源 亚麻籽油（2500ml）","itemPrice":"59.88","itemType":"2.5L/桶","itemCount":"50","itemNum":"0097498744","totalPrice":"2994.00"},{"orderNumber":"20210319095133","generateTime":"20210319095133","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/c0497521d35d66866e3af408094864c320210120141819574.jpg","itemName":"胡老七蟹田米25kg（25kg）","itemPrice":"114.00","itemType":"25KG/袋","itemCount":"50","itemNum":"0097498762","totalPrice":"5700.00"}],"totalPrice":"30,389.04"},{"orderNumber":"20210327042953","generateTime":"20210327042953","provider":"得力工具","buyer":"中铁七局","delayTime":"2021-03-31","orderItemBeans":[{"orderNumber":"20210327042953","generateTime":"20210327042953","orderState":"","pictureUrl":"https://oss.mall.95306.cn/mall/d24bade136bc8cd77e37395ea94226eb20210115192423691.jpg","itemName":"得力工具DL-P6_帆布工具包320x110x310mm(新VI)（DL-P6）","itemPrice":"299.00","itemType":"DL-P6 320*110*310","itemCount":"2","itemNum":"0851487348","totalPrice":"598.00"}],"totalPrice":"598.00"}]}]
     */

    private String stateString;
    private String purchaseNumber;
    private String supplier;
    private String buyer;
    private String receiveDelay;
    private String settleApartment;
    private String invoiceApartment;
    private String comment;
    /**
     * name : 王小帅
     * phoneNumber : 13158810201
     * address : 北京市海淀区大柳树路2号
     */

    private BuyerInfoBean buyerInfo;
    /**
     * state : 提交订单
     * time : 2021-01-01 17:29:29
     */

    private ArrayList<OrderProcessBean> process;
    /**
     * state : 一级审批
     * auditMan : admin
     * time : 2021-01-01 17:29:29
     */

    private ArrayList<OrderAuditBean> audit;
    /**
     * orderNum : 1349485893038450272773
     * generateTime : 2021-01-01 17:29:29
     */

    private ArrayList<OrderCreatedBean> createdOrder;
    /**
     * code : 20210308125400
     * time : 2021.03.08 12:54:00
     * men : 得力文具
     * purchars : 中国铁路局某集团有限公司
     * totalPrice : 1308.80
     * order : [{"orderNumber":"20210308125400","generateTime":"20210308125400","provider":"得力文具","buyer":"中国铁路局某集团有限公司","delayTime":"2021-04-02","orderItemBeans":[{"orderNumber":"20210308125400","generateTime":"20210308125400","orderState":"准备出库","pictureUrl":"https://oss.mall.95306.cn/mall/a39ac26ef82abd36f1825b068fb3b56b20210129135909663.jpg","itemName":"得力正品2B铅笔小学生专用考试专用","itemPrice":"59.90","itemType":"100支/盒","itemCount":"10","itemNum":"11115649","totalPrice":"599.00"},{"orderNumber":"20210308125400","generateTime":"20210308125400","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/a39ac26ef82abd36f1825b068fb3b56b20210129135909663.jpg","itemName":"酒店会议专用幼儿园宝宝用HB铅笔","itemPrice":"33.99","itemType":"100支/盒","itemCount":"10","itemNum":"11115650","totalPrice":"339.90"},{"orderNumber":"20210308125400","generateTime":"20210308125400","orderState":"未发货","pictureUrl":"https://oss.mall.95306.cn/mall/a39ac26ef82abd36f1825b068fb3b56b20210129135909663.jpg","itemName":"正品中华牌HB铅笔素描美术专用","itemPrice":"36.99","itemType":"100支/盒","itemCount":"10","itemNum":"11115651","totalPrice":"369.90"}],"totalPrice":"1308.80"},{"orderNumber":"20210312160750","generateTime":"20210312160750","provider":"联想电脑","buyer":"中国铁路科学研究院","delayTime":"","orderItemBeans":[{"orderNumber":"20210312160750","generateTime":"20210312160750","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/a39ac26ef82abd36f1825b068fb3b56b20210129135909663.jpg","itemName":"联想ThinkVision P27i-10（27英寸低蓝光护眼窄边框显示器，分辨率1920*1080，VGA+HDMI+DP接口，支持俯仰、升降、旋转）","itemPrice":"2288.00","itemType":"27英寸低蓝光护眼窄边框显示器","itemCount":"2","itemNum":"44568953","totalPrice":"4576.00"}],"totalPrice":"52434.00"},{"orderNumber":"20210319095133","generateTime":"20210319095133","provider":"鲁花香","buyer":"中铁十三局","delayTime":null,"orderItemBeans":[{"orderNumber":"20210319095133","generateTime":"20210319095133","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/c0497521d35d66866e3af408094864c320210115192011241.jpg","itemName":"（整箱4桶）鲁花 食用油 5S 压榨一级 花生油 5L（3.1-5L 5000）","itemPrice":"208.88","itemType":"4桶/箱","itemCount":"8","itemNum":"0097498725","totalPrice":"1671.04"},{"orderNumber":"20210319095133","generateTime":"20210319095133","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/6512bd43d9caa6e02c990b0a82652dca20210118102726839.jpg","itemName":"晋思源 稻花香大米10KG（10kg）","itemPrice":"98.00","itemType":"10KG/袋","itemCount":"100","itemNum":"0097498729","totalPrice":"9800.00"},{"orderNumber":"20210319095133","generateTime":"20210319095133","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/a4a1ba17583cfe90320350fb820ca54120210118102831477.jpg","itemName":"晋思源 雪花粉（25kg）","itemPrice":"102.24","itemType":"25KG/袋","itemCount":"100","itemNum":"0097498737","totalPrice":"10224.00"},{"orderNumber":"20210319095133","generateTime":"20210319095133","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/396659e52bb13faf746d147f3edb3d4f20210118102922266.jpg","itemName":"晋思源 亚麻籽油（2500ml）","itemPrice":"59.88","itemType":"2.5L/桶","itemCount":"50","itemNum":"0097498744","totalPrice":"2994.00"},{"orderNumber":"20210319095133","generateTime":"20210319095133","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/c0497521d35d66866e3af408094864c320210120141819574.jpg","itemName":"胡老七蟹田米25kg（25kg）","itemPrice":"114.00","itemType":"25KG/袋","itemCount":"50","itemNum":"0097498762","totalPrice":"5700.00"}],"totalPrice":"30,389.04"},{"orderNumber":"20210327042953","generateTime":"20210327042953","provider":"得力工具","buyer":"中铁七局","delayTime":"2021-03-31","orderItemBeans":[{"orderNumber":"20210327042953","generateTime":"20210327042953","orderState":"","pictureUrl":"https://oss.mall.95306.cn/mall/d24bade136bc8cd77e37395ea94226eb20210115192423691.jpg","itemName":"得力工具DL-P6_帆布工具包320x110x310mm(新VI)（DL-P6）","itemPrice":"299.00","itemType":"DL-P6 320*110*310","itemCount":"2","itemNum":"0851487348","totalPrice":"598.00"}],"totalPrice":"598.00"}]
     */

    private ArrayList<OrderListBean> orderList;

    public String getStateString() {
        return stateString;
    }

    public void setStateString(String stateString) {
        this.stateString = stateString;
    }

    public String getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(String purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getReceiveDelay() {
        return receiveDelay;
    }

    public void setReceiveDelay(String receiveDelay) {
        this.receiveDelay = receiveDelay;
    }

    public String getSettleApartment() {
        return settleApartment;
    }

    public void setSettleApartment(String settleApartment) {
        this.settleApartment = settleApartment;
    }

    public String getInvoiceApartment() {
        return invoiceApartment;
    }

    public void setInvoiceApartment(String invoiceApartment) {
        this.invoiceApartment = invoiceApartment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BuyerInfoBean getBuyerInfo() {
        return buyerInfo;
    }

    public void setBuyerInfo(BuyerInfoBean buyerInfo) {
        this.buyerInfo = buyerInfo;
    }

    public ArrayList<OrderProcessBean> getProcess() {
        return process;
    }

    public void setProcess(ArrayList<OrderProcessBean> process) {
        this.process = process;
    }

    public ArrayList<OrderAuditBean> getAudit() {
        return audit;
    }

    public void setAudit(ArrayList<OrderAuditBean> audit) {
        this.audit = audit;
    }

    public ArrayList<OrderCreatedBean> getCreatedOrder() {
        return createdOrder;
    }

    public void setCreatedOrder(ArrayList<OrderCreatedBean> createdOrder) {
        this.createdOrder = createdOrder;
    }

    public ArrayList<OrderListBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<OrderListBean> orderList) {
        this.orderList = orderList;
    }

    public static class BuyerInfoBean {
        private String name;
        private String phoneNumber;
        private String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }


    public static class OrderListBean {
        private String code;
        private String time;
        private String men;
        private String purchars;
        private String totalPrice;
        /**
         * orderNumber : 20210308125400
         * generateTime : 20210308125400
         * provider : 得力文具
         * buyer : 中国铁路局某集团有限公司
         * delayTime : 2021-04-02
         * orderItemBeans : [{"orderNumber":"20210308125400","generateTime":"20210308125400","orderState":"准备出库","pictureUrl":"https://oss.mall.95306.cn/mall/a39ac26ef82abd36f1825b068fb3b56b20210129135909663.jpg","itemName":"得力正品2B铅笔小学生专用考试专用","itemPrice":"59.90","itemType":"100支/盒","itemCount":"10","itemNum":"11115649","totalPrice":"599.00"},{"orderNumber":"20210308125400","generateTime":"20210308125400","orderState":"已发货","pictureUrl":"https://oss.mall.95306.cn/mall/a39ac26ef82abd36f1825b068fb3b56b20210129135909663.jpg","itemName":"酒店会议专用幼儿园宝宝用HB铅笔","itemPrice":"33.99","itemType":"100支/盒","itemCount":"10","itemNum":"11115650","totalPrice":"339.90"},{"orderNumber":"20210308125400","generateTime":"20210308125400","orderState":"未发货","pictureUrl":"https://oss.mall.95306.cn/mall/a39ac26ef82abd36f1825b068fb3b56b20210129135909663.jpg","itemName":"正品中华牌HB铅笔素描美术专用","itemPrice":"36.99","itemType":"100支/盒","itemCount":"10","itemNum":"11115651","totalPrice":"369.90"}]
         * totalPrice : 1308.80
         */

        private List<OrderBean> order;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMen() {
            return men;
        }

        public void setMen(String men) {
            this.men = men;
        }

        public String getPurchars() {
            return purchars;
        }

        public void setPurchars(String purchars) {
            this.purchars = purchars;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public List<OrderBean> getOrder() {
            return order;
        }

        public void setOrder(List<OrderBean> order) {
            this.order = order;
        }

        public static class OrderBean {
            private String orderNumber;
            private String generateTime;
            private String provider;
            private String buyer;
            private String delayTime;
            private String totalPrice;
            /**
             * orderNumber : 20210308125400
             * generateTime : 20210308125400
             * orderState : 准备出库
             * pictureUrl : https://oss.mall.95306.cn/mall/a39ac26ef82abd36f1825b068fb3b56b20210129135909663.jpg
             * itemName : 得力正品2B铅笔小学生专用考试专用
             * itemPrice : 59.90
             * itemType : 100支/盒
             * itemCount : 10
             * itemNum : 11115649
             * totalPrice : 599.00
             */

            private List<OrderItemBeansBean> orderItemBeans;

            public String getOrderNumber() {
                return orderNumber;
            }

            public void setOrderNumber(String orderNumber) {
                this.orderNumber = orderNumber;
            }

            public String getGenerateTime() {
                return generateTime;
            }

            public void setGenerateTime(String generateTime) {
                this.generateTime = generateTime;
            }

            public String getProvider() {
                return provider;
            }

            public void setProvider(String provider) {
                this.provider = provider;
            }

            public String getBuyer() {
                return buyer;
            }

            public void setBuyer(String buyer) {
                this.buyer = buyer;
            }

            public String getDelayTime() {
                return delayTime;
            }

            public void setDelayTime(String delayTime) {
                this.delayTime = delayTime;
            }

            public String getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(String totalPrice) {
                this.totalPrice = totalPrice;
            }

            public List<OrderItemBeansBean> getOrderItemBeans() {
                return orderItemBeans;
            }

            public void setOrderItemBeans(List<OrderItemBeansBean> orderItemBeans) {
                this.orderItemBeans = orderItemBeans;
            }

            public static class OrderItemBeansBean {
                private String orderNumber;
                private String generateTime;
                private String orderState;
                private String pictureUrl;
                private String itemName;
                private String itemPrice;
                private String itemType;
                private String itemCount;
                private String itemNum;
                private String totalPrice;

                public String getOrderNumber() {
                    return orderNumber;
                }

                public void setOrderNumber(String orderNumber) {
                    this.orderNumber = orderNumber;
                }

                public String getGenerateTime() {
                    return generateTime;
                }

                public void setGenerateTime(String generateTime) {
                    this.generateTime = generateTime;
                }

                public String getOrderState() {
                    return orderState;
                }

                public void setOrderState(String orderState) {
                    this.orderState = orderState;
                }

                public String getPictureUrl() {
                    return pictureUrl;
                }

                public void setPictureUrl(String pictureUrl) {
                    this.pictureUrl = pictureUrl;
                }

                public String getItemName() {
                    return itemName;
                }

                public void setItemName(String itemName) {
                    this.itemName = itemName;
                }

                public String getItemPrice() {
                    return itemPrice;
                }

                public void setItemPrice(String itemPrice) {
                    this.itemPrice = itemPrice;
                }

                public String getItemType() {
                    return itemType;
                }

                public void setItemType(String itemType) {
                    this.itemType = itemType;
                }

                public String getItemCount() {
                    return itemCount;
                }

                public void setItemCount(String itemCount) {
                    this.itemCount = itemCount;
                }

                public String getItemNum() {
                    return itemNum;
                }

                public void setItemNum(String itemNum) {
                    this.itemNum = itemNum;
                }

                public String getTotalPrice() {
                    return totalPrice;
                }

                public void setTotalPrice(String totalPrice) {
                    this.totalPrice = totalPrice;
                }
            }
        }
    }
}

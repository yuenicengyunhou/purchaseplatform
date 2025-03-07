package com.rails.lib_data.bean;

/**
 * 确认单对象
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/28
 */
public class OrderVerifyBean {


    /**
     * address : {"name":"张三三","phone":"139******583","address":"北京市海淀区大钟寺颐和家园8号院","isdefault":true}
     * cart : {"platformId":20,"userId":1000090104,"organizeId":13,"shopList":[{"platformId":20,"shopId":202003030109,"sellerId":58955,"shopName":"华为官方旗舰","shopType":2,"shopTypeView":"第三方卖货店铺","skuList":[{"shopId":202003030109,"itemId":1315833,"itemName":"华为 HUAWEI Mate 30 5G 麒麟990 4000万超感光徕卡影像双超级快充8GB+256GB亮黑色5G全网通游戏手机","skuName":"测试商品主图问题（红色）","skuId":1427348,"sourceSkuId":"","categoryId":1000887,"shortCode":"1427348","attributes":"[{\"aid\":\"29187\",\"vid\":\"134501\"}]","attributesName":"机壳颜色:亮黑色;内存版本:8GB + @256GB","pictureUrl":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3239984347,2818030613&fm=26&gp=0.jpg","saleStatus":1,"skuStatus":"","unit":"","skuNum":2,"marketPrice":"4999.00","sellPrice":"4999.00","subtotalPrice":"2.00","selected":false,"collect":false,"taxRate":"","packageDis":"**","packinglist":"","weight":"","lockout":false,"materialCode":"","recommendOrgId":"","brandId":"","brandName":"","firstCategoryId":"","firstCategoryName":"","secondCategoryId":"","secondCategoryName":"","thirdCategoryId":"","thirdCategoryName":"","limit":false,"canUser":true},{"shopId":202003030109,"itemId":1315833,"itemName":"华为 HUAWEI Mate X2 麒麟9000旗舰芯片 无缝鹰翼折叠 超感知徕卡四摄支持100倍双目变焦256GB冰晶蓝5G全网通","skuName":"测试商品主图问题（红色）","skuId":1427348,"sourceSkuId":"","categoryId":1000887,"shortCode":"1427348","attributes":"[{\"aid\":\"29187\",\"vid\":\"134501\"}]","attributesName":"机壳颜色:亮黑色;内存版本:8GB + @256GB","pictureUrl":"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=327821447,1451301479&fm=11&gp=0.jpg","saleStatus":1,"skuStatus":"","unit":"","skuNum":2,"marketPrice":"17999.00","sellPrice":"17999.00","subtotalPrice":"2.00","selected":false,"collect":false,"taxRate":"","packageDis":"**","packinglist":"","weight":"","lockout":false,"materialCode":"","recommendOrgId":"","brandId":"","brandName":"","firstCategoryId":"","firstCategoryName":"","secondCategoryId":"","secondCategoryName":"","thirdCategoryId":"","thirdCategoryName":"","limit":false,"canUser":true}],"selected":false,"subtotalPrice":"2.00","selectedSubPrice":"0","selectedSkuNum":0,"freightPrice":"2.00"},{"platformId":20,"shopId":202003030109,"sellerId":58955,"shopName":"tanmesso旗舰店","shopType":2,"shopTypeView":"第三方卖货店铺","skuList":[{"shopId":202003030109,"itemId":1315833,"itemName":"tanmesso送手包男包男士单肩包斜挎包商务休闲包公文皮包潮流背包","skuName":"测试商品主图问题（红色）","skuId":1427348,"sourceSkuId":"","categoryId":1000887,"shortCode":"1427348","attributes":"[{\"aid\":\"29187\",\"vid\":\"134501\"}]","attributesName":"颜色:棕色;","pictureUrl":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2901972870,1324449825&fm=26&gp=0.jpg","saleStatus":1,"skuStatus":"","unit":"","skuNum":2,"marketPrice":"187.50","sellPrice":"187.50","subtotalPrice":"2.00","selected":false,"collect":false,"taxRate":"","packageDis":"**","packinglist":"","weight":"","lockout":false,"materialCode":"","recommendOrgId":"","brandId":"","brandName":"","firstCategoryId":"","firstCategoryName":"","secondCategoryId":"","secondCategoryName":"","thirdCategoryId":"","thirdCategoryName":"","limit":true,"canUser":true},{"shopId":202003030109,"itemId":1315833,"itemName":"tanmesso时尚男包手提包A4商务包斜挎包单肩包公文包男士包包背包","skuName":"测试商品主图问题（红色）","skuId":1427348,"sourceSkuId":"","categoryId":1000887,"shortCode":"1427348","attributes":"[{\"aid\":\"29187\",\"vid\":\"134501\"}]","attributesName":"颜色:棕色;","pictureUrl":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3091424941,2542489160&fm=26&gp=0.jpg","saleStatus":1,"skuStatus":"","unit":"","skuNum":2,"marketPrice":"78.00","sellPrice":"78.00","subtotalPrice":"2.00","selected":false,"collect":false,"taxRate":"","packageDis":"**","packinglist":"","weight":"","lockout":false,"materialCode":"","recommendOrgId":"","brandId":"","brandName":"","firstCategoryId":"","firstCategoryName":"","secondCategoryId":"","secondCategoryName":"","thirdCategoryId":"","thirdCategoryName":"","limit":false,"canUser":false}],"selected":false,"subtotalPrice":"2.00","selectedSubPrice":"0","selectedSkuNum":0,"freightPrice":"2.00"}],"goodsNum":0,"totalSkuNum":2,"totalPrice":"0","paymentPrice":"0","freightPrice":"0","selected":false,"updateTime":1616134017524}
     * time : 2021.12.11
     * company : 中国铁路14局集团有限公司
     * payType : 账期支付
     * totalNum : 11
     * totalPay : 1235.43
     * yearTotal : 9999999.0
     * yearExtra : 9999999.0
     */

    private AddressBean invoiceAddress;
    private CartBean cart;
    private String time;
    private OrderBudgetBean budgetBean;
    private OrderPurchaseBean company;
    private InvoiceTitleBean invoice;

    public AddressBean getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(AddressBean invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public CartBean getCart() {
        return cart;
    }

    public void setCart(CartBean cart) {
        this.cart = cart;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public OrderBudgetBean getBudgetBean() {
        return budgetBean;
    }

    public void setBudgetBean(OrderBudgetBean budgetBean) {
        this.budgetBean = budgetBean;
    }

    public void setCompany(OrderPurchaseBean company) {
        this.company = company;
    }

    public OrderPurchaseBean getCompany() {
        return company;
    }

    public InvoiceTitleBean getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceTitleBean invoice) {
        this.invoice = invoice;
    }
}

package com.rails.lib_data.contract;

import android.app.Activity;
import android.util.Log;

import com.rails.lib_data.bean.BuyerBean;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.ListBeen;
import com.rails.lib_data.bean.OrderFilterBean;
import com.rails.lib_data.bean.OrderInfoBean;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.model.OrderModel;
import com.rails.purchaseplatform.framwork.BaseApp;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;

import java.util.ArrayList;

public class OrderPresenterImpl extends BasePresenter<OrderContract.OrderView> implements OrderContract.OrderPresenter {

    private final OrderModel model;
    private String accountId = "";
    private String organizeName;
    private String organizationId = "";

    public OrderPresenterImpl(Activity mContext, OrderContract.OrderView orderView) {
        super(mContext, orderView);
        model = new OrderModel();
        UserInfoBean bean = PrefrenceUtil.getInstance(BaseApp.getContext()).getBean(ConShare.USERINFO, UserInfoBean.class);
        if (null == bean) {
            return;
        }
        accountId = bean.getId();
        organizeName = bean.getDepartmentOrganizationName();
        organizationId = bean.getDepartmentOrganizationId();
    }

    /**
     * 采购人 用户名列表
     */
    @Override
    public void getBuyerNameList(String nameLike, String findType) {
        model.getBuyerNames(nameLike, findType, organizationId, new HttpRxObserver<ArrayList<BuyerBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);

            }

            @Override
            protected void onSuccess(ArrayList<BuyerBean> response) {
                baseView.loadConditionNameList(response);
            }
        });
    }

    /*{"code":"0","msg":"查询成功","data":[{"id":2853,"supplierId":62839,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"91220701072256304B","supplierName":"沈铁松原三江港实业集团有限公司开发区产业园分公司","supplierStatus":2,"recommendOrg":"国铁物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":"劳保用品","cid":null,"createTime":1616055630000,"modifyTime":1616122518000,"yn":1,"code":"fcb4c103-4db4-4584-a406-18cb617532d1","threeCategoryName":"洗衣皂,身体防护,呼吸防护,手部防护","bindOrgId":null,"threeCid":"1005576,1005563,1005560,1005564","erow":null},{"id":2912,"supplierId":86134,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"914101057440548455","supplierName":"河南海利未来科技集团有限公司","supplierStatus":2,"recommendOrg":"国铁物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":null,"cid":null,"createTime":1616055643000,"modifyTime":1616122731000,"yn":1,"code":"d8e4a547-2097-4121-a7e8-cd2e579e646f","threeCategoryName":null,"bindOrgId":null,"threeCid":null,"erow":null},{"id":2938,"supplierId":87227,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"91120102066876793N","supplierName":"中国铁路北京局集团有限公司研发中心","supplierStatus":2,"recommendOrg":"国铁物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":"工业品","cid":null,"createTime":1616055649000,"modifyTime":1616122490000,"yn":1,"code":"cbcb47e8-d027-4c31-958f-56bc5653e885","threeCategoryName":"化学试剂","bindOrgId":null,"threeCid":"1005397","erow":null},{"id":2946,"supplierId":96560,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"91320612723504050G","supplierName":"江苏鹏源纺织集团有限公司","supplierStatus":2,"recommendOrg":"国铁物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":"家纺布艺","cid":null,"createTime":1616055662000,"modifyTime":1616122963000,"yn":1,"code":"acb462a9-f6c2-4c56-ad45-adf1aedb8bb3","threeCategoryName":"枕巾枕套,三件套,浴巾,床单/床笠,四件套,被套","bindOrgId":null,"threeCid":"1005542,1005534,1005553,1005522,1005535,1005517","erow":null},{"id":2992,"supplierId":65874,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"911101165604060256","supplierName":"北京大为家具集团有限公司","supplierStatus":2,"recommendOrg":"国铁物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":"装修建材","cid":null,"createTime":1616055678000,"modifyTime":1616123087000,"yn":1,"code":"972010bf-2f26-4518-836c-2251fbf899fe","threeCategoryName":"货架/展示架,办公桌,屏风,户外家具,办公椅,办公前台/收银台,卧室套房,儿童沙发,密集架,校园教学家具,电视柜,儿童书桌,床垫,床头柜,会议台/桌,书柜,床,客厅套房,屏���工位,儿童衣柜,壁炉,鞋柜,服装店家具,火锅桌,折叠床,凳子,餐桌,餐边柜,书架,窗,班台/班桌,椅子,层架/置物架,桑拿/足浴/健身家具,酒店后厨家具,酒店行李柜,斗柜,酒店大堂家具,边桌/茶几,鞋架,儿童床垫,酒店套房家具,办公沙发,电竞椅,酒柜,梳妆台/凳,定制衣柜,文件柜/办公柜,儿童椅凳,餐饮沙发/卡座,儿童餐椅,儿童桌椅套装,餐厅套房,储物/收纳用品,电脑桌,简易衣柜,书桌,衣帽架,衣柜,医疗家具,酒店桌椅,晾衣架,阅览桌,电脑椅,穿衣镜,榻榻米,儿童床,书房套房,沙发床,沙发","bindOrgId":null,"threeCid":"1006620,1006614,1006601,1006668,1006613,1006611,1006648,1006584,1006627,1006633,1006600,1006585,1006652,1006653,1006618,1006641,1006651,1006646,1006628,1006586,1006597,1006604,1006617,1006619,1006672,1006599,1006530,1006529,1006642,1006676,1006610,1006532,1006554,1006629,1006623,1006622,1006655,1006621,1006598,1006557,1006583,1006624,1006612,1006638,1006531,1006657,1006654,1006630,1006587,1006615,1006581,1006588,1006645,1006555,1006640,1006656,1006643,1006558,1006659,1006634,1006625,1006671,1006635,1006639,1006650,1006658,1006582,1006647,1006603,1006602","erow":null},{"id":3000,"supplierId":87938,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"913302001445302461","supplierName":"广博集团股份有限公司","supplierStatus":2,"recommendOrg":"国铁物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":"办公用品,电子产品","cid":null,"createTime":1616055703000,"modifyTime":1616122822000,"yn":1,"code":"c51a00ec-4ee9-4304-b0d3-7ba3ab5cb675","threeCategoryName":"打印纸,白板,签字笔,本册/便签,其它笔类,钢笔,学生文具,考勤机,墨水,文件管理,办公文具,财会用品,笔芯,复印纸,塑封膜","bindOrgId":null,"threeCid":"1004823,1004793,1004804,1004814,1004805,1004803,1004819,1005065,1004809,1004818,1004813,1004815,1004802,1004824,1004798","erow":null},{"id":3008,"supplierId":89639,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"9133038225602433XW","supplierName":"人民电器集团有限公司","supplierStatus":2,"recommendOrg":"国铁物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":"工业品","cid":null,"createTime":1616055706000,"modifyTime":1616122842000,"yn":1,"code":"c1af8a99-f7bb-490a-9368-521f8cc67989","threeCategoryName":"按钮/指示灯,断路器,接触器","bindOrgId":null,"threeCid":"1005378,1005508,1005385","erow":null},{"id":3071,"supplierId":82605,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"914201057310647884","supplierName":"武汉天鸣集团有限公司","supplierStatus":2,"recommendOrg":"国铁物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":"劳保用品","cid":null,"createTime":1616055727000,"modifyTime":1616123310000,"yn":1,"code":"70211a7f-1dc3-428a-b10c-33f58f592d06","threeCategoryName":"手部防护,身体防护,足部防护,呼吸防护","bindOrgId":null,"threeCid":"1005564,1005563,1005569,1005560","erow":null},{"id":3075,"supplierId":76599,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"911101056336607540","supplierName":"北京天泽电力集团有限公司","supplierStatus":2,"recommendOrg":"国铁物资���限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":"通用工具","cid":null,"createTime":1616055727000,"modifyTime":1616123620000,"yn":1,"code":"35c81603-b083-40d4-bcb6-0383a6177baa","threeCategoryName":"手动工具,工具配件","bindOrgId":null,"threeCid":"1006521,1006518","erow":null},{"id":3139,"supplierId":64527,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"91420506722070756W","supplierName":"萧氏茶业集团有限公司","supplierStatus":2,"recommendOrg":"国铁物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":"粮油食品","cid":null,"createTime":1616055737000,"modifyTime":1616123388000,"yn":1,"code":"61a11f6c-7bd9-438d-b074-4825b705731b","threeCategoryName":"绿茶,红茶","bindOrgId":null,"threeCid":"1005621,1005617","erow":null},{"id":3145,"supplierId":69900,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"","supplierName":"上海新一名实业（集团）有限公司","supplierStatus":2,"recommendOrg":"国铁物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":"劳保用品","cid":null,"createTime":1616055738000,"modifyTime":1616123097000,"yn":1,"code":"94086970-b466-45ff-84a8-8b1f46bbc2e5","threeCategoryName":"身体防护,足部防护,手部防护,劳防用品,静电无尘,呼吸防护","bindOrgId":null,"threeCid":"1005563,1005569,1005564,1005562,1005561,1005560","erow":null},{"id":3168,"supplierId":101035,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"913501002605728734","supplierName":"福建春伦集团有限公司","supplierStatus":2,"recommendOrg":"国铁物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":"粮油食品","cid":null,"createTime":1616055745000,"modifyTime":1616123166000,"yn":1,"code":"894532af-98a9-45d9-8b0c-d074934c4c48","threeCategoryName":"养生茶,龙井,红茶,花果茶,白茶,铁观音,花草茶,茉莉花茶,乌龙茶,普洱,绿茶,其它茶,黑茶","bindOrgId":null,"threeCid":"1005627,1005620,1005617,1005619,1005615,1005625,1005618,1005622,1005626,1005623,1005621,1005624,1005616","erow":null},{"id":3169,"supplierId":95177,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"91442000770978582P","supplierName":"迪欧家具集团有限公司","supplierStatus":2,"recommendOrg":"国铁物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":null,"cid":null,"createTime":1616055746000,"modifyTime":1616123177000,"yn":1,"code":"88a58505-9c36-48fe-8a93-c528afd627a4","threeCategoryName":null,"bindOrgId":null,"threeCid":null,"erow":null},{"id":3191,"supplierId":99121,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"91110101664649224L","supplierName":"领先未来科技集团有限公司","supplierStatus":2,"recommendOrg":"国铁物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":"办公用品","cid":null,"createTime":1616055752000,"modifyTime":1616123229000,"yn":1,"code":"7e817088-49c9-4a63-9c43-d7c99ee34587","threeCategoryName":"画具画材,复印纸,办公文具,墨粉,文件管理,硒鼓,学生文具,保险柜/箱,本册/便签,财会用品,签字笔","bindOrgId":null,"threeCid":"1004816,1004824,1004813,1004807,1004818,1004811,1004819,1004794,1004814,1004815,1004804","erow":null},{"id":3201,"supplierId":61628,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"911101015530708778","supplierName":"诚和致远商业集团有限公司","supplierStatus":2,"recommendOrg":"国铁物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":"办公用品","cid":null,"createTime":1616055797000,"modifyTime":1616123712000,"yn":1,"code":"2195700e-7e52-4755-925c-92797ce96c01","threeCategoryName":"文件管理,签字笔,墨粉,本册/便签,考勤架,画具画材,墨盒,硒鼓,办公文具,复印纸,财会用品","bindOrgId":null,"threeCid":"1004818,1004804,1004807,1004814,1004796,1004816,1004808,1004811,1004813,1004824,1004815","erow":null},{"id":3202,"supplierId":79498,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"91140100558728391D","supplierName":"太原晋太实业（集团）有限公司新星月环能科技分公司","supplierStatus":2,"recommendOrg":"国铁物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":null,"cid":null,"createTime":1616055798000,"modifyTime":1616123722000,"yn":1,"code":"2188ddad-db4f-4b34-9744-29a77e1da562","threeCategoryName":null,"bindOrgId":null,"threeCid":null,"erow":null},{"id":3221,"supplierId":87746,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"914415006176845969","supplierName":"广东大哥大集团有限公司","supplierStatus":2,"recommendOrg":"国铁物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":"日用百货","cid":null,"createTime":1616055837000,"modifyTime":1616123453000,"yn":1,"code":"5822e177-9de0-4019-a067-037281f01eee","threeCategoryName":"旅行包,拉杆箱,腰包/胸包","bindOrgId":null,"threeCid":"1005831,1005830,1005835","erow":null},{"id":3222,"supplierId":98031,"batchNum":null,"recommendOrgId":112221,"platformId":20,"requestId":null,"unSocialCrCode":"91450200737644011X","supplierName":"广西志光家具集团有限责任公司","supplierStatus":2,"recommendOrg":"中国铁路南宁局集团公司","bindOrgName":"钦州车务段","accountName":"黄娜","accountOrgName":"钦州车务段营销中心","accountPhone":"0777-5122492 ","accountMobile":"18376887635","accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":"装修建材","cid":null,"createTime":1616055837000,"modifyTime":1616123453000,"yn":1,"code":"57f9c1d4-b8ee-4f13-87c7-5ed6bd5da0a2","threeCategoryName":"办公桌,办公椅","bindOrgId":null,"threeCid":"1006614,1006613","erow":null},{"id":3230,"supplierId":78913,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"91320000608950987L","supplierName":"苏宁易购集团股份有限公司","supplierStatus":2,"recommendOrg":"国���物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"梁先生","accountOrgName":"物资公司","accountPhone":"010-51896005","accountMobile":null,"accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":"办公用品","cid":null,"createTime":1616055839000,"modifyTime":1616123479000,"yn":1,"code":"53020247-742e-42b5-9cf3-795232b8f3e1","threeCategoryName":"签字笔,其它笔类,办公文具,钢笔,本册/便签","bindOrgId":null,"threeCid":"1004804,1004805,1004813,1004803,1004814","erow":null},{"id":3304,"supplierId":60555,"batchNum":null,"recommendOrgId":112229,"platformId":20,"requestId":null,"unSocialCrCode":"91140108MA0GXKD41T","supplierName":"山西铁路装备制造集团朗炫照明有限公司","supplierStatus":2,"recommendOrg":"国铁物资有限公司","bindOrgName":"国铁物资有限公司","accountName":"石峰","accountOrgName":"生产质量部","accountPhone":"0351-2642928","accountMobile":"13934532343","accountManagerOrg":null,"accountManagerOrgPhone":null,"accountManagerOrgMobile":null,"categoryName":"工业品,装修建材","cid":null,"createTime":1616120461000,"modifyTime":1616122567000,"yn":1,"code":"f43daa1b-3e15-4955-92bf-4115f172b3fd","threeCategoryName":"照明,吸顶灯,筒灯/射灯,LED灯源","bindOrgId":null,"threeCid":"1005390,1006569,1006568,1006560","erow":null}]}*/
/*{"code":"0","msg":"","data":[{"id":1000088881,"userName":"cezdbj001","accountName":"cezdbj001","realName":"长度"},{"id":1000089288,"userName":"beijing001","accountName":"beijing001","realName":"北京"},{"id":1000089347,"userName":"张家口工务段审核账号","accountName":"testBjZd001","realName":"站段"},{"id":1000088882,"userName":"cecjbj001","accountName":"cecjbj001","realName":null}]}
 */
    /**
     * 供应商名称列表
     */
    @Override
    public void getSupplierNameList(String supplierName) {
        model.getSupplierNames(supplierName, accountId, "2", organizeName, organizationId, new HttpRxObserver<ArrayList<BuyerBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ArrayList<BuyerBean> response) {
                baseView.loadConditionNameList(response);
            }
        });
    }
    /**
     * 采购单列表数据
     */

    @Override
    public void getOrder(boolean isDialog, int page, int queryType, String squence, String content, OrderFilterBean filterBean) {
        if (isDialog) {
            baseView.showResDialog(R.string.loading);
        }
        model.getPurchasePageList(20, accountId, queryType, 2, squence, content, page,filterBean, new HttpRxObserver<ListBeen<OrderInfoBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
                baseView.dismissDialog();
            }

            @Override
            protected void onSuccess(ListBeen<OrderInfoBean> response) {
                boolean firstPage = response.isFirstPage();
                int totalCount = response.getTotalCount();
//                int totalPageCount = response.getTotalPageCount();
                baseView.dismissDialog();
                ArrayList<OrderInfoBean> list = response.getList();
//                boolean isClear = page <= 1;
                baseView.getOrder(list, firstPage,totalCount);
            }
        });
    }
}

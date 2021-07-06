package com.rails.lib_data.contract;

import android.app.Activity;

import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.AuthorBean;
import com.rails.lib_data.bean.AuthorButtonBean;
import com.rails.lib_data.bean.AuthorMenuBean;
import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.lib_data.model.UserToolModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;

import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.CONNECT_ERROR;

/**
 * 用户相关工具
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/20
 */
public class UserToolPresenterImpl extends BasePresenter<UserToolContract.UserToolView> implements UserToolContract.UserToolPresenter {

    UserToolModel model;

    public UserToolPresenterImpl(Activity mContext, UserToolContract.UserToolView userToolView) {
        super(mContext, userToolView);
        model = new UserToolModel();
    }

    @Override
    public void getUserStatictics() {
        model.getUserStatictics(new HttpRxObserver<UserStatisticsBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(UserStatisticsBean bean) {
                if (isCallBack()) {
                    baseView.getUserStatictics(bean);
                }
            }
        });
    }

    @Override
    public void getUserInfoStatictics() {
        model.getUserInfoStatictics(new HttpRxObserver<UserStatisticsBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(UserStatisticsBean bean) {
                if (isCallBack()) {
                    baseView.getUserInfoStatictics(bean);
                }
            }
        });
    }

    @Override
    public void checkPermissions() {
        model.checkPermissions(new HttpRxObserver<UserStatisticsBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(UserStatisticsBean bean) {
                if (isCallBack()) {
                    baseView.checkPermissions(bean);
                }
            }
        });
    }

    @Override
    public void queryAuthor() {
        model.getQueryResource(new HttpRxObserver<AuthorBean>() {
            @Override
            protected void onError(ErrorBean e) {
//                String code = e.getCode();
//                if ("500".equals(code))
                baseView.onError(e);
//                else {
                e.setCode(CONNECT_ERROR);
//                    baseView.onError(e);
//                }

            }

            @Override
            protected void onSuccess(AuthorBean authorBean) {
                if (isCallBack()) {
                    if (authorBean != null) {
                        //菜单
                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.MENU_PURCHAR, false);
                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.MENU_APPROVE, false);
                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.MENU_COLLECT, false);
                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.MENU_TRACK, false);
                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.MENU_ADDRESS, false);

                        //按钮
                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.BUTTON_EVA_COMMIT, false);
                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.BUTTON_EVA_SEEK, false);
                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.BUTTON_RECEIVE, false);
                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.BUTTON_APPROVE, false);
                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.BUTTON_DETAIL, false);
                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.BUTTON_ADDRESS_ADD, false);
                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.BUTTON_ADDRESS_EDIT, false);
                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.BUTTON_ADDRESS_DEL, false);


                        for (AuthorMenuBean authorMenuBean : authorBean.getMenuBeans()) {
                            //2000055 采购交易{2000056:采购单 ； 2000057：审批单}
                            //2000100 关注中心{2000101:商品收藏;2000102 :浏览历史}
                            if ("2000055".equals(authorMenuBean.getCode())) {
                                for (AuthorMenuBean.SubMenusBean subMenusBean : authorMenuBean.getSubMenus()) {
                                    if ("2000056".equals(subMenusBean.getCode())) {
                                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.MENU_PURCHAR, true);
                                    }

                                    if ("2000057".equals(subMenusBean.getCode())) {
//                                    isApprove = true;
                                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.MENU_APPROVE, true);
                                    }
                                }
                                continue;
                            }

                            if ("2000100".equals(authorMenuBean.getCode())) {
                                for (AuthorMenuBean.SubMenusBean subMenusBean : authorMenuBean.getSubMenus()) {
                                    if ("2000101".equals(subMenusBean.getCode())) {
                                        //商品收藏
//                                    isCollect = true;
                                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.MENU_COLLECT, true);
                                    }

                                    if ("2000102".equals(subMenusBean.getCode())) {
                                        //浏览历史
//                                    isTrack = true;
                                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.MENU_TRACK, true);
                                    }
                                }
                                continue;
                            }

                            if ("2000000".equals(authorMenuBean.getCode())) {
                                for (AuthorMenuBean.SubMenusBean subMenusBean : authorMenuBean.getSubMenus()) {
                                    if ("2000050".equals(subMenusBean.getCode())) {
                                        //地址管理
                                        PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.MENU_ADDRESS, true);
                                    }
                                }
                            }
                        }

                        //提交评价：order:submit-evaluation
                        //查看评价：order:view-evaluation
                        //确认收货：order-detail:confirm-the-goods
                        //查看详情：order:detail
                        //审批：order:audit
                        for (AuthorButtonBean bean : authorBean.getButtonBeans()) {
                            if ("order:submit-evaluation".equals(bean.getHtmlCode())) {
                                PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.BUTTON_EVA_COMMIT, true);
                                continue;
                            }

                            if ("order:view-evaluation".equals(bean.getHtmlCode())) {
                                PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.BUTTON_EVA_SEEK, true);
                                continue;
                            }

                            if ("order-detail:confirm-the-goods".equals(bean.getHtmlCode())) {
                                PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.BUTTON_RECEIVE, true);
                                continue;
                            }

                            if ("order:audit".equals(bean.getHtmlCode())) {
                                PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.BUTTON_APPROVE, true);
                                continue;
                            }

                            if ("order:detail".equals(bean.getHtmlCode())) {
                                PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.BUTTON_DETAIL, true);
                                continue;
                            }

                            //创建地址
                            if ("list:add".equals(bean.getHtmlCode())) {
                                PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.BUTTON_ADDRESS_ADD, true);
                                continue;
                            }
                            //编辑地址
                            if ("list:edit".equals(bean.getHtmlCode())) {
                                PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.BUTTON_ADDRESS_EDIT, true);
                                continue;
                            }
                            //删除地址
                            if ("list:del".equals(bean.getHtmlCode())) {
                                PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.BUTTON_ADDRESS_DEL, true);
                            }
                        }

                        baseView.getAuthor(authorBean);
                    }

                }
            }

        });
    }
}

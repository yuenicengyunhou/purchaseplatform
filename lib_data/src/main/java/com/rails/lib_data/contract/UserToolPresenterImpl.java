package com.rails.lib_data.contract;

import android.app.Activity;
import android.view.MenuItem;

import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.AuthorButtonBean;
import com.rails.lib_data.bean.AuthorMenuBean;
import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.lib_data.model.UserToolModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;

import java.util.ArrayList;

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
    public void getUserStatictics(String userId, String userType) {
        model.getUserStatictics(userId, userType, new HttpRxObserver<UserStatisticsBean>() {
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
    public void getUserInfoStatictics(String userId, String userType) {
        model.getUserInfoStatictics(userId, userType, new HttpRxObserver<UserStatisticsBean>() {
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
    public void checkPermissions(String userId, String userType) {
        model.checkPermissions(userId, userType, new HttpRxObserver<UserStatisticsBean>() {
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
    public void queryResource(String userId, String userType) {
        model.queryResource(userId, userType, new HttpRxObserver<ArrayList<AuthorMenuBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ArrayList<AuthorMenuBean> beans) {
                if (isCallBack()) {
                    boolean isPurchase = false;
                    boolean isApprove = false;
                    boolean isCollect = false;
                    boolean isTrack = false;
                    for (AuthorMenuBean authorMenuBean : beans) {
                        //2000055 采购交易{2000056:采购单 ； 2000057：审批单}
                        //2000100 关注中心{2000101:商品收藏;2000102 :浏览历史}
                        if ("2000055".equals(authorMenuBean.getCode())) {
                            for (AuthorMenuBean.SubMenusBean subMenusBean : authorMenuBean.getSubMenus()) {
                                if ("2000056".equals(subMenusBean.getCode())) {
                                    isPurchase = true;
                                }

                                if ("2000057".equals(subMenusBean.getCode())) {
                                    isApprove = true;
                                }
                            }
                            continue;
                        }

                        if ("2000100".equals(authorMenuBean.getCode())) {
                            for (AuthorMenuBean.SubMenusBean subMenusBean : authorMenuBean.getSubMenus()) {
                                if ("2000101".equals(subMenusBean.getCode())) {
                                    //商品收藏
                                    isCollect = true;
                                }

                                if ("2000102".equals(subMenusBean.getCode())) {
                                    //浏览历史
                                    isTrack = true;
                                }
                            }
                        }
                    }
                    baseView.getAuthor(isPurchase, isApprove, isCollect, isTrack);
                }
            }
        });
    }

    @Override
    public void queryResourceButton(String userId, String userType) {
        model.queryResourceButton(userId, userType, new HttpRxObserver<ArrayList<AuthorButtonBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ArrayList<AuthorButtonBean> beans) {
                if (isCallBack()) {
                    //提交评价：order:submit-evaluation
                    //查看评价：order:view-evaluation
                    //确认收货：order-detail:confirm-the-goods
                    //查看详情：order:detail
                    for (AuthorButtonBean bean : beans) {
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

                        if ("order:detail".equals(bean.getHtmlCode())) {
                            PrefrenceUtil.getInstance(mContext).setBoolean(ConShare.BUTTON_DETAIL, true);
                        }
                    }
                }
            }
        });
    }
}

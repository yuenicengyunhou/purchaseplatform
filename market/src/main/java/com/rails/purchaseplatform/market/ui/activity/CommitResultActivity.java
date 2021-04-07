package com.rails.purchaseplatform.market.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.ResultWebBean;
import com.rails.lib_data.contract.ProductContract;
import com.rails.lib_data.contract.ProductPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.ProductHotAdapter;
import com.rails.purchaseplatform.market.databinding.ActivityMarketResultBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 订单相关的结果页面
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/28
 */
@Route(path = ConRoute.MARKET.COMMIT_RESULT)
public class CommitResultActivity extends ToolbarActivity<ActivityMarketResultBinding> implements ProductContract.ProductView {

    final int DEF_PAGE = 1;
    int page = DEF_PAGE;
    ProductHotAdapter recAdapter;
    private GridLayoutManager hotManager;
    private ResultWebBean webBean;

    ProductContract.ProductPresenter productPresenter;


    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        webBean = extras.getParcelable("bean");
    }

    @Override
    protected void initialize(Bundle bundle) {

        binding.titleBar.setImgLeftRes(R.drawable.svg_close);

        recAdapter = new ProductHotAdapter(this, 0);
        hotManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        barBinding.recRecycler.setLayoutManager(hotManager);
        barBinding.recRecycler.setAdapter(recAdapter);


        productPresenter = new ProductPresenterImpl(this, this);
        onRefresh();
    }


    /**
     * 刷新效果
     */
    private void onRefresh() {
        barBinding.smart.setEnableLoadMore(false);
        barBinding.smart.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                barBinding.smart.finishRefresh();
                page = DEF_PAGE;
                notifyData(false, page);
            }
        });

        notifyData(false, page);
    }


    /**
     * 请求推荐商品列表
     *
     * @param isDialog
     * @param page
     */
    private void notifyData(boolean isDialog, int page) {
        productPresenter.getHotProducts(isDialog, page);
    }

    @Override
    protected int getColor() {
        return android.R.color.white;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void getHotProducts(ArrayList<ProductBean> productBeans, boolean hasMore, boolean isClear) {
        recAdapter.update(productBeans, true);
    }


    @Override
    protected void onClick() {
        super.onClick();
        barBinding.btnAppraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(webBean == null ? ConRoute.WEB.WEB_EVALUTE : webBean.getUrlright())
                        .withString("url", ConRoute.WEB_URL.EVALUTE)
                        .navigation();
            }
        });


        barBinding.btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(ConRoute.WEB.WEB_ORDER)
                        .withString("url", webBean == null ? ConRoute.WEB_URL.ORDER_DETAIL : webBean.getUrlleft())
                        .navigation();
            }
        });
    }


    private void setStyle(ResultWebBean bean) {
        if (bean == null)
            return;
        barBinding.btnList.setText(bean.getBtnleft());
        barBinding.btnAppraise.setText(bean.getBtnright());
    }
}

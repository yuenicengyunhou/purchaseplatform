package com.rails.purchaseplatform.market.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.BannerBean;
import com.rails.lib_data.bean.BrandBean;
import com.rails.lib_data.bean.CategorySubBean;
import com.rails.lib_data.bean.MarketIndexBean;
import com.rails.lib_data.bean.ProductBean;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.bean.ResultWebBean;
import com.rails.lib_data.contract.MarKetIndexPresenterImpl;
import com.rails.lib_data.contract.MarketIndexContract;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.widget.SpaceGirdWeightDecoration;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
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
public class CommitResultActivity extends ToolbarActivity<ActivityMarketResultBinding> implements MarketIndexContract.MarketIndexView {

    final int DEF_PAGE = 1;
    int page = DEF_PAGE;
    ProductHotAdapter recAdapter;
    private GridLayoutManager hotManager;
    private ResultWebBean webBean;

    MarKetIndexPresenterImpl productPresenter;


    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        webBean = extras.getParcelable("bean");
    }

    @Override
    protected void initialize(Bundle bundle) {

        binding.titleBar.setImgLeftRes(R.drawable.svg_close);
        setStyle(webBean);

        recAdapter = new ProductHotAdapter(this, 0);
        hotManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        barBinding.recRecycler.addItemDecoration(new SpaceGirdWeightDecoration(this, 8, 5, 0, 8, R.color.white));
        barBinding.recRecycler.setLayoutManager(hotManager);
        barBinding.recRecycler.setAdapter(recAdapter);
        recAdapter.setListener(new PositionListener<ProductBean>() {
            @Override
            public void onPosition(ProductBean bean, int position) {
                if (TextUtils.isEmpty(bean.getItemId())) {
                    ToastUtil.showCenter(CommitResultActivity.this, "商品不存在或已下架");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("itemId", bean.getItemId());
                ARouter.getInstance().build(ConRoute.MARKET.PRODUCT_DETAIL).with(bundle).navigation();
            }
        });


        productPresenter = new MarKetIndexPresenterImpl(this, this);
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
        productPresenter.getHotProducts(false, 1, "10");
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
    protected void onClick() {
        super.onClick();
        barBinding.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConRoute.WEB.WEB_APPROVAL.equals(webBean.getUrlleft())) {
                    ARouter.getInstance()
                            .build(webBean.getUrlleft())
                            .withString("url", ConRoute.WEB_URL.APPROVAL)
                            .navigation();
                } else
                    ARouter.getInstance()
                            .build(webBean.getUrlleft())
                            .withParcelable("webBean", webBean)
                            .navigation();

                finish();
            }
        });


        barBinding.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rightParams = webBean.getRightParams();
                if (webBean.getType() == 2) {
                    ARouter.getInstance()
                            .build(webBean.getUrlright())
                            .withString("id", webBean.getOrderNo())
                            .navigation();
                } else {
                    if (webBean.getCode() == 3)
                        finish();
                    else {
                        ARouter.getInstance()
                                .build(webBean.getUrlright())
                                .withParcelable("webBean", webBean)
                                .navigation();
                        finish();
                    }

                }

            }
        });
    }


    private void setStyle(ResultWebBean bean) {
        if (bean == null)
            return;
        barBinding.tvMsg.setText(bean.getMsg());
        barBinding.btnLeft.setText(bean.getBtnleft());
        barBinding.btnRight.setText(bean.getBtnright());
    }

    @Override
    public void getHotProducts(ArrayList<ProductBean> beans) {
        if (beans == null)
            return;
        if (beans.isEmpty())
            return;
        recAdapter.update(beans, true);
    }


    @Override
    public void getIndexInfo(MarketIndexBean bean) {

    }

    @Override
    public void getBrands(ArrayList<BrandBean> brandBeans, boolean hasMore, boolean isClear) {

    }

    @Override
    public void getFloorProducts(ArrayList<ProductBean> productBeans, boolean hasMore, boolean isClear) {

    }

    @Override
    public void getFloors(ArrayList<ProductRecBean> productBeans) {

    }
}

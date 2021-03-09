package com.rails.purchaseplatform.market.ui.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.rails.lib_data.bean.ProductRecBean;
import com.rails.lib_data.contract.ProductContract;
import com.rails.lib_data.contract.ProductPresenterImpl;
import com.rails.purchaseplatform.common.adapter.ViewPageAdapter;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.ProductRecAdapter;
import com.rails.purchaseplatform.market.databinding.FrmMallBinding;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * 商城首页
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/28
 */
public class MallFrm extends LazyFragment<FrmMallBinding> implements ProductContract.ProductView {


    private ProductContract.ProductPresenter presenter;
    private ProductRecAdapter recAdapter;


    @Override
    protected void loadData() {


        binding.bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (i > 0) {
                    binding.rlRecycler.setEnabled(true);
                } else {
                    binding.rlRecycler.setEnabled(false);

                }

                boolean isVisit = getLocalVisibleRect(getActivity(), binding.rvStep);
                if (Math.abs(i) > 500 && !isVisit) {
                    StatusBarUtil.StatusBarLightMode(getActivity());
                } else {
                    StatusBarUtil.StatusBarMode(getActivity(), R.color.bg_blue);
                }
            }
        });

        recAdapter = new ProductRecAdapter(getActivity());
        presenter = new ProductPresenterImpl(getActivity(), this);
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.recycler.addItemDecoration(new SpaceDecoration(getActivity(), 20, R.color.bg));
        binding.recycler.setAdapter(recAdapter);

        onRefresh();
    }


    /**
     * 数据刷新操作
     */
    void onRefresh() {
        binding.rlRecycler.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新数据
                binding.rlRecycler.setRefreshing(false);
                presenter.getRectProducts(false);
            }
        });
        presenter.getRectProducts(true);
    }

    @Override
    protected void loadPreVisitData() {
        StatusBarUtil.StatusBarMode(getActivity(), R.color.bg_blue);
    }


    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    @Override
    public void onError(ErrorBean errorBean) {

    }


    public boolean getLocalVisibleRect(Context context, View view) {
        int screenWidth = ScreenSizeUtil.getScreenWidth(context);
        int screenHeight = ScreenSizeUtil.getScreenHeight(context);
        Rect rect = new Rect(0, 0, screenWidth, screenHeight);
        int[] location = new int[2];
        view.getLocationInWindow(location);
        view.setTag(location[1]);//存储y方向的位置
        if (view.getLocalVisibleRect(rect)) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void getRecProducts(ArrayList<ProductRecBean> beans) {
        recAdapter.update(beans, true);
    }
}

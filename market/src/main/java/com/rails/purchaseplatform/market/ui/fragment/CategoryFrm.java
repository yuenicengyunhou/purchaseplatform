package com.rails.purchaseplatform.market.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.Logger;
import com.rails.lib_data.bean.CategoryBean;
import com.rails.lib_data.bean.CategoryRootBean;
import com.rails.lib_data.bean.CategorySubBean;
import com.rails.lib_data.contract.CategoryContract;
import com.rails.lib_data.contract.CategoryPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.CategoryAdapter;
import com.rails.purchaseplatform.market.adapter.CategoryRootAdapter;
import com.rails.purchaseplatform.market.adapter.CategoryTabAdapter;
import com.rails.purchaseplatform.market.databinding.FrmCategoryBinding;
import com.rails.purchaseplatform.market.widget.CenterManger;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 分类列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/26
 */
public class CategoryFrm extends LazyFragment<FrmCategoryBinding> implements PositionListener<CategoryRootBean>
        , CategoryContract.CategoryView {

    CategoryRootAdapter adapter;
    CenterManger centerManger;
    private CategoryContract.CategoryPresenter presenter;

    //判断是点击还是滑动
    private boolean isScroll = true;

    CenterManger tabManger;
    private CategoryTabAdapter tabAdapter;

    private CategoryAdapter subAdapter;


    public CategoryFrm(){

    }

    @Override
    protected void loadData() {
        adapter = new CategoryRootAdapter(getActivity());
        adapter.setListener(this);
        centerManger = new CenterManger(getActivity(), RecyclerView.VERTICAL, false);
        binding.recycler.setLayoutManager(centerManger);
        binding.recycler.setAdapter(adapter);


        //分类标签
        tabAdapter = new CategoryTabAdapter(getActivity());
        tabManger = new CenterManger(getActivity(), RecyclerView.HORIZONTAL, false);
        binding.magic.setLayoutManager(tabManger);
        binding.magic.setAdapter(tabAdapter);
        tabAdapter.setListener(new PositionListener<CategoryBean>() {

            @Override
            public void onPosition(CategoryBean bean, int position) {
                tabManger.smoothScrollToPosition(binding.magic, new RecyclerView.State(), position);
                isScroll = false;
                ((LinearLayoutManager) binding.subRecycler.getLayoutManager()).scrollToPositionWithOffset(position, 0);
            }
        });


        subAdapter = new CategoryAdapter(getActivity());
        binding.subRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.subRecycler.addItemDecoration(new SpaceDecoration(getActivity(), 10, R.color.white));
        subAdapter.setListener(new PositionListener<CategorySubBean>() {
            @Override
            public void onPosition(CategorySubBean bean, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("cid", bean.getFcid());
                bundle.putString("mode", "from_category");
                ARouter.getInstance()
                        .build(ConRoute.MARKET.SEARCH_RESULT)
                        .with(bundle)
                        .navigation();
            }

        });
        binding.subRecycler.setAdapter(subAdapter);


        presenter = new CategoryPresenterImpl(getActivity(), this);
        onRefresh();
    }


    /**
     * 数据刷新操作
     */
    void onRefresh() {
        binding.smart.setEnableLoadMore(false);
        binding.smart.setEnableRefresh(false);
        binding.smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                binding.smart.finishRefresh();
                presenter.getCategorys(false, false);
            }
        });
        presenter.getCategorys(true, false);
    }

    @Override
    protected void loadPreVisitData() {
//        StatusBarUtil.StatusBarLightMode(getActivity());
        try {
            StatusBarUtil.StatusBarLightMode(getActivity());
        }catch (Exception e){

        }

    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }


    @Override
    public void getCategorys(ArrayList<CategoryRootBean> beans) {
        setFragments(beans);
    }


    private void setFragments(ArrayList<CategoryRootBean> tabs) {
        ArrayList<CategoryRootBean> beans = new ArrayList<>();
        for (CategoryRootBean bean : tabs) {
            beans.add(bean);
        }
        adapter.update(beans, true);
        CategoryRootBean bean = beans.get(0);
        if (bean != null) {
            initPager((ArrayList<CategoryBean>) bean.getSecondPlatformCategoryList());
            subAdapter.update((ArrayList<CategoryBean>) bean.getSecondPlatformCategoryList(), true);
        }
    }

    @Override
    public void onPosition(CategoryRootBean bean, int position) {
        centerManger.smoothScrollToPosition(binding.recycler, new RecyclerView.State(), position);
        if (bean != null) {
            initPager((ArrayList<CategoryBean>) bean.getSecondPlatformCategoryList());
            subAdapter.update((ArrayList<CategoryBean>) bean.getSecondPlatformCategoryList(), true);
        }
    }


    @Override
    protected void onClick() {
        super.onClick();
        binding.etSearch.setOnClickListener(v -> ARouter.getInstance().build(ConRoute.COMMON.SEARCH).navigation());

        binding.imgMsg.setOnClickListener(v -> ARouter.getInstance().build(ConRoute.WEB.WEB_MSG).withString("url", ConRoute.WEB_URL.MSG).navigation());
    }


    @Override
    public void onError(ErrorBean errorBean) {
    }


    /**
     * 初始化pageradapter
     */
    private void initPager(ArrayList<CategoryBean> tabs) {
        tabAdapter.updateData(tabs, true);
        binding.subRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScroll = true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isScroll) {
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int position = manager.findFirstVisibleItemPosition();
                    tabAdapter.setLastBean(position);
                    binding.magic.smoothScrollToPosition(position);
                }
            }
        });
//        binding.magic.scrollBy(0,0);
        binding.magic.scrollToPosition(0);
        binding.subRecycler.scrollToPosition(0);
    }

}

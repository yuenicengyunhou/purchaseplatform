package com.rails.purchaseplatform.market.ui.fragment;

import com.rails.lib_data.bean.CategoryRootBean;
import com.rails.lib_data.contract.CategoryContract;
import com.rails.lib_data.contract.CategoryPresenterImpl;
import com.rails.purchaseplatform.common.adapter.ViewPageAdapter;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.PageTransformer;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.CategoryRootAdapter;
import com.rails.purchaseplatform.market.databinding.FragmentCategoryBinding;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 分类列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/26
 */
public class CategoryFragment extends LazyFragment<FragmentCategoryBinding> implements PositionListener<CategoryRootBean>
        , CategoryContract.CategoryView {

    CategoryRootAdapter adapter;
    ViewPageAdapter viewPageAdapter;
    private CategoryContract.CategoryPresenter presenter;

    @Override
    protected void loadData() {
        adapter = new CategoryRootAdapter(getActivity());
        adapter.setListener(this);
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.recycler.setAdapter(adapter);

        viewPageAdapter = new ViewPageAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.pager.setAdapter(viewPageAdapter);


        presenter = new CategoryPresenterImpl(getActivity(), this);
        presenter.getCategorys(false);
    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    @Override
    public void getCategorys(ArrayList<CategoryRootBean> beans) {
        setFragments(beans);
    }


    private void setFragments(ArrayList<CategoryRootBean> tabs) {
        ArrayList<CategoryRootBean> beans = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();

        for (CategoryRootBean bean : tabs) {
            beans.add(bean);
            fragments.add(CategorySubFragment.newInstance(bean));
        }
        adapter.update(beans, true);
        viewPageAdapter.update(fragments, true);
        binding.pager.setOffscreenPageLimit(tabs.size());
    }

    @Override
    public void onPosition(CategoryRootBean bean, int position) {
        binding.pager.setCurrentItem(position);
    }

}

package com.rails.purchaseplatform.market.ui.fragment;

import com.rails.lib_data.bean.CategoryBean;
import com.rails.purchaseplatform.common.adapter.ViewPageAdapter;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.CategoryAdapter;
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
public class CategoryFragment extends LazyFragment<FragmentCategoryBinding> implements PositionListener<CategoryBean> {

    CategoryAdapter adapter;
    ViewPageAdapter viewPageAdapter;

    @Override
    protected void loadData() {
        adapter = new CategoryAdapter(getActivity());
        adapter.setListener(this);
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        binding.recycler.setAdapter(adapter);

        viewPageAdapter = new ViewPageAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.pager.setAdapter(viewPageAdapter);

        String[] arrays = getResources().getStringArray(R.array.market_categorys);
        setFragments(arrays);
    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    private void setFragments(String[] tabs) {
        ArrayList<CategoryBean> beans = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();
        CategoryBean bean;

        for (String name : tabs) {
            bean = new CategoryBean();
            bean.name.set(name);
            beans.add(bean);
            fragments.add(CategorySubFragment.newInstance(name));
        }
        adapter.update(beans, true);
        viewPageAdapter.update(fragments, true);
    }

    @Override
    public void onPosition(CategoryBean bean, int position) {
        binding.pager.setCurrentItem(position);
    }
}

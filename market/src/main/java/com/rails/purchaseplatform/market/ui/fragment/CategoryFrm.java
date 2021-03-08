package com.rails.purchaseplatform.market.ui.fragment;

import com.rails.lib_data.bean.CategoryRootBean;
import com.rails.lib_data.contract.CategoryContract;
import com.rails.lib_data.contract.CategoryPresenterImpl;
import com.rails.purchaseplatform.common.adapter.ViewPageAdapter;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.bean.BusEvent;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.adapter.CategoryRootAdapter;
import com.rails.purchaseplatform.market.databinding.FrmCategoryBinding;
import com.rails.purchaseplatform.market.widget.CenterManger;

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
public class CategoryFrm extends LazyFragment<FrmCategoryBinding> implements PositionListener<CategoryRootBean>
        , CategoryContract.CategoryView {

    CategoryRootAdapter adapter;
    ViewPageAdapter viewPageAdapter;
    CenterManger centerManger;
    private CategoryContract.CategoryPresenter presenter;
    private int curPosition = 0;

    @Override
    protected void loadData() {
        adapter = new CategoryRootAdapter(getActivity());
        adapter.setListener(this);
        centerManger = new CenterManger(getActivity(), RecyclerView.VERTICAL, false);
        binding.recycler.setLayoutManager(centerManger);
        binding.recycler.setAdapter(adapter);

        viewPageAdapter = new ViewPageAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.pager.setAdapter(viewPageAdapter);


        presenter = new CategoryPresenterImpl(getActivity(), this);
        presenter.getCategorys(false);
    }

    @Override
    protected void loadPreVisitData() {
        StatusBarUtil.StatusBarMode(getActivity(), R.color.white);
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }


    @Override
    public void onMessageEvent(BusEvent event) {
        super.onMessageEvent(event);
        String code = event.getEventCode();
        String mathord = (String) event.getBean();
        if ("CategoryFragment".equals(code)) {
            if ("onMore".equals(mathord)) {
                doNext();
            } else
                doPre();
        }

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
        curPosition = position;
        centerManger.smoothScrollToPosition(binding.recycler,new RecyclerView.State(),position);
    }


    /**
     * 下一步
     */
    private void doNext() {
        if (curPosition < adapter.getCount() - 1) {
            curPosition++;
            binding.pager.setCurrentItem(curPosition);
            adapter.setLastBean(curPosition);
        }
    }


    /**
     * 上一步
     */
    private void doPre() {
        if (curPosition > 0) {
            curPosition--;
            binding.pager.setCurrentItem(curPosition);
            adapter.setLastBean(curPosition);
        }
    }


}

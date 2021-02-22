package com.rails.purchaseplatform.fragment;

import com.rails.purchaseplatform.adapter.AuctionAdapter;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.databinding.FragmentAutionBinding;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * 临时
 * @author： sk_comic@163.com
 * @date: 2021/1/28
 */
public class AutionFragment extends LazyFragment<FragmentAutionBinding> {


    private final int DEF_PAGE = 1;
    private int page = DEF_PAGE;
    AuctionAdapter auctionAdapter;

    @Override
    protected void loadData() {
        auctionAdapter = new AuctionAdapter(getActivity());
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
//        msgRecycler.addItemDecoration(new SpaceDecoration(this, ScreenSizeUtil.dp2px(this, 15), R.color.white));
        binding.recycler.setAdapter(auctionAdapter);

        ArrayList<String> msgs = new ArrayList<>();
        msgs.add("");
        msgs.add("");
        msgs.add("");
        msgs.add("");
        msgs.add("");
        msgs.add("");
        msgs.add("");
        auctionAdapter.update(msgs, true);
        onRefresh();
    }

    @Override
    protected void loadPreVisitData() {

    }


    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void onError(ErrorBean errorBean) {

    }

    @Override
    public void onFailure(String errorMsg) {

    }


    /**
     * 数据刷新操作
     */
    void onRefresh() {
        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新数据
                page = DEF_PAGE;
                notifyData(false, page);
                binding.swipe.setRefreshing(false);
            }
        });
        notifyData(true, page);
    }


    private void notifyData(boolean isDialog, int page) {

    }
}

package com.rails.purchaseplatform.notice.fragment;

import com.rails.lib_data.bean.NoticeParentBean;
import com.rails.lib_data.contract.NoticeContract;
import com.rails.lib_data.contract.NoticePresenterImpl;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.notice.adapter.AuctionAdapter;
import com.rails.purchaseplatform.notice.databinding.FragmentNoticeAutionBinding;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * 临时公告
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/28
 */
public class AutionFragment extends LazyFragment<FragmentNoticeAutionBinding> implements NoticeContract.NoticeView {

    private NoticeContract.NoticePresenter presenter;

    private final int DEF_PAGE = 1;
    private int page = DEF_PAGE;
    AuctionAdapter auctionAdapter;


    @Override
    protected void loadData() {
        auctionAdapter = new AuctionAdapter(getActivity(), 1);
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
//        msgRecycler.addItemDecoration(new SpaceDecoration(this, ScreenSizeUtil.dp2px(this, 15), R.color.white));
        binding.recycler.setAdapter(auctionAdapter);


        presenter = new NoticePresenterImpl(getActivity(), this);
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
        presenter.getNotice();
    }


    @Override
    public void getNotice(ArrayList<NoticeParentBean> bean) {
        auctionAdapter.update(bean.get(0).getSub(), true);
    }
}

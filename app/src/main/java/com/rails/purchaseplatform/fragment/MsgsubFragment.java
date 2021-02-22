package com.rails.purchaseplatform.fragment;

import com.rails.purchaseplatform.adapter.MsgAdapter;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.databinding.FragmentMsgSubBinding;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * 购物车
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/26
 */
public class MsgsubFragment extends LazyFragment<FragmentMsgSubBinding> {

    private final int DEF_PAGE = 1;
    private int page = DEF_PAGE;
    MsgAdapter msgAdapter;


    @Override
    protected void loadData() {

        msgAdapter = new MsgAdapter(getActivity());
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 2);
//        msgRecycler.addItemDecoration(new SpaceDecoration(this, ScreenSizeUtil.dp2px(this, 15), R.color.white));
        binding.recycler.setAdapter(msgAdapter);

        ArrayList<String> msgs = new ArrayList<>();
        msgs.add("");
        msgs.add("");
        msgs.add("");
        msgs.add("");
        msgs.add("");
        msgs.add("");
        msgs.add("");
        msgAdapter.update(msgs, true);
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

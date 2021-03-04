package com.rails.purchaseplatform.notice.fragment;

import com.rails.lib_data.bean.NoticeParentBean;
import com.rails.lib_data.contract.NoticeContract;
import com.rails.lib_data.contract.NoticePresenterImpl;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.notice.R;
import com.rails.purchaseplatform.notice.adapter.AuctionAdapter;
import com.rails.purchaseplatform.notice.databinding.FrmPlatformIndexBinding;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 平台首页
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/26
 */
public class PlatformFrm extends LazyFragment<FrmPlatformIndexBinding> implements NoticeContract.NoticeView {

    private AuctionAdapter zbAdapter, fzbAdapter, pmAdapter, fpmAdapter;
    private NoticeContract.NoticePresenter presenter;

    @Override
    protected void loadData() {

        int w = ScreenSizeUtil.getScreenWidth(getActivity()) / 3 - ScreenSizeUtil.dp2px(getActivity(), 40);
        //招标
        zbAdapter = new AuctionAdapter(getActivity(), 0);
        binding.zbRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.HORIZONTAL, false, 2);
//        msgRecycler.addItemDecoration(new SpaceDecoration(this, ScreenSizeUtil.dp2px(this, 15), R.color.white));
        binding.zbRecycler.setAdapter(zbAdapter, w);

        //非招标
        fzbAdapter = new AuctionAdapter(getActivity(), 0);
        binding.fzbRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.HORIZONTAL, false, 2);
//        msgRecycler.addItemDecoration(new SpaceDecoration(this, ScreenSizeUtil.dp2px(this, 15), R.color.white));
        binding.fzbRecycler.setAdapter(fzbAdapter, w);


        pmAdapter = new AuctionAdapter(getActivity(), 0);
        binding.pmRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.HORIZONTAL, false, 2);
//        msgRecycler.addItemDecoration(new SpaceDecoration(this, ScreenSizeUtil.dp2px(this, 15), R.color.white));
        binding.pmRecycler.setAdapter(pmAdapter, w);


        fpmAdapter = new AuctionAdapter(getActivity(), 0);
        binding.fpmRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.HORIZONTAL, false, 2);
//        msgRecycler.addItemDecoration(new SpaceDecoration(this, ScreenSizeUtil.dp2px(this, 15), R.color.white));
        binding.fpmRecycler.setAdapter(fpmAdapter, w);


        presenter = new NoticePresenterImpl(getActivity(), this);
        presenter.getNotice();

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
        super.onError(errorBean);
    }


    @Override
    public void getNotice(ArrayList<NoticeParentBean> bean) {
        zbAdapter.update(bean.get(0).getSub(), true);
        fzbAdapter.update(bean.get(0).getSub(), true);
        pmAdapter.update(bean.get(0).getSub(), true);
        fpmAdapter.update(bean.get(0).getSub(), true);
    }
}

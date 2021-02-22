package com.rails.purchaseplatform.fragment;

import com.rails.purchaseplatform.R;
import com.rails.purchaseplatform.adapter.AuctionAdapter;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.databinding.FrmMallBinding;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 商城首页
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/26
 */
public class MallFrm extends LazyFragment<FrmMallBinding> {


    private AuctionAdapter zbAdapter, fzbAdapter, pmAdapter, fpmAdapter;

    @Override
    protected void loadData() {

        //招标
        zbAdapter = new AuctionAdapter(getActivity());
        binding.zbRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.HORIZONTAL, false, 2);
//        msgRecycler.addItemDecoration(new SpaceDecoration(this, ScreenSizeUtil.dp2px(this, 15), R.color.white));
        binding.zbRecycler.setAdapter(zbAdapter);

        //非招标
        fzbAdapter = new AuctionAdapter(getActivity());
        binding.fzbRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.HORIZONTAL, false, 2);
//        msgRecycler.addItemDecoration(new SpaceDecoration(this, ScreenSizeUtil.dp2px(this, 15), R.color.white));
        binding.fzbRecycler.setAdapter(fzbAdapter);


        pmAdapter = new AuctionAdapter(getActivity());
        binding.pmRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.HORIZONTAL, false, 2);
//        msgRecycler.addItemDecoration(new SpaceDecoration(this, ScreenSizeUtil.dp2px(this, 15), R.color.white));
        binding.pmRecycler.setAdapter(pmAdapter);


        fpmAdapter = new AuctionAdapter(getActivity());
        binding.fpmRecycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.HORIZONTAL, false, 2);
//        msgRecycler.addItemDecoration(new SpaceDecoration(this, ScreenSizeUtil.dp2px(this, 15), R.color.white));
        binding.fpmRecycler.setAdapter(fpmAdapter);


        ArrayList<String> datas = new ArrayList<>();
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        zbAdapter.update(datas, true);
        fzbAdapter.update(datas, true);
        pmAdapter.update(datas, true);
        fpmAdapter.update(datas, true);

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

    @Override
    public void onFailure(String errorMsg) {

    }
}

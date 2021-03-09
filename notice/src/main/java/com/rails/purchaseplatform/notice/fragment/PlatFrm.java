package com.rails.purchaseplatform.notice.fragment;

import com.bumptech.glide.Glide;
import com.rails.lib_data.bean.NoticeParentBean;
import com.rails.lib_data.contract.NoticeContract;
import com.rails.lib_data.contract.NoticePresenterImpl;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.systembar.StatusBarUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.notice.R;
import com.rails.purchaseplatform.notice.adapter.AuctionAdapter;
import com.rails.purchaseplatform.notice.adapter.NoticeTypeAdapter;
import com.rails.purchaseplatform.notice.adapter.bean.ResBean;
import com.rails.purchaseplatform.notice.databinding.FrmPlatBinding;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 平台首页
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/2
 */
public class PlatFrm extends LazyFragment<FrmPlatBinding> implements NoticeContract.NoticeView {

    private AuctionAdapter zbAdapter, fzbAdapter, pmAdapter, fpmAdapter;
    private NoticeTypeAdapter typeAdapter;
    private NoticeContract.NoticePresenter presenter;


    @Override
    protected void loadData() {

        String url = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1251675307,2817095624&fm=26&gp=0.jpg";
        Glide.with(this).load(url).into(binding.imgBg);


        typeAdapter = new NoticeTypeAdapter(getActivity());
        binding.typeRecycler.setLayoutManager(BaseRecyclerView.GRID, RecyclerView.VERTICAL, false, 5);
        binding.typeRecycler.addItemDecoration(new SpaceDecoration(getActivity(), ScreenSizeUtil.dp2px(getActivity(), 15), R.color.white));
        binding.typeRecycler.setAdapter(typeAdapter);

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

        setTypeData();

    }

    @Override
    protected void loadPreVisitData() {
        StatusBarUtil.StatusBarMode(getActivity(), R.color.trans);
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


    private void setTypeData() {
        ArrayList<ResBean> resBeans = new ArrayList<>();
        String[] names = getResources().getStringArray(R.array.notice_type_name);
        int[] reses = new int[]{R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};

        ResBean resBean;
        for (int i = 0; i < names.length; i++) {
            resBean = new ResBean(names[i], reses[i], "");
            resBeans.add(resBean);
        }
        typeAdapter.update(resBeans, true);
    }

}

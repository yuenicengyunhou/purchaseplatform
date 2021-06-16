package com.rails.purchaseplatform.order.fragment;


import android.view.View;

import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.AuthorBean;
import com.rails.lib_data.bean.BuyerBean;
import com.rails.lib_data.bean.OrderFilterBean;
import com.rails.lib_data.bean.OrderInfoBean;
import com.rails.lib_data.bean.UserStatisticsBean;
import com.rails.lib_data.bean.orderdetails.DeliveredFile;
import com.rails.lib_data.contract.OrderContract;
import com.rails.lib_data.contract.OrderPresenterImpl;
import com.rails.lib_data.contract.UserToolContract;
import com.rails.lib_data.contract.UserToolPresenterImpl;
import com.rails.purchaseplatform.common.base.LazyFragment;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.EmptyView;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.utils.NetWorkUtil;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.adapter.order.OrderParentAdapter;
import com.rails.purchaseplatform.order.databinding.FragmentOrderBinding;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import static com.rails.purchaseplatform.framwork.http.faction.ExceptionEngine.CONNECT_ERROR;

/**
 * 采购单列表
 */
public class OrderFragment extends LazyFragment<FragmentOrderBinding> implements OrderContract.OrderView, UserToolContract.UserToolView {

    private final int DEF_PAGE = 1;
    private int page = DEF_PAGE;
    private OrderParentAdapter mAdapter;
    private OrderContract.OrderPresenter presenter;
    private UserToolContract.UserToolPresenter toolPresenter;

    private final int status;// 区分我的/全部
//    private String statusCode;//区分采购单状态

    private String squence = "purchaseNo";//默认采购单编号搜索
    private int searchType = 0;
    private String searchContent = "";
    private OrderFilterBean filterBean;

    private OrderFragment(int status, String statusCode, OrderFilterBean bean) {
        this.status = status;
        if (null != statusCode) {
            this.filterBean = bean;
        }
    }

    public static OrderFragment getInstance(int status, String statusCode, OrderFilterBean bean) {
        return new OrderFragment(status, statusCode, bean);

    }

    @Override
    protected void loadData() {

        setNetView(binding.netError);

        //权限请求
        toolPresenter = new UserToolPresenterImpl(getActivity(), this);
        toolPresenter.queryAuthor();

        presenter = new OrderPresenterImpl(getActivity(), this);
        mAdapter = new OrderParentAdapter(getActivity());
        binding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
        binding.recycler.addItemDecoration(new SpaceDecoration(getActivity(), 10, R.color.line_gray));
        binding.empty.setDescEmpty(R.string.order_empty).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        binding.recycler.setAdapter(mAdapter);
        binding.recycler.setEmptyView(binding.empty);

        onRefresh();
    }

    @Override
    protected void loadPreVisitData() {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    /**
     * 刷新效果
     */
    private void onRefresh() {
        binding.swipe.setOnRefreshListener(refreshLayout -> {
            binding.swipe.finishRefresh();
            if (null != toolPresenter) {
                toolPresenter.queryAuthor();
            }
            page = DEF_PAGE;
            notifyData(true, page, searchType, searchContent, filterBean);
        });

        binding.swipe.setOnLoadMoreListener(refreshLayout -> {
            page++;
            notifyData(false, page, searchType, searchContent, filterBean);
        });

        notifyData(true, page, searchType, searchContent, filterBean);
    }


    /**
     * 请求推荐商品列表
     * <p>
     * param isDialog
     * param page
     */
    private void notifyData(boolean isDialog, int page, int searchType, String searchContent, OrderFilterBean filterBean) {
        int queryType = status == 0 ? 1 : 0;
        squence = getQuestSquence(searchType);
        presenter.getOrder(isDialog, page, queryType, squence, searchContent, filterBean);
    }

    /**
     * Search type, default 0.
     * 0 - 采购单号
     * 1 - 采购人用户名
     * 2 - 供应商名称
     */
    public void notifyFragment(int searchType, String searchContent, OrderFilterBean filterBean) {
        page = DEF_PAGE;
        this.searchType = searchType;
        this.searchContent = searchContent;
        this.filterBean = filterBean;
        notifyData(true, page, searchType, searchContent, filterBean);
    }

    private String getQuestSquence(int searchType) {
        if (searchType == 0) {
            return "purchaseNo";
        } else if (searchType == 1) {
            return "buyerAccountId";
        } else if (searchType == 2) {
            return "supplierOrgId";
        } else if (searchType == 3) {
            return "orderNo";
        } else if (searchType == 4) {
            return "shortCode";
        } else if (searchType == 5) {
            return "skuId";
        } else if (searchType == 6) {
            return "receiverName";
        } else if (searchType == 7) {
            return "mobile";
        } else if (searchType == 8) {
            return "brandId";
        } else {
            return "demandNo";
        }
    }

    @Override
    public void getOrder(ArrayList<OrderInfoBean> orderBeans, boolean firstPage, int totalCount) {
        binding.swipe.finishLoadMore();

        if (firstPage) {
            mAdapter.update(orderBeans, true);
        } else {
            if (mAdapter.getItemCount() < totalCount) {
                mAdapter.update(orderBeans, false);
            }
        }
    }

    @Override
    public void loadConditionNameList(ArrayList<BuyerBean> list) {

    }


    /**
     * 设置重试
     * <p>
     * param netView
     */
    private void setNetView(EmptyView netView) {
        if (netView != null) {
            netView.setImgEmpty(R.drawable.ic_net_error).setContentEmpty("无法连接到网络").setMarginTop(100)
                    .setBtnEmpty("刷新重试")
                    .setListener(v -> {
                        if (NetWorkUtil.isWifiEnabled(getActivity())) {
                            binding.netError.setVisibility(View.GONE);
                            reNetLoad();
                        } else {
                            binding.netError.setVisibility(View.VISIBLE);
                        }
                    });
        }
    }

    private void reNetLoad() {
        toolPresenter.getUserStatictics();

        page = DEF_PAGE;
        onRefresh();
    }

    @Override
    public void getUserStatictics(UserStatisticsBean bean) {

    }

    @Override
    public void getUserInfoStatictics(UserStatisticsBean bean) {

    }

    @Override
    public void checkPermissions(UserStatisticsBean bean) {

    }

    @Override
    public void getAuthor(AuthorBean authorBean) {
        boolean detail = PrefrenceUtil.getInstance(getActivity()).getBoolean(ConShare.BUTTON_DETAIL, false);
        if (null != mAdapter) {
            mAdapter.setDetail(detail);
        }
    }

    @Override
    public void loadDeliveredFileList(ArrayList<DeliveredFile> list) {

    }

    @Override
    public void onError(ErrorBean errorBean) {
        super.onError(errorBean);
        if (errorBean.getCode().equals(CONNECT_ERROR)) {
            binding.netError.setVisibility(View.VISIBLE);
        }
    }

}
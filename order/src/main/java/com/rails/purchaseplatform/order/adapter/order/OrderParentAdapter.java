package com.rails.purchaseplatform.order.adapter.order;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.OrderBean;
import com.rails.lib_data.bean.OrderParentBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.LrLableLayout;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.adapter.BaseRecycleAdapter;
import com.rails.purchaseplatform.framwork.utils.DecimalUtil;
import com.rails.purchaseplatform.order.R;

import java.util.ArrayList;

/**
 * 采购单列表
 */
public class OrderParentAdapter extends BaseRecycleAdapter<OrderParentBean, OrderParentAdapter.ItemHolder> {


    public OrderParentAdapter(Context context) {
        super(context);
        mContext = context;
    }


    @NonNull
    @Override
    public OrderParentAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(layoutInflater.inflate(R.layout.item_order_parent, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

        OrderParentBean parentBean = mDataSource.get(position);
        Resources res = mContext.getResources();
        holder.lrCode.setKey(String.format(res.getString(R.string.order_buy_code), parentBean.getCode()));
        holder.lrTime.setKey(String.format(res.getString(R.string.order_buy_code), parentBean.getTime()));
        holder.lrSupplier.setKey(String.format(res.getString(R.string.order_buy_code), parentBean.getMen()));
        holder.lrCompany.setKey(String.format(res.getString(R.string.order_buy_code), parentBean.getPurchars()));
        holder.tvPrice.setText(DecimalUtil.formatStrSize("¥", parentBean.getTotalPrice(), "", 16));

        ArrayList<OrderBean> orderItemBeans = (ArrayList<OrderBean>) parentBean.getOrder();
        OrderRecyclerAdapter adapter = new OrderRecyclerAdapter(mContext);
        holder.recycler.setAdapter(adapter);
        adapter.update(orderItemBeans, true);

        holder.lrCode.setOnClickListener(v -> {
            ARouter.getInstance()
                    .build(ConRoute.WEB.WEB_ORDER_DETAIL)
                    .withString("url", ConRoute.WEB_URL.ORDER_DETAIL)
                    .navigation();
        });
    }


    class ItemHolder extends RecyclerView.ViewHolder {

        private BaseRecyclerView recycler;
        private LrLableLayout lrCode;
        private LrLableLayout lrTime;
        private LrLableLayout lrSupplier;
        private LrLableLayout lrCompany;
        private TextView tvPrice;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            recycler = itemView.findViewById(R.id.recycler);
            lrCode = itemView.findViewById(R.id.lr_code);
            lrTime = itemView.findViewById(R.id.lr_time);
            lrSupplier = itemView.findViewById(R.id.lr_supplier);
            lrCompany = itemView.findViewById(R.id.lr_company);
            tvPrice = itemView.findViewById(R.id.tv_price);

            recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 1);
            recycler.addItemDecoration(new SpaceDecoration(mContext, 1, R.color.line_gray));

        }
    }
}

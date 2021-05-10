package com.rails.purchaseplatform.order.adapter.order;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rails.lib_data.bean.OrderInfoBean;
import com.rails.lib_data.bean.SubOrderInfoBean;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.common.widget.LrLableLayout;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.adapter.BaseRecycleAdapter;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.widget.Title4OrderRecyclerItem;

import java.util.ArrayList;

/**
 * 采购单列表
 */
public class OrderParentAdapter extends BaseRecycleAdapter<OrderInfoBean, OrderParentAdapter.ItemHolder> {


    private final ClipboardManager clipboardManager;

    public OrderParentAdapter(Context context) {
        super(context);
        mContext = context;
        clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
    }


    @NonNull
    @Override
    public OrderParentAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(layoutInflater.inflate(R.layout.item_order_parent, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

        OrderInfoBean parentBean = mDataSource.get(position);
        String orderNo = parentBean.getOrderNo();

//        Resources res = mContext.getResources();
//        holder.lrCode.setKey(String.format(res.getString(R.string.order_buy_code), parentBean.getCode()));
//        holder.lrTime.setKey(String.format(res.getString(R.string.order_buy_code), parentBean.getTime()));
//        holder.lrSupplier.setKey(String.format(res.getString(R.string.order_buy_code), parentBean.getMen()));
//        holder.lrCompany.setKey(String.format(res.getString(R.string.order_buy_code), parentBean.getPurchars()));
        holder.tvPrice.setText(parentBean.getTotalPrice());
//        String orderStatusView = parentBean.getOrderStatusView();
        String orderNoStr = parentBean.getOrderNoStr();
        String orderTime = parentBean.getOrderTime();
        String provider = parentBean.getShopName();
        String buyer = parentBean.getBuyerName();
        String delayTime = parentBean.getDelayReceiveTime() == null ? "" : parentBean.getDelayReceiveTime();
        holder.title.setText(orderNoStr, orderTime, provider, buyer, delayTime);
        holder.title.setOnClickListener(v -> ARouter.getInstance()
                .build(ConRoute.WEB.WEB_PURCHASE_DETAIL)
                .withString("url", ConRoute.WEB_URL.PURCHASE_DETAIL)
                .withString("orderNo",orderNo)
                .navigation());

        // 跳转到Android原生的 采购单详情页
//        holder.title.setOnClickListener(v -> {
//            ARouter.getInstance().build(ConRoute.ORDER.ORDER_DETAILS).navigation();
//        });


        ArrayList<SubOrderInfoBean> subOrderInfo = parentBean.getSubOrderInfo();
        OrderRecyclerAdapter adapter = new OrderRecyclerAdapter(mContext);
        holder.recycler.setAdapter(adapter);
        adapter.update(subOrderInfo, true);

        holder.lrCode.setOnClickListener(v -> ARouter.getInstance()
                .build(ConRoute.WEB.WEB_ORDER_DETAIL)
                .withString("url", ConRoute.WEB_URL.ORDER_DETAIL)
                .navigation());

        holder.title.setOnLongClickListener(v -> {
            ClipData clipData = ClipData.newPlainText("text", orderNoStr);
            clipboardManager.setPrimaryClip(clipData);
            ToastUtil.showCenter(mContext,"已复制采购单号");
            return true;
        });
    }


    class ItemHolder extends RecyclerView.ViewHolder {

        private BaseRecyclerView recycler;
        private Title4OrderRecyclerItem title;
        private LrLableLayout lrCode;
        private LrLableLayout lrTime;
        private LrLableLayout lrSupplier;
        private LrLableLayout lrCompany;
        private TextView tvPrice;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            recycler = itemView.findViewById(R.id.recycler);
            title = itemView.findViewById(R.id.t4oci_title);
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

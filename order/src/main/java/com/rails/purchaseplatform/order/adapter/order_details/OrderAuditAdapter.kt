package com.rails.purchaseplatform.order.adapter.order_details

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rails.lib_data.bean.OrderAuditBean
import com.rails.purchaseplatform.framwork.adapter.BaseRecycleAdapter
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter
import com.rails.purchaseplatform.order.R
import com.rails.purchaseplatform.order.databinding.ItemOrderDetailAuditBinding

class OrderAuditAdapter(context: Context):BaseRecyclerAdapter<OrderAuditBean,ItemOrderDetailAuditBinding>(context) {
    override fun getContentID(): Int =R.layout.item_order_detail_audit

    override fun onBindItem(binding: ItemOrderDetailAuditBinding?, t: OrderAuditBean?, position: Int) {
        if (null != binding) {
            binding.audit=t
        }
    }


}
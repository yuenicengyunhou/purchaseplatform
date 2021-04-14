package com.rails.purchaseplatform.order.adapter.order_details

import android.content.Context
import com.rails.lib_data.bean.OrderCreatedBean
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter
import com.rails.purchaseplatform.order.R
import com.rails.purchaseplatform.order.databinding.ItemOrderDetailCreatedBinding

class OrderCreatedAdapter(context: Context) :BaseRecyclerAdapter<OrderCreatedBean,ItemOrderDetailCreatedBinding>(context) {
    override fun getContentID(): Int = R.layout.item_order_detail_created

    override fun onBindItem(binding: ItemOrderDetailCreatedBinding?, t: OrderCreatedBean?, position: Int) {
        if (null != binding) {
            binding.order=t
        }
    }


}
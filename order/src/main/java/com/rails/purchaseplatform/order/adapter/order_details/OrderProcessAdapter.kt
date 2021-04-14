package com.rails.purchaseplatform.order.adapter.order_details

import android.content.Context
import com.rails.lib_data.bean.OrderProcessBean
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter
import com.rails.purchaseplatform.order.R
import com.rails.purchaseplatform.order.databinding.ItemOrderDetailProcessBinding

class OrderProcessAdapter (context: Context): BaseRecyclerAdapter<OrderProcessBean,ItemOrderDetailProcessBinding>(context){
    override fun getContentID(): Int =R.layout.item_order_detail_process

    override fun onBindItem(binding: ItemOrderDetailProcessBinding?, t: OrderProcessBean?, position: Int) {
        if (binding != null) {
            binding.process=t
        }
    }

}
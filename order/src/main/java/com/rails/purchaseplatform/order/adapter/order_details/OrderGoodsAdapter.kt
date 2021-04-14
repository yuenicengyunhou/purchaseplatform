package com.rails.purchaseplatform.order.adapter.order_details

import android.content.Context
import com.rails.lib_data.bean.ProductBean
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter
import com.rails.purchaseplatform.order.R
import com.rails.purchaseplatform.order.databinding.ItemOrderDetailGoodBinding

//地址  发票信息

class OrderGoodsAdapter(context: Context) : BaseRecyclerAdapter<ProductBean, ItemOrderDetailGoodBinding>(context) {
    override fun getContentID(): Int = R.layout.item_order_detail_good

    override fun onBindItem(binding: ItemOrderDetailGoodBinding?, t: ProductBean?, position: Int) {
        if (binding != null) {
            binding.product = t
        }
    }


}

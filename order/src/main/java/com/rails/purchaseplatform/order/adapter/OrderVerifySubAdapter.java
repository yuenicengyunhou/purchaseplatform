package com.rails.purchaseplatform.order.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;

import com.rails.lib_data.bean.CartShopProductBean;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.utils.DecimalUtil;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;
import com.rails.purchaseplatform.framwork.utils.SystemUtil;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemOrderVerifyProductBinding;

/**
 * 确认订单商品列表
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/27
 */
public class OrderVerifySubAdapter extends BaseRecyclerAdapter<CartShopProductBean, ItemOrderVerifyProductBinding> {
    public OrderVerifySubAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_order_verify_product;
    }

    @Override
    protected void onBindItem(ItemOrderVerifyProductBinding binding, CartShopProductBean bean, int position) {
        binding.setProduct(bean);

        String price = DecimalUtil.formatDouble(bean.getSellPrice());
        String unit = bean.getUnitName();

        int size;
        if (SystemUtil.isPad(mContext)) {
            size = ScreenSizeUtil.sp2px(10, mContext);
        } else {
            size = 18;
        }

        binding.tvPrice.setText(DecimalUtil.formatStrSize("¥ ", price, TextUtils.isEmpty(unit) ? "" : " /" + unit, 13));
    }
}

package com.rails.purchaseplatform.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.rails.purchaseplatform.R;
import com.rails.purchaseplatform.databinding.ItemAuctionBinding;
import com.rails.purchaseplatform.framwork.adapter.BaseRecycleAdapter;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 首页拍卖 ，临时内容
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/28
 */
public class AuctionAdapter extends BaseRecycleAdapter<String, ItemAuctionBinding> {


    public AuctionAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_auction;
    }

    @Override
    protected void onBindItem(ItemAuctionBinding binding, String s, int position) {
        RecyclerView.LayoutParams linearParams =
                (RecyclerView.LayoutParams) binding.getRoot().getLayoutParams();
        linearParams.width = (ScreenSizeUtil.getScreenWidth(mContext) << 1) / 3;

        binding.getRoot().setLayoutParams(linearParams);
    }

}

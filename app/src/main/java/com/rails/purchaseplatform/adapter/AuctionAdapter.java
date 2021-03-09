package com.rails.purchaseplatform.adapter;

import android.content.Context;

import com.rails.lib_data.bean.NoticeBean;
import com.rails.purchaseplatform.R;
import com.rails.purchaseplatform.databinding.ItemAuctionBinding;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.utils.ScreenSizeUtil;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 首页拍卖 ，临时内容
 *
 * @author： sk_comic@163.com
 * @date: 2021/1/28
 */
public class AuctionAdapter extends BaseRecyclerAdapter<NoticeBean, ItemAuctionBinding> {


    private int type;

    public AuctionAdapter(Context context, int type) {
        super(context);
        this.type = type;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_auction;
    }

    @Override
    protected void onBindItem(ItemAuctionBinding binding, NoticeBean noticeBean, int position) {
        if (type == 0) {
            RecyclerView.LayoutParams linearParams = (RecyclerView.LayoutParams) binding.getRoot().getLayoutParams();
            linearParams.width = (ScreenSizeUtil.getScreenWidth(mContext) << 1) / 3;
            binding.getRoot().setLayoutParams(linearParams);
        }
        binding.setAuction(noticeBean);
    }
}

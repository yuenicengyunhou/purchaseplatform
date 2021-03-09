package com.rails.purchaseplatform.notice.adapter;

import android.content.Context;

import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.notice.R;
import com.rails.purchaseplatform.notice.adapter.bean.ResBean;
import com.rails.purchaseplatform.notice.databinding.ItemNoticeTypeBinding;

/**
 * 公告首页种类adapter
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/5
 */
public class NoticeTypeAdapter extends BaseRecyclerAdapter<ResBean, ItemNoticeTypeBinding> {
    public NoticeTypeAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_notice_type;
    }

    @Override
    protected void onBindItem(ItemNoticeTypeBinding binding, ResBean resBean, int position) {
        binding.setType(resBean);
    }
}

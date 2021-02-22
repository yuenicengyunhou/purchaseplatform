package com.rails.purchaseplatform.adapter;

import android.content.Context;

import com.rails.purchaseplatform.R;
import com.rails.purchaseplatform.databinding.ItemMsgBinding;
import com.rails.purchaseplatform.framwork.adapter.BaseRecycleAdapter;

/**
 * 消息adapter
 * @author： sk_comic@163.com
 * @date: 2021/1/28
 */
public class MsgAdapter extends BaseRecycleAdapter<String , ItemMsgBinding> {


    public MsgAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_msg;
    }

    @Override
    protected void onBindItem(ItemMsgBinding binding, String s, int position) {

    }


}

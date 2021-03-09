package com.rails.purchaseplatform.msg.adapter;

import android.content.Context;

import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.msg.R;
import com.rails.purchaseplatform.msg.databinding.ItemMsgBinding;


/**
 * 消息adapter
 * @author： sk_comic@163.com
 * @date: 2021/1/28
 */
public class MsgAdapter extends BaseRecyclerAdapter<String , ItemMsgBinding> {


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

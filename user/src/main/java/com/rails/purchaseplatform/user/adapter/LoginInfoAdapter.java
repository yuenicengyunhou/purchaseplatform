package com.rails.purchaseplatform.user.adapter;

import android.content.Context;

import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.user.R;
import com.rails.purchaseplatform.user.databinding.ItemLoginInfoBinding;

import java.util.ArrayList;

public class LoginInfoAdapter extends BaseRecyclerAdapter<String, ItemLoginInfoBinding> {
    final private String TAG = LoginInfoAdapter.class.getSimpleName();

    private Context mContext;
    private FillAccountListener mFillAccountListener;
    private FillPhoneListener mFillPhoneListener;
    private ArrayList<String> data;
    private int type;

    public LoginInfoAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_login_info;
    }

    @Override
    protected void onBindItem(ItemLoginInfoBinding binding, String s, int position) {
        binding.tvInfo.setText(s);
        binding.tvInfo.setOnClickListener(v -> {
            // TODO: 2021/7/6 通知对应的fragment填充账号信息
            if (type == 0) mFillAccountListener.fillAccount(s);
            if (type == 1) mFillPhoneListener.fillPhone(s);
        });
    }

    public void updateData(ArrayList<String> data, int type, boolean isClear) {
        this.type = type;
        if (data == null) {
            return;
        }
        if (data.isEmpty()) {
            this.notifyDataSetChanged();
            return;
        }
        if (isClear) {
            this.mDataSource.clear();
        }
        this.mDataSource.addAll(data);
        this.notifyDataSetChanged();
    }

    public void setFillAccountListener(FillAccountListener fillAccountListener) {
        this.mFillAccountListener = fillAccountListener;
    }

    public interface FillAccountListener {
        public void fillAccount(String account);
    }

    public void setFillPhoneListener(FillPhoneListener fillPhoneListener) {
        this.mFillPhoneListener = fillPhoneListener;
    }

    public interface FillPhoneListener {
        public void fillPhone(String phone);
    }
}

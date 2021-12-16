package com.rails.purchaseplatform.market.ui.pop;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rails.lib_data.bean.AddressBean;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.market.adapter.PopChooseAddressAdapter;
import com.rails.purchaseplatform.market.databinding.PopCartAddressBinding;

import java.util.ArrayList;


/**
 * 购物车选择地址列表弹窗
 */
public class CartAddressPop extends BasePop<PopCartAddressBinding> {

    private Context mContext;
    private ArrayList<AddressBean> mAddresses;
    private String mAddressId;
    private PopChooseAddressAdapter mAdapter;
    private AddressListener listener;

    public CartAddressPop(Context context, ArrayList<AddressBean> addresses) {
        super();
        mContext = context;
        mAddresses = addresses;
    }


    public CartAddressPop(Context context, ArrayList<AddressBean> addresses, String addressId) {
        super();
        mContext = context;
        mAddresses = addresses;
        mAddressId = addressId;
    }

    public void setListener(AddressListener listener) {
        this.listener = listener;
    }

    @Override
    protected void initialize(Bundle bundle) {

        mAdapter = new PopChooseAddressAdapter(mContext, mAddressId);
        binding.brvAddress.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        binding.brvAddress.setAdapter(mAdapter);
        mAdapter.setListener((PositionListener<AddressBean>) (bean, position) -> {
            if (listener != null)
                listener.getAddrss(bean);
            dismiss();
        });

        mAdapter.update(mAddresses, true);


        binding.ibClose.setOnClickListener(v -> {

            dismiss();
        });

    }


    public interface AddressListener {

        void getAddrss(AddressBean bean);
    }
}

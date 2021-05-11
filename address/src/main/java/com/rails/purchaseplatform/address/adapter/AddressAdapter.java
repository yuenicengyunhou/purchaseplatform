package com.rails.purchaseplatform.address.adapter;

import android.content.Context;
import android.view.View;

import com.rails.lib_data.bean.AddressBean;
import com.rails.purchaseplatform.address.R;
import com.rails.purchaseplatform.address.databinding.ItemAddressBinding;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 地址列表adapter
 */
public class AddressAdapter extends BaseRecyclerAdapter<AddressBean, ItemAddressBinding> {

    private int selPosition = 0;

    public AddressAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_address;
    }

    @Override
    protected void onBindItem(ItemAddressBinding binding, AddressBean addressBean, int position) {
        binding.setAddress(addressBean);
        binding.imgEdit.setOnClickListener(v -> {
            if (positionListener != null)
                positionListener.onPosition(addressBean, position);
        });

        binding.getRoot().setOnClickListener(v -> {
            if (mulPositionListener != null) {
                mulPositionListener.onPosition(addressBean, position);
            }

        });
        if (addressBean.getReceivingAddress() == 1) {
            binding.btnRecivice.setVisibility(View.VISIBLE);
            binding.view.setVisibility(View.VISIBLE);
        } else {
            binding.view.setVisibility(View.GONE);
            binding.btnRecivice.setVisibility(View.GONE);
        }

        if (addressBean.getInvoiceAddress() == 1) {
            binding.btnBill.setVisibility(View.VISIBLE);
        } else {
            binding.btnBill.setVisibility(View.GONE);
        }

//        binding.tvDef.setVisibility(View.GONE);//不显示默认标志
    }


    public ArrayList<AddressBean> getResourse() {
        return mDataSource;
    }


    public void swapData(int fromPos, int toPos) {
        Collections.swap(mDataSource, fromPos, toPos);
        notifyItemMoved(fromPos, toPos);
        notifyDataSetChanged();
    }


    /**
     * 获取对象
     * <p>
     * param position
     * return
     */
    public AddressBean getBean(int position) {
        if (mDataSource.size() > position)
            return mDataSource.get(position);
        return null;
    }


    /**
     * 变更选中状态
     * <p>
     * param position
     */
    public void modifyDef(int position) {
        for (int i = 0; i < mDataSource.size(); i++) {
            mDataSource.get(i).setHasDefault(i == position?1:0);
        }
    }

}

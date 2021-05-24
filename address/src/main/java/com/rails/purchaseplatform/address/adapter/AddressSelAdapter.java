package com.rails.purchaseplatform.address.adapter;

import android.content.Context;
import android.view.View;

import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.AddressBean;
import com.rails.purchaseplatform.address.R;
import com.rails.purchaseplatform.address.databinding.ItemAddressSelBinding;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 地址列表adapter
 */
public class AddressSelAdapter extends BaseRecyclerAdapter<AddressBean, ItemAddressSelBinding> {

    private int selPosition = 0;

    public AddressSelAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getContentID() {
        return R.layout.item_address_sel;
    }

    @Override
    protected void onBindItem(ItemAddressSelBinding binding, AddressBean addressBean, int position) {
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
            mDataSource.get(i).setHasDefault(i == position ? 1 : 0);
        }
    }


    public void setSelPosition(AddressBean bean) {
        for (AddressBean addressBean : mDataSource) {
            if (bean.getId().equals(addressBean.getId())) {
                addressBean.isSel.set(true);
            } else {
                addressBean.isSel.set(false);
            }
        }
    }


}

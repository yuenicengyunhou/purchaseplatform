package com.rails.purchaseplatform.order.pop;

import android.os.Bundle;
import android.view.View;

import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.order.databinding.ItemOrderRecyclerTitleBinding;
import com.rails.purchaseplatform.order.databinding.PopVerifyGoodsBinding;

/**
 * 收货方式弹窗
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/29
 */
public class GoodsPop extends BasePop<PopVerifyGoodsBinding> {


    @Override
    protected void initialize(Bundle bundle) {

        onClick();
    }


    void onClick() {
        binding.btnClose.setOnClickListener(v -> {
            dismiss();
        });
    }
}

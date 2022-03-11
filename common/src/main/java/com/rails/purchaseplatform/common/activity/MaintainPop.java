package com.rails.purchaseplatform.common.activity;

import android.os.Bundle;

import com.rails.purchaseplatform.common.databinding.PopMaintainBinding;
import com.rails.purchaseplatform.framwork.base.BaseActManager;
import com.rails.purchaseplatform.framwork.base.BasePop;

/**
 * @author： sk_comic@163.com
 * @date: 2022/3/7
 */
public class MaintainPop extends BasePop<PopMaintainBinding> {
    @Override
    protected void initialize(Bundle bundle) {
        binding.btn.setOnClickListener(v -> {
            try{
                BaseActManager.getInstance().clear();
               dismiss();
                System.exit(0);
            }catch (Exception e){

            }
        });
    }
}

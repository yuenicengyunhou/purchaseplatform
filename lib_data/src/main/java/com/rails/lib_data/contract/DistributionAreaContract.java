package com.rails.lib_data.contract;

import com.rails.lib_data.AddressArea;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;


/**
 *
 */
public interface DistributionAreaContract {


    /**
     *
     */
    interface DistributionAreaView extends BaseView {

        /**
         * 获取区域Code成功的回调
         */
        void onGetDistributionAreaSuccess(ArrayList<AddressArea> list);
    }


    /**
     *
     */
    interface DistributionAreaPresenter {

        /**
         * 获取各个级别配送区域Code
         * <p>
         * 获取provinceCode   (parentCode = 0)
         * <p>
         * 获取cityCode       (parentCode = provinceCode)
         * <p>
         * 获取countryCode    (parentCode = cityCode)
         *
         * @param parentCode
         * @param isDialog
         */
        void getDistributionArea(String parentCode, boolean isDialog);
    }


}

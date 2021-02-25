package com.rails.lib_data.contract;

import com.rails.lib_data.bean.NoticeParentBean;
import com.rails.purchaseplatform.framwork.base.BaseView;

import java.util.ArrayList;

/**
 * 公告契约类
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/25
 */
public interface NoticeContract {

    interface NoticeView extends BaseView {

        /**
         * 获取公告，返回的数据以数字作为key，需要手动解析
         *
         * @param bean
         */
        void getNotice(ArrayList<NoticeParentBean> bean);
    }


    interface NoticePresenter {


        /**
         * 获取公告列表
         */
        void getNotice();

    }

}

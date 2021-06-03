package com.rails.lib_data.contract;

import com.rails.purchaseplatform.framwork.base.BaseView;

public interface PswContract {

    interface PassWordView extends BaseView {

        void getAllPassWord();

        void finishPage();

    }

    interface PswPresenter {

        void verifyToCommit(String oldPsw, String newPsw, String newPswAgain);

//        void commitPassword(String oldPsw, String newPsw, String newPswAgain);

    }

}

package com.rails.lib_data.model;


import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.service.UserService;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObservable;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.MD5Util;

import java.util.HashMap;

public class PasswordModel {

    public void updatePassword(String old, String newPsw, String newPswAgain, HttpRxObserver httpRxObserver) {
        String md5Old = MD5Util.MD5(MD5Util.MD5(old));
        String md5new = MD5Util.MD5(MD5Util.MD5(newPsw));
        String md5newAgain = MD5Util.MD5(MD5Util.MD5(newPswAgain));
        HashMap<String, Object> map = new HashMap<>();
        map.put("oldWord", md5Old);
        map.put("newWord", md5new);
        map.put("newWordAgain", md5newAgain);
        HttpRxObservable.getObservable(RetrofitUtil.getInstance()
                .create(UserService.class, 1).updatePassword(map))
                .subscribe(httpRxObserver);
    }


}

package com.rails.lib_data.contract;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.AddressDTO;
import com.rails.lib_data.bean.AddressListVO;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.http.RetrofitUtil;
import com.rails.lib_data.model.UserModel;
import com.rails.lib_data.service.UserService;
import com.rails.purchaseplatform.framwork.BaseApp;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.faction.HttpResult;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.framwork.utils.VerificationUtil;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * 地址管理presneter
 * <p>
 * author： sk_comic@163.com
 * date: 2021/3/27
 */
public class AddressPresenterImpl extends BasePresenter<AddressContract.AddressView> implements AddressContract.AddressPresenter {

    private final UserModel model;
    private  long accountId = 0;

    public AddressPresenterImpl(Activity mContext, AddressContract.AddressView addressView) {
        super(mContext, addressView);
        model = new UserModel();
        UserInfoBean bean = PrefrenceUtil.getInstance(BaseApp.getContext()).getBean(ConShare.USERINFO, UserInfoBean.class);
        String id = bean.getId();
        Log.e("WQ", "accountId==" + id);
        if (null == id) {
            ToastUtil.show(mContext, "用户信息错误");
            return;
        }
        accountId = Long.parseLong(id);
    }

    @Override
    public void getAddresses(Boolean isDialog) {
//        String token = PrefrenceUtil.getInstance(mContext).getString("token", "");

        model.queryAddressList(20, accountId, 2, 1, 10, new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(String response) {

            }
        });

//        Call<ResponseBody> call = RetrofitUtil.getInstance().create(UserService.class).getAddressList2(20, accountId, 2, 1, 10);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    Log.e("WQ", "==" + response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("WQ", "fail");
//            }
//        });

    }

    @Override
    public void setDefAddress(String id, int position) {
        baseView.getResult(1, position, "更改成功");
    }

    @Override
    public void delAddress(long id, int position) {
        model.deleteAddress(20, accountId, id, new HttpRxObserver<Boolean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(Boolean response) {
                if (response) {
                    baseView.deleteAddressSuccess(position);
                } else {
                    ToastUtil.show(mContext, "删除地址失败");
                }
            }
        });
        baseView.getResult(0, position, "删除成功");
    }

    @Override
    public void addAddress(String men, String phone, String area, String address, boolean isDef, int isReceiAddress, int isInvoiceAddress) {
        if (TextUtils.isEmpty(men)) {
            ToastUtil.show(mContext, "请输入收货人");
            return;
        }

        if (!VerificationUtil.isMobile(phone)) {
            ToastUtil.show(mContext, "请输入收货人手机号");
            return;
        }

        if (TextUtils.isEmpty(area)) {
            ToastUtil.show(mContext, "请输入省市区县、乡镇等");
            return;
        }

        if (TextUtils.isEmpty(address)) {
            ToastUtil.show(mContext, "请输入省街道、楼牌号等");
            return;
        }
        AddressDTO dto = new AddressDTO();
        dto.setReceiverName(men);
        dto.setMobile(phone);
        dto.setFullAddress(area + address);
        dto.setReceivingAddress(isReceiAddress);
        dto.setInvoiceAddress(isInvoiceAddress);
        String json = new Gson().toJson(dto);
        model.addAddress(20, 111, json, new HttpRxObserver<Boolean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(Boolean response) {
                if (response) {
                    ToastUtil.show(mContext, "成功");
                }
            }
        });

        baseView.getResult(0, 0, "添加成功");
    }
}

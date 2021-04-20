package com.rails.lib_data.contract;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.rails.lib_data.AddressArea;
import com.rails.lib_data.BaseAddress;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.AddressDTO;
import com.rails.lib_data.bean.AddressListVO;
import com.rails.lib_data.bean.BaseAddressResultVo;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.model.AddressModel;
import com.rails.purchaseplatform.framwork.BaseApp;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.framwork.utils.VerificationUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 地址管理presneter
 * <p>
 * author： sk_comic@163.com
 * date: 2021/3/27
 */
public class AddressPresenterImpl extends BasePresenter<AddressContract.AddressView> implements AddressContract.AddressPresenter {

    private final AddressModel model;
    private long accountId = 0;

    public AddressPresenterImpl(Activity mContext, AddressContract.AddressView addressView) {
        super(mContext, addressView);
        model = new AddressModel();
        UserInfoBean bean = PrefrenceUtil.getInstance(BaseApp.getContext()).getBean(ConShare.USERINFO, UserInfoBean.class);
        String id = bean.getId();
        if (null == id) {
            ToastUtil.show(mContext, "用户信息错误");
            return;
        }
        accountId = Long.parseLong(id);
    }

    @Override
    public void getAddresses(Boolean isDialog) {
        model.queryAddressList(20, accountId, 2, 1, 10, new HttpRxObserver<AddressListVO<AddressBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(AddressListVO<AddressBean> response) {
                ArrayList<AddressBean> list = response.getList();
                if (list != null) {
                    baseView.getAddresses(list);
                }
            }
        });
    }

    @Override
    public void setDefAddress(long id, int position, boolean isReceiveDef, boolean isInvoiceDef) {

        if (isReceiveDef) {
            setDefaultReceiveAddress(id, position);
        }
        if (isInvoiceDef) {
            setDefaultInvoiceAddress(id, position);
        }
    }

    private void setDefaultReceiveAddress(long id, int position) {
        model.updateDefaultReceiveAddress(20, accountId, id, new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(String response) {
                baseView.getResult(1, position, "设置默认成功");
            }
        });
    }

    private void setDefaultInvoiceAddress(long id, int position) {
        model.updateDefaultInvoiceAddress(20, accountId, id, new HttpRxObserver<String>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);

            }

            @Override
            protected void onSuccess(String response) {
                baseView.getResult(1, position, "设置默认成功");
            }
        });
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
                    baseView.getResult(0, position, "删除成功");
                } else {
                    ToastUtil.show(mContext, "删除地址失败");
                }
            }
        });

    }

    @Override
    public void addAddress(String men, String phone, String area, String address, boolean isDef, int isReceiAddress, int isInvoiceAddress, long addressId) {
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
        if (addressId == 0) {
            model.addAddress(20, 111, json, new HttpRxObserver<Boolean>() {
                @Override
                protected void onError(ErrorBean e) {
                    baseView.onError(e);
                }

                @Override
                protected void onSuccess(Boolean response) {
                    if (response) {
                        baseView.getResult(0, 0, "操作成功");
                    }
                }
            });
        } else {
            model.editAddress(20, accountId, addressId, json, new HttpRxObserver<Boolean>() {
                @Override
                protected void onError(ErrorBean e) {
                    baseView.onError(e);
                }

                @Override
                protected void onSuccess(Boolean response) {
                    if (response) {
                        baseView.getResult(0, 0, "操作成功");
                    }
                }
            });
        }


    }

    /**
     * 获取省市区
     * 获取一级 parentCode: 0
     * 获取二级 使用一级code
     */
    @Override
    public void getArea(long platformId, String parentCode) {
        model.getArea(platformId, parentCode, new HttpRxObserver<ArrayList<AddressArea>>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ArrayList<AddressArea> response) {
                model.saveAreaInfo(response);//缓存
                baseView.getArea(response);
            }
        });
    }
}

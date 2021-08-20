package com.rails.lib_data.contract;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.AddressDTO;
import com.rails.lib_data.bean.ListBeen;
import com.rails.lib_data.model.AddressModel;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.framwork.utils.VerificationUtil;

import java.util.ArrayList;

/**
 * 地址管理presneter
 * <p>
 * author： sk_comic@163.com
 * date: 2021/3/27
 */
public class AddressPresenterImpl extends BasePresenter<AddressContract.AddressView> implements AddressContract.AddressPresenter {

    private final AddressModel model;
//    private String accountId;
//    private String platformId;
    //    private  MessageModel messageModel;
//    private String accountType;

    public AddressPresenterImpl(Activity mContext, AddressContract.AddressView addressView) {
        super(mContext, addressView);
        model = new AddressModel();
//        messageModel = new MessageModel();
//        UserInfoBean bean = PrefrenceUtil.getInstance(BaseApp.getContext()).getBean(ConShare.USERINFO, UserInfoBean.class);
//        if (bean == null) {
//            ToastUtil.showCenter(mContext, "用户信息为空");
//            return;
//        }
//        accountId = bean.getId();
//        platformId = bean.getPlatformId();
//        accountType = bean.getAccountType();
    }

    /**
     * 获取地址列表
     * <p>
     * param isDialog 是否显示dialog    type-搜索类型 0收货人  1手机号码  2详细地址  -1无条件
     */
    @Override
    public void getAddresses(Boolean isDialog, int page, String type, String params) {
        if (isDialog) {
            baseView.showResDialog(R.string.loading);
        }
        model.queryAddressList(page, type, params, new HttpRxObserver<ListBeen<AddressBean>>() {
            @Override
            protected void onError(ErrorBean e) {
                if (isDialog) {
                    baseView.dismissDialog();
                }
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(ListBeen<AddressBean> response) {
                if (isDialog) {
                    baseView.dismissDialog();
                }
                boolean lastPage = response.isLastPage();
                int totalCount = response.getTotalCount();
                ArrayList<AddressBean> list = response.getList();
                baseView.getAddresses(list, lastPage, totalCount);
            }
        });
    }

    @Override
    public void getAddressInfo(String addressId) {
        baseView.showResDialog(R.string.loading);
        model.getAddressInfo(addressId, new HttpRxObserver<AddressBean>() {
            @Override
            protected void onError(ErrorBean e) {
                baseView.dismissDialog();
                baseView.onError(e);
            }

            @Override
            protected void onSuccess(AddressBean response) {
                baseView.dismissDialog();
                baseView.loadAddressInfo(response);
            }
        });
    }


    //    @Override
//    public void setDefAddress(long id, int position, boolean isReceiveDef, boolean isInvoiceDef) {
//
//        if (isReceiveDef) {
//            setDefaultReceiveAddress(id, position);
//        }
//        if (isInvoiceDef) {
//            setDefaultInvoiceAddress(id, position);
//        }
//    }

//    private void setDefaultReceiveAddress(long id, int position) {
//        model.updateDefaultReceiveAddress(20, accountId, id, new HttpRxObserver<String>() {
//            @Override
//            protected void onError(ErrorBean e) {
//                baseView.onError(e);
//            }
//
//            @Override
//            protected void onSuccess(String response) {
//                baseView.getResult(1, position, "设置默认成功");
//            }
//        });
//    }

//    private void setDefaultInvoiceAddress(long id, int position) {
//        model.updateDefaultInvoiceAddress(20, accountId, id, new HttpRxObserver<String>() {
//            @Override
//            protected void onError(ErrorBean e) {
//                baseView.onError(e);
//
//            }
//
//            @Override
//            protected void onSuccess(String response) {
//                baseView.getResult(1, position, "设置默认成功");
//            }
//        });
//    }

    @Override
    public void delAddress(String id, int position) {
        model.deleteAddress(id, new HttpRxObserver<Boolean>() {
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
    public void addAddress(String men, String phone, String area, String address, boolean isDef, int isReceiAddress, int isInvoiceAddress, String addressId, String provinceCode, String cityCode, String countryCode) {
        if (TextUtils.isEmpty(men)) {
            ToastUtil.showCenter(mContext, "请输入收货人");
            return;
        }

        if (!VerificationUtil.isMobile(phone)) {
            ToastUtil.showCenter(mContext, "请输入正确手机号码");
            return;
        }

        if (TextUtils.isEmpty(area)) {
            ToastUtil.showCenter(mContext, "请输入省市区县、乡镇等");
            return;
        }

        if (TextUtils.isEmpty(address)) {
            ToastUtil.showCenter(mContext, "请输入省街道、楼牌号等");
            return;
        }
        if ((isReceiAddress + isInvoiceAddress) < 1) {
            ToastUtil.showCenter(mContext, "至少选择一种地址类型");
            return;
        }

//        if (TextUtils.isEmpty(provinceCode)) {
//            ToastUtil.show(mContext, "省级地区码未获取，请尝试重选地址");
//            return;
//        }
//        if (TextUtils.isEmpty(cityCode)) {
//            ToastUtil.show(mContext, "市级地区码未获取，请尝试重选地址");
//            return;
//        }
        if (TextUtils.isEmpty(countryCode) || TextUtils.isEmpty(cityCode)) {
            ToastUtil.showCenter(mContext, "地区码未获取，请尝试重选地址");
            return;
        }
        baseView.showResDialog(R.string.saving);
        AddressDTO dto = new AddressDTO();
        dto.setReceiverName(men);//收货人姓名
        dto.setMobile(phone);//手机号
        dto.setFullAddress(area + address);//全部地址-所在地区+详细地址
        dto.setAttachAddress(address);//详细地址
        dto.setCityCode(cityCode);//市号
        dto.setProvinceCode(provinceCode);//省号
        dto.setCountryCode(countryCode);//县号
        dto.setReceivingAddress(String.valueOf(isReceiAddress));//是否收获地址 1是 0否
        dto.setInvoiceAddress(String.valueOf(isInvoiceAddress));//是否发票地址 1是 0否
        String json = new Gson().toJson(dto);
        if (null == addressId || TextUtils.isEmpty(addressId)) {
            model.addAddress(json, new HttpRxObserver<Boolean>() {
                @Override
                protected void onError(ErrorBean e) {
                    baseView.dismissDialog();
                    baseView.onError(e);
                }

                @Override
                protected void onSuccess(Boolean response) {
                    baseView.dismissDialog();
                    if (response) {
                        baseView.getResult(0, 0, "操作成功");
                    }
                }
            });
        } else {
            model.editAddress(addressId, json, new HttpRxObserver<Boolean>() {
                @Override
                protected void onError(ErrorBean e) {
                    baseView.dismissDialog();
                    baseView.onError(e);
                }

                @Override
                protected void onSuccess(Boolean response) {
                    baseView.dismissDialog();
                    if (response) {
                        baseView.getResult(0, 0, "操作成功");
                    }
                }
            });
        }
    }

}

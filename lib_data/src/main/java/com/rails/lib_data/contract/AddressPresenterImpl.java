package com.rails.lib_data.contract;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.rails.lib_data.AddressArea;
import com.rails.lib_data.ConShare;
import com.rails.lib_data.R;
import com.rails.lib_data.bean.AddressBean;
import com.rails.lib_data.bean.AddressDTO;
import com.rails.lib_data.bean.ListBeen;
import com.rails.lib_data.bean.UserInfoBean;
import com.rails.lib_data.bean.message.MessageRaw;
import com.rails.lib_data.model.AddressModel;
import com.rails.lib_data.model.MessageModel;
import com.rails.purchaseplatform.framwork.BaseApp;
import com.rails.purchaseplatform.framwork.base.BasePresenter;
import com.rails.purchaseplatform.framwork.bean.ErrorBean;
import com.rails.purchaseplatform.framwork.http.observer.HttpRxObserver;
import com.rails.purchaseplatform.framwork.utils.PrefrenceUtil;
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
    private final String accountId;
    private final String platformId;
    private final MessageModel messageModel;

    public AddressPresenterImpl(Activity mContext, AddressContract.AddressView addressView) {
        super(mContext, addressView);
        model = new AddressModel();
        messageModel = new MessageModel();
        UserInfoBean bean = PrefrenceUtil.getInstance(BaseApp.getContext()).getBean(ConShare.USERINFO, UserInfoBean.class);
        accountId = bean.getId();
        platformId = bean.getPlatformId();
    }

    @Override
    public void test() {
//        String[] arr = new String[]{"243071", "243057"};
//        String ids = new Gson().toJson(arr);
//        Log.e("WQ", "=====" + ids);
//        messageModel.deleteMessages("20", accountId, ids, new HttpRxObserver() {
//            @Override
//            protected void onError(ErrorBean e) {
//
//            }
//
//            @Override
//            protected void onSuccess(Object response) {
//                messageModel.getMessageList("20", accountId, 1, 10, new HttpRxObserver<MessageRaw>() {
//                    @Override
//                    protected void onError(ErrorBean e) {
//
//                    }
//
//                    @Override
//                    protected void onSuccess(MessageRaw response) {
//                        Log.e("WQ", "====" + response.getAllMessage());
//                    }
//                });
//            }
//        });


//        messageModel.getNotReadMessageCount("20", accountId, new HttpRxObserver() {
//            @Override
//            protected void onError(ErrorBean e) {
//
//            }
//
//            @Override
//            protected void onSuccess(Object response) {
//
//            }
//        });
    }

    @Override
    public void getAddresses(Boolean isDialog, int page) {
        if (isDialog) {
            baseView.showResDialog(R.string.loading);
        }
        model.queryAddressList(platformId, accountId, 2, page, new HttpRxObserver<ListBeen<AddressBean>>() {
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
    public void getAddressInfo(long addressId) {
        baseView.showResDialog(R.string.loading);
        model.getAddressInfo(platformId, accountId, addressId, new HttpRxObserver<AddressBean>() {
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
    public void delAddress(long id, int position) {
        model.deleteAddress(platformId, accountId, id, new HttpRxObserver<Boolean>() {
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
    public void addAddress(String men, String phone, String area, String address, boolean isDef, int isReceiAddress, int isInvoiceAddress, long addressId, String provinceCode, String cityCode, String countryCode) {
        if (TextUtils.isEmpty(men)) {
            ToastUtil.show(mContext, "请输入收货人");
            return;
        }

        if (!VerificationUtil.isMobile(phone)) {
            ToastUtil.show(mContext, "请输入正确手机号码");
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
        if ((isReceiAddress + isInvoiceAddress) < 1) {
            ToastUtil.showCenter(mContext, "至少选择一种地址类型");
            return;
        }
        if (TextUtils.isEmpty(provinceCode) || TextUtils.isEmpty(cityCode) || TextUtils.isEmpty(countryCode)) {
            ToastUtil.show(mContext, "获取地区码错误，请重选地址");
            return;
        }
        baseView.showResDialog(R.string.saving);
        AddressDTO dto = new AddressDTO();
        dto.setReceiverName(men);
        dto.setMobile(phone);
        dto.setFullAddress(area + address);
        dto.setAttachAddress(address);
        dto.setCityCode(cityCode);
        dto.setProvinceCode(provinceCode);
        dto.setCountryCode(countryCode);
        dto.setReceivingAddress(String.valueOf(isReceiAddress));
        dto.setInvoiceAddress(String.valueOf(isInvoiceAddress));
        String json = new Gson().toJson(dto);
        if (addressId == 0) {
            model.addAddress(platformId, accountId, json, new HttpRxObserver<Boolean>() {
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
            model.editAddress(platformId, accountId, addressId, json, new HttpRxObserver<Boolean>() {
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

    /**
     * 获取省市区
     * 获取一级 parentCode: 0
     * 获取二级 使用一级code
     */
    @Override
    public void getArea(String parentCode) {
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

package com.rails.lib_data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.JSONObject;

/**
 * 结果页面，web返回的json
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/7
 */
public class ResultWebBean implements Parcelable {


    /**
     * type : 1: 评价为 2
     * msg : 评价成功
     * btnleft : 查看采购单
     * btnright : 返回我的
     * urlleft : /web/purchase/detail
     * urlright : /rails/main
     */


    private Integer type;
    private String msg;
    private String btnleft;
    private String btnright;
    private String urlleft;
    private String urlright;
    private String leftParams;
    private String rightParams;
    private int code;
    private String orderNo;


    protected ResultWebBean(Parcel in) {
        if (in.readByte() == 0) {
            type = null;
        } else {
            type = in.readInt();
        }
        msg = in.readString();
        btnleft = in.readString();
        btnright = in.readString();
        urlleft = in.readString();
        urlright = in.readString();
        leftParams = in.readString();
        rightParams = in.readString();
        code = in.readInt();
        orderNo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (type == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(type);
        }
        dest.writeString(msg);
        dest.writeString(btnleft);
        dest.writeString(btnright);
        dest.writeString(urlleft);
        dest.writeString(urlright);
        dest.writeString(leftParams);
        dest.writeString(rightParams);
        dest.writeInt(code);
        dest.writeString(orderNo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResultWebBean> CREATOR = new Creator<ResultWebBean>() {
        @Override
        public ResultWebBean createFromParcel(Parcel in) {
            return new ResultWebBean(in);
        }

        @Override
        public ResultWebBean[] newArray(int size) {
            return new ResultWebBean[size];
        }
    };

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBtnleft() {
        return btnleft;
    }

    public void setBtnleft(String btnleft) {
        this.btnleft = btnleft;
    }

    public String getBtnright() {
        return btnright;
    }

    public void setBtnright(String btnright) {
        this.btnright = btnright;
    }

    public String getUrlleft() {
        return urlleft;
    }

    public void setUrlleft(String urlleft) {
        this.urlleft = urlleft;
    }

    public String getUrlright() {
        return urlright;
    }

    public void setUrlright(String urlright) {
        this.urlright = urlright;
    }

    public String getLeftParams() {
        return leftParams;
    }

    public void setLeftParams(String leftParams) {
        this.leftParams = leftParams;
    }

    public String getRightParams() {
        return rightParams;
    }

    public void setRightParams(String rightParams) {
        this.rightParams = rightParams;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}

package com.rails.lib_data.bean.message;

import com.rails.lib_data.bean.ListBeen;

import java.util.ArrayList;

public class MessageRaw {


    /**
     * allMessage : 10
     * notReadMessageCount : 1
     * alreadyReadMessageCount : 9
     * pageResult : {}
     */

    private int allMessage;
    private int notReadMessageCount;
    private int alreadyReadMessageCount;
    private ListBeen<MessageBean> pageResult;

    public ListBeen<MessageBean> getPageResult() {
        return pageResult;
    }

    public void setPageResult(ListBeen<MessageBean> pageResult) {
        this.pageResult = pageResult;
    }

    public int getAllMessage() {
        return allMessage;
    }

    public void setAllMessage(int allMessage) {
        this.allMessage = allMessage;
    }

    public int getNotReadMessageCount() {
        return notReadMessageCount;
    }

    public void setNotReadMessageCount(int notReadMessageCount) {
        this.notReadMessageCount = notReadMessageCount;
    }

    public int getAlreadyReadMessageCount() {
        return alreadyReadMessageCount;
    }

    public void setAlreadyReadMessageCount(int alreadyReadMessageCount) {
        this.alreadyReadMessageCount = alreadyReadMessageCount;
    }
}

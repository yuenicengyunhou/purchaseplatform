package com.rails.lib_data.bean;

/**
 * 用户中心统计数据
 *
 * @author： sk_comic@163.com
 * @date: 2021/4/20
 */
public class UserStatisticsBean {

    private int stayPayCount;
    private int stayDeliverCount;
    private int stayReceiveCount;

    private int failureCount;
    private int stayAuditCount;
    private int rejectCount;
    private int passedCount;

    public int getStayPayCount() {
        return stayPayCount;
    }

    public void setStayPayCount(int stayPayCount) {
        this.stayPayCount = stayPayCount;
    }

    public int getStayDeliverCount() {
        return stayDeliverCount;
    }

    public void setStayDeliverCount(int stayDeliverCount) {
        this.stayDeliverCount = stayDeliverCount;
    }

    public int getStayReceiveCount() {
        return stayReceiveCount;
    }

    public void setStayReceiveCount(int stayReceiveCount) {
        this.stayReceiveCount = stayReceiveCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public int getStayAuditCount() {
        return stayAuditCount;
    }

    public void setStayAuditCount(int stayAuditCount) {
        this.stayAuditCount = stayAuditCount;
    }

    public int getRejectCount() {
        return rejectCount;
    }

    public void setRejectCount(int rejectCount) {
        this.rejectCount = rejectCount;
    }

    public int getPassedCount() {
        return passedCount;
    }

    public void setPassedCount(int passedCount) {
        this.passedCount = passedCount;
    }
}

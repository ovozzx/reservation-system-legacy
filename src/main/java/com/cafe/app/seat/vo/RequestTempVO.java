package com.cafe.app.seat.vo;

import java.util.List;

public class RequestTempVO {

    private String reservationId;
    private String orderId;
    private String seatId;
    private List<String> seatIdList;
    private String reserveDate; 
    private Integer reserveTime; // 이용 시간, null 구분을 위하여 Integer
    private String startTime;
    private String endTime;
    private String status;
    private String createdTime;
    private String updatedTime;
    private String isUsed;

    public String getReservationId() {
        return reservationId;
    }
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
 
    public String getReserveDate() {
        return reserveDate;
    }
    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
    public String getUpdatedTime() {
        return updatedTime;
    }
    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }
    public String getIsUsed() {
        return isUsed;
    }
    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }
 
    public Integer getReserveTime() {
        return reserveTime;
    }
    public void setReserveTime(Integer reserveTime) {
        this.reserveTime = reserveTime;
    }
    public List<String> getSeatIdList() {
        return seatIdList;
    }
    public void setSeatIdList(List<String> seatIdList) {
        this.seatIdList = seatIdList;
    }
    public String getSeatId() {
        return seatId;
    }
    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }
    @Override
    public String toString() {
        return "RequestTempVO [reservationId=" + reservationId + ", orderId=" + orderId + ", seatId=" + seatId
                + ", seatIdList=" + seatIdList + ", reserveDate=" + reserveDate + ", reserveTime=" + reserveTime
                + ", startTime=" + startTime + ", endTime=" + endTime + ", status=" + status + ", createdTime="
                + createdTime + ", updatedTime=" + updatedTime + ", isUsed=" + isUsed + "]";
    }
   
    



}

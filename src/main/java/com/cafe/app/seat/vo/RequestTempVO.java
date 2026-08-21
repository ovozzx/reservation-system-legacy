package com.cafe.app.seat.vo;

import lombok.Data;

import java.util.List;

@Data
public class RequestTempVO {

    private String reservationId;
    private int orderId;
    private String seatId;
    private List<String> seatIdList;
    private String reserveDate;
    private Integer reserveTime;
    private List<Integer> reserveTimeList; // 이용 시간, null 구분을 위하여 Integer
    private String startTime;
    private String endTime;
    private String status;
    private String createdTime;
    private String updatedTime;
    private String isUsed;

}

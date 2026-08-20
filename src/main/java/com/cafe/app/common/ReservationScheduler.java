package com.cafe.app.common;

import com.cafe.app.seat.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservationScheduler {

    @Autowired
    private SeatService seatService;

    @Scheduled(cron = "0 * * * * *") // 1분마다
    public void expireSeats(){
        this.seatService.checkSeatStatus();
    }

    // TEMP 예약 좌석 비활성화 해제
    @Scheduled(cron = "0 * * * * *") // 1분마다
    public void expireTempSeats(){
        this.seatService.checkTempSeat();
    }
}

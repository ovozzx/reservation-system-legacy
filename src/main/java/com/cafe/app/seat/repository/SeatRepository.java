package com.cafe.app.seat.repository;

import java.util.List;
import java.util.Map;

import com.cafe.app.seat.vo.RequestTempVO;
import com.cafe.app.seat.vo.SeatVO;

public interface SeatRepository {

	List<SeatVO> selectAllSeats();

    int insertTempSeat(RequestTempVO requestTempVO);

    int updateSeatStatus(Map<String, String> param);

    SeatVO selectSeatForUpdate(String seatId);

    List<String> selectExpiredSeats();

    int updateReservationStatus(String seatId);
}

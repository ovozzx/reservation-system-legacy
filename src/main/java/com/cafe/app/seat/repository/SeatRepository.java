package com.cafe.app.seat.repository;

import java.util.List;

import com.cafe.app.seat.vo.RequestTempVO;
import com.cafe.app.seat.vo.SeatVO;

public interface SeatRepository {

	List<SeatVO> selectAllSeats();

    int insertTempSeat(RequestTempVO requestTempVO);

}

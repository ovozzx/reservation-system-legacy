package com.cafe.app.seat.service;

import java.util.List;

import com.cafe.app.seat.vo.RequestTempVO;
import com.cafe.app.seat.vo.SeatVO;

public interface SeatService {

	List<SeatVO> readAllSeats();

    boolean saveTempSeat(RequestTempVO requestTempVO);

}

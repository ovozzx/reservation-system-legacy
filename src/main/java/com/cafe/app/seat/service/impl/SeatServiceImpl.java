package com.cafe.app.seat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe.app.seat.repository.SeatRepository;
import com.cafe.app.seat.service.SeatService;
import com.cafe.app.seat.vo.SeatVO;

@Service
public class SeatServiceImpl implements SeatService{
	
	@Autowired
	private SeatRepository seatRepository;

	@Override
	public List<SeatVO> readAllSeats() {
		return this.seatRepository.selectAllSeats();
	}

}

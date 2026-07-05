package com.cafe.app.seat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe.app.order.repository.OrderRepository;
import com.cafe.app.seat.repository.SeatRepository;
import com.cafe.app.seat.service.SeatService;
import com.cafe.app.seat.vo.RequestTempVO;
import com.cafe.app.seat.vo.SeatVO;

@Service
public class SeatServiceImpl implements SeatService{
	
	@Autowired
	private SeatRepository seatRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<SeatVO> readAllSeats() {
		return this.seatRepository.selectAllSeats();
	}

	@Transactional
	@Override
	public boolean saveTempSeat(RequestTempVO requestTempVO) {
		// 주문 음료 수 조회
		int itemCnt = this.orderRepository.readItemCountById(requestTempVO);

		if(requestTempVO.getSeatIdList() == null){
			return false;
		}

		if(itemCnt != requestTempVO.getSeatIdList().size()){
			throw new IllegalArgumentException("음료 수만큼 좌석을 선택해 주세요.");
		}


	 	// TODO : 예약 시간 다르게 가능..?
		int totalResult = 0;

		for(String seatId : requestTempVO.getSeatIdList()){
			requestTempVO.setSeatId(seatId);
			// 좌석 상태 확인
			String status = this.seatRepository.readSeatStatus(seatId);
			if(!"AVAILABLE".equals(status)){
				throw new IllegalArgumentException(seatId + " 이미 예약 중인 좌석입니다.");
			}
			int result = this.seatRepository.insertTempSeat(requestTempVO);
			this.seatRepository.updateSeatStatus(seatId);
			totalResult += result;
		}

		return totalResult == itemCnt;
	}

}

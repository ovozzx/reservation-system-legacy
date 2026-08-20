package com.cafe.app.seat.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			throw new IllegalArgumentException("음료 수만큼 좌석을 선택해 주세요.");
		}

		if(itemCnt != requestTempVO.getSeatIdList().size()){
			throw new IllegalArgumentException("음료 수만큼 좌석을 선택해 주세요.");
		}


	 	// TODO : 예약 시간 다르게 가능..?
		int totalResult = 0;

		for(String seatId : requestTempVO.getSeatIdList()){
			requestTempVO.setSeatId(seatId);
			// 좌석 상태 확인
			SeatVO seat = this.seatRepository.selectSeatForUpdate(seatId);
			if(!"AVAILABLE".equals(seat.getStatus())){
				throw new IllegalArgumentException(seat.getSeatNumber() + " 이미 예약 중인 좌석입니다.");
			}
			int result = this.seatRepository.insertTempSeat(requestTempVO);
			Map<String, String> param = new HashMap<>();
			param.put("seatId", seatId);
			param.put("status", "IN_PROGRESS");
			this.seatRepository.updateSeatStatus(param);
			totalResult += result;
		}

		return totalResult == itemCnt;
	}

	@Override
	@Transactional
	public void checkSeatStatus() {
		// seat > status > AVAILABLE, IN_PROGRESS, RESERVED
		List<String> seatIdList = this.seatRepository.selectExpiredSeats();

		for(String seatId : seatIdList){
			// 예약 만료 처리
			this.seatRepository.updateReservationStatus(seatId);
			// 좌석 만료 처리
			Map<String, String> param = new HashMap<>();
			param.put("seatId", seatId);
			param.put("status", "AVAILABLE");
			this.seatRepository.updateSeatStatus(param);
		}
	}

	@Override
	@Transactional
	public void checkTempSeat() {
		// reservation > status > TEMP만 추출 >> 좌석 리스트 추출
		List<String> seatIdList = this.seatRepository.selectTempSeats();

		for(String seatId : seatIdList){
			// 예약 만료 처리
			this.seatRepository.updateReservationStatus(seatId);
			// 좌석 만료 처리 (비활성화 해제_
			Map<String, String> param = new HashMap<>();
			param.put("seatId", seatId);
			param.put("status", "AVAILABLE");
			this.seatRepository.updateSeatStatus(param);
		}


	}


}

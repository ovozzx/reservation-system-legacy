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

		if(itemCnt > requestTempVO.getSeatIdList().size()){
			throw new IllegalArgumentException("음료 수만큼 좌석을 선택해 주세요.");
		}

		int totalResult = 0;

		for(String  seatId : requestTempVO.getSeatIdList()){
			requestTempVO.setSeatId(seatId);
			int result = this.seatRepository.insertTempSeat(requestTempVO);
			totalResult += result;
		}

		return totalResult == itemCnt;

		// 조회된 음료 수만큼 좌석 선택 
		// 현재 오류 : 동일 좌석을 여러번 INSERT 하는 구조 
		/* 
		while(itemCnt > seatCnt){
			int result = this.seatRepository.insertTempSeat(requestTempVO);
			if(result != 1){ // 실패 시 즉시 종료 (무한 루프 방지)
				throw new IllegalArgumentException("좌석 임시 예약 실패");
			}
			seatCnt += result;
		}
		return itemCnt == seatCnt;
		*/
	}

}

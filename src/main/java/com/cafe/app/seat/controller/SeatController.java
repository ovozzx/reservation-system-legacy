package com.cafe.app.seat.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe.app.seat.service.SeatService;
import com.cafe.app.seat.vo.RequestTempVO;
import com.cafe.app.seat.vo.SeatVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class SeatController {
	
	@Autowired
	private SeatService seatService;
	
	@GetMapping("/seat")
	public String viewAllSeats(Model model){
		
		List<SeatVO> seatList = this.seatService.readAllSeats();
		List<SeatVO> leftSeatList = seatList.stream().filter(seat -> seat.getSeatNumber().startsWith("L")).toList();
		List<SeatVO> rightSeatList = seatList.stream().filter(seat -> seat.getSeatNumber().startsWith("R")).toList();
		List<SeatVO> windowSeatList = seatList.stream().filter(seat -> seat.getSeatNumber().startsWith("W")).toList();
		
		model.addAttribute("leftSeatList", leftSeatList);
		model.addAttribute("rightSeatList", rightSeatList);
		model.addAttribute("windowSeatList", windowSeatList);
	
		return "seat/list";
	}

	// 좌석 시간 설정  팝업 완료 클릭 시 : 좌석 예약 임시 테이블 저장 
	@PostMapping("/seat")
	public String reserveSeat(RequestTempVO requestTempVO, HttpSession session, Model model){ // 전달 정보 : 좌석 id, 시간

		System.out.println("====" + requestTempVO);
		/**
		 * 설계 (동시 접근 막으려면 DB)
		 * 프론트 : 좌석 선택마다 > 히든 input 추가
		 * 서버에서 리스트로 받음 > 좌석마다 INSERT
		 * 
		 */
		boolean complete = this.seatService.saveTempSeat(requestTempVO);
		
		if(complete == true){
			return "redirect:/order/summary/" + requestTempVO.getOrderId();
		}else{
			model.addAttribute("msg", "음료 수만큼 좌석을 선택해 주세요");
			return "seat/list";
		}
	}


}

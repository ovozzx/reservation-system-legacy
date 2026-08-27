package com.cafe.app.seat.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SeatController {
	
	@Autowired
	private SeatService seatService;
	
	@GetMapping("/seat")
	public String viewAllSeats(@RequestParam String orderId, @RequestParam int remainingSeconds, Model model, HttpSession session){
		String sessionOrderId = (String) session.getAttribute("orderId");
		if(!orderId.equals(sessionOrderId)){
			throw new IllegalArgumentException("잘못된 접근");
		}
		List<SeatVO> seatList = this.seatService.readAllSeats();
		List<SeatVO> leftSeatList = seatList.stream().filter(seat -> seat.getSeatNumber().startsWith("L")).toList();
		List<SeatVO> rightSeatList = seatList.stream().filter(seat -> seat.getSeatNumber().startsWith("R")).toList();
		List<SeatVO> windowSeatList = seatList.stream().filter(seat -> seat.getSeatNumber().startsWith("W")).toList();
		int availableSeatCnt = (int) seatList.stream().filter(seat -> "AVAILABLE".equals(seat.getStatus())).count();

		model.addAttribute("leftSeatList", leftSeatList);
		model.addAttribute("rightSeatList", rightSeatList);
		model.addAttribute("windowSeatList", windowSeatList);
		model.addAttribute("orderId", orderId);
		model.addAttribute("availableSeatCnt", availableSeatCnt);
		model.addAttribute("remainingSeconds", remainingSeconds);

		return "seat/list";
	}

	// 좌석 시간 설정  팝업 완료 클릭 시 : 좌석 예약 임시 테이블 저장 
	@PostMapping("/seat")
	public String reserveSeat(RequestTempVO requestTempVO, @RequestParam int remainingSeconds, HttpSession session, Model model, RedirectAttributes redirectAttributes){ // 전달 정보 : 좌석 id, 시간
		String sessionOrderId = (String) session.getAttribute("orderId");
		if(!requestTempVO.getOrderId().equals(sessionOrderId)){
			throw new IllegalArgumentException("잘못된 접근");
		}
		try{
			this.seatService.saveTempSeat(requestTempVO);
			return "redirect:/order/summary/" + requestTempVO.getOrderId();
		}catch(IllegalArgumentException e){
			redirectAttributes.addFlashAttribute("msg", e.getMessage()); // addAttribute → URL 쿼리스트링에 붙음, addFlashAttribute → URL에 안 보이고, 세션에 잠깐 저장됐다가 다음 요청에서 한 번 쓰고 사라짐
			redirectAttributes.addAttribute("orderId", requestTempVO.getOrderId());
			redirectAttributes.addAttribute("remainingSeconds", remainingSeconds);
			return "redirect:/seat";
		}

	}


}

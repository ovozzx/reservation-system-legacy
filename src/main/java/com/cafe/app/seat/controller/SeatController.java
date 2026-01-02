package com.cafe.app.seat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cafe.app.seat.service.SeatService;
import com.cafe.app.seat.vo.SeatVO;

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

}
